package Snake;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Joueur {
    
    private String nom;
    private int score;
    
    private int[] controles; //code ASCII des touches <-0  ^1 ->2 v3
    private Snake snake;
    
    
    
    public Joueur(String nom) {
        this.nom = nom;
        this.setControles(new int[]{KeyEvent.VK_5,KeyEvent.VK_2,KeyEvent.VK_1,KeyEvent.VK_3});
        this.score = 0;
        this.snake = new Snake(0,0);
    }
    
    public void setControles(int[] controles) {
        this.controles = controles;
    }
    
    public void addPoints(int points){
        score = score + points;
    }
    
    public int getScore(){
        return this.score;
    }
    public String getNom(){ return this.nom; }
    public int[] getControles(){ return this.controles; }
    public Snake getSnake(){ return this.snake; }
    
}