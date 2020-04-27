package Snake;

public class Position {
    private int x;
    private int y;
    private double posReelleX;
    private double posReelleY;
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Position(int x, int y, double tailleCase) {
        this(x,y);
        this.initPosReelle(tailleCase);
    }
    
    public Position(Position pos){
        this.x = pos.getX();
        this.y = pos.getY();
    }
    
    public Position() {}
    
    /***
    * @return true si la position entrée en argument est égale à la position de l'instance
    ***/
    public boolean equals(Position p){
        return x==p.getX() && y==p.getY();
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setXY(int x, int y){
        this.setX(x);
        this.setY(y);
    }
    
    public int[] getXY(){
        return new int[]{this.x,this.y};
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void transX(int x){
        this.x += x;
    }
    
    public void transY(int y){
        this.y += y;
    }
    
    public void initPosReelle(double tailleCase) {
        this.posReelleX = tailleCase * this.x;
        this.posReelleY = tailleCase * this.y;
    }
    public double getPosReelleX() {
        return this.posReelleX;
    }
    
    public double getPosReelleY() {
        return this.posReelleY;
    }
    
    public void setPosReelleX(double posReelleX) {
        this.posReelleX = posReelleX;
    }
    
    public void setPosReelleY(double posReelleY) {
        this.posReelleY = posReelleY;
    }
    
    
    public void transXY(int x, int y){
        this.transX(x);
        this.transY(y);
    }
}