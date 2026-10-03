<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="{{ repo_url }}/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="{{ icon_url }}" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>{{ synopsis }}</p>
  <p>
    <a href="{{ repo_url }}/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/{{ repo_slug }}?label=Release"/></a>
    <a href="{{ repo_url }}/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/{{ repo_slug }}?label=Issues"/></a>
    <a href="{{ license_url }}"><img alt="GitHub License" src="https://img.shields.io/github/license/{{ repo_slug }}?label=License"/></a>
  </p>
</div>

******

### {{ h_languages }}

{{ p_languages }}:

{{ language_links }}

******

### {{ h_introduction }}

{{ p_introduction }}

{{ p_dual_mode }}

{{ p_migration }}

******

### {{ h_features }}

{{ feature_list }}

******

### {{ h_standalone }}

<picture>
  <source srcset="{{ repo_url }}/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="{{ repo_url }}/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

{{ p_standalone }}

{{ p_launcher_modes }}:

{{ standalone_steps_list }}

******

### {{ h_host }}

{{ p_host }}:

{{ host_steps_list }}

******

### {{ h_privacy }}

{{ p_privacy }}

{{ privacy_list }}

{{ p_permission_note }}

******

### {{ h_compatibility }}

{{ p_compatibility }}

| {{ th_mode }} | {{ th_requirement }} |
|---|---|
| {{ td_standalone }} | Android {{ min_android_version }} (API {{ min_sdk }}) or later |
| {{ td_plugin }} | AutoJs6 versionCode {{ required_host_version }} or later |

******

### {{ h_contract }}

{{ p_contract }}

```text
application id: {{ application_id }}
plugin id / INFO category: {{ plugin_id }}
engine / capture service category: {{ plugin_engine }}
variant: {{ plugin_variant }}
service action: {{ service_action }}
wake action: {{ wake_action }}
binder: {{ binder_interface }}
contract version: {{ contract_version }}
minimum host versionCode: {{ required_host_version }}
native libraries: none
```

******

### {{ h_release_history }}

{{ latest_release }}

[{{ text_full_changelog }}]({{ repo_url }}/blob/master/app/src/main/assets/doc/CHANGELOG-{{ $code }}.md)

******

### {{ h_build }}

{{ p_build }}

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

{{ p_docs_generation }}

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### {{ h_design_reference }}

{{ p_design_reference }}

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

{{ text_design_documents }}: [Design reference]({{ repo_url }}/blob/master/docs/design-reference.md) · [Rights and takedown cooperation]({{ repo_url }}/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### {{ h_license }}

{{ p_license }}

[LICENSE]({{ license_url }})


[16 KB page alignment and build verification]({{ repo_url }}/blob/master/docs/16kb.md)
