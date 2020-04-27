package Snake;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.*;
import javax.swing.*;

public class Launcher extends JFrame implements ActionListener, KeyListener {
    
	public final static int MAX_JOUEURS = 8;
	private final static int N_MEILLEURS_SCORES = 10;
    private static final String RECORDS_FILE = "records";
    private static final String THEME_FOLDER = "Themes";


	private JPanel panneauPrincipal;
	private JPanel panneauTitre;
	private JPanel panneauCredits;
	private JPanel panneauMenu;
	private JPanel panneauBordure;
	private JPanel panneauChoixNombreJoueurs;
	private JPanel panneauChoixCarte;
	private JPanel panneauChoixTheme;
    private JPanel panneauChoixDifficulte;
	private JPanel panneauParametres;
	private JPanel panneauJeuMenu;
    private JPanel panneauRetourMenu;


	private JButton bSolo;
	private JButton bMulti;
	private JButton bGestionCartes;
	private JButton bParametres;
	private JButton bRetourMenuPrincipal;
	private JButton bRetourJeuMenu;
	private JButton bCredits;

	//private JLabel lCompteAreboursAvantLancement;

	private JComboBox<String> cbSelectionNombreJoueurs;
	private JComboBox<String> cbSelectionCarte;
    public JComboBox<String> cbSelectionDifficulte;

	private JButton bValiderSelectionNombreJoueurs;
	private JButton bValiderSelectionCarte;

    private JButton bAfficherScoresCarte;

	private JCheckBox ckbParamSon;

	private JComboBox cbParamJoueurSelectionne;

	private JTextField tfParamControleHaut;
	private JTextField tfParamControleBas;
	private JTextField tfParamControleGauche;
	private JTextField tfParamControleDroite;
	private int hiddenParamControleHaut;
	private int hiddenParamControleBas;
	private int hiddenParamControleGauche;
	private int hiddenParamControleDroite;
	private JButton bEnregistrerParametres;

	private JComboBox cbParamCouleur;

	public JLabel limageSnake;
	public JLabel lTheme;

	public JComboBox<String> cbThemes;

	private ImageIcon imageSnake;

	private int nJoueurs;
    
    public Launcher(){ 	
        Parametres.chargerParametres();
        this.initWidgets();
        this.deployerMenuPrincipal();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setThemeComboBox();
        this.setSize(800,500);
        this.setResizable(false);
        this.setTitle("Snake Projet S2");
        this.setVisible(true);
        this.setIconImage(new ImageIcon("Images/menuSnake.png").getImage());
        this.setFocusable(true);
    }
    
    public void initWidgets(){
    	try {
    	javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName());
    	}catch(Exception e) {
    		
    	}
        this.panneauPrincipal = new JPanel();
        this.panneauMenu = new JPanel();
        this.panneauTitre = new JPanel();
        this.panneauCredits = new JPanel();
        this.panneauBordure = new JPanel();
        this.panneauChoixCarte = new JPanel();
        this.panneauChoixTheme = new JPanel();
        this.panneauChoixDifficulte = new JPanel();
        this.panneauChoixNombreJoueurs = new JPanel();
        this.panneauParametres = new JPanel();
        this.panneauRetourMenu = new JPanel();
        this.panneauJeuMenu = new JPanel();
        
        this.panneauPrincipal.setLayout(new GridLayout(2,1));
        this.panneauCredits.setLayout(new GridLayout(4,1));
     
		this.lTheme = new JLabel();
		this.cbThemes = new JComboBox<String>();

        this.bCredits = new JButton("Crédits");
        this.bRetourJeuMenu = new JButton("Retour");
        this.bRetourJeuMenu.addActionListener(this);
        
        JLabel lTitre = new JLabel("Snake");
        this.imageSnake = new ImageIcon("Images/menuSnake.png");
        this.limageSnake = new JLabel(imageSnake);
        lTitre.setForeground(new Color(0,200,0));
        lTitre.setFont(new Font("Sans Serif", Font.PLAIN, 100));
        JLabel lcreditsProjet = new JLabel("Projet tutoré Semestre 2");
        lcreditsProjet.setFont(new Font("Serif", Font.BOLD, 20));
        

