package classes;

public class Echiquier { // Echiquier de 64 cases
	
	private Piece[][] pieces; // tableau � 2 dimensions contenant les pi�ces pos�es sur l'�chiquier
	
	public Echiquier() { // Initialisation des cases de l'�chiquier � null (�chiquier vide)
		
		pieces = new Piece[8][8];
		int i,j;
		for(i=0 ; i<8 ; i++) 
			for(j=0 ; j<8 ; j++)
				pieces[i][j] = null;
		
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[][] pieces) {
		this.pieces = pieces;
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

}
