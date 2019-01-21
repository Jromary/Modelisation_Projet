package modele.tests;

import com.sun.org.apache.xpath.internal.SourceTree;
import modele.graph.Graph;
import modele.seamCarving.SeamCarving;

import java.util.ArrayList;

public class tests {
    private static String FILENAME_TOWRITE_PGM = "projet/src/ressources/testPerso.pgm";
    private static String FILENAME_TOREAD_PGM = "ressources/ex1.pgm";
    private static String FILENAME_WRITE_GRAPH = "projet/src/ressources/testPerso.txt";

    public static void main(String[] args) {
//        testWrite();
//        testRead();
//        testInterest();
//        testToGraph();
//        testTritopo();
//        testBellmnan();
//        testRemove();
        testAll();
    }

    private static void testAll() {
        SeamCarving sc = new SeamCarving();
        long startTime;
        long startTimeG;
        long endTime;
        long endTimeG;
        long duration;
        startTime = java.lang.System.currentTimeMillis();
        startTimeG = java.lang.System.currentTimeMillis();
        int[][] image;
        image = sc.readpgm(FILENAME_TOREAD_PGM);
        int hauteur = image.length;
        int largeur = image[0].length;
        endTime = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Recuperation de l'image : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        int[][] interet;
        interet = sc.interest(image);
        endTime = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Creation de la matrice d'interet : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        Graph graph;
        graph = sc.tograph(interet);
        endTime = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Creation du graph d'interet : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        ArrayList<Integer> triTopo;
        triTopo = sc.tritopo(graph);
        endTime = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Creation du tri topologique : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        ArrayList<Integer> path;
        path = sc.Bellman(graph, 0, hauteur * largeur + 1, triTopo);
        endTime = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Application de l'algo de bellamn : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        int[][] imageRes = sc.removeColumn(image, path);
        endTime = java.lang.System.currentTimeMillis();
        System.out.println("Supression de la colone : " + duration + " ms");

        startTime = java.lang.System.currentTimeMillis();
        sc.writepgm(imageRes, FILENAME_TOWRITE_PGM);
        endTime = java.lang.System.currentTimeMillis();
        endTimeG = java.lang.System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Ecriture de l'image : " + duration + " ms");
        duration = (endTimeG - startTimeG);
        System.out.println("Temps global : " + duration + " ms");
    }

    private static void testRemove() {
        SeamCarving sc = new SeamCarving();
        int[][] matrice = {
                {3, 11, 24, 39},
                {8, 21, 29, 39},
                {200, 60, 25, 0}
        };
        System.out.println(sc.toStringMatrice(matrice));
        int[][] resultit;
        resultit = sc.interest(matrice);
        Graph result;
        result = sc.tograph(resultit);
        ArrayList<Integer> resultTopo;
        resultTopo = sc.tritopo(result);
        ArrayList<Integer> path;
        path = sc.Bellman(result, 0, 13, resultTopo);
        int[][] imgres = sc.removeColumn(matrice, path);
        System.out.println(sc.toStringMatrice(imgres));

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
