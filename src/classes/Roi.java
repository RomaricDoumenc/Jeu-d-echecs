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
		return estEnEchecParFou() || estEnEchecParTour() || estEnEchecParDame();
		
	}
	
	private boolean estEnEchecParFou() { // Indique si le roi est en �chec par un fou adverse
		
		int xRoi = this.x; // Coordonn�es du roi
		int yRoi = this.y;
		
		// V�rifier en haut � gauche
		int x = xRoi - 1;
		int y = yRoi - 1;
		
		
		Piece pieceActuelle;
		if((x >= 0) && (y >= 0))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
			
		while((x > 0) && (y > 0) && (pieceActuelle == null)) {
			x--;
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Fou) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en bas � droite
		x = xRoi + 1;
		y = yRoi + 1;
		if((x <= 7) && (y <= 7))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (y < 7) && (pieceActuelle == null)) {
			x++;
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Fou) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en bas � gauche
		x = xRoi + 1;
		y = yRoi - 1;
		if((x <= 7) && (y >= 0))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (y > 0) && (pieceActuelle == null)) {
			x++;
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Fou) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en haut � droite
		x = xRoi - 1;
		y = yRoi + 1;
		if((x >= 0) && (y <= 7))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x > 0) && (y < 7) && (pieceActuelle == null)) {
			x--;
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Fou) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}

		return false;
		
		
		
	}
	
	private boolean estEnEchecParTour() {
		
		int xRoi = this.x; // Coordonn�es du roi
		int yRoi = this.y;
		Piece pieceActuelle;
		
		// V�rifier en haut
		int x = xRoi - 1;
		int y = yRoi;
		
		if(x >= 0)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x > 0) && (pieceActuelle == null)) {
			x--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Tour) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}

		// V�rifier en bas
		x = xRoi + 1;
		y = yRoi;
		
		if(x <= 7)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (pieceActuelle == null)) {
			x++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Tour) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier � gauche
		x = xRoi;
		y = yRoi - 1;
		
		if(y >= 0)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((y > 0) && (pieceActuelle == null)) {
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Tour) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier � droite
		x = xRoi;
		y = yRoi + 1;
		
		if(y <= 7)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((y  < 7) && (pieceActuelle == null)) {
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Tour) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		
		
		return false;
		
	}
	private boolean estEnEchecParDame() {
		
		int xRoi = this.x; // Coordonn�es du roi
		int yRoi = this.y;
		Piece pieceActuelle;
		
		// V�rifier en haut
		int x = xRoi - 1;
		int y = yRoi;
		
		if(x >= 0)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x > 0) && (pieceActuelle == null)) {
			x--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}

		// V�rifier en bas
		x = xRoi + 1;
		y = yRoi;
		
		if(x <= 7)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (pieceActuelle == null)) {
			x++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier � gauche
		x = xRoi;
		y = yRoi - 1;
		
		if(y >= 0)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((y > 0) && (pieceActuelle == null)) {
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier � droite
		x = xRoi;
		y = yRoi + 1;
		
		if(y <= 7)
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((y  < 7) && (pieceActuelle == null)) {
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		
		// V�rifier en haut � gauche
		x = xRoi - 1;
		y = yRoi - 1;
		if((x >= 0) && (y >= 0))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
			
		while((x > 0) && (y > 0) && (pieceActuelle == null)) {
			x--;
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en bas � droite
		x = xRoi + 1;
		y = yRoi + 1;
		if((x <= 7) && (y <= 7))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (y < 7) && (pieceActuelle == null)) {
			x++;
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en bas � gauche
		x = xRoi + 1;
		y = yRoi - 1;
		if((x <= 7) && (y >= 0))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x < 7) && (y > 0) && (pieceActuelle == null)) {
			x++;
			y--;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		// V�rifier en haut � droite
		x = xRoi - 1;
		y = yRoi + 1;
		if((x >= 0) && (y <= 7))
			pieceActuelle = this.ech.getPieces()[x][y];
		else
			pieceActuelle = null;
		while((x > 0) && (y < 7) && (pieceActuelle == null)) {
			x--;
			y++;
			pieceActuelle = this.ech.getPieces()[x][y];	
		}
		if(pieceActuelle != null) {
			if((pieceActuelle instanceof Dame) && (pieceActuelle.getCoul() != this.coul))
				return true;
		}
		
		return false;
		
	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		return true;
	}

}
