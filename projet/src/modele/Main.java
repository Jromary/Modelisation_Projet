package modele;

import modele.graph.Graph;
import modele.seamCarving.SeamCarving;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        String file_in;
        String file_out;
        int iterationcol;
        int iterationlig;
        if (args.length != 4 ) {
            System.out.println("Usage : java -jar SeamCarving <File In> <Nb column to delete> <Nb ligne to delete> <File Out>");
            return;
        }
        file_in = args[0];
        file_out = args[3];
        iterationcol = Integer.parseInt(args[1]);
        iterationlig = Integer.parseInt(args[2]);

        System.out.println(file_in + " | " + file_out + " | " + iterationcol + " | " + iterationlig);
        SeamCarving sc = new SeamCarving();
        int[][] image;
        image = sc.readpgm(file_in);
        for (int i = 0; i < iterationcol; i++) {
            int hauteur = image.length;
            int largeur = image[0].length;

//            System.out.println(hauteur + " " + largeur);

            int[][] interet;
            interet = sc.interest(image);

            Graph graph;
            //graph = sc.tograph(interet);
            graph = sc.tographImplicite(interet);

//            graph.writeFile("projet/src/ressources/testPerso.txt");

            ArrayList<Integer> triTopo;
            triTopo = sc.tritopo(graph);
            //System.out.println("trie topo: " + triTopo);

            ArrayList<Integer> path;
//            System.out.println(hauteur + " " + largeur + " " + (hauteur*largeur+1));
//            System.out.println("nombre de sommet : " + graph.vertices());

            //path = sc.Bellman(graph, 0, graph.vertices() - 1, triTopo);//pour graph normal
            path = sc.Bellman(graph,graph.vertices()-2, graph.vertices()-1, triTopo); //pour graph implicite
            //System.out.println("Path : " + path);

            int[][] imageRes = sc.removeColumn(image, path);
//            image = new int[imageRes.length][imageRes[0].length];
            image = imageRes;
        }
        if (iterationlig > 0){
            image = sc.rotateLeft(image);
            for (int i = 0; i < iterationlig; i++) {
                int hauteur = image.length;
                int largeur = image[0].length;
                int[][] interet;
                interet = sc.interest(image);

                Graph graph;
                graph = sc.tographImplicite(interet);

                ArrayList<Integer> triTopo;
                triTopo = sc.tritopo(graph);

                ArrayList<Integer> path;
                path = sc.Bellman(graph,graph.vertices()-2, graph.vertices()-1, triTopo); //pour graph implicite
                int[][] imageRes = sc.removeColumn(image, path);
                image = imageRes;
            }
            image = sc.rotateRight(image);
        }



        sc.writepgm(image, file_out);
    }
}
