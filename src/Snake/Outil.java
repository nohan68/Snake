package Snake;
import java.awt.Color;

public enum Outil {
	MUR(Color.GRAY),
	FRUIT(Color.RED),
	SNAKE(Color.PINK),
	GOMME(Color.WHITE);

	private Color couleur;

	Outil(Color couleur) {
		this.couleur = couleur;
	}

	Color getCouleur() {
		return this.couleur;
	}

}