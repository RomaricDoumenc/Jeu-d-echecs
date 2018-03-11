package classes;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Piece implements Serializable { // Classe de base sur laquelle vont se baser les types des pièces 
							  // du jeu d'échecs.
	
	private static final long serialVersionUID = 1L;
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
	
	public abstract boolean seDeplacer(int xArr , int yArr); // Déplacer la pièce sur l'échiquier (si c'est possible)

	protected void bougerPieceSurEchiquier(int xDep , int yDep , int xArr , int yArr) {
		// Met à jour l'échiquier en faisant bouger la pièce
		this.ech.getPieces()[xArr][yArr] = this; // Placement de la pièce sur la case d'arrivée
		this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case d'origine
		this.x = xArr; // Mise à jour de la position de la pièce
		this.y = yArr;
	}
	
	protected void capturerAdversaire(int xArr , int yArr) {
		// Capture la pièce adverse situé sur la case passée en paramètre
		Piece pieceASuppr = this.ech.getPieces()[xArr][yArr];
		pieceASuppr.getJ().supprimerPiece(pieceASuppr);
	}
	
	protected abstract boolean trajectoireLibre(int xDep , int yDep , int xArr , int yArr);
	// Vérifie s'il n'y a pas d'autre pièce dans la trajectoire que la pièce va emprunter
	
	public boolean estBloque() {
		int i,j;
		ArrayList<Piece> piecesAllies  = new ArrayList<Piece>(); /* Pièces alliés du roi mis en échec
																	ainsi que le roi lui-même */
		Pile pile = new Pile(); // Pile pour mémoriser l'état de départ
		pile.empiler(this.ech); // On mémorise l'état actuel (état initial)
		piecesAllies.add(this);
		
		int xDep = this.x;
		int yDep = this.y;
		
		for(Piece p : piecesAllies) { // Tester tous les mouvements possibles par la pièce
			for(i=0 ; i<8 ; i++) {
				for(j=0 ; j<8 ; j++) {
					if((xDep != i) || (yDep != j)) { // On ne teste pas le déplacement sur elle-même
						// On teste le mouvement
						if(p.seDeplacer(i, j) == true) { // Si on trouve un déplacement possible pour la pièce
							for(Piece p2 : p.getJ().getPieces()) {
								if(p2 instanceof Roi)
									if(((Roi) p2).estEnEchec() == false) {
										pile.depiler(this.ech); // Retour à l'état initial
										return false; // Pas de blocage
									}
										
							}
							
							
						}
						else { // Sinon
							pile.depiler(this.ech); // Retour à l'état initial
							pile.empiler(this.ech); // On mémorise de nouveau l'état initial , passage à la situation suivante ou à la pièce alliée suivante
						}
					}
					
						
				}
			}
				
		}
		
		
		return true; // Tous les mouvements testés ne peuvent être joués , la pièce est bloqué
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
