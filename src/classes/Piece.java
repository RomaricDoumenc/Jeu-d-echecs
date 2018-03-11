package classes;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public boolean estBloque() {
		int i,j;
		ArrayList<Piece> piecesAllies  = new ArrayList<Piece>(); /* Pi�ces alli�s du roi mis en �chec
																	ainsi que le roi lui-m�me */
		Pile pile = new Pile(); // Pile pour m�moriser l'�tat de d�part
		pile.empiler(this.ech); // On m�morise l'�tat actuel (�tat initial)
		piecesAllies.add(this);
		
		int xDep = this.x;
		int yDep = this.y;
		
		for(Piece p : piecesAllies) { // Tester tous les mouvements possibles par la pi�ce
			for(i=0 ; i<8 ; i++) {
				for(j=0 ; j<8 ; j++) {
					if((xDep != i) || (yDep != j)) { // On ne teste pas le d�placement sur elle-m�me
						// On teste le mouvement
						if(p.seDeplacer(i, j) == true) { // Si on trouve un d�placement possible pour la pi�ce
							for(Piece p2 : p.getJ().getPieces()) {
								if(p2 instanceof Roi)
									if(((Roi) p2).estEnEchec() == false) {
										pile.depiler(this.ech); // Retour � l'�tat initial
										return false; // Pas de blocage
									}
										
							}
							
							
						}
						else { // Sinon
							pile.depiler(this.ech); // Retour � l'�tat initial
							pile.empiler(this.ech); // On m�morise de nouveau l'�tat initial , passage � la situation suivante ou � la pi�ce alli�e suivante
						}
					}
					
						
				}
			}
				
		}
		
		
		return true; // Tous les mouvements test�s ne peuvent �tre jou�s , la pi�ce est bloqu�
	}
	
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
