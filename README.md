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

## Reponse aux questions:
1.  C'est un parcours en largeur sur lequel on a remplacer la file par une pile, 
ce changement ne sufit pas a produire un parcours en profondeur iteratif.

2.  Cest un parcours en largeur, cela ne correspond donc pas a un parcours en 
profondeur.

3.  On ne met le sommet a visité que a la sortie de celui-ci ce qui fait que l'on
peut rentrer dans le sommet plusieurs fois, ce qui fait exploser la pile, par exemple
un graph K100 entraine une pile > 1000 ; ce n'est donc pas ce que l'on veut
obtenir.

4.  A chaque neud, on test les Aretes si on trouve un sommet non visité on y va
a la sortie du neud choisi on retest toutes les arétes dans l'ordre, ce qui fait qu'on traite
trop de fois toutes les arrétes. Exemple: 1 sommet relié a 99 autre, la première arréte testé
sera tester 99 fois, ce qui est beaucoup trop.

## Sources
https://people.sc.fsu.edu/~jburkardt/data/pgma/pgma.html