        this.cbSelectionCarte = new JComboBox<String>();
        this.cbSelectionNombreJoueurs = new JComboBox<String>();
        this.cbSelectionDifficulte  = new JComboBox<String>();
        this.cbSelectionDifficulte.addItem("Limace");
        this.cbSelectionDifficulte.addItem("Calme");
        this.cbSelectionDifficulte.addItem("Facile");
        this.cbSelectionDifficulte.addItem("Normal");
        this.cbSelectionDifficulte.addItem("Difficile");
        this.cbSelectionDifficulte.addItem("Très difficile");
        this.cbSelectionDifficulte.addItem("Apocalypse");
        this.cbSelectionDifficulte.setSelectedIndex(3);
         
        this.bValiderSelectionNombreJoueurs = new JButton("Valider");
        this.bValiderSelectionCarte = new JButton("Jouer");
        
        this.bSolo = new JButton("Solo");
        this.bMulti = new JButton("Multijoueurs");
        this.bGestionCartes = new JButton("Gérer les cartes");
        this.bParametres = new JButton("Paramètres");
        this.bEnregistrerParametres = new JButton("Enregistrer");
        this.bRetourMenuPrincipal = new JButton("Retour");
        this.bAfficherScoresCarte = new JButton("Meilleurs scores");


        
        this.bSolo.addActionListener(this);
        this.bMulti.addActionListener(this);
        this.bGestionCartes.addActionListener(this);
        this.bParametres.addActionListener(this);
        this.bValiderSelectionCarte.addActionListener(this);
        this.bValiderSelectionNombreJoueurs.addActionListener(this);
        this.bEnregistrerParametres.addActionListener(this);
        this.bRetourMenuPrincipal.addActionListener(this);
        this.bCredits.addActionListener(this);
        this.bAfficherScoresCarte.addActionListener(this);
        
        //this.lCompteAreboursAvantLancement = new JLabel();
        //this.lCompteAreboursAvantLancement.setVisible(false);
        
        JLabel lChoixCartes = new JLabel("Choisissez une carte");
        this.panneauChoixCarte.add(lChoixCartes);
        this.panneauChoixCarte.add(this.cbSelectionCarte);


        JLabel lChoixDifficulte = new JLabel("Choisissez une difficulté");
        this.panneauChoixDifficulte.add(lChoixDifficulte);
        this.panneauChoixDifficulte.add(this.cbSelectionDifficulte);


        //this.panneauChoixCarte.add(this.lCompteAreboursAvantLancement);
        
		JLabel lChoixThemes = new JLabel("Choisissez un thème");
		this.panneauChoixTheme.add(lChoixThemes);
		this.panneauChoixTheme.add(this.cbThemes);

        this.panneauJeuMenu.add(this.bValiderSelectionCarte);
        this.panneauJeuMenu.add(this.bAfficherScoresCarte);
        this.panneauJeuMenu.add(this.bRetourJeuMenu);

        JLabel lNombreJoueurs = new JLabel("Choisissez le nombre de joueurs");
        this.panneauChoixNombreJoueurs.add(lNombreJoueurs);
        this.panneauChoixNombreJoueurs.add(this.cbSelectionNombreJoueurs);
        this.panneauChoixNombreJoueurs.add(this.bValiderSelectionNombreJoueurs);
        
        
        this.ckbParamSon = new JCheckBox("Son activé", Parametres.sonActive);
        this.cbParamJoueurSelectionne = new JComboBox<Object>();
        for (int i = 1;i<= MAX_JOUEURS;i++) {
            this.cbParamJoueurSelectionne.addItem("Joueur "+i);
        }
        
        this.cbParamJoueurSelectionne.addActionListener(this);
        this.ckbParamSon.addActionListener(this);
        
