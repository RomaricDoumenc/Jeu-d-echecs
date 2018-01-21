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

}
