"""Build deterministic Three-series icons from the maintainer's original artwork.

Light/dark describe the usage mode. Preserve both supplied PNGs unchanged; derive
all shapes from the light source alpha, using the common black/white palette.
"""
from __future__ import annotations

import argparse
import math
from pathlib import Path

from PIL import Image, ImageDraw
import icon_geometry as geometry

ROOT = Path(__file__).resolve().parents[1]
RES = ROOT / "app/src/main/res"
SOURCE = ROOT / ".python/icons/three-color-picker-ic-launcher-light.png"
SIZE = 432
SUPERSAMPLE = 4
# Fractions of the scaled artwork width/height; the supplied near-square composition is centered.
OPTICAL_X = 0.0
OPTICAL_Y = 0.0
DAY = (0x27, 0x27, 0x27)
NIGHT = (0xD8, 0xD8, 0xD8)


def source_alpha():
    with Image.open(SOURCE) as image:
        alpha = image.convert("RGBA").getchannel("A")
    bounds = alpha.getbbox()
    if bounds is None:
        raise ValueError("Icon source has no visible artwork")
    return alpha.crop(bounds)


# Optical geometry v1; ratios are derived, not tuned independently by surface.
OPTICAL_SCALE = 1.0
UI_GLYPH, ADAPTIVE_GLYPH = geometry.normalized_ratios(source_alpha(), OPTICAL_SCALE)


def positioned_alpha(alpha, ratio):
    placed = geometry.positioned_alpha(alpha, ratio, OPTICAL_X, OPTICAL_Y)
    geometry.validate_circle(placed, SIZE * (33 / 108 if ratio == ADAPTIVE_GLYPH else .5))
    return placed


def glyph(alpha, color):
    image = Image.new("RGBA", alpha.size, (*color, 255))
    image.putalpha(alpha)
    return image


def legacy(foreground, color):
    size = SIZE * SUPERSAMPLE
    circle = Image.new("L", (size, size))
    ImageDraw.Draw(circle).ellipse((0, 0, size - 1, size - 1), fill=255)
    background = glyph(circle.resize((SIZE, SIZE), Image.Resampling.LANCZOS), color)
    return Image.alpha_composite(background, foreground)


def adaptive(foreground, background):
    return f'''<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/{background}" />
    <foreground android:drawable="@mipmap/{foreground}" />
    <monochrome android:drawable="@mipmap/ic_launcher_monochrome" />
</adaptive-icon>
'''.encode()


def encode_png(image):
    return geometry.encode_png(image)


def generated_files():
    alpha = source_alpha()
    ui = positioned_alpha(alpha, UI_GLYPH)
    small = positioned_alpha(alpha, ADAPTIVE_GLYPH)
    images = {
        "mipmap/ic_launcher.png": glyph(ui, DAY),
        "mipmap-night/ic_launcher.png": glyph(ui, NIGHT),
        "mipmap/ic_launcher_system.png": legacy(glyph(ui, NIGHT), (0x21, 0x21, 0x21)),
        "mipmap/ic_launcher_system_light.png": legacy(glyph(ui, DAY), (0xFA, 0xFA, 0xFA)),
        "mipmap/ic_launcher_system_foreground.png": glyph(small, NIGHT),
        "mipmap/ic_launcher_system_light_foreground.png": glyph(small, DAY),
        "mipmap/ic_launcher_monochrome.png": glyph(small, (0, 0, 0)),
    }
    images["mipmap/ic_plugin_center.png"] = images["mipmap/ic_launcher.png"]
    images["mipmap-night/ic_plugin_center.png"] = images["mipmap-night/ic_launcher.png"]
    outputs = {}
    for name, image in images.items():
        outputs[RES / name] = encode_png(image)
    for name, background in (("ic_launcher_system", "launcher_icon_background_dark"),
                             ("ic_launcher_system_light", "launcher_icon_background_light")):
        outputs[RES / f"mipmap-anydpi-v26/{name}.xml"] = adaptive(f"{name}_foreground", background)
    for directory, target in (("mipmap", "ic_launcher_system"), ("mipmap-notnight", "ic_launcher_system_light")):
        outputs[RES / directory / "ic_launcher_system_auto.xml"] = (
            '<?xml version="1.0" encoding="utf-8"?>\n'
            f'<bitmap xmlns:android="http://schemas.android.com/apk/res/android" android:src="@mipmap/{target}" />\n'
        ).encode()
    outputs[RES / "mipmap-anydpi-v26/ic_launcher_system_auto.xml"] = adaptive("ic_launcher_system_foreground", "launcher_icon_background_dark")
    outputs[RES / "mipmap-notnight-anydpi-v26/ic_launcher_system_auto.xml"] = adaptive("ic_launcher_system_light_foreground", "launcher_icon_background_light")
    outputs[RES / "values/launcher_icons.xml"] = b'''<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="launcher_icon_background_dark">#212121</color>
    <color name="launcher_icon_background_light">#FAFAFA</color>
</resources>
'''
    outputs[RES / "raw/keep_plugin_center_icon.xml"] = geometry.KEEP_RESOURCE
    return outputs


def obsolete_files():
    # Explicit former outputs only; no recursive deletion.
    names = [
        "mipmap/ic_launcher_system.xml", "mipmap/ic_launcher_system_light.xml",
        "mipmap/ic_launcher_transparent.xml", "mipmap-night/ic_launcher_transparent.xml",
        "mipmap-anydpi/ic_launcher.xml", "mipmap-anydpi/ic_launcher_round.xml",
        "mipmap-anydpi-v26/ic_launcher.xml", "mipmap-anydpi-v26/ic_launcher_round.xml",
        "mipmap-anydpi-v33/ic_launcher.xml", "mipmap-anydpi-v33/ic_launcher_round.xml",
        "drawable/launcher_glyph_dark.xml", "drawable/launcher_glyph_light.xml",
        "drawable/launcher_glyph_monochrome.xml", "drawable/ic_launcher_foreground.xml",
        "drawable/ic_launcher_monochrome.xml",
    ]
    return [RES / name for name in names if (RES / name).is_file()]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true")
    options = parser.parse_args()
    outputs = generated_files()
    stale = [path for path, data in outputs.items() if not path.is_file() or path.read_bytes() != data]
    obsolete = obsolete_files()
    if options.check:
        if stale or obsolete:
            raise SystemExit("Stale icon resources: " + ", ".join(str(p.relative_to(ROOT)) for p in stale + obsolete))
        print(f"Verified {len(outputs)} icon resources")
        return
    for path in obsolete:
        if not path.resolve().is_relative_to(RES.resolve()):
            raise ValueError("Icon output escaped the resource directory")
        path.unlink()
    for path, data in outputs.items():
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(data)
    print(f"Generated {len(outputs)} icon resources")


if __name__ == "__main__":
    main()
