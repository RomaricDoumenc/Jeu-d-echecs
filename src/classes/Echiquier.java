package classes;

public class Echiquier { // Echiquier de 64 cases
	
	private Piece[][] pieces; // tableau à 2 dimensions contenant les pièces posées sur l'échiquier
	
	public Echiquier() { // Initialisation des cases de l'échiquier à null (échiquier vide)
		
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
