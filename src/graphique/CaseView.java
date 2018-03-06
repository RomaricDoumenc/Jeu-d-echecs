package graphique;

import classes.Cavalier;
import classes.Couleur;
import classes.Dame;
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

public class CaseView extends Parent {  // Représentation graphique d'une case d'un échiquier.
	
	private int posX; // Coordonnées de la case
	private int posY;
	private Rectangle fond; // Fond de la case
	private ImageView image; // Image de fond de la case qui représentera la pièce
	

	public CaseView(int x , int y , Color coul , Piece p) { 
		fond = new Rectangle();
		fond.setWidth(EchiquierView.largeurCase);
		fond.setHeight(EchiquierView.largeurCase);
		this.posX = x; // Positionnement de la case
		this.posY = y;
		fond.setTranslateX(posX);
		fond.setTranslateY(posY);
		fond.setFill(coul); // Remplissage de la couleur de fond de la case
		
		this.image = null;
		
		if(p != null) { // Sélcetion de l'image à afficher en fonction de la pièce à afficher
			if(p instanceof Pion) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/pionBlanc.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/pionNoir.png")));
			}
			if(p instanceof Cavalier) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/cavalierBlanc.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/cavalierNoir.png")));
			}
			if(p instanceof Fou) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/fouBlanc.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/fouNoir.png")));
			}
			if(p instanceof Roi) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/roiBlanc.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/roiNoir.png")));
			}
			if(p instanceof Dame) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/dameBlanche.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/dameNoire.png")));
			}
			if(p instanceof Tour) {
				if(p.getCoul() == Couleur.BLANC)
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/tourBlanche.png")));
				else
					image = new ImageView(new Image(getClass().getResourceAsStream("/images/tourNoire.png")));
			}
		}
		
		this.getChildren().add(fond);
		
		if(this.image != null) { // Positionnement et affichage de l'image (s'il y a une pièce sur cette case)
			this.image.setTranslateX(posX);
			this.image.setTranslateY(posY);
			this.getChildren().add(image);
			
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


	public Rectangle getFond() {
		return fond;
	}


	public void setFond(Rectangle fond) {
		this.fond = fond;
	}


	public ImageView getImage() {
		return image;
	}


	public void setImage(ImageView image) {
		this.image = image;
	}
	
	

}
