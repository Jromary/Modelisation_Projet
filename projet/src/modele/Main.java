package modele;

import modele.graph.Graph;
import modele.seamCarving.SeamCarving;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        String file_in;
        String file_out;
        int iteration;
        if (args.length != 3 ) {
            System.out.println("Usage : java -jar SeamCarving <File In> <Nb column to delete> <File Out>");
        }
        file_in = args[0];
        file_out = args[2];
        iteration = Integer.parseInt(args[1]);

        System.out.println(file_in + " | " + file_out + " | " + iteration);
        SeamCarving sc = new SeamCarving();
        int[][] image;
        image = sc.readpgm(file_in);
        for (int i = 0; i < iteration; i++) {
            int hauteur = image.length;
            int largeur = image[0].length;

//            System.out.println(hauteur + " " + largeur);

            int[][] interet;
            interet = sc.interest(image);

            Graph graph;
            graph = sc.tograph(interet);

//            graph.writeFile("projet/src/ressources/testPerso.txt");

            ArrayList<Integer> triTopo;
            triTopo = sc.tritopo(graph);

            ArrayList<Integer> path;
//            System.out.println(hauteur + " " + largeur + " " + (hauteur*largeur+1));
//            System.out.println("nombre de sommet : " + graph.vertices());
            path = sc.Bellman(graph, 0, graph.vertices() - 1, triTopo);

            int[][] imageRes = sc.removeColumn(image, path);
//            image = new int[imageRes.length][imageRes[0].length];
            image = imageRes;
        }
        sc.writepgm(image, file_out);
    }
}
