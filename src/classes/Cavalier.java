package classes;

public class Cavalier extends Piece {

	public Cavalier(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void seDeplacer(int xArr, int yArr) { /* Se déplace en L , le cavalier peut sauter par-dessus
												  * les autres pièces. */
	
		int xDep = this.x;
		int yDep = this.y;
		
		
		
		

	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		return true;
	}

}
