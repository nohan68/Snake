package Snake;

import java.util.ArrayList;
import java.awt.Color;

public class Snake {
    
    private final static int MAX_INPUT_QUEUE_LENGTH = 2;
    private char dir;
    private boolean grandit; //vrai si au prochain pas du snake si dois grandir
    private boolean vivant;
    private Position tete;
    private Color couleur;
    private ArrayList<Position> membres; //partie du corps du snake
    private ArrayList<Character> inputQueue;
    
    public Snake(int x,int y){
        this.dir = 'N';
        this.vivant = true;
        this.grandit = false;
        this.tete = new Position(x,y);
        this.membres = new ArrayList<Position>();
        this.inputQueue = new ArrayList<Character>();
        this.ajouterMembre();
        this.ajouterMembre();
        this.ajouterMembre();
    }
    
    public Snake(int x, int y, char dir) {
        this(x, y);
        this.dir = dir;
    }
    
    public void avancer(){
        if (this.inputQueue.size() > 0) {
            this.dir = this.inputQueue.get(0);
            this.inputQueue.remove(0);
        }
        this.membres.add(0,new Position(this.tete));
        
        if(this.grandit)
        this.grandit=false;
        else
        this.membres.remove(this.membres.size()-1);
        
        int[] intDir = getIntDirFromChar(this.dir);
        this.tete.transX(intDir[0]);
        this.tete.transY(intDir[1]);
        
    }
    
    public void ajouterMembre() {
        int dirXdernierMembre = 0, dirYdernierMembre = 0;
        int[] intDir;
        if (this.membres.size() <= 1) {
            intDir = getIntDirFromChar(this.dir);
        }
        else
        intDir = getMembreDir(this.membres.size() - 1);
        
        dirXdernierMembre = intDir[0];
        dirYdernierMembre = intDir[1];
        if (this.membres.size() == 0)
        this.membres.add(new Position(this.tete.getX() + dirXdernierMembre, this.tete.getY() + dirYdernierMembre));
        else
        this.membres.add(new Position(this.membres.get(this.membres.size()-1).getX() + dirXdernierMembre, this.membres.get(this.membres.size()-1).getY() + dirYdernierMembre));
    }
    
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
    
    public Color getCouleur() {
        return this.couleur;
    }
    
    public int getTaille() {
        return this.membres.size() + 1; //Nombre de partie du corps + 1 pour la tete
    }
    
    public void agrandir(){
        this.grandit = true;
    }
    
    public char getDir(){
        return this.dir;
    }
    
    public boolean estVivant() {
        return this.vivant;
    }
    
    public void meurt() {
        this.vivant = false;
    }
    
    public void setDir(char dir){
        if (this.inputQueue.size() == MAX_INPUT_QUEUE_LENGTH)
        return;
        char derniereDir;
        if (this.inputQueue.size() == 0)
        derniereDir = this.dir;
        else
        derniereDir = this.inputQueue.get(this.inputQueue.size()-1);
        if (((derniereDir == 'N' || derniereDir == 'S') && (dir == 'N' || dir == 'S')) || ((derniereDir == 'W' || derniereDir == 'E') && (dir == 'W' || dir == 'E')))
        return;
        this.inputQueue.add(dir);
    }
    
    public int[] getMembreDir(int index) {
        if (index == 0)
        return getIntDirFromChar(this.getDir());
        Position membre = this.membres.get(index);
        Position membreAvant = this.membres.get(index-1);
        int diffX = membreAvant.getX() - membre.getX();
        int diffY = membreAvant.getY() - membre.getY();
        
        
        if (diffX > 1)
        diffX = -1;
        else if (diffX < -1)
        diffX = 1;
        
        if (diffY > 1)
        diffY = -1;
        else if (diffY < -1)
        diffY = 1;
        
        
        return new int[] {diffX, diffY};
        
    }
    
    public ArrayList<Position> getMembres(){
        return this.membres;
    }
    
    public Position getMembre(int i){
        return this.membres.get(i);
    }
    
    public Position getPosition(){
        return this.tete;
    }
    
    public void setPosition(Position pos) {
        this.tete = new Position(pos);
    }
    
    public static char getCharDirFromInt(int[] dir) {
        if (dir[0] == 0) {
            if (dir[1] == 1)
            return 'S';
            return 'N';
        }
        if (dir[0] == 1)
        return 'E';
        return 'W';
    }
    
    public static int[] getIntDirFromChar(char dir) {
        switch (dir) {
            case 'N':
                return new int[] {0,-1};
            case 'S':
                return new int[] {0,1};
            case 'W':
                return new int[] {-1,0};
            case 'E':
                return new int[] {1,0};
            default:
                return new int[] {0,0};
        }
    }
}