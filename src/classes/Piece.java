package classes;

public abstract class Piece { // Classe de base sur laquelle vont se baser les types des pièces 
							  // du jeu d'échecs.
	
	protected int x; // Coordonnées de la pièce sur l'échiquier
	protected int y; // x = ligne ; y = colonne
	
	protected Couleur coul; // Couleur de la pièce
	protected Echiquier ech; // Echiquier sur lequel est posé la pièce
	protected Joueur j; // Joueur propriétaire de la pièce
	
	public Piece(int x , int y , Couleur coul , Echiquier ech , Joueur j) {
		this.x = x;
		this.y = y;
		this.coul = coul;
		this.ech = ech;
		this.j = j;
	}
	
	public abstract void seDeplacer(int xArr , int yArr); // Déplacer la pièce sur l'échiquier (si c'est possible)

	
	
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
