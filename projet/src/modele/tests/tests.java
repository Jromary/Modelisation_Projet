package modele.tests;

import modele.graph.Graph;
import modele.seamCarving.SeamCarving;

import java.util.ArrayList;

public class tests {
    private static String FILENAME_TOWRITE_PGM = "projet/src/ressources/testPerso.pgm";
    private static String FILENAME_TOREAD_PGM = "ressources/ex1.pgm";
    private static String FILENAME_WRITE_GRAPH = "projet/src/ressources/testPerso.txt";

    public static void main(String[] args) {
        testWrite();
        testRead();
        testInterest();
        testToGraph();
        testTritopo();
        testBellmnan();
    }

    private static void testBellmnan() {
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        int[][] resultit;
        resultit = sc.interest(matrice);
        Graph result;
        result = sc.tograph(resultit);
        ArrayList<Integer> resultTopo;
        resultTopo = sc.tritopo(result);
        ArrayList<Integer> path;
        path = sc.Bellman(result, 0, 13, resultTopo);
        System.out.println(path);
    }

    private static void testTritopo() {
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        int[][] resultit;
        resultit = sc.interest(matrice);
        Graph result;
        result = sc.tograph(resultit);
        ArrayList<Integer> resultTopo;
        resultTopo = sc.tritopo(result);
        System.out.println(resultTopo);
    }

    private static void testToGraph() {
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        int[][] resultit;
        resultit = sc.interest(matrice);
        Graph result;
        result = sc.tograph(resultit);
        result.writeFile(FILENAME_WRITE_GRAPH);
    }

    private static void testInterest() {
        SeamCarving sc = new SeamCarving();

        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        int[][] result;
        result = sc.interest(matrice);
//TODO: de vrais test avec les resultat du sujet
//        for (int[] row : result) {
//            for (int pixel : row) {
//                System.out.print(pixel + " ");
//            }
//            System.out.println("");
//        }
    }

    private static void testWrite() {
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        assert (sc.writepgm(matrice, FILENAME_TOWRITE_PGM)):"ERREUR : Write pgm";
    }

    private static void testRead(){
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        sc.writepgm(sc.readpgm(FILENAME_TOREAD_PGM), FILENAME_TOWRITE_PGM);

//        System.out.println(sc.readpgm(FILENAME_TOREAD_PGM));
//        assert (matrice.equals(sc.readpgm(FILENAME_TOREAD_PGM))):"ERREUR : Read png";
    }
}
