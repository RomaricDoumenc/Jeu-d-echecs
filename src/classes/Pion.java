package classes;

public class Pion extends Piece {

	private boolean dejaBouge; // indique si le pion a déjà bougé (utile pour son 1er déplacement)
	
	public Pion(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		this.dejaBouge = false;
	}

	@Override
	public void seDeplacer(int xArr, int yArr) {
		// TODO Auto-generated method stub

	}

	public boolean isDejaBouge() {
		return dejaBouge;
	}

	public void setaDejBouge(boolean DejaBouge) {
		this.dejaBouge = DejaBouge;
	}
	
	

}
