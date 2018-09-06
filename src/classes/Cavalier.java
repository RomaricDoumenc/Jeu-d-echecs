package classes;

public class Cavalier extends Piece {

	private static final long serialVersionUID = 5843990674237856616L;

	public Cavalier(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean seDeplacer(int xArr, int yArr) { /* Se déplace en L , le cavalier peut sauter par-dessus
												  * les autres pièces. */
	
		int xDep = this.x;
		int yDep = this.y;
		int deltaX = Math.abs(xDep - xArr);
		int deltaY = Math.abs(yDep - yArr);
		
		if(((deltaX == 1) && (deltaY == 2)) || ((deltaX == 2) && (deltaY == 1))) {
			if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pièce sur la trajectoire du fou ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Case libre ?
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr); // Déplacement sur cette case
					return true;
				}
				else if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
					// Pièce adverse sur la case d'arrivée ?
					capturerAdversaire(xArr, yArr);
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
					return true;
				}
			}
		}
		
		return false;
		
		
		
		

	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		return true;
	}

}
