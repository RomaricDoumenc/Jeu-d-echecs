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
				if (trajectoireLibre(xDep, yDep, xArr, yArr) == true) { // Pas de pièce sur la trajectoire du fou ?
					if(this.ech.getPieces()[xArr][yArr] == null) // Case libre ?
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr); // Déplacement sur cette case
					else if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						// Pièce adverse sur la case d'arrivée ?
						capturerAdversaire(xArr, yArr);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
					}
				}
		
		

	}
	
	public boolean estEnEchec() { // Indique si le roi est en échec ou non
		return estEnEchecParFou() || estEnEchecParTour() || estEnEchecParDame() 
				|| estEnEchecParPion() || estEnEchecParRoi() || estEnEchecParCavalier();
		
	}
	
	private boolean estEnEchecParFou() { // Indique si le roi est en échec par un fou adverse
		
		int xRoi = this.x; // Coordonnées du roi
		int yRoi = this.y;
		
		// Vérifier en haut à gauche
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
		
		// Vérifier en bas à droite
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
		
		// Vérifier en bas à gauche
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
		
		// Vérifier en haut à droite
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
		
		int xRoi = this.x; // Coordonnées du roi
		int yRoi = this.y;
		Piece pieceActuelle;
		
		// Vérifier en haut
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

		// Vérifier en bas
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
		
		// Vérifier à gauche
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
		
		// Vérifier à droite
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
		
		int xRoi = this.x; // Coordonnées du roi
		int yRoi = this.y;
		Piece pieceActuelle;
		
		// Vérifier en haut
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

		// Vérifier en bas
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
		
		// Vérifier à gauche
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
		
		// Vérifier à droite
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
		
		
		// Vérifier en haut à gauche
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
		
		// Vérifier en bas à droite
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
		
		// Vérifier en bas à gauche
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
		
		// Vérifier en haut à droite
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
		
		 if((x-1 >= 0) && (y-1 >= 0)) { // Vérifier en haut à gauche
			 if(this.ech.getPieces()[x-1][y-1] != null)
				 if(this.ech.getPieces()[x-1][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y-1].getCoul() != this.coul)
						 return true;
		 }
		 if((x-1 >= 0) && (y+1 <=7)) { // Vérifier en haut à droite
			 if(this.ech.getPieces()[x-1][y+1] != null)
				 if(this.ech.getPieces()[x-1][y+1] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y+1].getCoul() != this.coul)
						 return true;
			 
		 }
		 
		 if((x+1 <= 7) && (y+1 <=7)) { // Vérifier en bas à droite
			 if(this.ech.getPieces()[x+1][y+1] != null)
				 if(this.ech.getPieces()[x+1][y+1] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y+1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if((x+1 <= 7) && (y-1 >= 0)) { // Vérifier en bas à gauche
			 if(this.ech.getPieces()[x+1][y-1] != null)
				 if(this.ech.getPieces()[x+1][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y-1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(x+1 <= 7) { // Vérifier en bas
			 if(this.ech.getPieces()[x+1][y] != null)
				 if(this.ech.getPieces()[x+1][y] instanceof Roi)
					 if(this.ech.getPieces()[x+1][y].getCoul() != this.coul)
						 return true;
			 
		 } 
		 if(x-1 >= 0) { // Vérifier en haut
			 if(this.ech.getPieces()[x-1][y] != null)
				 if(this.ech.getPieces()[x-1][y] instanceof Roi)
					 if(this.ech.getPieces()[x-1][y].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(y-1 >= 0) { // Vérifier à gauche
			 if(this.ech.getPieces()[x][y-1] != null)
				 if(this.ech.getPieces()[x][y-1] instanceof Roi)
					 if(this.ech.getPieces()[x][y-1].getCoul() != this.coul)
						 return true;
			 
		 }
		 if(y+1 <= 7) { // Vérifier à droite
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
		
		 if((x-1 >= 0) && (y-2 >= 0)) { // Vérifier en haut à gauche
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
		 if((x-2 >= 0) && (y+1 <= 7)) { // Vérifier en haut à droite
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
		 if((x+1 <= 7) && (y+2 <= 7)) { // Vérifier en bas à droite
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
		 if((x+2 <= 7) && (y-1 >= 0)) { // Vérifier en bas à gauche
			 if(this.ech.getPieces()[x+2][y-1] != null)
				 if(this.ech.getPieces()[x+2][y-1] instanceof Cavalier)
					 if(this.ech.getPieces()[x+2][y-1].getCoul() != this.coul)
						 return true;
		 }
		 if((x+1 <= 7) && (y-2 >= 0)) { // Vérifier en bas à gauche
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
	
	public boolean estEchecEtMat() { // Indique si le roi est échec et mat
		if (this.estEnEchec() == true) {
			
			int i,j;
			ArrayList<Piece> piecesAllies  = new ArrayList<Piece>(); /* Pièces alliés du roi mis en échec
																		ainsi que le roi lui-même */
			Pile pile = new Pile(); // Pile pour mémoriser l'état de départ
			pile.empiler(this.ech); // On mémorise l'état actuel (état initial)
			for(i=0 ; i<8 ; i++) { // On cherche sur l'échiquier les pièces de même couleur que le roi (i.e. ses alliés)
				for(j=0 ; j<8 ; j++) {
					if(this.ech.getPieces()[i][j] != null)
						if(this.ech.getPieces()[i][j].coul == this.coul)
							piecesAllies.add(this.ech.getPieces()[i][j]);
				}
			}
			piecesAllies.add(this); // Ajout du roi lui-même dans ses pièces alliés
			
			for(Piece p : piecesAllies) { // Tester tous les mouvements possibles par le joueur mis en échec
				for(i=0 ; i<8 ; i++) {
					for(j=0 ; j<8 ; j++) {
						p.seDeplacer(i, j);
						if(this.estEnEchec() == false) {
							pile.depiler(this.ech); // Retour à l'état initial
							return false; // Pas d'échec et mat
						}
						else {
							pile.depiler(this.ech); // Retour à l'état initial
							pile.empiler(this.ech); // On mémorise de nouveau l'état initial
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
