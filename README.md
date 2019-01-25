# Modelisation_Projet
## Travail à effectuer
### Description
Le but est de réaliser un logiciel permettant de diminuer la taille d’une 
image en supprimant progressivement des colonnes “inutiles”, comme présenté en TD.
Le travail est en deux parties :
- La première partie consiste à produire un logiciel qui enlève 50 pixels par ligne, en enlevant colonne
par colonne.
- La seconde partie consiste à implémenter un parcours en profondeur sans utiliser de récursivité.
- Chacune des deux parties peut être accompagnée d’améliorations, décrites dans ce document.

## Utilisation du projet

Un executable .jar nommé projet.jar peut étre utiliser pour éxécuter le programme, 
celui-ci prend 3 paramétres : 
* Le fichier .pgm d'entré (doit ce trouver dans le même dossier que l'executable)
* Le nombre de ligne a enlever (entre 0 et la largeur max de l'image)
* Lee fichier de sortie de l'image générer par le programme
Si l'executable ne fonctionne pas, dans le src/modele/ se trouve la classe main qui elle 
aussi prend ces 3 paramétres.

## Modification éffectuées
* Mise en public de la classe Edge (utilisation externe au package)

## Image ajouté
Ajout de l'image venus2.ascii.pgm (Image source dans src/ressources/)

Ajout de l'image result.pgm (Image resultat dans src/ressources/)

## Sources
https://people.sc.fsu.edu/~jburkardt/data/pgma/pgma.html