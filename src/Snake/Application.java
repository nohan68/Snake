package Snake;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Application extends JFrame implements ActionListener, KeyListener, Runnable {
    final public static String CHEMIN_SONS = "Sounds/";
    final private String CHEMIN_THEMES    = "Themes/";
    final private static int N_CLIP_MAX = 15;
    private final int WIN = 800; //Taille x y de la fenêtre
    public Carte carte;
    public volatile int compteArebours;
    public double tailleCase; //taille d'une case qui sera calculé ulterieurement
    public int nombreCases; //Nombre de cases X et Y
    private int aps; //Action par seconde
    private int ips; //Image par seconde
    private int scoreSolo;
    private boolean multijoueur;
    private boolean enJeu;
    public boolean dessinerGrilles;
    public ArrayList<Joueur> lJoueurs;
    public Launcher root;
    private PanelDessin panelDessin;
    private String theme;
    private Clip[] clipsFruit;
    private Clip[] clipsMort;
    private Clip clipTheme;
    
    
   
    public Application(String nomCarte, Launcher root) {
    	this.root = root;
    	this.setIconImage(new ImageIcon("Images/menuSnake.png").getImage());
        this.carte = new Carte(nomCarte);
        this.carte.charger();
        this.theme = (String)this.root.cbThemes.getSelectedItem();
        this.compteArebours = 3;
        this.nombreCases = this.carte.getLargeur();
        this.tailleCase = Math.round(1.0*WIN/(this.nombreCases));
        this.lJoueurs = new ArrayList<>();
        this.aps =1+(int)(Math.pow(1+this.root.cbSelectionDifficulte.getSelectedIndex(),2)*this.getCarte().getLargeur()/46);
        this.ips = 60;
        this.enJeu = true;
        this.dessinerGrilles = false;
        if (Parametres.sonActive)
        	this.chargerClips();
        this.setTitle("Snake S2A");
        this.setSize(WIN,WIN);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.panelDessin = new PanelDessin(this, WIN, this.ips);
        this.add(this.panelDessin);
        this.multijoueur = false;
    }
    
    public boolean[] getCollisions(Snake snake) {
        boolean corps = false;
        boolean corpsAutre = false;
        boolean fruit = false;
        boolean mur = false;
        
        //Tete sur corps
        for(int i = 0;i<snake.getTaille()-1;i++) {
            if(snake.getMembres().get(i).equals(snake.getPosition())) {
                corps=true;
            }
        }
        
        //Tete sur autre snake
        for (int i = 0;i<this.lJoueurs.size();i++) {
            if (this.lJoueurs.get(i).getSnake() == snake)
            continue;
            for (int j = 0;j<this.lJoueurs.get(i).getSnake().getTaille() - 1;j++) {
                if (this.lJoueurs.get(i).getSnake().getMembre(j).equals(snake.getPosition()))
                corpsAutre = true;
            }
        }
        
        //Tete sur fruit
        for(int i = 0;i<this.carte.getFruits().size();i++){
            if((this.carte.getFruits().get(i)).getPosition().equals(snake.getPosition())) {
                this.carte.getFruits().remove(this.carte.getFruits().get(i));
                fruit = true;
            }
        }
        
        //Tete sur mur
        for(int i = 0;i<this.carte.getMurs().size();i++){
            if((this.carte.getMurs().get(i)).getPosition().equals(snake.getPosition()))
            mur = true;
        }
        
        return new boolean[]{corps,corpsAutre,fruit,mur};
    }
    
    public void chargerClips() {
        int nClip = this.aps;
        if (nClip > N_CLIP_MAX)
            nClip = N_CLIP_MAX;
        this.clipsFruit = new Clip[nClip];
        this.clipsMort = new Clip[nClip];

        try {
            for (int i = 0;i<this.clipsFruit.length;i++) {
                this.clipsFruit[i] = AudioSystem.getClip();
                this.clipsFruit[i].open(AudioSystem.getAudioInputStream(new File(CHEMIN_SONS+"mange.wav")));
            }            
            for (int i = 0;i<this.clipsMort.length;i++) {
                this.clipsMort[i] = AudioSystem.getClip();
                this.clipsMort[i].open(AudioSystem.getAudioInputStream(new File(CHEMIN_SONS+"mort.wav")));
            }
            this.clipTheme = AudioSystem.getClip();
            clipTheme.open(AudioSystem.getAudioInputStream(new File(CHEMIN_THEMES+this.theme+"/theme.wav")));
            clipTheme.loop(Clip.LOOP_CONTINUOUSLY);
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void dechargerClips() {
        for (int i = 0;i<this.clipsFruit.length;i++) {
            if (this.clipsFruit[i] != null)
            this.clipsFruit[i].close();
        }
        for (int i = 0;i<this.clipsMort.length;i++) {
            if (this.clipsMort[i] != null)
            this.clipsMort[i].close();
        }
        try {
        	this.clipTheme.stop();
        	this.clipTheme.close();
        }catch(Exception e) {}
    }
    
    public void verifierHorsLimites() {
        for (int i = 0;i<this.lJoueurs.size();i++) {
            if (this.lJoueurs.get(i).getSnake().getPosition().getX() == -1)
            this.lJoueurs.get(i).getSnake().getPosition().setX(this.nombreCases-1);
            if (this.lJoueurs.get(i).getSnake().getPosition().getX() == this.nombreCases)
            this.lJoueurs.get(i).getSnake().getPosition().setX(0);
            if (this.lJoueurs.get(i).getSnake().getPosition().getY() == -1)
            this.lJoueurs.get(i).getSnake().getPosition().setY(this.nombreCases-1);
            if (this.lJoueurs.get(i).getSnake().getPosition().getY() == this.nombreCases)
            this.lJoueurs.get(i).getSnake().getPosition().setY(0);
        }
    }
    
    public void bougerSnakes() {
        int[] intDir;
        for (Joueur joueur : this.lJoueurs) {
            joueur.getSnake().getPosition().setPosReelleX(this.getGridPos(joueur.getSnake().getPosition().getX()));
            joueur.getSnake().getPosition().setPosReelleY(this.getGridPos(joueur.getSnake().getPosition().getY()));
            joueur.getSnake().avancer();
            for (int i = 0;i<joueur.getSnake().getMembres().size();i++) {
                joueur.getSnake().getMembre(i).setPosReelleX(this.getGridPos(joueur.getSnake().getMembre(i).getX()));
                joueur.getSnake().getMembre(i).setPosReelleY(this.getGridPos(joueur.getSnake().getMembre(i).getY()));
            }
        }
    }
    
    public void verifierCollisions() {
        boolean[] collisions;
        for (int i = this.lJoueurs.size()-1;i>=0;i--) {
            collisions = this.getCollisions(this.lJoueurs.get(i).getSnake());
            if(collisions[0] || collisions[1] || collisions[3]) {
                if (!this.multijoueur) {
                    this.scoreSolo = this.lJoueurs.get(0).getScore();
                }
                this.lJoueurs.get(i).getSnake().meurt();
                this.lJoueurs.remove(i);
                if (Parametres.sonActive)
                	this.sonMortSnake();
            }
            if(collisions[2]) {
                this.lJoueurs.get(i).getSnake().agrandir();
                this.lJoueurs.get(i).addPoints(10);
                if (Parametres.sonActive)
                	this.sonMangeFruit();
                this.pondreFruit();
            }
        }
    }
    
    public void fermerClipsTermines() {
        for (int i = 0;i<this.clipsFruit.length;i++) {
            if (this.clipsFruit[i] != null && this.clipsFruit[i].getFramePosition() == this.clipsFruit[i].getFrameLength()) {
                this.clipsFruit[i].stop();
                this.clipsFruit[i].setFramePosition(0);
            }
        }
        for (int i = 0;i<this.clipsMort.length;i++) {
            if (this.clipsMort[i] != null && this.clipsMort[i].getFramePosition() == this.clipsMort[i].getFrameLength()) {
                this.clipsMort[i].stop();
                this.clipsMort[i].setFramePosition(0);
            }
        }
    }
    
    public void sonMangeFruit() {
        for (int i = 0;i<this.clipsFruit.length;i++) {
            if (this.clipsFruit[i] != null && this.clipsFruit[i].getFramePosition() == 0) {
                this.clipsFruit[i].start();
                return;
            }
        }
    }
    
    public void sonMortSnake() {
        for (int i = 0;i<this.clipsMort.length;i++) {
            if (this.clipsMort[i] != null && this.clipsMort[i].getFramePosition() == 0) {
                this.clipsMort[i].start();
                return;
            }
        }
    }
    
    
    public void run(){
        System.setProperty("sun.java2d.opengl", "true");
        Timer timer = new Timer(1000,null);
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                compteArebours--;
                if (compteArebours <= 0) {
                    timer.stop();
                }
            }
        };
        timer.addActionListener(al);
        timer.start();
        
        long pauseBetweenFrames = (long)(1000.0/this.aps);
        while(this.enJeu) {
            if (this.compteArebours == 0){
                try {
                    Thread.sleep(pauseBetweenFrames);
            } catch (InterruptedException e) {}
            	if (Parametres.sonActive)
                	this.fermerClipsTermines();
                this.bougerSnakes();
                this.verifierHorsLimites();
                this.verifierCollisions();
                if (this.lJoueurs.size() == 0 || (this.lJoueurs.size() == 1 && this.multijoueur)) {
                    this.enJeu = false;
                    if (this.multijoueur) {
	                    this.compteArebours = 3;
	                    Timer timer2 = new Timer(1000,null);
	                    al = new ActionListener() {
	                        public void actionPerformed(ActionEvent evt) {
	                            compteArebours--;
	                            if (compteArebours <= 0) {
	                                timer2.stop();
	                                terminerPartie();
	                            }
	                        }
	                    };
	                    timer2.addActionListener(al);
	                    timer2.start();                    	
                    }
                    else
                    	this.terminerPartie();

                }
            }
            
        }
    }
    
    
    public void terminerPartie() {
        this.panelDessin.finir();
        if (!this.multijoueur) {
            this.carte.charger();
            this.ajouterScore(this.scoreSolo);
            int scorePos = getPositionScoreDansScores(this.scoreSolo);
            if (scorePos > -1)
                this.messageScore(this.scoreSolo, scorePos);
            else
            	JOptionPane.showMessageDialog(this,"Perdu !", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            this.carte.enregistrer();
        }
        if (Parametres.sonActive)
        	this.dechargerClips();
        this.dispose();
        if (this.root != null)
            this.root.afficher();
            this.root.revalidate();
    }
    
    
    public void messageScore(int score, int positionScore) {
        String strPosScore = String.valueOf(positionScore+1);
        if (positionScore > 0)
            strPosScore += "�me ";
        JOptionPane.showMessageDialog(this,"Vous avez fait un score de "+score+", c'est le "+strPosScore+"meilleur score de cette carte !", "Score dans le classement", JOptionPane.INFORMATION_MESSAGE);
    }

    public Joueur ajouterJoueur(String nom) {
        Joueur nouveauJoueur = new Joueur(nom);
        this.lJoueurs.add(nouveauJoueur);
        return nouveauJoueur;
    }
    
    public ArrayList<Joueur> getJoueurs() {
        return this.lJoueurs;
    }
    
    public void ajouterScore(int score) {
        for (int i = 0;i<this.carte.getMeilleursScores().length;i++) {
            if (score > this.carte.getMeilleursScores()[i]) {
                for (int j = this.carte.getMeilleursScores().length-1;j>=i+1;j--) {
                    this.carte.getMeilleursScores()[j] = this.carte.getMeilleursScores()[j-1];
                }
                this.carte.getMeilleursScores()[i] = score;
                return;
            }
        }
    }

    public int getPositionScoreDansScores(int score) {
        for (int i = this.carte.getMeilleursScores().length-1;i>=0;i--) {
            if (score == this.carte.getMeilleursScores()[i])
                return i;
        }
        return -1;
    }
    
    public void pondreFruit() {
        int fruitX, fruitY;
        Position fruitPos;
        int nombreEssais = 0;
        do {
            fruitX = (int)Math.floor(Math.random()*this.nombreCases);
            fruitY = (int)Math.floor(Math.random()*this.nombreCases);
            fruitPos = new Position(fruitX, fruitY);
            nombreEssais++;
        } while (!this.estPositionLibre(fruitPos) && nombreEssais <=10);
        if (!this.estPositionLibre(fruitPos)) {
            ArrayList<Position> lPositionsLibres = new ArrayList<Position>();
            Position currentPos;
            for (int i = 0;i<this.nombreCases;i++) {
                for (int j = 0;j<this.nombreCases;j++) {
                    currentPos = new Position(i,j);
                    if (this.estPositionLibre(currentPos))
                    lPositionsLibres.add(currentPos);
                }
            }
            if (lPositionsLibres.size() == 0)
            return;
            int positionAuHasard = (int)Math.floor(Math.random()*lPositionsLibres.size());
            fruitX = lPositionsLibres.get(positionAuHasard).getX();
            fruitY = lPositionsLibres.get(positionAuHasard).getY();
        }
        
        this.carte.addFruit(new Fruit(new Position(fruitX,fruitY,this.tailleCase), Outil.FRUIT.getCouleur()));
    }
    
    public boolean estPositionLibre(Position posTestee) {
        for (int i = 0;i<this.lJoueurs.size();i++) {
            if (posTestee.equals(this.lJoueurs.get(i).getSnake().getPosition()))
            return false;
            for (int j = 0;j<this.lJoueurs.get(i).getSnake().getTaille()-1;j++) {
                if (posTestee.equals(this.lJoueurs.get(i).getSnake().getMembre(j)))
                return false;
            }
        }
        for (int i = 0;i<this.carte.getMurs().size();i++) {
            if (posTestee.equals(this.carte.getMurs().get(i).getPosition()))
            return false;
        }
        for (int i = 0;i<this.carte.getFruits().size();i++) {
            if (posTestee.equals(this.carte.getFruits().get(i).getPosition()))
            return false;
        }
        return true;
    }
    
    public void initPositionsReelles() {
        int i;
        for (Joueur j : this.lJoueurs) {
            j.getSnake().getPosition().initPosReelle(this.tailleCase);
            for (Position p : j.getSnake().getMembres()) {
                p.initPosReelle(this.tailleCase);
            }
        }
        for (Fruit f : this.carte.getFruits()) {
            f.getPosition().initPosReelle(this.tailleCase);
        }
        
        for (Mur m : this.carte.getMurs()) {
            m.getPosition().initPosReelle(this.tailleCase);
        }
    }
    
    public int getGridPos(int n) {
        return (int)Math.round(n*this.tailleCase);
    }
    public Carte getCarte(){
        return this.carte;
    }
    
    public void setCarte(Carte carte) {
        this.carte = carte;
    }
    
    public boolean estMultijoueur() {
        return this.multijoueur;
    }
    
    public boolean estEnJeu() {
        return this.enJeu;
    }
    
    @Override
    public void keyPressed(KeyEvent key){
        if (this.compteArebours > 0)
        return;
        for (int i = 0;i<this.getJoueurs().size();i++) {
            if (key.getKeyCode() == this.getJoueurs().get(i).getControles()[0]) {
                this.getJoueurs().get(i).getSnake().setDir('N');
            }
            if (key.getKeyCode() == this.getJoueurs().get(i).getControles()[1]) {
                this.getJoueurs().get(i).getSnake().setDir('S');
            }
            if (key.getKeyCode() == this.getJoueurs().get(i).getControles()[2]) {
                this.getJoueurs().get(i).getSnake().setDir('W');
            }
            if (key.getKeyCode() == this.getJoueurs().get(i).getControles()[3]) {
                this.getJoueurs().get(i).getSnake().setDir('E');
            }
        }
        
        //DEBUG
        if (key.getKeyCode() == KeyEvent.VK_E){
            for(int i=0;i<this.getJoueurs().size();i++){
                this.getJoueurs().get(i).getSnake().agrandir();
            }
        }
        if (key.getKeyCode() == KeyEvent.VK_A)
        this.pondreFruit();
        
    }
    
    @Override
    public void keyReleased(KeyEvent key) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent key) {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public String getTheme() {
    	return this.theme;
    }
    
    public void setJoueurs(int nJoueurs) {
        Joueur joueurCourant;
        if (nJoueurs > this.carte.getNombreSnakes()) {
            System.out.println("Impossible d'instancier "+nJoueurs+" joueur"+((nJoueurs>1) ?"s":"")+" pour une carte avec seulement "+this.carte.getNombreSnakes()+" snake"+ ((this.carte.getNombreSnakes()>1) ?"s":""));
            return;
        }
        for (int i = 0;i<nJoueurs;i++) {
            joueurCourant = this.ajouterJoueur("Joueur "+(i+1));
            joueurCourant.getSnake().setPosition(this.carte.getSnakePos(i));
            
            if (i < Parametres.lControlesGeneraux.size())
            joueurCourant.setControles(Parametres.lControlesGeneraux.get(i));
            if (i < Parametres.lCouleursGenerales.size())
            joueurCourant.getSnake().setCouleur(Parametres.lCouleursGenerales.get(i));
        }
        this.initPositionsReelles();
        if (nJoueurs >=2)
        this.multijoueur = true;
    }
    
    public int getAPS() {
        return this.aps;
    }
    
}