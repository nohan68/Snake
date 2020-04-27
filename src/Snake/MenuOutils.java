package Snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuOutils extends JFrame implements ActionListener {
    public final int WINX = 400;
    public final int WINY = 100;

    public JPanel panneau;

    public JButton bMur;
    public JButton bFruit;
    public JButton bSnake;
    public JButton bGomme;
    public JButton bEnregistrer;
    public JButton bQuitter;

    public EditeurCarte root;
    public CarteEditable carteEditee;

    private boolean carteSauvee;

    public MenuOutils(EditeurCarte root, CarteEditable carteEditee){
        this.root = root;
        this.carteEditee = carteEditee;
        this.carteSauvee = true;
        setTitle("Snake Editeur de carte");
        setSize(WINX,WINY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initWidgets();
        setVisible(true);
    }

    public void initWidgets(){
        panneau = new JPanel();
        bMur = new JButton("Mur");
        bFruit = new JButton("Fruit");
        bSnake = new JButton("Snake");
        bGomme = new JButton("Gomme");
        bQuitter = new JButton("Quitter");
        bEnregistrer = new JButton("Enregistrer");

        bMur.addActionListener(this);
        bFruit.addActionListener(this);
        bSnake.addActionListener(this);
        bGomme.addActionListener(this);
        bQuitter.addActionListener(this);
        bEnregistrer.addActionListener(this);

        panneau.add(bMur);
        panneau.add(bFruit);
        panneau.add(bSnake);
        panneau.add(bGomme);
        panneau.add(bQuitter);
        panneau.add(bEnregistrer);

        setContentPane(panneau);


    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.bMur){
            root.setOutil(Outil.MUR);
        }
        else if(e.getSource() == this.bFruit){
            root.setOutil(Outil.FRUIT);
        }
        else if(e.getSource() == this.bSnake){
            root.setOutil(Outil.SNAKE);
        }
        else if(e.getSource() == this.bGomme){
            root.setOutil(Outil.GOMME);
        }
        else if(e.getSource() == this.bEnregistrer) {
            this.enregistrerCarte();
        }
        else if (e.getSource() == this.bQuitter) {
            if (!this.carteSauvee) {
                int reponse = JOptionPane.showConfirmDialog(this, "Enregistrer avant de quitter ?", "Informations non sauvegardées", JOptionPane.YES_NO_OPTION);
                boolean carteEnregistree = false;
                if (reponse == JOptionPane.YES_OPTION) {
                    carteEnregistree = this.enregistrerCarte();
                }
                if (carteEnregistree || reponse == JOptionPane.NO_OPTION) {
                    this.setVisible(false);
                    this.root.terminerEdition();
                }               
                return;           
            }
            this.setVisible(false);
            this.root.terminerEdition();

        }
    }


    public void setCarteSauvee(boolean carteSauvee) {
        this.carteSauvee = carteSauvee;
    }

    public boolean enregistrerCarte() {
        if (this.carteEditee.getSnakes().size() >= 1 && this.carteEditee.getSnakes().size() <= Launcher.MAX_JOUEURS) {
            if (this.carteEditee.getFruits().size() > 0) {
                root.getCarte().enregistrer();
                this.carteSauvee = true;
                this.carteEditee.setNsnakes(this.carteEditee.getSnakes().size());
                JOptionPane.showMessageDialog(this, "La carte a été enregistrée avec succès", "Carte enregistrée", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            else {
                JOptionPane.showMessageDialog(this, "La carte doit contenir au moins 1 fruit", "Erreur enregistrement carte", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "La carte doit contenir entre 1 et "+Launcher.MAX_JOUEURS+" snakes", "Erreur enregistrement carte", JOptionPane.ERROR_MESSAGE);
        }
        return false;   
    }

}
