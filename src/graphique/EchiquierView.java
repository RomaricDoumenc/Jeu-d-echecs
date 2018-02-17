package graphique;

import classes.Cavalier;
import classes.Couleur;
import classes.Dame;
import classes.Echiquier;
import classes.Fou;
import classes.Piece;
import classes.Pion;
import classes.Roi;
import classes.Tour;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EchiquierView extends Parent { // Repr�sentation graphique d'un �chiquier.
	
	private int posX; // Coordonn�es de la case
	private int posY;
	
	private int nbClics; // Nombre de fois o� on a cliqu� sur l'�chiquier
	
	private int xDep,yDep,xArr,yArr; // Coordonn�es des cases de d�part et d'arriv�e de la pi�ce qu'on veut d�placer
	
	private Text joueurActuel; // Texte o� est affich� le joueur actuel
	
	private CaseView[][] cases; // Tableau comprenant les cases de l'�chiquier
	
	public static int largeurEchiquier = 640; // Largeur de l'�chiquier (en pixels)
	public static int largeurCase = largeurEchiquier / 8; // Largeur d'une case

	
	
	public EchiquierView(int x , int y , Echiquier ech) { /* Initialisation de la vue de l'�chiquier 
										   * en fonction des coordonn�es de d�part pass�s en param�tre. */
		int i,j;
		this.nbClics = 0;
		Rectangle fond = new Rectangle();
		this.joueurActuel = new Text("Joueur actuel : "+ ech.getJoueurActuel().toString());
		fond.setWidth(largeurEchiquier); // R�glage des dimensions de l'�chiquier
		fond.setHeight(largeurEchiquier);
		
		
		
		this.posX = x;
		this.posY = y;
		this.setTranslateX(posX); // Positionnement de la vue
		this.setTranslateY(posY);
		Font police = new Font("Arial", 24); // Police du texte du joueur actuel
		joueurActuel.setFont(police);
		joueurActuel.setTranslateX(largeurEchiquier / 2); // Positionnement du texte du joueur actuel
		joueurActuel.setTranslateY(largeurEchiquier + police.getSize());
		this.getChildren().add(joueurActuel);
		
		cases = new CaseView[8][8];
		
		// Couleur des cases de l'�chiquier (marron et beige)
		Color marron = new Color(0.655, 0.431, 0.216, 1);
		Color clair = new Color(0.98, 0.827, 0.565, 1);
		
		for(i=0 ; i<8 ; i++) { // Positionnement des cases de l'�chiquier
			if (i % 2 == 0) {
				for(j=0 ; j<8 ; j++) { // Alternance case blanche/case noire sur les lignes paires 
					if (j % 2 == 0)
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, clair ,
								ech.getPieces()[i][j]);
					else
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, marron,
								ech.getPieces()[i][j]);
					this.getChildren().add(cases[i][j]);
				}
			}
			else {
				for(j=0 ; j<8 ; j++) { // Alternance case noire/case blanche sur les lignes impaires 
					if (j % 2 == 1)
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, clair ,
								ech.getPieces()[i][j]);
					else
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, marron ,
								ech.getPieces()[i][j]);
					this.getChildren().add(cases[i][j]);
				}
			}
			
		}
			
	}
	
	public void rafraichirAffichage(Echiquier ech) { // Actualiser l'affichage de la vue de l'�chiquier
		int i,j;
		if(ech.getJoueurActuel() != null) // Rafra�chir l'affichage du joueur actuel
			joueurActuel.setText("Joueur actuel : "+ ech.getJoueurActuel().toString());
		else
			joueurActuel.setText("");
		
		Color marron = new Color(0.655, 0.431, 0.216, 1);
		Color clair = new Color(0.98, 0.827, 0.565, 1);
		
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++)
				this.getChildren().remove(cases[i][j]); // Supression des anciennes cases
		
		for(i=0 ; i<8 ; i++) { /* Ecrasement de toutes les cases par des nouvelles
							    * car il est impossible de modifier les �l�ments � afficher de chaque case */
			if (i % 2 == 0) {
				for(j=0 ; j<8 ; j++) { // Alternance case blanche/case noire sur les lignes paires 
					if (j % 2 == 0)
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, clair ,
								ech.getPieces()[i][j]);
					else
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, marron,
								ech.getPieces()[i][j]);
					this.getChildren().add(cases[i][j]);
				}
			}
			else {
				for(j=0 ; j<8 ; j++) { // Alternance case noire/case blanche sur les lignes impaires 
					if (j % 2 == 1)
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, clair ,
								ech.getPieces()[i][j]);
					else
						cases[i][j] = new CaseView(largeurCase * j, largeurCase * i, marron ,
								ech.getPieces()[i][j]);
					this.getChildren().add(cases[i][j]);
				}
			}
			
		}
				
	}



	public int getPosX() {
		return posX;
	}



	public void setPosX(int posX) {
		this.posX = posX;
	}



	public int getPosY() {
		return posY;
	}



	public void setPosY(int posY) {
		this.posY = posY;
	}



	public CaseView[][] getCases() {
		return cases;
	}



	public void setCases(CaseView[][] cases) {
		this.cases = cases;
	}

	public int getNbClics() {
		return nbClics;
	}

	public void setNbClics(int nbClics) {
		this.nbClics = nbClics;
	}

	public int getxDep() {
		return xDep;
	}

	public void setxDep(int xDep) {
		this.xDep = xDep;
	}

	public int getyDep() {
		return yDep;
	}

	public void setyDep(int yDep) {
		this.yDep = yDep;
	}

	public int getxArr() {
		return xArr;
	}

	public void setxArr(int xArr) {
		this.xArr = xArr;
	}

	public int getyArr() {
		return yArr;
	}

	public void setyArr(int yArr) {
		this.yArr = yArr;
	}

	public Text getJoueurActuel() {
		return joueurActuel;
	}

	public void setJoueurActuel(Text joueurActuel) {
		this.joueurActuel = joueurActuel;
	}
	
	
	
	
	

}
