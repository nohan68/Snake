package Snake;
import java.awt.Color;

public class Mur{
	Position position;
	Color couleur;
	public Mur(Position position, Color couleur){
		this.position = position;
		this.couleur = couleur;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public Color getCouleur(){
		return this.couleur;
	}
}
