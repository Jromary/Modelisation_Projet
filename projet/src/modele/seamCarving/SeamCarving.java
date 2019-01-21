package modele.seamCarving;

import modele.graph.Edge;
import modele.graph.Graph;
import modele.graph.GraphArrayList;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

    public String toStringMatrice(int[][] matrice){
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrice) {
            for (int cell : row) {
                sb.append(cell + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

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
                if (i == 0){ // si on est tout a gauche
                    if (i == largeur - 1){ // si il n'y a qu'une collone
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                    }else { // sinon on est bien a gauche
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1 + 1, matriceInterest[j][i]));
                    }
                }else {
                    if (i == largeur - 1){ // si on est tout a droite
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i - 1 + 1, matriceInterest[j][i]));
                    }else { // cas normal au milieu de la matrice
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i + 1 + 1, matriceInterest[j][i]));
                        graph.addEdge(new Edge((j * largeur) + i + 1, ((j + 1) * largeur) + i - 1 + 1, matriceInterest[j][i]));
                    }
                }
            }
        }
        for (int i = 0; i < largeur; i++) {
            graph.addEdge(new Edge(((hauteur - 1) * largeur) + i + 1, (hauteur * largeur) + 1, matriceInterest[hauteur - 1][i]));
            graph.addEdge(new Edge(0, i + 1, 0));
        }

        return graph;
    }


    ArrayList<Integer> ordre;
    boolean[] visite;
    public ArrayList<Integer> tritopo(Graph graph){
        ordre = new ArrayList<>();
        int nb_vertices = graph.vertices();
        visite = new boolean[nb_vertices];
        dfs(graph, 0);
//        for (int u = 0; u <nb_vertices; u++) {
//            if (!visite.contains(u)){
//                dfs(graph, u);
//            }
//        }
        Collections.reverse(ordre);
        return ordre;
    }

    private void dfs(Graph graph, int u) {
        visite[u] = true;
        for(Edge edge : graph.next(u)){
            if (!visite[edge.to]){
                dfs(graph, edge.to);
            }
        }
        ordre.add(u);
    }

    public ArrayList<Integer> Bellman(Graph graph, int s, int t, ArrayList<Integer> order){
        int[] T = new int[graph.vertices()];

        for (int i = 0; i <T.length; i++) {
            T[i] = Integer.MAX_VALUE;
        }
        T[s] = 0;
        for (int sommet : order) {
//            int val = Integer.MAX_VALUE;
            for(Edge edge : graph.prev(sommet)){
                if(T[edge.from] != Integer.MAX_VALUE) {
                    T[sommet] = Math.min(T[sommet], T[edge.from] + edge.cost);
                }
//                System.out.println(val);
            }
//            T[sommet] = val;
        }
        for (int elem : T) {
//            System.out.print(elem + " ");
        }
//        System.out.println("");
        return backTrack(graph, T, s, t);
    }

    private ArrayList<Integer> backTrack(Graph graph, int[] T, int s, int t) {
        ArrayList<Integer> path = new ArrayList<>(0);
        if (s == t){
            path.add(s);
            return path;
        }
        int prev = -1;
        for (Edge edge : graph.prev(t)) {
            if (T[t] - edge.cost == T[edge.from]){
                prev = edge.from;
            }
        }
        if (prev == -1){
            return path;
        }
        path =  backTrack(graph, T, s, prev);
        path.add(t);
        return path;
    }

    public int[][] removeColumn(int[][] matrice, ArrayList<Integer> listToRemove){
        int pixelParcorue = 0;
        int hauteur = matrice.length;
        int largeur = matrice[0].length;
        int[][] matriceRes = new int[hauteur][largeur - 1];
        System.out.println(listToRemove);
        for (int i = 0; i < hauteur; i++) {
            // on recupere le numero du pixel a retirer
            int id = listToRemove.get(i+1);
//            boolean overlap = false;
            for (int j = 0; j < largeur - 1; j++) {
                pixelParcorue++;

                if (pixelParcorue >= id){
                    matriceRes[i][j] = matrice[i][j + 1];
                }else{
                    matriceRes[i][j] = matrice[i][j];
                }

                /**
                // on test si le pixel en cour est le bon (id / da +5454) = 0
//                System.out.println("" + (id - (i * largeur)) + j + 1);
                if((i*largeur) + j + 1 == id){
                    System.out.println("pipop");
                    overlap = true;
                }
                // on le retire et on pense a decaler tout les rexste
                if (overlap){
                    matriceRes[i][j] = matrice[i][j];
                }else {
                    matriceRes[i][j] = matrice[i][j + 1];
                }**/
            }
            pixelParcorue++;
        }
        return matriceRes;
    }
}
