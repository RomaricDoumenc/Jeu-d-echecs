package classes;

import java.util.ArrayList;

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
		return estEnEchecParFou() || estEnEchecParTour() || estEnEchecParDame() 
				|| estEnEchecParPion() || estEnEchecParRoi() || estEnEchecParCavalier();
		
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
	
	private boolean estEnEchecParPion() {
		
		 int xRoi = this.x;
		 int yRoi = this.y;
		 
		 if(this.coul == Couleur.BLANC) {
			 if((x-1 >= 0) && (y-1 >= 0)) {
				 if(this.ech.getPieces()[x-1][y-1] != null)
					 if(this.ech.getPieces()[x-1][y-1] instanceof Pion)
						 if(this.ech.getPieces()[x-1][y-1].getCoul() != this.coul)
							 return true;
			 }
			 if((x-1 >= 0) && (y+1 <=7)) {
				 if(this.ech.getPieces()[x-1][y+1] != null)
					 if(this.ech.getPieces()[x-1][y+1] instanceof Pion)
						 if(this.ech.getPieces()[x-1][y+1].getCoul() != this.coul)
							 return true;
				 
			 }
			 
				 
		 }
		 if(this.coul == Couleur.NOIR) {
			 if((x+1 <= 7) && (y+1 <=7)) {
				 if(this.ech.getPieces()[x+1][y+1] != null)
					 if(this.ech.getPieces()[x+1][y+1] instanceof Pion)
						 if(this.ech.getPieces()[x+1][y+1].getCoul() != this.coul)
							 return true;
				 
			 }
			 if((x+1 <= 7) && (y-1 >= 0)) {
				 if(this.ech.getPieces()[x+1][y-1] != null)
					 if(this.ech.getPieces()[x+1][y-1] instanceof Pion)
						 if(this.ech.getPieces()[x+1][y-1].getCoul() != this.coul)
							 return true;
				 
			 }
		 }
		 
		 return false;
	}
	
	private boolean estEnEchecParRoi() {
		
		int xRoi = this.x;
		int yRoi = this.y;
		
		 if((x-1 >= 0) && (y-1 >= 0)) { // V�rifier en haut � gauche
			 if(this.ech.getPieces()[x-1][y-1] != null)
				 if(this.ech.getPieces()[x-1][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y-1].getCoul() != this.coul)
						 return true;
		 }
		 if((x-1 >= 0) && (y+1 <=7)) { // V�rifier en haut � droite
			 if(this.ech.getPieces()[x-1][y+1] != null)
				 if(this.ech.getPieces()[x-1][y+1] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y+1].getCoul() != this.coul)
						 return true;
			 
		 }
		 
		 if((x+1 <= 7) && (y+1 <=7)) { // V�rifier en bas � droite
			 if(this.ech.getPieces()[x+1][y+1] != null)
				 if(this.ech.getPieces()[x+1][y+1] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y+1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if((x+1 <= 7) && (y-1 >= 0)) { // V�rifier en bas � gauche
			 if(this.ech.getPieces()[x+1][y-1] != null)
				 if(this.ech.getPieces()[x+1][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y-1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(x+1 <= 7) { // V�rifier en bas
			 if(this.ech.getPieces()[x+1][y] != null)
				 if(this.ech.getPieces()[x+1][y] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y].getCoul() != this.coul)
						 return true;
			 
		 } 
		 if(x-1 >= 0) { // V�rifier en haut
			 if(this.ech.getPieces()[x-1][y] != null)
				 if(this.ech.getPieces()[x-1][y] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(y-1 >= 0) { // V�rifier � gauche
			 if(this.ech.getPieces()[x][y-1] != null)
				 if(this.ech.getPieces()[x][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x][y-1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(y+1 <= 7) { // V�rifier � droite
			 if(this.ech.getPieces()[x][y+1] != null)
				 if(this.ech.getPieces()[x][y+1] instanceof Roi)
					 if(this.ech.getPieces()[x][y+1].getCoul() != this.coul)
						 return true;
			 
		 }
		return false;
	}

	private boolean estEnEchecParCavalier() {
		
		int xRoi = this.x;
		int yRoi = this.y;
		
		 if((x-1 >= 0) && (y-2 >= 0)) { // V�rifier en haut � gauche
			 if(this.ech.getPieces()[x-1][y-2] != null)
				 if(this.ech.getPieces()[x-1][y-2] instanceof Cavalier)
					 if(this.ech.getPieces()[x-1][y-2].getCoul() != this.coul)
						 return true;
		 }
		 if((x-2 >= 0) && (y-1 >= 0)) {
			 if(this.ech.getPieces()[x-2][y-1] != null)
				 if(this.ech.getPieces()[x-2][y-1] instanceof Cavalier)
					 if(this.ech.getPieces()[x-2][y-1].getCoul() != this.coul)
						 return true;
		 }
		 if((x-2 >= 0) && (y+1 <= 7)) { // V�rifier en haut � droite
			 if(this.ech.getPieces()[x-2][y+1] != null)
				 if(this.ech.getPieces()[x-2][y+1] instanceof Cavalier)
					 if(this.ech.getPieces()[x-2][y+1].getCoul() != this.coul)
						 return true;
		 }
		 if((x-1 >= 0) && (y+2 <= 7)) {
			 if(this.ech.getPieces()[x-1][y+2] != null)
				 if(this.ech.getPieces()[x-1][y+2] instanceof Cavalier)
					 if(this.ech.getPieces()[x-1][y+2].getCoul() != this.coul)
						 return true;
		 }
		 if((x+1 <= 7) && (y+2 <= 7)) { // V�rifier en bas � droite
			 if(this.ech.getPieces()[x+1][y+2] != null)
				 if(this.ech.getPieces()[x+1][y+2] instanceof Cavalier)
					 if(this.ech.getPieces()[x+1][y+2].getCoul() != this.coul)
						 return true;
		 }
		 if((x+2 <= 7) && (y+1 <= 7)) { 
			 if(this.ech.getPieces()[x+2][y+1] != null)
				 if(this.ech.getPieces()[x+2][y+1] instanceof Cavalier)
					 if(this.ech.getPieces()[x+2][y+1].getCoul() != this.coul)
						 return true;
		 }
		 if((x+2 <= 7) && (y-1 >= 0)) { // V�rifier en bas � gauche
			 if(this.ech.getPieces()[x+2][y-1] != null)
				 if(this.ech.getPieces()[x+2][y-1] instanceof Cavalier)
					 if(this.ech.getPieces()[x+2][y-1].getCoul() != this.coul)
						 return true;
		 }
		 if((x+1 <= 7) && (y-2 >= 0)) { // V�rifier en bas � gauche
			 if(this.ech.getPieces()[x+1][y-2] != null)
				 if(this.ech.getPieces()[x+1][y-2] instanceof Cavalier)
					 if(this.ech.getPieces()[x+1][y-2].getCoul() != this.coul)
						 return true;
		 }
		 
		 return false;
	}
	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		return true;
	}
	
	public boolean estEchecEtMat() { // Indique si le roi est �chec et mat
		if (this.estEnEchec() == true) {
			
			int i,j;
			ArrayList<Piece> piecesAllies  = new ArrayList<Piece>(); /* Pi�ces alli�s du roi mis en �chec
																		ainsi que le roi lui-m�me */
			Pile pile = new Pile(); // Pile pour m�moriser l'�tat de d�part
			pile.empiler(this.ech); // On m�morise l'�tat actuel (�tat initial)
			for(i=0 ; i<8 ; i++) { // On cherche sur l'�chiquier les pi�ces de m�me couleur que le roi (i.e. ses alli�s)
				for(j=0 ; j<8 ; j++) {
					if(this.ech.getPieces()[i][j] != null)
						if(this.ech.getPieces()[i][j].coul == this.coul)
							piecesAllies.add(this.ech.getPieces()[i][j]);
				}
			}
			piecesAllies.add(this); // Ajout du roi lui-m�me dans ses pi�ces alli�s
			
			for(Piece p : piecesAllies) { // Tester tous les mouvements possibles par le joueur mis en �chec
				for(i=0 ; i<8 ; i++) {
					for(j=0 ; j<8 ; j++) {
						p.seDeplacer(i, j);
						if(this.estEnEchec() == false) {
							pile.depiler(this.ech); // Retour � l'�tat initial
							return false; // Pas d'�chec et mat
						}
						else {
							pile.depiler(this.ech); // Retour � l'�tat initial
							pile.empiler(this.ech); // On m�morise de nouveau l'�tat initial
						}
							
					}
				}
					
			}
			
			return true;
			
			
		}
		else
			return false;
		
	}

}
