package Snake;
import java.awt.Color;

public class Fruit {
    private Color couleur;
    private Position position;
    
    public Fruit(Position position, Color couleur){
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