package classes;

public class Test { // Tester les différentes classes.
	
	public static void afficherEchiquier(Echiquier e) {
		int i,k;
		for(i=0 ; i<8 ; i++) {
			for(k=0 ; k<8 ; k++) {
				if (e.getPieces()[i][k] != null) {
					if(e.getPieces()[i][k].getClass().toString().equals("class classes.Pion") )
						System.out.print("P ");
					else if(e.getPieces()[i][k].getClass().toString().equals("class classes.Cavalier") )
						System.out.print("C ");
					else if(e.getPieces()[i][k].getClass().toString().equals("class classes.Tour") )
						System.out.print("T ");
					else if(e.getPieces()[i][k].getClass().toString().equals("class classes.Fou") )
						System.out.print("F ");
					else if(e.getPieces()[i][k].getClass().toString().equals("class classes.Dame") )
						System.out.print("D ");
					else if(e.getPieces()[i][k].getClass().toString().equals("class classes.Roi") )
						System.out.print("R ");
				}
				else	
					System.out.print("- ");
			}
			System.out.println();
		}
	}

	/*public static void main(String[] args) {
		Echiquier e = new Echiquier();
		Joueur j = new Joueur("blanc", Couleur.BLANC, e);
		Joueur j2 = new Joueur("noir", Couleur.NOIR, e);

		
		j.InitEchiquier(e);
		j2.InitEchiquier(e);
		

		
		Roi roiBlanc = (Roi) e.getPieces()[7][4];
		Dame dameNoire = (Dame) e.getPieces()[0][3];
		Tour tourNoire = (Tour) e.getPieces()[0][0];
		Tour tourBlanche = (Tour) e.getPieces()[7][0];
		
		int i,k;
		for(i=0 ; i<8 ; i++)
			for(k=0 ; k<8 ; k++)
				e.getPieces()[i][k] = null;
		
		e.getPieces()[0][0] = roiBlanc;
		e.getPieces()[0][6] = dameNoire;
		e.getPieces()[1][5] = tourNoire;
		e.getPieces()[0][7] = tourBlanche;
		
		e.mettreAJourCoordonnesPieces();
		
		


		
		afficherEchiquier(e);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(roiBlanc.estEchecEtMat());
		
		
		
		
			
		
	}*/

}
