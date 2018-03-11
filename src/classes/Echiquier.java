package classes;

import java.io.Serializable;



public class Echiquier implements Serializable { // Echiquier de 64 cases
	
	private static final long serialVersionUID = -7386441697609436944L;

	
	
	private Piece[][] pieces; // tableau � 2 dimensions contenant les pi�ces pos�es sur l'�chiquier
	private Couleur joueurActuel; // couleur du joueur actuel
	
	public Echiquier() { // Initialisation des cases de l'�chiquier � null (�chiquier vide)
		
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

	public void mettreAJourListeJoueurs() { /* Met � jour la liste des pi�ces des joueurs en 
											 * fonction des pi�ces restantes sur l'�chiquier */
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++) {
				if (this.pieces[i][j] != null) {
					if(this.pieces[i][j].getJ().getPieces().contains(this.pieces[i][j]) == false)
						// Si la pi�ce n'est pas dans la liste de son joueur
						// Alors la rajouter � cete liste
						this.pieces[i][j].getJ().getPieces().add(this.pieces[i][j]);
				}
			}
	}
	
	public void mettreAJourCoordonnesPieces() { /* Met � jour les coordonn�es contenus
		 										 * dans les pi�ces de l'�chiquier */
		
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++) {
				if (this.pieces[i][j] != null) {
					this.pieces[i][j].setX(i);
					this.pieces[i][j].setY(j);
				}
			}		
		
	}
	
	public boolean insufficanceMaterielle() { // Indique s'il y a nul par insufficance mat�rielle (pas assez de pi�ces pour mater un des 2 rois)
		int nbCavaliersBlancs = 0, nbFousBlancs = 0;
		int nbCavaliersNoirs = 0, nbFousNoirs = 0;
		
		// On ne compte pas les rois , il y en a 1 par camp , il ne peut pas �tre captur� sinon la partie est finie
		
		int i,j;
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++) { // On compte le mat�riel de chaque camp (on ne compte pas les rois , dames , tours et pions)
				Piece p = this.pieces[i][j];
				if (p != null) {
					if(p instanceof Pion) {
						return false; // Il suffit d'un pion pour gagner , le pion pouvant �tre promu en Dame , pas d'insuffisance mat�rielle
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
					if(p instanceof Dame) { // On peut mater avec Roi + Dame , pas d'insuffisance mat�rielle
						return false;
					}
					if(p instanceof Tour) { // On peut mater avec Roi + Tour , pas d'insuffisance mat�rielle
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
		
		return false; // Assez de mat�riel pour mater , pas d'insuffisance mat�rielle
	}
	
	public boolean pat() { /* Y a-t-il pat ? (c.�.d que le joueur actuel ne peut pas bouger son roi sans le mettre en �chec) , 
							* que son roi n'est pas en �chec , et que toutes ses autres pi�ces sont bloqu�es ou captur�es
							* Autrement dit , que le joueur ne peut pas effectuer un d�placement autoris�) */
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
