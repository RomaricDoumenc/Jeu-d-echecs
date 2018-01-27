package classes;

public class Roi extends Piece {

	public Roi(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void seDeplacer(int xArr, int yArr) {
		int xDep = this.x;
		int yDep = this.y;
		
		if((Math.abs(xDep - xArr) == 0) || (Math.abs(xDep - xArr) == 1))
			if((Math.abs(yDep - yArr) == 0) || (Math.abs(yDep - yArr) == 1))
				if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pi�ce sur la trajectoire du fou ?
					if(this.ech.getPieces()[xArr][yArr] == null) // Case libre ?
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr); // D�placement sur cette case
					else if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						// Pi�ce adverse sur la case d'arriv�e ?
						capturerAdversaire(xArr, yArr);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
					}
				}
		
		

	}
	
	public boolean estEnEchec() { // Indique si le roi est en �chec ou non
		
	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		return true;
	}

}
