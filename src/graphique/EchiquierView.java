package graphique;


import classes.Echiquier;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EchiquierView extends Parent { // Repr�sentation graphique d'un �chiquier.
	
	private int posX; // Coordonn�es de la case
	private int posY;
	
	private int nbClics; // Nombre de fois o� on a cliqu� sur l'�chiquier
	
	private int xDep,yDep,xArr,yArr; // Coordonn�es des cases de d�part et d'arriv�e de la pi�ce qu'on veut d�placer
	
	private Text joueurActuel; // Texte o� est affich� le joueur actuel
	
	private Text[] numLigne; // Textes o� seront affich�s les num�ros, de lignes (de 1 � 8 , de bas en haut)
	private Text[] numColonne; // Textes o� seront affich�s les num�ros, de lignes (de A � H , de gauche � droite)
	
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
		
		this.numLigne = new Text[8];
		this.numColonne = new Text[8];
		
		for(i=0 ; i<8 ; i++) {
			numLigne[i] = new Text(Integer.toString(8 - i)); // Initialisation des textes de 1 � 8
		}
		
		numColonne[0] = new Text("A");
		numColonne[1] = new Text("B");
		numColonne[2] = new Text("C");
		numColonne[3] = new Text("D");
		numColonne[4] = new Text("E");
		numColonne[5] = new Text("F");
		numColonne[6] = new Text("G");
		numColonne[7] = new Text("H");
			
		
		
		
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
		for(i=0 ; i<8 ; i++) {
			numLigne[i].setTranslateX(-police.getSize());
			numLigne[i].setTranslateY(largeurCase * i + largeurCase / 2);
			numLigne[i].setFont(police);
			this.getChildren().add(numLigne[i]);
			
			numColonne[i].setTranslateX(largeurCase * i + largeurCase / 2);
			numColonne[i].setTranslateY(-police.getSize() / 2);
			numColonne[i].setFont(police);
			this.getChildren().add(numColonne[i]);
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
		
		for(i=0 ; i<8 ; i++) { // Ecrasement de toutes les cases par des nouvelles
							   // car il est impossible de modifier les �l�ments � afficher de chaque case 
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
	
	
	public void animerPiece(int xDep , int yDep , int xArr , int yArr , Echiquier ech) {
		
		// Permet d'animer les pi�ces lorsqu'elles se d�placent ou lorsqu'elles sont captur�es.
		
		ImageView image = cases[xDep][yDep].getImage();
		if((image != null) && ((xDep != xArr) || (yDep != yArr))) {
			// Translation de la case de d�part � la case d'arriv�e
								
			Duration duree = Duration.seconds(1);
			TranslateTransition deplacement = new TranslateTransition();
			deplacement.setDuration(duree);
			deplacement.setNode(image);
			deplacement.setToX(largeurCase * yArr);
			deplacement.setToY(largeurCase * xArr);
			
			if(cases[xArr][yArr].getImage() != null) {
				// R�tr�cissement progressif de l'image de la pi�ce captur�e jusqu'� sa "disparition" compl�te
				ScaleTransition capture = new ScaleTransition();
				capture.setDuration(duree);
				capture.setNode(cases[xArr][yArr].getImage());
				capture.setToX(0);
				capture.setToY(0);
				capture.play();
			}
			deplacement.play();
			
			
	        
			deplacement.setOnFinished((e) -> {
				rafraichirAffichage(ech);
			});
			
		}
		else
			rafraichirAffichage(ech);
			
		
	}
	/*public void montrerDeplacementsPossibles(Echiquier ech , int x , int y) {
		// Montre les cases possibles en vert
		Piece p = ech.getPieces()[x][y];
		if (p != null) {
			if(p instanceof Pion) {
				if(p.getCoul() == Couleur.BLANC) {
					int xPion = p.getX();
					int yPion = p.getY();
					if (ech.getPieces()[xPion-1][yPion] == null)
						this.cases[xPion-1][yPion].getFond().setFill(Color.GREEN);
					if ((ech.getPieces()[xPion-1][yPion] == null) && (ech.getPieces()[xPion-2][yPion] == null)
						&& ((Pion) p).isDejaBouge() == false)
						this.cases[xPion-2][yPion].getFond().setFill(Color.GREEN);
					if ((ech.getPieces()[xPion-1][yPion+1] != null) && (ech.getPieces()[xPion-1][yPion+1].getCoul() != p.getCoul()))
						this.cases[xPion-1][yPion+1].getFond().setFill(Color.RED);
					if ((ech.getPieces()[xPion-1][yPion-1] != null) && (ech.getPieces()[xPion-1][yPion-1].getCoul() != p.getCoul()))
						this.cases[xPion-1][yPion-1].getFond().setFill(Color.RED);
				}
				
			}
		}
		
	}*/
	
	
	

}
