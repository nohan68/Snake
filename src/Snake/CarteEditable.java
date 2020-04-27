package Snake;

public class CarteEditable extends Carte{

    public CarteEditable(String nom) {
        super(nom);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setLargeur(int largeur){
        this.largeur = largeur;
    }

    public void setNsnakes(int nSnakes) {
        this.nSnakes = nSnakes;
    }

    public void ajouterMur(Mur mur){
        this.murs.add(mur);
    }

    public void effacerMur(Mur mur){
        this.murs.remove(mur);
    }

    public void ajouterFruit(Fruit fruit){
        this.fruits.add(fruit);
    }

    public void effacerFruit(Fruit fruit){
        this.fruits.remove(fruit);
    }

    public void ajouterSnake(Snake snake){
        this.snakes.add(snake);
    }

    public void effacerSnake(Snake snake){
        this.snakes.remove(snake);
    }
}

