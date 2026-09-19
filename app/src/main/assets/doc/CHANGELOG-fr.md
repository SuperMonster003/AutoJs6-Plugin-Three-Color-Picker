******

### Historique des versions

******

# v1.1.3

_2026/09/19_

- `Correction` Avertissements de lecture SDK XML v4 avec AGP 9.1 et contrôles d'alignement natif des APK déclenchés par erreur lors de l'assemblage des tests unitaires JVM, avec les plugins de compilation partagés 1.8.3
- `Amélioration` Après compileSdk, targetSdk passe à 37 (Android 17) ; le comportement du plugin ne dépend pas de la nouvelle cible

# v1.1.2

_2026/09/16_

- `Correction` Fermer l'ancienne boîte de dialogue expliquant l'autorisation de superposition lors de la recréation de l'écran pour éviter les fenêtres résiduelles et les conflits de focus

# v1.1.1

_2026/09/15_

- `Amélioration` compileSdk passe à 37 (Android 17) ; targetSdk reste à 36 jusqu'à la vérification du comportement dépendant de la cible

# v1.1.0

_2026/09/13_

- `Ajout` Historique local accessible depuis l'interface, avec traductions et repli en anglais
- `Amélioration` Vérification de la signature complète, des APK attendus et de la reproductibilité de la documentation
- `Amélioration` Conserver une icône PNG de base issue de l'icône existante et la référencer dans la documentation

# v1.0.1

_2026/09/11_

- `Correction` Éviter le plantage ThemeEnforcement en appliquant explicitement un thème Material aux boutons Material créés depuis le Context de service/application
- `Correction` N'afficher l'outil du tiroir AutoJs6 que lorsque le plugin est activé dans le Centre de plugins, puis le restaurer après réactivation
- `Amélioration` La vérification de compilation rejette les dépendances natives involontaires et produit un rapport JSON

# v1.0.0

_2026/09/01_

- `Ajout` Exécutez le même APK depuis le lanceur ou utilisez-le comme outil étendu Sélecteur de couleur d'écran dans AutoJs6
- `Ajout` Utilisez un sélecteur flottant tactile avec aperçu agrandi, coordonnées, HEX, RGB et HSL
- `Ajout` Utilisez l'interface Binder contract v1 avec getInfo, getState, getStartPendingIntent et stop
- `Ajout` Utilisez l'interface locale et la documentation en 10 langues avec un traitement des couleurs entièrement hors ligne
- `Amélioration` Gérez explicitement les autorisations de superposition, capture d'écran, service de premier plan et notification avec une action d'arrêt
- `Amélioration` Prenez en charge le protocole Wake d'AutoJs6 et la version hôte minimale 5278 avec CI et contrôle de dérive de la documentation
