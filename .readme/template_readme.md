<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="{{ icon_url }}" alt="Screen Color Picker icon" border="0" width="128" /></p>
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

******

### {{ h_features }}

{{ feature_list }}

******

### {{ h_standalone }}

{{ p_standalone }}:

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
plugin id / engine / category: {{ plugin_id }}
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

### {{ h_license }}

{{ p_license }}

[LICENSE]({{ license_url }})


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
