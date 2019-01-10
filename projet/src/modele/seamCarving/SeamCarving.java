package modele.seamCarving;

import modele.graph.Edge;
import modele.graph.Graph;
import modele.graph.GraphArrayList;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

	public static int[][] readpgm(String fn)
	{
		try {
			InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
			BufferedReader d = new BufferedReader(new InputStreamReader(f));
			String magic = d.readLine();
			String line = d.readLine();
			while (line.startsWith("#")) {
				line = d.readLine();
			}
			Scanner s = new Scanner(line);
			int width = s.nextInt();
			int height = s.nextInt();
			line = d.readLine();
			s = new Scanner(line);
			int maxVal = s.nextInt();
			int[][] im = new int[height][width];
			s = new Scanner(d);
			int count = 0;
			while (count < height*width) {
				im[count / width][count % width] = s.nextInt();
				count++;
			}
			return im;
		}

		catch(Throwable t) {
			t.printStackTrace(System.err) ;
			return null;
		}
	}

	public static boolean writepgm(int[][] image, String filename){
        try {
            // Creation d'un flux vers le fichier
            FileWriter fileWriter = new FileWriter(filename);
            // Creation d'un buffer sur le flux du fichier
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            int hauteur = image.length;
            int largeur = image[0].length;
            //System.out.println("Recuperation de la hauteur : " + hauteur + ", et de la largueur : " + largeur);

            buffer.write("P2");
            buffer.newLine();
            buffer.write(largeur + " " + hauteur);
            buffer.newLine();
            buffer.write("255");
            buffer.newLine();
            for (int[] row : image) {
                for (int pixel : row) {
                    buffer.write(pixel + " ");
                }
                buffer.newLine();
            }
            buffer.close();
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

	public int[][] interest(int[][] image){
		int hauteur = image.length;
		int largeur = image[0].length;
		int[][] matrice = new int[hauteur][largeur];


		for (int j = 0; j < hauteur; j++) {
			for (int i = 0; i < largeur; i++) {
				if (i == 0){ // si on est tout a gauche
					if (i == largeur - 1){ // si il n'y a qu'une collone
						matrice[j][i] = image[j][i];
					}else { // sinon on est bien a gauche
						matrice[j][i] = Math.abs(image[j][i] - image[j][i+1]);
					}
				}else {
					if (i == largeur - 1){ // si on est tout a droite
						matrice[j][i] = Math.abs(image[j][i] - image[j][i-1]);
					}else { // cas normal au milieu de la matrice
						matrice[j][i] = Math.abs(image[j][i] - (image[j][i-1] + image[j][i+1])/2);
					}
				}
			}
		}

		return matrice;
	}

    public Graph tograph(int[][] matriceInterest){
        int hauteur = matriceInterest.length;
        int largeur = matriceInterest[0].length;
        GraphArrayList graph = new GraphArrayList((hauteur * largeur) + 2);

        for (int j = 0; j < hauteur-1; j++) {
            for (int i = 0; i < largeur; i++) {
                System.out.println("neux: " + ((j * largeur) + i));
                if (i == 0){ // si on est tout a gauche
                    if (i == largeur - 1){ // si il n'y a qu'une collone
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i, matriceInterest[j][i]));
                    }else { // sinon on est bien a gauche
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                    }
                }else {
                    if (i == largeur - 1){ // si on est tout a droite
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i - 1, matriceInterest[j][i]));
                    }else { // cas normal au milieu de la matrice
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i, ((j + 1) * largeur) + i - 1, matriceInterest[j][i]));
                    }
                }
            }
        }
        System.out.println("premiere partie de tograph done");
        for (int i = 0; i < largeur; i++) {
            graph.addEdge(new Edge(((hauteur - 1) * largeur) + i, (hauteur * largeur), matriceInterest[hauteur - 1][i]));
            graph.addEdge(new Edge((hauteur * largeur) + 1, i, matriceInterest[0][i]));
        }

        return graph;
    }




}
