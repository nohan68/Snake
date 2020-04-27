package Snake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Parametres {
	public static boolean sonActive;
	final public static ArrayList<int[]> lControlesGeneraux = new ArrayList<int[]>();
	final public static ArrayList<Color> lCouleursGenerales = new ArrayList<Color>();
	final public static String FILE_NAME = "params";

	final public static String[] lCouleursChaine = {
		"Noir",
		"Bleu",
		"Cyan",
		"Gris fonce",
		"Gris",
		"Vert",
		"Gris clair",
		"Magenta",
		"Orange",
		"Rose",
		"Rouge",
		"Blanc",
		"Jaune",
	};

	final public static Color[] lCouleurs = {
		Color.BLACK,
		Color.BLUE,
		Color.CYAN,
		Color.DARK_GRAY,
		Color.GRAY,
		Color.GREEN,
		Color.LIGHT_GRAY,
		Color.MAGENTA,
		Color.ORANGE,
		Color.PINK,
		Color.RED,
		Color.WHITE,
		Color.YELLOW,
	};

	public static int[] getControlesFromChaine(String chaine) {
		String currentControl = "";
		int[] controls = new int[4];
		int currentControlPosition = 0;
		for (int i = 0;i<chaine.length();i++) {
			if (chaine.charAt(i) == '/') {
				controls[currentControlPosition] = Integer.valueOf(currentControl);
				currentControlPosition++;
				currentControl = "";
			}
			else
				currentControl += chaine.charAt(i);
		}
		return controls;
	}

	public static String getChaineFromControles(int[] controles) {
		if (controles.length != 4)
			return "ERREUR";
		String strControles = "";
		for (int i = 0;i<4;i++) {
			strControles += String.valueOf(controles[i]);
			strControles += "/";
		}
		return strControles;
	}

	public static String getChaineFromCouleur(Color couleur) {
		for (int i = 0;i<lCouleurs.length;i++) {
			if (couleur == lCouleurs[i])
				return lCouleursChaine[i];
		}
		return "black";
	}

	public static Color getCouleurFromChaine(String chaine) {
		for (int i = 0;i<lCouleursChaine.length;i++) {
			if (chaine.equals(lCouleursChaine[i]))
				return lCouleurs[i];
		}
		return Color.BLACK;
	   }

	public static void chargerParametres() {
		try {
			Scanner scanner = new Scanner(new File(FILE_NAME));
			if (scanner.hasNext()) {
				sonActive = scanner.next().equals("true");
			}
			String entree;
			boolean controlsDone = false;
			while (scanner.hasNext()) {
				entree = scanner.next();
				if (entree.equals("-----")) {
					controlsDone = true;
					continue;
				}
				if (!controlsDone)
					lControlesGeneraux.add(getControlesFromChaine(entree));
				else
					lCouleursGenerales.add(getCouleurFromChaine(entree));
			}
			scanner.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("Le fichier "+FILE_NAME+" n'existe pas");
		}

	}

	public static void enregistrerParametres() {
		try {
			FileWriter fileWriter = new FileWriter(FILE_NAME);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			if (sonActive)
				printWriter.println("true");
			else
				printWriter.println("false");
			for (int i = 0;i<lControlesGeneraux.size();i++) {
				printWriter.println(getChaineFromControles(lControlesGeneraux.get(i)));
			}
			printWriter.println("-----");
			for (int i = 0;i<lCouleursGenerales.size();i++) {
				printWriter.println(getChaineFromCouleur(lCouleursGenerales.get(i)));
			}
			System.out.println("Enregistrement terminé !");
			printWriter.close();			
		}
		catch (IOException ex) {
            System.out.println("Erreur : "+ex.getMessage());
        }
	}


	public static void main(String[] args) {
		chargerParametres();
		System.out.println("Son activé : "+ sonActive);
		int j;
		for (int i = 0;i<lControlesGeneraux.size();i++) {
			for (j = 0;j<lControlesGeneraux.get(i).length;j++) {
				System.out.print((lControlesGeneraux.get(i)[j]) + "/");
			}
			System.out.print("\n");
		}
		for (int i = 0;i<lCouleursGenerales.size();i++) {
			System.out.println(lCouleursGenerales.get(i));
		}
	}


}