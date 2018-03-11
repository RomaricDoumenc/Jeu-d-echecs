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
	
	public boolean insufficanceMaterielle() { // Indique s'il y a nul par insufficance matérielle (pas assez de pièces pour mater un des 2 rois)
		int nbCavaliersBlancs = 0, nbFousBlancs = 0;
		int nbCavaliersNoirs = 0, nbFousNoirs = 0;
		
		// On ne compte pas les rois , il y en a 1 par camp , il ne peut pas être capturé sinon la partie est finie
		
		int i,j;
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++) { // On compte le matériel de chaque camp (on ne compte pas les rois , dames , tours et pions)
				Piece p = this.pieces[i][j];
				if (p != null) {
					if(p instanceof Pion) {
						return false; // Il suffit d'un pion pour gagner , le pion pouvant être promu en Dame , pas d'insuffisance matérielle
					}
					if(p instanceof Cavalier) {
						if(p.getCoul() == Couleur.BLANC)
							nbCavaliersBlancs++;
						else
							nbCavaliersNoirs++;
					}
					if(p instanceof Fou) {
						if(p.getCoul() == Couleur.BLANC)
							nbFousBlancs++;
						else
							nbFousNoirs--;
					}
					if(p instanceof Dame) { // On peut mater avec Roi + Dame , pas d'insuffisance matérielle
						return false;
					}
					if(p instanceof Tour) { // On peut mater avec Roi + Tour , pas d'insuffisance matérielle
						return false;
					}
				}
			}
		if((nbCavaliersBlancs + nbCavaliersNoirs == 0) && (nbFousBlancs + nbFousNoirs == 0)) // Roi contre Roi , impossible de mater
			return true;
		if((nbCavaliersBlancs + nbCavaliersNoirs == 1) && (nbFousBlancs + nbFousNoirs == 0)) // Roi + Cavalier contre Roi , impossible de mater
			return true;
		if((nbCavaliersBlancs + nbCavaliersNoirs == 0) && (nbFousBlancs + nbFousNoirs == 1)) // Roi + Fou contre Roi , impossible de mater
			return true;
		if((nbCavaliersBlancs + nbCavaliersNoirs == 2) && (nbFousBlancs + nbFousNoirs == 0)) /* Roi + 2 Cavaliers contre Roi , impossible de mater
		 																		 ou Roi + Cavalier contre Roi + Cavalier , impossible de mater */
			return true;
		
		return false; // Assez de matériel pour mater , pas d'insuffisance matérielle
	}
	
	public boolean pat() { /* Y a-t-il pat ? (c.à.d que le joueur actuel ne peut pas bouger son roi sans le mettre en échec) , 
							* que son roi n'est pas en échec , et que toutes ses autres pièces sont bloquées ou capturées
							* Autrement dit , que le joueur ne peut pas effectuer un déplacement autorisé) */
		int i,j;
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++) {
				Piece p = pieces[i][j];
				if((p != null)  && (p.getCoul() == this.joueurActuel))
					if(p.estBloque() == false)
						return false;
			}
		
		return true;
		
	}
	
	public void mettreAJourJoueurActuel() {
		if (this.joueurActuel == Couleur.BLANC)
			joueurActuel = Couleur.NOIR;
		else
			joueurActuel = Couleur.BLANC;
	}

}
