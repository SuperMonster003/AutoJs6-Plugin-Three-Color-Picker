<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p><img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="Screen Color Picker icon" border="0" width="128" /></p>
  <p>Échantillonne les couleurs partout sur l'écran avec un sélecteur flottant tactile</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Langues

Le README est disponible dans les langues suivantes:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-en.md)
- Français [fr] # actuel
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/.readme/README-ar.md)

******

### Introduction

Screen Color Picker est un outil local de prélèvement de couleur conçu pour les écrans tactiles. Son sélecteur flottant associe un anneau cible déplaçable à une loupe en temps réel qui affiche une grille de pixels agrandie, la couleur actuelle et les coordonnées exactes, avec une interaction directe et claire.

Le même APK fonctionne seul depuis le lanceur ou est découvert par AutoJs6 comme troisième outil étendu du tiroir. Le mode autonome ne nécessite pas AutoJs6.

******

### Fonctions

- Deux entrées: utilisation autonome depuis le lanceur ou comme outil étendu AutoJs6
- Optimisé pour le tactile: faites glisser l'anneau cible pour les grands déplacements et glissez sur la loupe pour l'ajustement fin
- Données immédiates: la loupe affiche en temps réel la couleur échantillonnée et ses coordonnées
- Copie flexible: copiez la couleur en HEX ou RGB (valeur numérique seule possible) ou copiez les coordonnées
- Cycle de vie contrôlé: arrêtez depuis l'accueil, l'interface flottante, la notification ou l'interrupteur hôte
- Entièrement hors ligne: le contenu de l'écran n'est jamais envoyé et aucun historique de capture n'est conservé

******

### Utilisation autonome

Utilisez le sélecteur sans AutoJs6:

1. Ouvrez Screen Color Picker depuis le lanceur système.
2. Touchez Démarrer le sélecteur et suivez l'explication pour autoriser l'affichage sur les autres applications.
3. Autorisez la capture actuelle dans la demande du système Android.
4. Passez dans l'application à échantillonner, faites glisser l'anneau cible jusqu'au pixel souhaité, puis glissez sur le disque de la loupe pour affiner.
5. Touchez le texte de couleur ou de coordonnées sur la loupe pour le copier, appuyez longuement sur le texte de couleur pour changer de format, ou arrêtez le sélecteur à tout moment.

******

### Utilisation comme outil AutoJs6

Après installation AutoJs6 découvre l'application au moyen du contrat officiel du plugin et n'affiche son outil dans le tiroir que lorsque le plugin est activé dans le Centre de plugins:

1. Installez l'APK du plugin. Il n'est pas nécessaire d'ouvrir son entrée du lanceur.
2. Ouvrez le Centre de plugins d'AutoJs6 et vérifiez que Screen Color Picker est activé. Autorisez le plugin si demandé.
3. Ouvrez le tiroir AutoJs6. Screen Color Picker apparaît comme troisième outil étendu, avec son interrupteur d'exécution désactivé par défaut.
4. Acceptez la superposition et la capture d'écran lors de la première utilisation.
5. Désactivez l'interrupteur, utilisez l'interface flottante ou l'action de notification pour arrêter.
6. La désactivation du plugin dans le Centre de plugins masque l'entrée du tiroir. Réactivez le plugin pour la restaurer.

******

### Autorisations et confidentialité

Tout le traitement de l'écran reste local sur l'appareil et ne commence qu'après une action explicite de l'utilisateur.

- Capture d'écran: Android présente un écran de consentement pour chaque session
- Affichage sur les autres applications: utilisé uniquement pour le sélecteur flottant tactile
- Service au premier plan et notification: rendent une capture active visible et facile à arrêter
- Presse-papiers: écrit uniquement lorsque l'utilisateur touche une action de copie
- Réseau et stockage: aucune autorisation réseau ou de stockage partagé n'est demandée

Le refus de l'affichage superposé ou de la capture d'écran annule le démarrage en toute sécurité. Le refus des notifications affiche un avertissement, puis le démarrage continue.

******

### Compatibilité

Les modes autonome et plugin ont des exigences minimales différentes.

| Mode | Exigence minimale |
|---|---|
| Application autonome | Android 7.0 (API 24) or later |
| Outil étendu AutoJs6 | AutoJs6 versionCode 5278 or later |

******

### Contrat du plugin

Les détails suivants concernent les développeurs de l'hôte et du plugin. Le service Binder et Wake Activity sont protégés par org.autojs.permission.PLUGIN, tandis que l'entrée du lanceur ne dépend pas de cette autorisation.

```text
application id: io.github.supermonster003.autojs6.plugin.screencolorpicker
plugin id / engine / category: screen-color-picker
variant: default
service action: org.autojs.plugin.SCREEN_COLOR_PICKER
wake action: org.autojs.plugin.action.WAKE
binder: org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
contract version: 1
minimum host versionCode: 5278
native libraries: none
```

******

### Historique des versions

#### v1.1.2

_2026/09/16_

- `Correction` Fermer l'ancienne boîte de dialogue expliquant l'autorisation de superposition lors de la recréation de l'écran pour éviter les fenêtres résiduelles et les conflits de focus

#### v1.1.1

_2026/09/15_

- `Amélioration` compileSdk passe à 37 (Android 17) ; targetSdk reste à 36 jusqu'à la vérification du comportement dépendant de la cible

#### v1.1.0

_2026/09/13_

- `Ajout` Historique local accessible depuis l'interface, avec traductions et repli en anglais
- `Amélioration` Vérification de la signature complète, des APK attendus et de la reproductibilité de la documentation
- `Amélioration` Conserver une icône PNG de base issue de l'icône existante et la référencer dans la documentation

[Voir le CHANGELOG complet](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-fr.md)

******

### Construction

Utilisez le Gradle Wrapper inclus et JDK 21 pour exécuter les tests, construire les APK de débogage et d'instrumentation, puis lancer lint.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

Les README, instructions du plugin et changelog sont générés depuis des sources JSON. Après une modification exécutez les deux commandes suivants.

```powershell
py .python/generate_markdown.py
py .python/generate_markdown.py --check
```

******

### Licence

Le code du projet est distribué sous Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Screen-Color-Picker/blob/master/docs/16kb.md)
