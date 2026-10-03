<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap-night/ic_launcher.png?raw=true" media="(prefers-color-scheme: dark)" />
      <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-three-color-picker-ic-launcher" border="0" width="128" />
    </picture>
  </p>
  <h1>3-Color Picker</h1>
  <p>Échantillonne les couleurs partout sur l'écran avec un sélecteur flottant tactile</p>
  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker?label=License"/></a>
  </p>
</div>

******

### Langues

Le README est disponible dans les langues suivantes:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-en.md)
- Français [fr] # actuel
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/.readme/README-ar.md)

******

### Introduction

3-Color Picker est un outil local de prélèvement de couleur conçu pour les écrans tactiles. Son sélecteur flottant associe un anneau cible déplaçable à une loupe en temps réel qui affiche une grille de pixels agrandie, la couleur actuelle et les coordonnées exactes, avec une interaction directe et claire.

Le même APK fonctionne seul depuis le lanceur ou est découvert par AutoJs6 comme troisième outil étendu du tiroir. Le mode autonome ne nécessite pas AutoJs6.

La version 2.0 utilise le nouvel identifiant io.github.supermonster003.autojs6.plugin.three.color.picker. Android l'installe comme une application distincte; l'ancienne peut rester installée et les réglages ne sont pas migrés automatiquement. Accordez à nouveau les autorisations et utilisez AutoJs6 versionCode 5316 ou ultérieur pour le mode plugin. Le libellé du tiroir AutoJs6 reste inchangé.

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

<picture>
  <source srcset="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-dark.png?raw=true" media="(prefers-color-scheme: dark)" />
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/images/home-light.png?raw=true" alt="3-Color Picker home" width="280" />
</picture>

Utilisez le sélecteur sans AutoJs6

Uniformiser les réglages autonomes: langue, mode nuit, couleur et icône. Suivre AutoJs6 par défaut avec repli système, surfaces neutres et contrôles adaptés. Appliquer les choix après confirmation, proposer un aperçu HEX/RGB et utiliser le mode adaptatif automatique par défaut tout en préservant les choix explicites lors des mises à jour.:

1. Ouvrez 3-Color Picker depuis le lanceur système.
2. Touchez Démarrer le sélecteur et suivez l'explication pour autoriser l'affichage sur les autres applications.
3. Autorisez la capture actuelle dans la demande du système Android.
4. Passez dans l'application à échantillonner, faites glisser l'anneau cible jusqu'au pixel souhaité, puis glissez sur le disque de la loupe pour affiner.
5. Touchez le texte de couleur ou de coordonnées sur la loupe pour le copier, appuyez longuement sur le texte de couleur pour changer de format, ou arrêtez le sélecteur à tout moment.

******

### Utilisation comme outil AutoJs6

Après installation AutoJs6 découvre l'application au moyen du contrat officiel du plugin et n'affiche son outil dans le tiroir que lorsque le plugin est activé dans le Centre de plugins:

1. Installez l'APK du plugin. Il n'est pas nécessaire d'ouvrir son entrée du lanceur.
2. Ouvrez le Centre de plugins d'AutoJs6 et vérifiez que 3-Color Picker est activé. Autorisez le plugin si demandé.
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
| Outil étendu AutoJs6 | AutoJs6 versionCode 5316 or later |

******

### Contrat du plugin

Les détails suivants concernent les développeurs de l'hôte et du plugin. Le service Binder et Wake Activity sont protégés par org.autojs.permission.PLUGIN, tandis que l'entrée du lanceur ne dépend pas de cette autorisation.

```text
application id: io.github.supermonster003.autojs6.plugin.three.color.picker
plugin id / INFO category: three-color-picker
engine / capture service category: screen-color-picker
variant: default
service action: org.autojs.plugin.SCREEN_COLOR_PICKER
wake action: org.autojs.plugin.action.WAKE
binder: org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
contract version: 1
minimum host versionCode: 5316
native libraries: none
```

******

### Historique des versions

#### v2.0.0

_2026/10/03_

- `Indication` La version 2.0 utilise le nouvel identifiant io.github.supermonster003.autojs6.plugin.three.color.picker. Android l'installe comme une application distincte; l'ancienne peut rester installée et les réglages ne sont pas migrés automatiquement. Accordez à nouveau les autorisations et utilisez AutoJs6 versionCode 5316 ou ultérieur pour le mode plugin. Le libellé du tiroir AutoJs6 reste inchangé
- `Amélioration` Screen Color Picker devient 3-Color Picker, avec un nouvel accueil, des icônes claires/sombres et quatre modes de lanceur
- `Amélioration` Référence à MT Manager (bm.mt.plus) v2.26.9, remerciements et procédure de traitement des demandes des titulaires de droits dans les paramètres et la documentation
- `Amélioration` Taille visuelle harmonisée des icônes du lanceur et du Centre de plugins, avec des fonds transparents et des motifs noirs, blancs ou gris neutres

#### v1.2.0

_2026/09/30_

- `Ajout` Uniformiser les réglages autonomes: langue, mode nuit, couleur et icône. Suivre AutoJs6 par défaut avec repli système, surfaces neutres et contrôles adaptés. Appliquer les choix après confirmation, proposer un aperçu HEX/RGB et utiliser le mode adaptatif automatique par défaut tout en préservant les choix explicites lors des mises à jour.

#### v1.1.3

_2026/09/19_

- `Correction` Avertissements de lecture SDK XML v4 avec AGP 9.1 et contrôles d'alignement natif des APK déclenchés par erreur lors de l'assemblage des tests unitaires JVM, avec les plugins de compilation partagés 1.8.3
- `Amélioration` Après compileSdk, targetSdk passe à 37 (Android 17) ; le comportement du plugin ne dépend pas de la nouvelle cible

[Voir le CHANGELOG complet](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/app/src/main/assets/doc/CHANGELOG-fr.md)

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

### Référence de conception et remerciements

La conception actuelle prend pour référence MT Manager (bm.mt.plus) v2.26.9, notamment les interactions de son sélecteur de couleur flottant. Nous remercions ses développeurs pour leur travail. Ce projet est indépendant; ces remerciements ne constituent ni affiliation, ni approbation, ni autorisation. Les titulaires de droits peuvent nous contacter via les Issues du projet. Nous examinerons les demandes et coopérerons, selon le cas, à la correction des attributions, au remplacement ou au retrait du contenu.

[MT Manager](https://mt.cc/) · [v2.26.9](https://mt.cc/releases/)

Documentation du projet: [Design reference](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) · [Rights and takedown cooperation](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md)

******

### Licence

Le code du projet est distribué sous Mozilla Public License 2.0.

[LICENSE](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/LICENSE)


[16 KB page alignment and build verification](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/16kb.md)
