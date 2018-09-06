package classes;

public class Dame extends Piece {

	private static final long serialVersionUID = 7880220656661021932L;

	public Dame(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean seDeplacer(int xArr, int yArr) { /* Se d�place dans toutes les
	 											  * directions d'un nombre illimit� de cases. */
		
		int xDep = this.x;
		int yDep = this.y;
		
		if(((yDep == yArr) && (xDep != xArr)) || ((yDep != yArr) && (xDep == xArr)) || 
				(Math.abs(xDep - xArr) == Math.abs(yDep - yArr))) {
			if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pi�ce sur la trajectoire de la dame ?
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
		if((xArr == xDep) && (yArr > yDep)) { // � droite
			for(i=1 ; i<(yArr-yDep) ; i++)
				if(this.ech.getPieces()[xDep][yDep+i] != null)
					return false;
			return true;
		}
		else if((xArr == xDep) && (yArr < yDep)) { // � gauche
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
