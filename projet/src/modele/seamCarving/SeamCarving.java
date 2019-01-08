package modele.seamCarving;

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
            System.out.println("Recuperation de la hauteur : " + hauteur + ", et de la largueur : " + largeur);

            buffer.write("P2");
            buffer.newLine();
            buffer.write(largeur + " " + hauteur);
            buffer.newLine();
            buffer.write("255");
            buffer.newLine();
            for (int[] row : image) {
                for (int pixel : row) {
                    System.out.print(pixel + " ");
                    buffer.write(pixel + " ");
                }
                System.out.println("");
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


}