        this.tfParamControleHaut = new JTextField(4);
        this.tfParamControleBas = new JTextField(4);
        this.tfParamControleGauche = new JTextField(4);
        this.tfParamControleDroite = new JTextField(4);
        this.tfParamControleHaut.setEditable(false);
        this.tfParamControleBas.setEditable(false);
        this.tfParamControleGauche.setEditable(false);
        this.tfParamControleDroite.setEditable(false);
        this.tfParamControleHaut.addKeyListener(this);
        this.tfParamControleBas.addKeyListener(this);
        this.tfParamControleGauche.addKeyListener(this);
        this.tfParamControleDroite.addKeyListener(this);
        
        JLabel lControleHaut   = new JLabel("Haut");
        JLabel lControleBas    = new JLabel("Bas");
        JLabel lControleGauche = new JLabel("Gauche");
        JLabel lControleDroite = new JLabel("Droite");
        JLabel lCouleur        = new JLabel("Couleur");
        
        this.cbParamCouleur = new JComboBox<String>();
        for (int i = 0;i<Parametres.lCouleursChaine.length;i++) {
            this.cbParamCouleur.addItem(Parametres.lCouleursChaine[i]);
        }
        
        this.panneauParametres.add(this.ckbParamSon);
        this.panneauParametres.add(this.cbParamJoueurSelectionne);
        this.panneauParametres.add(lControleHaut);
        this.panneauParametres.add(this.tfParamControleHaut);
        this.panneauParametres.add(lControleBas);
        this.panneauParametres.add(this.tfParamControleBas);
        this.panneauParametres.add(lControleGauche);
        this.panneauParametres.add(this.tfParamControleGauche);
        this.panneauParametres.add(lControleDroite);
        this.panneauParametres.add(this.tfParamControleDroite);
        this.panneauParametres.add(lCouleur);
        this.panneauParametres.add(this.cbParamCouleur);
        this.panneauParametres.add(this.bEnregistrerParametres);
        
    
        this.panneauRetourMenu.add(this.bRetourMenuPrincipal);
        
        //panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal,BoxLayout.Y_AXIS));
        this.panneauPrincipal.add(this.panneauTitre);
        this.panneauTitre.add(this.limageSnake);
        this.panneauPrincipal.add(this.panneauBordure);
        this.panneauBordure.add(this.panneauMenu);
        
        //this.panneauTitre.setLayout(new BoxLayout(panneauTitre,BoxLayout.Y_AXIS));
        this.panneauTitre.add(lTitre);
        
        this.panneauMenu.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
        
