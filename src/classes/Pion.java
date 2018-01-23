package classes;

public class Pion extends Piece {

	private boolean dejaBouge; // indique si le pion a d�j� boug� (utile pour son 1er d�placement)
	
	public Pion(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		this.dejaBouge = false;
	}

	@Override
	public void seDeplacer(int xArr, int yArr) { /* Se d�place � l'avant d'une case , capture 
												  * en diagonale , � l'avant et d'une case.
												  * A sa position de d�part , le pion peut se d�placer
												  * soit de 1 , soit de 2 cases. */
		int xDep = this.x;
		int yDep = this.y;
		
		if(this.coul == Couleur.BLANC) {
			if((yArr == yDep) && (xArr == xDep - 2) && (this.dejaBouge == false)) {
				// Le pion est-il sur sa case d'origine , et y a-t-il d�placement � l'avant de 2 cases ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arriv�e est-elle libre ?
					if(this.ech.getPieces()[xDep - 1][yDep] == null) { // Y a t-il une pi�ce dans la trajectoire du pion ?
						this.ech.getPieces()[xArr][yArr] = this; // Si non , placement du pion sur la case d'arriv�e
						this.ech.getPieces()[xDep][yDep] = null; // et vidange de la case de d�part
						this.dejaBouge = true; // Le pion a maintenant boug� pour la 1re fois
						this.x = xArr; // Mise � jour de la position du pion
						this.y = yArr;
					}
					
					
				}
				
				
			}
			else if((yArr == yDep) && (xArr == xDep - 1)) { // Y a-t-il d�placement � l'avant d'une case ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arriv�e est-elle libre ?
					this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arriv�e
					this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de d�part
					this.dejaBouge = true;
					this.x = xArr; // Mise � jour de la position du pion
					this.y = yArr;
				}				
			}
			
			else if((yDep - 1 >= 0)  && (xArr == xDep - 1) && (yArr == yDep - 1)) {
				// Y a t-il tentative de capture en diagonale � gauche d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pi�ce adverse � la case d'arriv�e ?
						// Si oui , capture et suppression de la pi�ce adverse en question
						
						// Suppression de la pi�ce adverse dans la liste des pi�ces de l'adversaire
						Piece pieceASuppr = this.ech.getPieces()[xArr][yArr];
						pieceASuppr.getJ().supprimerPiece(pieceASuppr);
						
						this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arriv�e
						this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de d�part
						this.dejaBouge = true;
						this.x = xArr; // Mise � jour de la position du pion
						this.y = yArr;
						
						
						
					}
						
						
				}
			}
			
			else if((yDep + 1 < 8)  && (xArr == xDep - 1) && (yArr == yDep + 1)) {
				// Y a t-il tentative de capture en diagonale � droite d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pi�ce adverse � la case d'arriv�e ?
						// Si oui , capture et suppression de la pi�ce adverse en question
						
						// Suppression de la pi�ce adverse dans la liste des pi�ces de l'adversaire
						Piece pieceASuppr = (Piece) this.ech.getPieces()[xArr][yArr];
						pieceASuppr.getJ().supprimerPiece(pieceASuppr);
						
						this.ech.getPieces()[xArr][yArr] = this; // Si oui , placement du pion sur la case d'arriv�e
						this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case de d�part
						this.dejaBouge = true;
						this.x = xArr; // Mise � jour de la position du pion
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
