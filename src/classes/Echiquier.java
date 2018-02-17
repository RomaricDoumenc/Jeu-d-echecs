package classes;

import java.io.Serializable;

public class Echiquier implements Serializable { // Echiquier de 64 cases
	
	private static final long serialVersionUID = -7386441697609436944L;

	
	
	private Piece[][] pieces; // tableau à 2 dimensions contenant les pièces posées sur l'échiquier
	private Couleur joueurActuel; // couleur du joueur actuel
	
	public Echiquier() { // Initialisation des cases de l'échiquier à null (échiquier vide)
		
		pieces = new Piece[8][8];
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++)
				pieces[i][j] = null;
		joueurActuel = Couleur.BLANC; // Ce sont les blancs qui commencent
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[][] pieces) {
		this.pieces = pieces;
	}
	
	
	public Couleur getJoueurActuel() {
		return joueurActuel;
	}

	public void setJoueurActuel(Couleur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	public void mettreAJourListeJoueurs() { /* Met à jour la liste des pièces des joueurs en 
											 * fonction des pièces restantes sur l'échiquier */
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++) {
				if (this.pieces[i][j] != null) {
					if(this.pieces[i][j].getJ().getPieces().contains(this.pieces[i][j]) == false)
						// Si la pièce n'est pas dans la liste de son joueur
						// Alors la rajouter à cete liste
						this.pieces[i][j].getJ().getPieces().add(this.pieces[i][j]);
				}
			}
	}
	
	public void mettreAJourCoordonnesPieces() { /* Met à jour les coordonnées contenus
		 										 * dans les pièces de l'échiquier */
		
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++) {
				if (this.pieces[i][j] != null) {
					this.pieces[i][j].setX(i);
					this.pieces[i][j].setY(j);
				}
			}		
		
	}
	
	public void mettreAJourJoueurActuel() {
		if (this.joueurActuel == Couleur.BLANC)
			joueurActuel = Couleur.NOIR;
		else
			joueurActuel = Couleur.BLANC;
	}

}
