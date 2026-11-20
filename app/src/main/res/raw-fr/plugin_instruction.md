3-Color Picker est une pipette d'écran: un anneau cible déplaçable et une loupe en direct sélectionnent n'importe quel pixel de l'écran, dont la couleur et les coordonnées peuvent ensuite être copiées en HEX ou en RVB. Il s'utilise seul depuis le lanceur, ou comme outil "Sélecteur de couleur d'écran" dans le tiroir latéral d'AutoJs6; le contenu de l'écran est traité uniquement sur l'appareil.

### Prise en main

1. Ouvrez 3-Color Picker depuis le lanceur et touchez "Démarrer le sélecteur".
2. À l'invite, autorisez l'affichage par-dessus les autres applications, puis autorisez cette capture d'écran.
3. Passez à l'application à échantillonner, faites glisser l'anneau cible près du pixel, puis glissez sur le disque de la loupe pour l'ajuster finement.
4. Touchez la couleur ou les coordonnées sur l'anneau pour les copier; touchez le × en bas de l'anneau pour arrêter.

### Fonctionnalités principales

- **Pipette flottante**: L'anneau cible sert aux grands déplacements, tandis que la loupe affiche la grille de pixels agrandie, la couleur actuelle et les coordonnées; glissez sur le disque de la loupe pour avancer pixel par pixel.
- **Copie des couleurs et des coordonnées**: Touchez la couleur ou les coordonnées pour les copier, et appuyez longuement sur la couleur pour basculer entre HEX et RVB; avec "Copier uniquement la valeur numérique" activé, le # et les lettres R, G, B sont omis.
- **Tiroir latéral d'AutoJs6**: Une fois le plugin activé dans le "Centre des plugins" d'AutoJs6, le tiroir latéral affiche un interrupteur "Sélecteur de couleur d'écran" qui démarre la pipette quand il est activé et l'arrête quand il est désactivé.
- **Paramètres de la pipette**: Dans "Paramètres de la pipette" sous "Paramètres", réglez "Taille de la loupe", "Plage d'échantillonnage", "Vitesse de réglage fin" et "Afficher le quadrillage".
- **Apparence**: "Langue", "Mode sombre" et "Couleur du thème" sont par défaut sur "Suivre AutoJs6" et peuvent aussi être réglés séparément; "Icône du lanceur" propose plusieurs styles.
- **Notification d'activité**: Pendant que la pipette fonctionne, la notification "Le sélecteur de couleur d'écran est actif" s'affiche avec une action "Arrêter".
- **Historique et remerciements**: "Paramètres" donne aussi accès à "Historique des versions" et à "Référence de conception et remerciements".

### Prérequis

- Android 7.0 ou version ultérieure. L'utilisation dans le tiroir latéral d'AutoJs6 nécessite AutoJs6 6.8.0 ou version ultérieure; l'utilisation seule ne nécessite pas AutoJs6.
- L'autorisation d'affichage par-dessus les autres applications sert à la pipette flottante; Android demande de confirmer la capture d'écran à chaque démarrage.
- L'autorisation de notification sert à l'état d'activité et à l'action "Arrêter"; sans elle, la pipette fonctionne quand même.
- Il n'utilise pas le réseau, n'enregistre pas de captures d'écran et n'écrit dans le presse-papiers que lorsque vous touchez pour copier.

### Questions fréquentes

**Cas où l'affichage par-dessus les autres applications ou la capture d'écran est refusé**

Le démarrage est annulé sans risque et aucune superposition ne reste à l'écran. Touchez de nouveau "Démarrer le sélecteur" pour redemander l'autorisation.

**Raisons pour lesquelles "Sélecteur de couleur d'écran" n'apparaît pas dans le tiroir latéral d'AutoJs6**

Vérifiez que le plugin est activé dans le "Centre des plugins" d'AutoJs6, et autorisez-le si cela vous est demandé. Lorsque le plugin y est désactivé, l'entrée est masquée.

La conception s'inspire des interactions du sélecteur de couleur flottant de [MT Manager](https://mt.cc/) (bm.mt.plus) v2.26.9, dont nous remercions les développeurs; ce projet est indépendant, et ces remerciements n'impliquent ni affiliation, ni approbation, ni autorisation. Voir la documentation du projet: [Référence de conception](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md) et [Droits et retrait](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/RIGHTS_AND_TAKEDOWN.md).

Le détail des fonctionnalités et des autorisations se trouve sur la [page du projet](https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker).
