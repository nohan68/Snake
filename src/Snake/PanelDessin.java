package Snake;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;



public class PanelDessin extends JPanel implements ActionListener {
    private Application app;
    private int largeurFenetre;
    private double vitesseReelleSnake;
    private Color couleurPolice;
    private int ips;
    private Timer timer;
    
    final private String CHEMIN_THEMES    = "Themes/";
    final private String EXTENSION_IMAGES = ".png";
    //Images
    BufferedImage arrierePlanImage;
    BufferedImage corpsImage;
    BufferedImage fruitsImage;
    BufferedImage mursImage;
    
    
    
    public PanelDessin(Application app, int largeurFenetre, int ips) {
        super();
        this.app = app;
        this.ips = ips;
        this.largeurFenetre = largeurFenetre;
        this.chargerImages();
        this.init();
        timer=new Timer(1000/this.ips, this);
        timer.start();
        
    }
    
    public void init() {
        this.vitesseReelleSnake = this.app.tailleCase * this.app.getAPS() / this.ips;
        try {
            Scanner sc = new Scanner(new File(CHEMIN_THEMES+this.app.getTheme()+"/meta"));
            this.couleurPolice = Parametres.getCouleurFromChaine(sc.next());
        }
        catch (FileNotFoundException e) {}

    }
    
    
    public void chargerImages(){
        String theme = (this.app.getTheme()+"/");
        System.out.println(CHEMIN_THEMES+theme+"arrierePlan"+EXTENSION_IMAGES);
            try {
                arrierePlanImage = ImageIO.read(new File(CHEMIN_THEMES+theme+"arrierePlan"+EXTENSION_IMAGES));
                fruitsImage      = ImageIO.read(new File(CHEMIN_THEMES+theme+"fruits"+EXTENSION_IMAGES));
                mursImage      = ImageIO.read(new File(CHEMIN_THEMES+theme+"murs"+EXTENSION_IMAGES));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    
    private void dessiner(Graphics g) {
        
        
        g.setColor(Color.WHITE);
        g.drawImage(arrierePlanImage, 0, 0, this.largeurFenetre, this.largeurFenetre, null);
        g.setColor(Color.BLACK);
        
        
        
        
        //Dessin des grilles
        if(this.app.dessinerGrilles){
            for(int i=0; i< this.app.nombreCases;i++){
                g.drawLine(0,this.app.getGridPos(i), (int) Math.round(this.app.nombreCases*this.app.tailleCase), this.app.getGridPos(i));
                g.drawLine(this.app.getGridPos(i),0, this.app.getGridPos(i),(int) Math.round(this.app.nombreCases*this.app.tailleCase));
            }
        }
        
        this.dessinerFruits(g);
        this.dessinerMurs(g);
        this.dessinerSnakes(g);
        
        g.setColor(this.couleurPolice);
        if (this.app.compteArebours > 0) {
            
            if (this.app.estEnJeu()) {
                g.setFont(new Font("Courier", Font.BOLD, this.largeurFenetre/10));
                g.drawString(String.valueOf(this.app.compteArebours),this.largeurFenetre/2,this.largeurFenetre/2);
            }
            else if (this.app.estMultijoueur() && this.app.getJoueurs().size() >= 1) {
                g.setFont(new Font("Courier", Font.BOLD, this.largeurFenetre/20));
                g.drawString("Victoire de "+this.app.getJoueurs().get(0).getNom(),this.largeurFenetre/2-250,this.largeurFenetre/2);
            }
            
        }
        
        if (!this.app.estMultijoueur() && this.app.getJoueurs().size() > 0) {
            g.setFont(new Font("Courier", Font.BOLD, this.largeurFenetre/50));
            g.drawString("Score : "+this.app.getJoueurs().get(0).getScore(),20,20);
        }
                
    }
    
    
    private void dessinerFruits(Graphics g) {
        //    :Fruits
        int tailleCaseEntiere = (int)Math.round(this.app.tailleCase);
        Fruit fruitCourant;
        for (int i = this.app.carte.getFruits().size() - 1;i>=0;i--) {
            fruitCourant = this.app.carte.getFruits().get(i);
            g.setColor(fruitCourant.getCouleur());
            g.drawImage(fruitsImage,
            (int) fruitCourant.getPosition().getPosReelleX(),
            (int) fruitCourant.getPosition().getPosReelleY(),
            tailleCaseEntiere,
            tailleCaseEntiere,
            null
            );
        }
    }
    
    private void dessinerMurs(Graphics g) {
        int tailleCaseEntiere = (int)Math.floor(this.app.tailleCase)+1;
        
        //    :Murs
        for (Mur m : this.app.carte.getMurs()) {
            g.setColor(m.getCouleur());
            g.drawImage(mursImage,
            (int) m.getPosition().getPosReelleX(),
            (int) m.getPosition().getPosReelleY(),
            tailleCaseEntiere,
            tailleCaseEntiere,
            null
            );
        }
    }
    
    private void dessinerSnakes(Graphics g) {
        
        
        //Dessin du corps du snake
        int tailleCaseEntiere = (int)Math.round(this.app.tailleCase);
        
        int[] teteDir;
        int[] membreDir;
        for (int i = 0;i<this.app.lJoueurs.size();i++) {
            Snake snakeCourant = this.app.lJoueurs.get(i).getSnake();
            
            
            teteDir = Snake.getIntDirFromChar(snakeCourant.getDir());
            if (this.app.compteArebours == 0) {
                snakeCourant.getPosition().setPosReelleX(snakeCourant.getPosition().getPosReelleX() + teteDir[0] * this.vitesseReelleSnake);
                snakeCourant.getPosition().setPosReelleY(snakeCourant.getPosition().getPosReelleY() + teteDir[1] * this.vitesseReelleSnake);
            }
            
            g.setColor(snakeCourant.getCouleur());
            g.fillOval(
            (int) snakeCourant.getPosition().getPosReelleX()-5,
            (int) snakeCourant.getPosition().getPosReelleY()-5,
            tailleCaseEntiere+10,
            tailleCaseEntiere+10
            );
            
            double valeurRouge = snakeCourant.getCouleur().getRed();
            double valeurVerte = snakeCourant.getCouleur().getGreen();
            double valeurBleue = snakeCourant.getCouleur().getBlue();
            double diffCouleur = 256.0/snakeCourant.getTaille();
            if (snakeCourant.getCouleur() == Color.BLACK)
                diffCouleur *=-1;

            for(int j=0; j<snakeCourant.getTaille()-1;j++){

                g.setColor(new Color((int)valeurRouge, (int)valeurVerte, (int)valeurBleue));

                valeurRouge -= diffCouleur;
                valeurVerte -= diffCouleur;
                valeurBleue -= diffCouleur;
                if (valeurRouge < 0)
                valeurRouge = 0;
                if (valeurVerte < 0)
                valeurVerte = 0;
                if (valeurBleue < 0)
                valeurBleue = 0;
                
                membreDir = snakeCourant.getMembreDir(j);
                if (this.app.compteArebours == 0) {
                    snakeCourant.getMembre(j).setPosReelleX(snakeCourant.getMembre(j).getPosReelleX() + membreDir[0] * this.vitesseReelleSnake);
                    snakeCourant.getMembre(j).setPosReelleY(snakeCourant.getMembre(j).getPosReelleY() + membreDir[1] * this.vitesseReelleSnake);
                }
                g.fillOval(
                (int) snakeCourant.getMembre(j).getPosReelleX(),
                (int) snakeCourant.getMembre(j).getPosReelleY(),
                tailleCaseEntiere,
                tailleCaseEntiere
                );
            }
            g.setColor(Color.RED);
            if (teteDir[0] > 0 || teteDir[1] > 0) {
                g.fillOval(
                        (int) (snakeCourant.getPosition().getPosReelleX()+(this.app.tailleCase*2/3*teteDir[0])+Math.round(this.app.tailleCase/4)),
                        (int) (snakeCourant.getPosition().getPosReelleY()+(this.app.tailleCase*2/3*teteDir[1])+Math.round(this.app.tailleCase/4)),
                        (int) Math.round(this.app.tailleCase/3+teteDir[0]*(this.app.tailleCase/3)),
                        (int) Math.round(this.app.tailleCase/3+teteDir[1]*(this.app.tailleCase/3))
                );
            }

            if (teteDir[0] < 0 || teteDir[1] < 0) {
                g.fillOval(
                        (int) (snakeCourant.getPosition().getPosReelleX()+(this.app.tailleCase*2/3*teteDir[0])+Math.round(this.app.tailleCase/4)+teteDir[0]*this.app.tailleCase/3),
                        (int) (snakeCourant.getPosition().getPosReelleY()+(this.app.tailleCase*2/3*teteDir[1])+Math.round(this.app.tailleCase/4)+teteDir[1]*this.app.tailleCase/3),
                        (int) Math.abs(Math.round(-this.app.tailleCase/3+teteDir[0]*(this.app.tailleCase/3))),
                        (int) Math.abs(Math.round(-this.app.tailleCase/3+teteDir[1]*(this.app.tailleCase/3)))
                );
            }


            g.setColor(Color.BLACK);
            if (snakeCourant.getCouleur() == Color.BLACK)
                g.setColor(Color.WHITE);
            g.fillOval(
                    (int) (snakeCourant.getPosition().getPosReelleX()+this.app.tailleCase/3-(teteDir[1]*this.app.tailleCase/3)),
                    (int) (snakeCourant.getPosition().getPosReelleY()+this.app.tailleCase/3+(teteDir[0]*this.app.tailleCase/3)),
                    (int) Math.round(this.app.tailleCase/3),
                    (int) Math.round(this.app.tailleCase/3)
            );

            g.fillOval(
                    (int) (snakeCourant.getPosition().getPosReelleX()+this.app.tailleCase/3+(teteDir[1]*this.app.tailleCase/3)),
                    (int) (snakeCourant.getPosition().getPosReelleY()+this.app.tailleCase/3-(teteDir[0]*this.app.tailleCase/3)),
                    (int) Math.round(this.app.tailleCase/3),
                    (int) Math.round(this.app.tailleCase/3)
            );

            
        }
        
    }
    
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.dessiner(g);
    }
    
    public void actionPerformed(ActionEvent ev){
        if(ev.getSource() == this.timer)
        repaint();
    }
    
    public void finir() {
        this.timer.stop();
    }
    
    
    
}