package classes;

public class Pion extends Piece {

	private boolean dejaBouge; // indique si le pion a déjà bougé (utile pour son 1er déplacement)
	
	public Pion(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		this.dejaBouge = false;
	}

	@Override
	public void seDeplacer(int xArr, int yArr) { /* Se déplace à l'avant d'une case , capture 
												  * en diagonale , à l'avant et d'une case.
												  * A sa position de départ , le pion peut se déplacer
												  * soit de 1 , soit de 2 cases. */
		int xDep = this.x;
		int yDep = this.y;
		
		if(this.coul == Couleur.BLANC) {
			if((yArr == yDep) && (xArr == xDep - 2) && (this.dejaBouge == false)) {
				// Le pion est-il sur sa case d'origine , et y a-t-il déplacement à l'avant de 2 cases ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arrivée est-elle libre ?
					if(this.ech.getPieces()[xDep - 1][yDep] == null) { // Y a t-il une pièce dans la trajectoire du pion ?
						this.ech.getPieces()[xArr][yArr] = this; // Si non , placement du pion sur la case d'arrivée
						this.ech.getPieces()[xDep][yDep] = null; // et vidange de la case de départ
						this.dejaBouge = true; // Le pion a maintenant bougé pour la 1re fois
						this.x = xArr; // Mise à jour de la position du pion
						this.y = yArr;
					}
					
					
				}
				
				
			}
			else if((yArr == yDep) && (xArr == xDep - 1)) { // Y a-t-il déplacement à l'avant d'une case ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arrivée est-elle libre ?
					this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arrivée
					this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de départ
					this.dejaBouge = true;
					this.x = xArr; // Mise à jour de la position du pion
					this.y = yArr;
				}				
			}
			
			else if((yDep - 1 >= 0)  && (xArr == xDep - 1) && (yArr == yDep - 1)) {
				// Y a t-il tentative de capture en diagonale à gauche d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pièce adverse à la case d'arrivée ?
						// Si oui , capture et suppression de la pièce adverse en question
						
						// Suppression de la pièce adverse dans la liste des pièces de l'adversaire
						Piece pieceASuppr = this.ech.getPieces()[xArr][yArr];
						pieceASuppr.getJ().supprimerPiece(pieceASuppr);
						
						this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arrivée
						this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de départ
						this.dejaBouge = true;
						this.x = xArr; // Mise à jour de la position du pion
						this.y = yArr;
						
						
						
					}
						
						
				}
			}
			
			else if((yDep + 1 < 8)  && (xArr == xDep - 1) && (yArr == yDep + 1)) {
				// Y a t-il tentative de capture en diagonale à droite d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pièce adverse à la case d'arrivée ?
						// Si oui , capture et suppression de la pièce adverse en question
						
						// Suppression de la pièce adverse dans la liste des pièces de l'adversaire
						Piece pieceASuppr = (Piece) this.ech.getPieces()[xArr][yArr];
						pieceASuppr.getJ().supprimerPiece(pieceASuppr);
						
						this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arrivée
						this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de départ
						this.dejaBouge = true;
						this.x = xArr; // Mise à jour de la position du pion
						this.y = yArr;
						
						
						
					}
						
						
				}
			}
		}
		
		
	}

	public boolean isDejaBouge() {
		return dejaBouge;
	}

	public void setaDejBouge(boolean DejaBouge) {
		this.dejaBouge = DejaBouge;
	}
	
	

}
