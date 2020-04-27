package Snake;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class EditeurCarte extends JFrame implements ActionListener, KeyListener, MouseListener {
	final int WIN = 800;
	
	private int nombreCases;
	private double tailleCase;
	
	private JPanel panneauPrincipal;

	private JPanel panneauMenuGestionCartes;

	private JPanel panneauMenuEditionSuppressionCartes;
	private JPanel panneauMenuCreationCarte;


	//private JPanel menu;

	private JTextField tfTitreNouvelleCarte;
	private JTextField tfTailleNouvelleCarte;

	private JLabel lNom;
	private JTextField tNom;
	private Outil outil;

	private JComboBox cbChoixCarte;

	private JButton bRetourMenuEditionSuppressionCartes;
	private JButton bEditerCarte;
	private JButton bSupprimerCarte;
	private JButton bVersMenuCreerCarte;
	private JButton bCreerCarte;
	private JButton bRetourLauncher;

	private MenuOutils menuOutils;

	private Graphics g;
	private CarteEditable carte;
	private boolean editionEnCours = false;

	private Launcher root;

	public EditeurCarte(){
		this.setTitle("Snake Editeur de carte");
		this.setIconImage(new ImageIcon("Images/menuSnake.png").getImage());
		this.setSize(WIN,WIN);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		this.addKeyListener(this);
		this.addMouseListener(this);
		//Timer tm = new Timer(100,this); 
		//tm.start();
		this.creerWidgets();
		this.setVisible(true);
		this.setResizable(false);
		this.deployerMenuEditionSuppressionCartes();
		/*if(param.equals("new")){
			System.out.println("Lancement de l'outil pour une nouvelle carte");
		}
		else if(param.equals("")) {
			System.out.println("Lancement de l'outil pour gerer les cartes");
		}
		else{
			System.out.println("Lancement de l'outil pour la carte '"+param+"'");
			//Changement du text du bouton en "Editer" et on eleve la possibilit√© de d√©finir une taille
			this.bCreerCarte.setText("Editer");
			this.tTaille.setVisible(false);
			this.lTaille.setVisible(false);
		}*/
	}

	public EditeurCarte(Launcher root) {
		this();
		this.root = root;
	}

	public void creerWidgets(){
		this.panneauPrincipal = new JPanel();
		this.panneauPrincipal.setLayout(new FlowLayout());
		this.panneauMenuGestionCartes = new JPanel();
		this.panneauMenuEditionSuppressionCartes = new JPanel();
		this.panneauMenuCreationCarte = new JPanel();


		this.cbChoixCarte = new JComboBox();
		for (int i = 0;i<Carte.lCartes.size();i++) {
			this.cbChoixCarte.addItem(Carte.lCartes.get(i).getNom());
		}

		this.bRetourMenuEditionSuppressionCartes = new JButton("Retour");
		this.bCreerCarte = new JButton("CrÈer");
		this.bVersMenuCreerCarte = new JButton("CrÈer une nouvelle carte");
		this.bEditerCarte = new JButton("Editer");
		this.bSupprimerCarte = new JButton("Supprimer");
		this.bRetourLauncher = new JButton("Retour menu principal");


		this.bRetourMenuEditionSuppressionCartes.addActionListener(this);
		this.bCreerCarte.addActionListener(this);
		this.bVersMenuCreerCarte.addActionListener(this);
		this.bEditerCarte.addActionListener(this);
		this.bSupprimerCarte.addActionListener(this);
		this.bRetourLauncher.addActionListener(this);




		JLabel lCreationCarte = new JLabel("CrÈation d'une nouvelle carte");
		JLabel lTitreNouvelleCarte = new JLabel("Titre de la nouvelle carte");
		JLabel lTailleNouvelleCarte = new JLabel("Taille");
		this.tfTitreNouvelleCarte = new JTextField(4);
		this.tfTailleNouvelleCarte = new JTextField(4);

        this.panneauMenuEditionSuppressionCartes.add(new JLabel("Selectionnez une carte: "));
        this.panneauMenuEditionSuppressionCartes.add(new JLabel());
        this.panneauMenuEditionSuppressionCartes.add(new JLabel());

		this.panneauMenuEditionSuppressionCartes.add(this.cbChoixCarte);
		this.panneauMenuEditionSuppressionCartes.add(this.bEditerCarte);
		this.panneauMenuEditionSuppressionCartes.add(this.bSupprimerCarte);
		this.panneauMenuEditionSuppressionCartes.add(new JLabel());
		this.panneauMenuEditionSuppressionCartes.add(this.bRetourLauncher);
		this.panneauMenuEditionSuppressionCartes.add(this.bVersMenuCreerCarte);


		this.panneauMenuCreationCarte.add(new JLabel());
		this.panneauMenuCreationCarte.add(lCreationCarte);
		this.panneauMenuCreationCarte.add(lTitreNouvelleCarte);
		this.panneauMenuCreationCarte.add(this.tfTitreNouvelleCarte);
		this.panneauMenuCreationCarte.add(lTailleNouvelleCarte);
		this.panneauMenuCreationCarte.add(this.tfTailleNouvelleCarte);
        this.panneauMenuCreationCarte.add(this.bRetourMenuEditionSuppressionCartes);
		this.panneauMenuCreationCarte.add(this.bCreerCarte);





		/*
		menu = new JPanel();
		menu.setLayout(new GridLayout(4,2));
		menu.add(lTitre);
		menu.add(new JLabel());
		menu.add(lNom);
		menu.add(tNom);
		menu.add(lTaille);
		menu.add(tTaille);
		menu.add(bCreerCarte);
		menu.add(bAnnuler);

		panneauPrincipal.add(menu);
		*/

		setContentPane(this.panneauPrincipal);

	}

	
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == this.bRetourLauncher) {
			this.fermerEditeur();
		}

		else if (e.getSource() == this.bVersMenuCreerCarte) {
			this.deployerMenuCreationCarte();
		}

		else if (e.getSource() == this.bRetourMenuEditionSuppressionCartes) {
			this.deployerMenuEditionSuppressionCartes();
		}

		else if(e.getSource() == this.bCreerCarte) {

			if (!Carte.carteExiste((String)this.tfTitreNouvelleCarte.getText())) {
				String nomCarte = this.tfTitreNouvelleCarte.getText();
				int tailleCarte = 0;
				try {
					tailleCarte = Integer.valueOf(this.tfTailleNouvelleCarte.getText());
				}
				catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(this, "La taille de la carte doit Ítre un entier", "Erreur crÈation carte", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (nomCarte.length() >= 3 && nomCarte.length() <= 30) {
					if (tailleCarte >= 10 && tailleCarte <= 200) {
						this.creerCarte(nomCarte, tailleCarte);
					}
					else {
						JOptionPane.showMessageDialog(this, "La taille de la carte doit Ítre comprise entre 10 et 200", "Erreur crÈation carte", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Le nom de la carte doit comprendre entre 3 et 30 caract√®res", "Erreur cr√©ation carte", JOptionPane.ERROR_MESSAGE);					
					return;
				}
				
			}
			else {
				JOptionPane.showMessageDialog(this, "Une carte de ce nom existe dÈja†", "Erreur crÈation carte", JOptionPane.ERROR_MESSAGE);				
				return;
			}
		}

		else if (e.getSource() == this.bEditerCarte) {
			String nomCarte = (String)this.cbChoixCarte.getSelectedItem();
			if (!Carte.carteExiste(nomCarte)) {
				System.out.println("Erreur, la carte demandÈe n'existe pas");
			}
			else {
				this.editerCarteExistante(nomCarte);
			}
		}

		else if (e.getSource() == this.bSupprimerCarte) {
			String nomCarte = (String)this.cbChoixCarte.getSelectedItem();
			if (!Carte.carteExiste(nomCarte)) {
				System.out.println("Erreur, la carte demandÈe n'existe pas");
			}
			else {
				int reponse = JOptionPane.showConfirmDialog(this, "Ítes-vous s˚r de vouloir supprimer la carte "+nomCarte+" ?", "Demande de confirmation", JOptionPane.YES_NO_OPTION);
				if (reponse == JOptionPane.YES_OPTION) {
					Carte.supprimerCarte(nomCarte);
					this.cbChoixCarte.removeItem(nomCarte);					
				}
			}
		}
	}

	public void update(){
		//Dessin des entit√©s
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIN,WIN);
		this.dessinerGrille();

		int x, y;
		int tailleCaseInt = (int)Math.round(this.tailleCase);
		g.setColor(Outil.FRUIT.getCouleur());
		for(int i=0;i<carte.getFruits().size();i++){
			x = this.carte.getFruits().get(i).getPosition().getX();
			y = this.carte.getFruits().get(i).getPosition().getY();
			g.fillOval(
					this.getGrillePos(x),
					this.getGrillePos(y),
					tailleCaseInt,
					tailleCaseInt
			);
		}

		//    :Murs
		g.setColor(Outil.MUR.getCouleur());
		for(int i=0;i<carte.getMurs().size();i++){
			x = (carte.getMurs().get(i)).getPosition().getX();
			y = (carte.getMurs().get(i)).getPosition().getY();
			g.fillRect(
					this.getGrillePos(x),
					this.getGrillePos(y),
					tailleCaseInt,
					tailleCaseInt
			);
		}

		g.setColor(Outil.SNAKE.getCouleur());
		for(int i=0;i<carte.getSnakes().size();i++){
			x = (carte.getSnakes().get(i)).getPosition().getX();
			y = (carte.getSnakes().get(i)).getPosition().getY();
			g.fillRect(
					this.getGrillePos(x),
					this.getGrillePos(y),
					tailleCaseInt,
					tailleCaseInt
			);
		}
	}

	public void editerCarteExistante(String nomCarte){
		g = getGraphics();
		carte = new CarteEditable(nomCarte);
		carte.charger();
		this.editionEnCours=true;
		this.nombreCases=carte.getLargeur();
		this.tailleCase = (double)WIN/this.nombreCases;
		this.menuOutils = new MenuOutils(this, carte);
		this.deployerEditionCarte();
		this.update();
		panneauPrincipal.revalidate();
		panneauPrincipal.update(g);
		repaint();
		validate();
		panneauPrincipal.repaint();
	}

	public void creerCarte(String nomCarte, int tailleCarte){
		g = getGraphics();
		carte = new CarteEditable(nomCarte);
		carte.setLargeur(tailleCarte);
		carte.setNsnakes(0);
		carte.enregistrer();
		this.cbChoixCarte.addItem(nomCarte);
		this.editionEnCours=true;
		this.nombreCases=carte.getLargeur();
		this.tailleCase = (double)WIN/this.nombreCases;
		this.menuOutils = new MenuOutils(this, carte);
		this.deployerEditionCarte();
		this.update();

	}

	public int getGrillePos(int n) {
		return (int)Math.floor(n*this.tailleCase);
	}

	public CarteEditable getCarte(){
		return this.carte;
	}

	public int getCoordPos(int n){
		return (int) Math.floor(n/this.tailleCase);
	}

	public void dessinerGrille(){
		g.setColor(Color.BLACK);
		for(int i=0; i< this.nombreCases;i++){
			g.drawLine(0,this.getGrillePos(i), (int) Math.round(this.nombreCases*this.tailleCase), this.getGrillePos(i));
			g.drawLine(this.getGrillePos(i), 0, this.getGrillePos(i),(int) Math.round(this.nombreCases*this.tailleCase));
		}
	}

	@Override
	public void keyPressed(KeyEvent key){
		if(key.getKeyCode() == 65){ //A
			this.outil = Outil.MUR;
		}
		if(key.getKeyCode() == 90){ //Z
			this.outil = Outil.FRUIT;
		}
		if(key.getKeyCode() == 69){ //E
			this.outil = Outil.SNAKE;
		}
		if(key.getKeyCode() == 82){ //R
			this.outil = Outil.GOMME;
		}
	}

	@Override
	public void keyReleased(KeyEvent key){

	}

	@Override
	public void keyTyped(KeyEvent key){

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(editionEnCours) {
			this.update();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		if (!this.editionEnCours)
			return;
		int x = getCoordPos(e.getX());
		int y = getCoordPos(e.getY());

		this.menuOutils.setCarteSauvee(false);
		if (carte.getElementAtPosition(x,y) != null) {
				
				Object element = carte.getElementAtPosition(x,y);
				if(element instanceof Mur)
					carte.effacerMur((Mur)element);
				else if(element instanceof Fruit )
					carte.effacerFruit((Fruit)element);
				else if(element instanceof Snake)
					carte.effacerSnake((Snake)element);
				else if (this.outil != Outil.GOMME)
					this.menuOutils.setCarteSauvee(true);
		}
		else if (this.outil == Outil.GOMME)
			this.menuOutils.setCarteSauvee(true);

		if (this.outil == Outil.MUR) {
			carte.ajouterMur(new Mur(new Position(x,y), Outil.MUR.getCouleur()));
			System.out.println("Nouveau mur √† X: " + x+ " - Y: " + y);			
		}
		else if (this.outil == Outil.FRUIT) {
			carte.ajouterFruit(new Fruit(new Position(x,y), Outil.FRUIT.getCouleur()));
			System.out.println("Nouveau fruit √† X: " + x+ " - Y: " + y);			
		}
		else if (this.outil == Outil.SNAKE) {
			carte.ajouterSnake(new Snake(x, y));
			System.out.println("Nouveau snake √† X: " + x+ " - Y: " + y);			
		}

		this.update();
		this.revalidate();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public void setOutil(Outil outil) {
		this.outil = outil;
	}

	public void deployerMenuEditionSuppressionCartes() {
		this.panneauMenuGestionCartes.removeAll();
		this.panneauMenuEditionSuppressionCartes.setLayout(new GridLayout(3,3));
		this.panneauMenuGestionCartes.add(this.panneauMenuEditionSuppressionCartes);
		this.panneauPrincipal.removeAll();
		this.panneauPrincipal.add(this.panneauMenuGestionCartes);
		this.setContentPane(this.panneauPrincipal);
	}

	public void deployerMenuCreationCarte() {
		this.panneauMenuGestionCartes.removeAll();
        this.panneauMenuCreationCarte.setLayout(new GridLayout(4,2));
		this.panneauMenuGestionCartes.add(this.panneauMenuCreationCarte);
		this.panneauPrincipal.removeAll();
		this.panneauPrincipal.add(this.panneauMenuGestionCartes);
		this.setContentPane(this.panneauPrincipal);		
	}

	public void deployerEditionCarte() {
		this.panneauPrincipal.removeAll();
		this.setContentPane(this.panneauPrincipal);
	}

	public void terminerEdition() {
		this.editionEnCours = false;
		this.menuOutils = null;
		this.deployerMenuEditionSuppressionCartes();
	}

	public void fermerEditeur() {
		this.dispose();
		if (this.root != null)
			this.root.deployerMenuPrincipal();
			this.root.setVisible(true);
	}


	public static void main(String args[]){
		new EditeurCarte();
	}
}
