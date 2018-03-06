package classes;

import java.io.Serializable;

public abstract class Piece implements Serializable { // Classe de base sur laquelle vont se baser les types des pi�ces 
							  // du jeu d'�checs.
	
	private static final long serialVersionUID = 1L;
	protected int x; // Coordonn�es de la pi�ce sur l'�chiquier
	protected int y; // x = ligne ; y = colonne
	
	protected Couleur coul; // Couleur de la pi�ce
	protected Echiquier ech; // Echiquier sur lequel est pos� la pi�ce
	protected Joueur j; // Joueur propri�taire de la pi�ce
	
	public Piece(int x , int y , Couleur coul , Echiquier ech , Joueur j) {
		this.x = x;
		this.y = y;
		this.coul = coul;
		this.ech = ech;
		this.j = j;
	}
	
	public abstract boolean seDeplacer(int xArr , int yArr); // D�placer la pi�ce sur l'�chiquier (si c'est possible)

	protected void bougerPieceSurEchiquier(int xDep , int yDep , int xArr , int yArr) {
		// Met � jour l'�chiquier en faisant bouger la pi�ce
		this.ech.getPieces()[xArr][yArr] = this; // Placement de la pi�ce sur la case d'arriv�e
		this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case d'origine
		this.x = xArr; // Mise � jour de la position de la pi�ce
		this.y = yArr;
	}
	
	protected void capturerAdversaire(int xArr , int yArr) {
		// Capture la pi�ce adverse situ� sur la case pass�e en param�tre
		Piece pieceASuppr = this.ech.getPieces()[xArr][yArr];
		pieceASuppr.getJ().supprimerPiece(pieceASuppr);
	}
	
	protected abstract boolean trajectoireLibre(int xDep , int yDep , int xArr , int yArr);
	// V�rifie s'il n'y a pas d'autre pi�ce dans la trajectoire que la pi�ce va emprunter
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Couleur getCoul() {
		return coul;
	}

	public void setCoul(Couleur coul) {
		this.coul = coul;
	}

	public Echiquier getEch() {
		return ech;
	}

	public void setEch(Echiquier ech) {
		this.ech = ech;
	}

	public Joueur getJ() {
		return j;
	}

	public void setJ(Joueur j) {
		this.j = j;
	}
	
	
}
