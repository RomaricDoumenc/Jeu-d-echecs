package classes;

import java.util.ArrayList;

public class Joueur { // un joueur
	
	private String nom; // Nom du joueur
	private Couleur coul; // Couleur des pièces du joueur
	private ArrayList<Piece> pieces; // listes des pièces du joueur
	
	public Joueur(String nom , Couleur coul) {
		/*this.nom = nom;
		this.coul = coul;
		this.pieces = new ArrayList<Piece>();
		
		int i;
		for(i=1 ; i<=8 ; i++)
			pieces.add(new Pion())*/
	}
	
	public void deplacerPiece(int xDep , int yDep , int xArr , int yArr , Echiquier ech) {
		// Déplace une pièce (si c'est possible)
	}
	
	public void ajouterPiece(Piece p) { // Ajoute une pièce à la liste des pièces du joueur.
		
	}
	public void supprimerPiece(Piece p) { // Supprime une pièce à la liste des pièces du joueur.
		
	}
	
	public void InitEchiquier(Echiquier ech) { // Place les pièces de départ du joueur sur l'échiquier.
		
	}

	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Couleur getCoul() {
		return coul;
	}

	public void setCoul(Couleur coul) {
		this.coul = coul;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
	
	

}
