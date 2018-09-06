package classes;

public class Fou extends Piece {
	
	private static final long serialVersionUID = -1629078007359932853L;

	public Fou(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
		public boolean seDeplacer(int xArr, int yArr) {	/* Se d�place en diagonale */
		
		int xDep = this.x;
		int yDep = this.y;
		
		if(Math.abs(xDep - xArr) == Math.abs(yDep - yArr)) { // Lors d'un d�placement en diagonale ,
															 // deltaX = deltaY
			if(Math.abs(xDep - xArr) > 0)
				if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pi�ce sur la trajectoire du fou ?
					if(this.ech.getPieces()[xArr][yArr] == null) { // Case libre ?
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr); // D�placement sur cette case
						return true;
					}
					else if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						// Pi�ce adverse sur la case d'arriv�e ?
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
		int i;
		if((xArr > xDep) && (yArr > yDep)) { // En bas � droite
			for(i=1 ; i<(xArr-xDep) ; i++)
				if(this.ech.getPieces()[xDep+i][yDep+i] != null)
					return false;
			return true;
		}
		else if((xArr < xDep) && (yArr < yDep)) { // En haut � gauche
			for(i=1 ; i<(xDep-xArr) ; i++)
				if(this.ech.getPieces()[xDep-i][yDep-i] != null)
					return false;
			return true;
		}
		else if((xArr < xDep) && (yArr > yDep)) { // En haut � droite
			for(i=1 ; i<(xDep-xArr) ; i++)
				if(this.ech.getPieces()[xDep-i][yDep+i] != null)
					return false;
			return true;
		}
		else if((xArr > xDep) && (yArr < yDep)) { // En bas � gauche
			for(i=1 ; i<(xArr-xDep) ; i++)
				if(this.ech.getPieces()[xDep+i][yDep-i] != null)
					return false;
			return true;
		}
		return true;
	}

}
