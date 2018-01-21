package classes;

import java.util.ArrayList;

public class Joueur { // un joueur
	
	private String nom; // Nom du joueur
	private Couleur coul; // Couleur des pièces du joueur
	private ArrayList<Piece> pieces; // listes des pièces du joueur
	
	public Joueur(String nom , Couleur coul , Echiquier ech) {
		this.nom = nom;
		this.coul = coul;
		this.pieces = new ArrayList<Piece>();
		
		if (coul == Couleur.BLANC) { // Initialisation des 16 pièces du joueur en fonction de sa couleur
			int i;
			for(i=0 ; i<8 ; i++) 
				pieces.add(new Pion(6,i,coul,ech,this));
			
			pieces.add(new Tour(7,0,coul,ech,this));
			pieces.add(new Tour(7,7,coul,ech,this));
			pieces.add(new Cavalier(7, 1, coul, ech, this));
			pieces.add(new Cavalier(7, 6, coul, ech, this));
			pieces.add(new Fou(7,2,coul,ech,this));
			pieces.add(new Fou(7,5,coul,ech,this));
			pieces.add(new Dame(7,3,coul,ech,this));
			pieces.add(new Roi(7,4,coul,ech,this));
		}
		if (coul == Couleur.NOIR) {
			int i;
			for(i=0 ; i<8 ; i++) 
				pieces.add(new Pion(1,i,coul,ech,this));
			
			pieces.add(new Tour(0,0,coul,ech,this));
			pieces.add(new Tour(0,7,coul,ech,this));
			pieces.add(new Cavalier(0, 1, coul, ech, this));
			pieces.add(new Cavalier(0, 6, coul, ech, this));
			pieces.add(new Fou(0,2,coul,ech,this));
			pieces.add(new Fou(0,5,coul,ech,this));
			pieces.add(new Dame(0,3,coul,ech,this));
			pieces.add(new Roi(0,4,coul,ech,this));
		}
	}
	
	public void deplacerPiece(int xDep , int yDep , int xArr , int yArr , Echiquier ech) {
		// Déplace une pièce (si c'est possible)
	}
	
	public void ajouterPiece(Piece p) { // Ajoute une pièce à la liste des pièces du joueur.
		
	}
	public void supprimerPiece(Piece p) { // Supprime une pièce à la liste des pièces du joueur.
		
	}
	
	public void InitEchiquier(Echiquier ech) { // Place les pièces de départ du joueur sur l'échiquier.
		for(Piece p : this.getPieces())
			ech.getPieces()[p.getX()][p.getY()] = p;
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
