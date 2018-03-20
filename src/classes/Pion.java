package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Pion extends Piece {

	private static final long serialVersionUID = -5803497094579220230L;
	private boolean dejaBouge; // indique si le pion a d�j� boug� (utile pour son 1er d�placement)
	private boolean aBouge2cases; // indique si le pion a boug� de 2 cases (sert pour v�rifier la prise en passant)
	
	public Pion(int x, int y, Couleur coul, Echiquier ech, Joueur j) {
		super(x, y, coul, ech, j);
		this.dejaBouge = false;
	}
	
	protected void bougerPieceSurEchiquier(int xDep , int yDep , int xArr , int yArr) {
		// Met � jour l'�chiquier en faisant bouger la pi�ce
		this.ech.getPieces()[xArr][yArr] = this; // Placement du pion sur la case d'arriv�e
		this.ech.getPieces()[xDep][yDep] = null; // Vidange de la case d'origine
		this.dejaBouge = true; // Le pion a boug�
		this.aBouge2cases = false;
		this.x = xArr; // Mise � jour de la position du pion
		this.y = yArr;
	}

	@Override
	public boolean seDeplacer(int xArr, int yArr) { /* Se d�place � l'avant d'une case , capture 
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
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = true;
						return true;
					}
					
					
				}
				
				
			}
			else if((yArr == yDep) && (xArr == xDep - 1)) { // Y a-t-il d�placement � l'avant d'une case ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arriv�e est-elle libre ?
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
					this.aBouge2cases = false;
					return true;
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
						
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = false;
						return true;
						
						
						
					}
					
						
						
				}
				else { // Y a-t-il prise en passant � gauche ?
					if((this.ech.getPieces()[xDep][yDep-1] instanceof Pion)
							&& (this.ech.getPieces()[xDep][yDep-1].getCoul() != this.coul)
							&& (((Pion)this.ech.getPieces()[xDep][yDep-1]).isaBouge2cases() == true) && (xDep == 3)) {
						capturerAdversaire(xDep, yDep-1);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.ech.getPieces()[xDep][yDep-1] = null;
						this.aBouge2cases = false;
						return true;
						
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
						capturerAdversaire(xArr, yArr);
						
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = false;
						return true;
						
						
						
					}
						
						
				}
				else { // Y a-t-il prise en passant � droite ?
					if((this.ech.getPieces()[xDep][yDep+1] instanceof Pion)
							&& (this.ech.getPieces()[xDep][yDep+1].getCoul() != this.coul)
							&& (((Pion)this.ech.getPieces()[xDep][yDep+1]).isaBouge2cases() == true) && (xDep == 3)) {
						capturerAdversaire(xDep, yDep+1);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.ech.getPieces()[xDep][yDep+1] = null;
						this.aBouge2cases = false;
						return true;
						
					}
				}
			}
		}
		
		if(this.coul == Couleur.NOIR) {
			if((yArr == yDep) && (xArr == xDep + 2) && (this.dejaBouge == false)) {
				// Le pion est-il sur sa case d'origine , et y a-t-il d�placement � l'avant de 2 cases ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arriv�e est-elle libre ?
					if(this.ech.getPieces()[xDep + 1][yDep] == null) { // Y a t-il une pi�ce dans la trajectoire du pion ?
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = true;
						return true;
					}
					
					
				}
				
				
			}
			else if((yArr == yDep) && (xArr == xDep + 1)) { // Y a-t-il d�placement � l'avant d'une case ?
				if(this.ech.getPieces()[xArr][yArr] == null) { // Si oui , la case d'arriv�e est-elle libre ?
					bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
					this.aBouge2cases = false;
					return true;
				}				
			}
			
			else if((yDep - 1 >= 0)  && (xArr == xDep + 1) && (yArr == yDep - 1)) {
				// Y a t-il tentative de capture en diagonale � gauche d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pi�ce adverse � la case d'arriv�e ?
						// Si oui , capture et suppression de la pi�ce adverse en question
						
						// Suppression de la pi�ce adverse dans la liste des pi�ces de l'adversaire
						capturerAdversaire(xArr, yArr);
						
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = false;
						return true;
						
						
						
					}
						
						
				}
				else { // Y a-t-il prise en passant � gauche ?
					if((this.ech.getPieces()[xDep][yDep-1] instanceof Pion)
							&& (this.ech.getPieces()[xDep][yDep-1].getCoul() != this.coul)
							&& (((Pion)this.ech.getPieces()[xDep][yDep-1]).isaBouge2cases() == true) && (xDep == 4)) {
						capturerAdversaire(xDep, yDep-1);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.ech.getPieces()[xDep][yDep-1] = null;
						this.aBouge2cases = false;
						return true;
						
					}
				}
			}
			
			else if((yDep + 1 < 8)  && (xArr == xDep + 1) && (yArr == yDep + 1)) {
				// Y a t-il tentative de capture en diagonale � droite d'une case ?
				if(this.ech.getPieces()[xArr][yArr] != null) {
					if(this.ech.getPieces()[xArr][yArr].getCoul() != this.coul) {
						
						// Y a-t-il une pi�ce adverse � la case d'arriv�e ?
						// Si oui , capture et suppression de la pi�ce adverse en question
						
						// Suppression de la pi�ce adverse dans la liste des pi�ces de l'adversaire
						capturerAdversaire(xArr, yArr);
						
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.aBouge2cases = false;
						return true;
						
						
						
					}
						
						
				}
				else { // Y a-t-il prise en passant � droite ?
					if((this.ech.getPieces()[xDep][yDep+1] instanceof Pion)
							&& (this.ech.getPieces()[xDep][yDep+1].getCoul() != this.coul)
							&& (((Pion)this.ech.getPieces()[xDep][yDep+1]).isaBouge2cases() == true) && (xDep == 4)) {
						capturerAdversaire(xDep, yDep+1);
						bougerPieceSurEchiquier(xDep, yDep, xArr, yArr);
						this.ech.getPieces()[xDep][yDep+1] = null;
						this.aBouge2cases = false;
						return true;
						
					}
				}
				
			}
		}
		
		return false;
		
		
	}
	
	public void resetDejaBouge() { /* R�initialise le bool�en dejaBouge du pion � sa valeur de d�part (faux)
	 								* si ce pion est revenu � sa position de d�part suite � l'annulation  
	 								* d'un ou plusieurs coups. */
		if(this.coul == Couleur.BLANC) {
			if(this.x == 6)
				this.dejaBouge = false;
		}
		if(this.coul == Couleur.NOIR) {
			if(this.x == 1)
				this.dejaBouge = false;
		}
		
	}
	
	public void promouvoir() { /* Coup sp�cial consistant � transformer le pion en une autre pi�ce
								* (c.�.d supprimer ce pion de la liste du joueur et ajouter une nouvelle pi�ce
								* que le joueur choisira (choix possibles : dame , tour , cavalier , fou))*/
		int xPion = this.x;
		int yPion = this.y;
		
		List<String> choices = new ArrayList<String>();
		choices.add("Dame");
		choices.add("Tour");
		choices.add("Fou");
		choices.add("Cavalier");
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>("Dame", choices);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/pionBlanc.png")));
		
		dialog.setTitle("Promotion");
		dialog.setHeaderText("Vous avez amen� votre pion jusqu'au bout de l'�chiquier\net votre pion va se transformer en une autre pi�ce.");
		dialog.setContentText("Choisissez la nouvelle pi�ce :");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			switch (result.get()) {
				case "Dame":
					this.ech.getPieces()[xPion][yPion] = new Dame(xPion, yPion, this.coul, this.ech, this.j);
					this.j.supprimerPiece(this); // Suppression du pion
					this.j.ajouterPiece(this.ech.getPieces()[xPion][yPion]); // Ajout de la nouvelle pi�ce
					break;
				case "Tour":
					this.ech.getPieces()[xPion][yPion] = new Tour(xPion, yPion, this.coul, this.ech, this.j);
					this.j.supprimerPiece(this); // Suppression du pion
					this.j.ajouterPiece(this.ech.getPieces()[xPion][yPion]); // Ajout de la nouvelle pi�ce
					break;
				case "Fou":
					this.ech.getPieces()[xPion][yPion] = new Fou(xPion, yPion, this.coul, this.ech, this.j);
					this.j.supprimerPiece(this); // Suppression du pion
					this.j.ajouterPiece(this.ech.getPieces()[xPion][yPion]); // Ajout de la nouvelle pi�ce
					break;
				case "Cavalier":
					this.ech.getPieces()[xPion][yPion] = new Cavalier(xPion, yPion, this.coul, this.ech, this.j);
					this.j.supprimerPiece(this); // Suppression du pion
					this.j.ajouterPiece(this.ech.getPieces()[xPion][yPion]); // Ajout de la nouvelle pi�ce
					break;	
				default:
					break;
			}
		}
		else {
			this.ech.getPieces()[xPion][yPion] = new Dame(xPion, yPion, this.coul, this.ech, this.j);
			this.j.supprimerPiece(this); // Suppression du pion
			this.j.ajouterPiece(this.ech.getPieces()[xPion][yPion]); // Ajout de la nouvelle pi�ce
		}
	}

	public boolean isDejaBouge() {
		return dejaBouge;
	}

	public void setaDejBouge(boolean DejaBouge) {
		this.dejaBouge = DejaBouge;
	}
	
	

	public boolean isaBouge2cases() {
		return aBouge2cases;
	}

	public void setaBouge2cases(boolean aBouge2cases) {
		this.aBouge2cases = aBouge2cases;
	}

	@Override
	protected boolean trajectoireLibre(int xDep, int yDep, int xArr, int yArr) {
		// M�thode inutile pour le pion
		return true;
	}
	
	

}
