package Snake;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Carte{
    final public static String MAP_EXTENSION = ".map";
    final protected static int N_MEILLEURS_SCORES = 10;
    final public static String MAP_FOLDER = System.getProperty("user.dir") + "/Maps/";
    public static ArrayList<Carte> lCartes = getCartes();
    protected int[] meilleursScores;
    protected String nom;
    protected int largeur;
    protected int nSnakes;
    protected String chemin_fichier;
    protected File fichier;
    protected ArrayList<Mur> murs;
    protected ArrayList<Fruit> fruits;
    protected ArrayList<Snake> snakes;
    protected Scanner scanner;
    protected FileWriter fileWriter;
    protected PrintWriter ecrivain;

    public Carte(String nom){
        this.murs = new ArrayList<Mur>();
        this.fruits = new ArrayList<Fruit>();
        this.snakes = new ArrayList<Snake>();
        this.meilleursScores = new int[N_MEILLEURS_SCORES];
        for (int i = 0;i<this.meilleursScores.length;i++) {
            this.meilleursScores[i] = 0;
        }
        this.nom = nom;
        this.chemin_fichier = MAP_FOLDER+this.nom+MAP_EXTENSION;
        this.fichier = new File(chemin_fichier);
        boolean nouveauFichier = false;
        try {
            nouveauFichier = this.fichier.createNewFile();
        }
        catch(IOException e) {}
        
        if (nouveauFichier) {
            System.out.println("Création de la carte : "+this.nom);
            Carte.lCartes.add(this);
        }
        else
        System.out.println("Chargement de la carte : "+this.nom);
        System.out.println("\t\t\t\t\t\t:'"+chemin_fichier+"'");
        this.rechargerScanner();
    }
    
    
    public void chargerMetadonnees() {
        this.largeur = Integer.valueOf(scanner.next());
        this.nSnakes = Integer.valueOf(scanner.next());
        for (int i = 0;i<this.meilleursScores.length;i++) {
            this.meilleursScores[i] = Integer.valueOf(scanner.next());
        }
    }
    
    public void charger(){
        this.rechargerScanner();
        this.chargerMetadonnees();
        int y=0;
        String ligne;
        this.murs.clear();
        this.fruits.clear();
        this.snakes.clear();
        while(scanner.hasNext()){
            ligne = scanner.next();
            for(int x=0;x<ligne.length();x++){ //x = 1 car la première ligne est réservé aux métadonnées
                if(ligne.charAt(x) == 'X'){
                    //System.out.println("Mur à la position: "+x+","+y);
                    this.murs.add(new Mur(new Position(x,y),Outil.MUR.getCouleur()));
                }
                else if(ligne.charAt(x) == 'O'){
                    //System.out.println("Fruit à la position: "+x+","+y);
                    this.fruits.add(new Fruit(new Position(x,y),Outil.FRUIT.getCouleur()));
                }
                else if(ligne.charAt(x) == 'S'){
                    //System.out.println("Snake à la position: "+x+","+y);
                    this.snakes.add(new Snake(x,y));
                }
            }
            y++;
        }
    }
    
    public void rechargerScanner() {
        if (this.scanner != null)
        this.scanner.close();
        try {
            this.scanner = new Scanner(this.fichier);
        }
        catch (FileNotFoundException e){
            System.out.println("La carte "+this.nom+" n'existe pas");
            return;
        }
    }
    
    public int getLargeur(){
        return this.largeur;
    }
    
    public int getNombreSnakes() {
        return this.nSnakes;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public int[] getMeilleursScores() {
        return this.meilleursScores;
    }
    
    /*public ArrayList<Position> getAllSnakePos(){
        ArrayList<Positions> snakesPositions = new ArrayList<Position>();
        for (int i = 0;i<this.snakes.size();i++) {
            snakesPositions.add(this.snakes.get(i).getPosition());
        }
        return snakesPositions;
    }*/
    
    public Position getSnakePos(int pos){
        return snakes.get(pos).getPosition();
    }
    
    public ArrayList<Fruit> getFruits(){
        return this.fruits;
    }
    
    public void addFruit(Fruit fruit) {
        this.fruits.add(fruit);
    }
    
    public ArrayList<Mur> getMurs(){
        return this.murs;
    }
    
    public ArrayList<Snake> getSnakes() {
        return this.snakes;
    }
    
    public Object getElementAtPosition(int x, int y){
        Position pos = new Position(x,y);
        for(Mur mur : murs){
            if(mur.getPosition().equals(pos)){
                return mur;
            }
        }
        
        for(Fruit fruit : fruits){
            if(fruit.getPosition().equals(pos)){
                return fruit;
            }
        }
        
        for(Snake snake : snakes){
            if(snake.getPosition().equals(pos)){
                return snake;
            }
        }
        return null;
    }
    
    public void enregistrer(){
        System.out.println("Enregistrement de "+ (this.largeur*this.largeur) + " éléments");
        try {
            this.fileWriter = new FileWriter(chemin_fichier);
            this.ecrivain = new PrintWriter(fileWriter);
        }
        catch(IOException ex){
            System.out.println("Erreur : "+ex.getMessage());
        }
        this.ecrivain.println(this.largeur);
        this.ecrivain.println(this.getSnakes().size());
        for (int i = 0;i<this.meilleursScores.length;i++) {
            this.ecrivain.println(this.meilleursScores[i]);
        }
        for(int y=0;y<this.largeur;y++){
            for(int x=0;x<this.largeur;x++){
                if(this.getElementAtPosition(x,y) instanceof Mur)
                this.ecrivain.print("X");
                else if(this.getElementAtPosition(x,y) instanceof Fruit)
                this.ecrivain.print("O");
                else if(this.getElementAtPosition(x,y) instanceof Snake)
                this.ecrivain.print("S");
                else
                this.ecrivain.print("-");
            }
            this.ecrivain.println("");
        }
        this.ecrivain.close();
        System.out.println("Enregistrement terminé !");
    }
    
    
    
    public static boolean carteExiste(String nom) {
        for (String nomsCarte : getNomsCartesFromDossier()) {
            if (nomsCarte.equals(nom))
            return true;
        }
        return false;
    }
    
    public static ArrayList<Carte> getCartes() {
        ArrayList<String> nomsCartes = getNomsCartesFromDossier();
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        Carte carteCourante;
        for (int i = 0;i<nomsCartes.size();i++) {
            carteCourante = new Carte(nomsCartes.get(i));
            carteCourante.chargerMetadonnees();
            cartes.add(carteCourante);
        }
        return cartes;
    }
    
    public static ArrayList<String> getNomsCartesFromDossier() {
        File folder = new File(MAP_FOLDER);
        ArrayList<String> fileNames = new ArrayList<String>();
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().substring(file.getName().length()-4).equals(MAP_EXTENSION)) {
                fileNames.add(file.getName().substring(0,file.getName().length()-4));
            }
        }
        return fileNames;
    }
    
    public static Carte getCarteByNom(String nom) {
        for (Carte carte : lCartes) {
            if (carte.nom.equals(nom))
            return carte;
        }
        return null;
    }
    
    public static void supprimerCarte(String nom) {
        Carte carteAsupprimer = Carte.getCarteByNom(nom);
        if (carteAsupprimer != null) {
            carteAsupprimer.scanner.close();
            Carte.lCartes.remove(carteAsupprimer);
            boolean carteSupprimee = carteAsupprimer.fichier.delete();
            if (carteSupprimee)
                System.out.println("Carte supprimée avec succès");
            else
                System.out.println("La carte n'a pas pu être supprimée");
        }
    }
    
    
    
}