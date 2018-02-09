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

public class EchiquierView extends Parent { // Repr�sentation graphique d'un �chiquier.
	
	private int posX; // Coordonn�es de la case
	private int posY;
	
	private CaseView[][] cases; // Tableau comprenant les cases de l'�chiquier
	
	public static int largeurEchiquier = 640; // Largeur de l'�chiquier (en pixels)
	public static int largeurCase = largeurEchiquier / 8; // Largeur d'une case

	
	
	public EchiquierView(int x , int y , Echiquier ech) { /* Initialisation de la vue de l'�chiquier 
										   * en fonction des coordonn�es de d�part pass�s en param�tre. */
		int i,j;
		Rectangle fond = new Rectangle();
		fond.setWidth(largeurEchiquier); // R�glage des dimensions de l'�chiquier
		fond.setHeight(largeurEchiquier);
		
		
		
		this.posX = x;
		this.posY = y;
		this.setTranslateX(posX); // Positionnement de la vue
		this.setTranslateY(posY);
		
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
		Color marron = new Color(0.655, 0.431, 0.216, 1);
		Color clair = new Color(0.98, 0.827, 0.565, 1);
		
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
	
	

}
