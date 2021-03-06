package classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Pile implements Serializable { // Pile contenant l'ensemble des coups jou�s depuis le d�but de la partie.
	
	private static final long serialVersionUID = 7066916896549162520L;
	
	private ArrayList<Piece[][]> coups; // Liste des coups ( grilles qui repr�sente l'�chiquier � un �tat n donn� de la partie.

	public Pile() {
		this.coups = new ArrayList<Piece[][]>();
	}
	
	public void empiler(Echiquier ech) { // Ajouter un coup � la pile des coups � partir de l'�chiquier
		int i,j;
		Piece[][] nouveauCoup = new Piece[8][8];
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++)
				nouveauCoup[i][j] = ech.getPieces()[i][j]; // Remplissage d'une grille � partir de la grille de l'�chiquier
		this.coups.add(nouveauCoup);
	}
	
	public void depiler(Echiquier ech) {
		int taillePile = this.coups.size();
		if(taillePile > 0) {
			int i,j;
			Piece[][] dernierCoup = this.coups.get(taillePile - 1);
			for(i=0 ; i<8 ; i++)
				for(j=0 ; j<8 ; j++)
					ech.getPieces()[i][j] = dernierCoup[i][j];
			this.coups.remove(dernierCoup);
			ech.mettreAJourCoordonnesPieces();
			ech.mettreAJourListeJoueurs();
			
			for(i=0 ; i<8 ; i++)
				for(j=0 ; j<8 ; j++)
					if(ech.getPieces()[i][j] != null)
						if(ech.getPieces()[i][j] instanceof Pion) {
							Pion p = (Pion) ech.getPieces()[i][j];
							p.resetDejaBouge();
						}
							
		}
	}
	
	public static boolean coupsEgaux(Piece[][] c1 , Piece[][] c2) { // Indique si 2 coups sont egaux
		int i,j;
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++)
				if(c1[i][j] != c2[i][j])
					return false;
		return true;
	}

	public ArrayList<Piece[][]> getCoups() {
		return coups;
	}

	public void setCoups(ArrayList<Piece[][]> coups) {
		this.coups = coups;
	}
	
	

	

}