        this.panneauMenu.setLayout(new GridLayout(5,1));
        this.panneauMenu.add(this.bSolo);
        this.panneauMenu.add(this.bMulti);
        this.panneauMenu.add(this.bParametres);
        this.panneauMenu.add(this.bGestionCartes);
        this.panneauMenu.add(this.bCredits);

    }

    public void setThemeComboBox(){
		this.cbThemes.removeAllItems();
		File dossierThemes = new File(THEME_FOLDER);
		for (File fichier : dossierThemes.listFiles()) {
			this.cbThemes.addItem(fichier.getName());
		}
	}
    
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == this.bSolo){
            this.nJoueurs = 1;
            this.deployerChoixCartes();
        }
        
        if(event.getSource() == bCredits){
            JOptionPane.showMessageDialog(this, "Snake projet tutoré\nJAUGEY Nohan\nBOULLE Benjamin\n\nIUT de Belfort-Montbéliard\nDépartement Informatique","Crédits", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if(event.getSource() == this.bMulti){
            this.deployerChoixNombreJoueurs();
        }
        
        else if (event.getSource() == this.bParametres) {
            this.deployerParametres();
        }
        
        else if (event.getSource() == this.bValiderSelectionNombreJoueurs) {
            this.nJoueurs = Integer.valueOf((String)this.cbSelectionNombreJoueurs.getSelectedItem());
            this.deployerChoixCartes();
            
        }
        
        else if (event.getSource() == this.bGestionCartes) {
            this.setVisible(false);
            new EditeurCarte(this);
        }

        else if (event.getSource() == this.bAfficherScoresCarte) {
            String listeScores = "";
            Carte carteTemp = new Carte((String)this.cbSelectionCarte.getSelectedItem());
            carteTemp.chargerMetadonnees();
            for (int i = 0;i<carteTemp.getMeilleursScores().length;i++) {
                listeScores += (carteTemp.getMeilleursScores()[i] + "\n");
            }
            JOptionPane.showMessageDialog(this, listeScores, "Meilleurs scores pour "+carteTemp.getNom(), JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if (event.getSource() == this.ckbParamSon) {
            Parametres.sonActive = this.ckbParamSon.isSelected();
            Parametres.enregistrerParametres();
        }
        
        else if (event.getSource() == this.bEnregistrerParametres) {
            int index = this.cbParamJoueurSelectionne.getSelectedIndex();
            Parametres.lControlesGeneraux.get(index)[0] = this.hiddenParamControleHaut;
            Parametres.lControlesGeneraux.get(index)[1] = this.hiddenParamControleBas;
            Parametres.lControlesGeneraux.get(index)[2] = this.hiddenParamControleGauche;
            Parametres.lControlesGeneraux.get(index)[3] = this.hiddenParamControleDroite;
            Parametres.lCouleursGenerales.set(index,Parametres.getCouleurFromChaine((String)this.cbParamCouleur.getSelectedItem()));
            JOptionPane.showMessageDialog(this, "Les paramètres pour le joueur "+(index+1)+" ont été enregistrés", "Informations enregistrées", JOptionPane.INFORMATION_MESSAGE);
            Parametres.enregistrerParametres();
        }
        
        else if (event.getSource() == this.cbParamJoueurSelectionne) {
            this.remplirParametresJoueur(this.cbParamJoueurSelectionne.getSelectedIndex());
        }
        
        else if (event.getSource() == this.bValiderSelectionCarte) {
            //this.lCompteAreboursAvantLancement.setText("3");
            //this.lCompteAreboursAvantLancement.setVisible(true);
            //Timer timer = new Timer(1000,null);
            //Launcher localLauncher = this;
            //timer.addActionListener(new ActionListener() {
                //    public void actionPerformed(ActionEvent evt) {
                    //lCompteAreboursAvantLancement.setText(String.valueOf(Integer.valueOf(lCompteAreboursAvantLancement.getText())-1));
                    //if (lCompteAreboursAvantLancement.getText().equals("0")) {
                        //timer.stop();
                        this.deployerMenuPrincipal();
                        this.setVisible(false);
                        Application app = new Application((String)cbSelectionCarte.getSelectedItem(), this);
                        app.setJoueurs(nJoueurs);
                        Thread t = new Thread(app);
                        t.start();
                        //lCompteAreboursAvantLancement.setVisible(false);
                    //}
                //   }
            //});
            //timer.start();
        }
        
        else if (event.getSource() == this.bRetourMenuPrincipal || event.getSource() == this.bRetourJeuMenu) {
            this.deployerMenuPrincipal();
        }
    }
    
	public void deployerChoixCartes() {
		this.panneauPrincipal.removeAll();
		this.setCarteComboBox();
		this.panneauPrincipal.setLayout(new GridLayout(4,1));
		this.panneauPrincipal.add(this.panneauChoixCarte);
		this.panneauPrincipal.add(this.panneauChoixTheme);
        this.panneauPrincipal.add(this.panneauChoixDifficulte);
		this.panneauPrincipal.add(this.panneauJeuMenu);
		this.setContentPane(this.panneauPrincipal);
	}
    
    public void deployerChoixNombreJoueurs() {
        this.panneauPrincipal.removeAll();
        this.setNombreJoueursComboBox();
        this.panneauPrincipal.add(this.panneauRetourMenu);
        this.panneauPrincipal.add(this.panneauChoixNombreJoueurs);
        this.setContentPane(this.panneauPrincipal);
    }
    
    public void deployerParametres(){
        this.panneauPrincipal.removeAll();
        this.remplirParametresJoueur(0);
        this.panneauPrincipal.add(this.panneauRetourMenu);
        this.panneauPrincipal.add(this.panneauParametres);
        this.setContentPane(this.panneauPrincipal);
    }
    
    public void deployerMenuPrincipal(){
        this.panneauPrincipal.removeAll();
        this.panneauPrincipal.setLayout(new GridLayout(3,1));
        this.panneauPrincipal.add(this.panneauTitre);
        this.panneauPrincipal.add(this.panneauBordure);
        this.setContentPane(this.panneauPrincipal);
    }
    
    public void remplirParametresJoueur(int numeroJoueur) {
        if (Parametres.lControlesGeneraux.size() >= numeroJoueur) {
            this.tfParamControleHaut.setText(KeyEvent.getKeyText(Parametres.lControlesGeneraux.get(numeroJoueur)[0]));
            this.tfParamControleBas.setText(KeyEvent.getKeyText(Parametres.lControlesGeneraux.get(numeroJoueur)[1]));
            this.tfParamControleGauche.setText(KeyEvent.getKeyText(Parametres.lControlesGeneraux.get(numeroJoueur)[2]));
            this.tfParamControleDroite.setText(KeyEvent.getKeyText(Parametres.lControlesGeneraux.get(numeroJoueur)[3]));
            this.hiddenParamControleHaut = Parametres.lControlesGeneraux.get(numeroJoueur)[0];
            this.hiddenParamControleBas = Parametres.lControlesGeneraux.get(numeroJoueur)[1];
            this.hiddenParamControleGauche = Parametres.lControlesGeneraux.get(numeroJoueur)[2];
            this.hiddenParamControleDroite = Parametres.lControlesGeneraux.get(numeroJoueur)[3];
        }
        if (Parametres.lCouleursGenerales.size() >= numeroJoueur)
        this.cbParamCouleur.setSelectedItem(Parametres.getChaineFromCouleur(Parametres.lCouleursGenerales.get(numeroJoueur)));
    }
    
    public void setCarteComboBox() {
        String carteSelectionne = (String)this.cbSelectionCarte.getSelectedItem();
        this.cbSelectionCarte.removeAllItems();
        for (Carte carte : Carte.lCartes) {
            if (carte.getNombreSnakes() >= this.nJoueurs) {
                this.cbSelectionCarte.addItem(carte.getNom());
                if (carte.getNom().equals(carteSelectionne))
                    this.cbSelectionCarte.setSelectedItem(carte.getNom());
            }
        }
    }
    
    public void setNombreJoueursComboBox() {
        int joueursMax = 0;
        this.cbSelectionNombreJoueurs.removeAllItems();
        for (Carte carte : Carte.lCartes) {
            if (carte.getNombreSnakes() > joueursMax)
            joueursMax = carte.getNombreSnakes();
        }
        for (int i = 2;i<=joueursMax;i++) {
            this.cbSelectionNombreJoueurs.addItem(String.valueOf(i));
        }
    }
    
    @Override
    public void keyPressed(KeyEvent key){
        JTextField tfFocusOwner = (JTextField)getFocusOwner();
        tfFocusOwner.setText(KeyEvent.getKeyText(key.getKeyCode()));
        if (tfFocusOwner == this.tfParamControleHaut)
        this.hiddenParamControleHaut = key.getKeyCode();
        else if (tfFocusOwner == this.tfParamControleBas)
        this.hiddenParamControleBas = key.getKeyCode();
        else if (tfFocusOwner == this.tfParamControleGauche)
        this.hiddenParamControleGauche = key.getKeyCode();
        else if (tfFocusOwner == this.tfParamControleDroite)
        this.hiddenParamControleDroite = key.getKeyCode();
    }
    
    
    @Override
    public void keyReleased(KeyEvent key) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent key) {
        
    }
    
    public void afficher() {
        this.deployerChoixCartes();
        this.revalidate();
        this.setVisible(true);
    }

    
    public static void main(String[] args){
        new Launcher();
    }
    
}