package classes;


public class Tour extends Piece { /* Se déplace horizontalement et verticalement */

	private static final long serialVersionUID = 1968502700647108722L;

	public Tour(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void seDeplacer(int xArr, int yArr) {
		
		int xDep = this.x;
		int yDep = this.y;
		
		if(((yDep == yArr) && (xDep != xArr)) || ((yDep != yArr) && (xDep == xArr))) {
			if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pièce sur la trajectoire de la tour ?
				if(this.ech.getPieces()[xArr][yArr] == null) // Case libre ?
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr); // Déplacement sur cette case
				else if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
					// Pièce adverse sur la case d'arrivée ?
					capturerAdversaire(xArr, yArr);
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
				}
			}
		}

	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		int i;
		if((xArr == xDep) && (yArr > yDep)) { // à droite
			for(i=1 ; i<(yArr-yDep) ; i++)
				if(this.ech.getPieces()[xDep][yDep+i] != null)
					return false;
			return true;
		}
		else if((xArr == xDep) && (yArr < yDep)) { // à gauche
			for(i=1 ; i<(yDep-yArr) ; i++)
				if(this.ech.getPieces()[xDep][yDep-i] != null)
					return false;
			return true;
		}
		else if((xArr < xDep) && (yArr == yDep)) { // en haut
			for(i=1 ; i<(xDep-xArr) ; i++)
				if(this.ech.getPieces()[xDep-i][yDep] != null)
					return false;
			return true;
		}
		else if((xArr > xDep) && (yArr == yDep)) { // en bas
			for(i=1 ; i<(xArr-xDep) ; i++)
				if(this.ech.getPieces()[xDep+i][yDep] != null)
					return false;
			return true;
		}
		return true;
	}
	
	

}
