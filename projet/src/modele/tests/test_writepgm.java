package modele.tests;

import modele.seamCarving.SeamCarving;

public class test_writepgm {
    private static String FILENAME = "projet/src/ressources/testPerso.pgm";

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        assert (SeamCarving.writepgm(matrice, FILENAME)):"ERREUR : Write pgm throw une exeption";
        System.out.println(SeamCarving.writepgm(matrice, FILENAME));
    }
}
