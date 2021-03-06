package graphique;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import classes.Couleur;
import classes.Echiquier;
import classes.Joueur;
import classes.Piece;
import classes.Pile;
import classes.Pion;
import classes.Roi;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Jeu extends Application { // Boucle principale o� se d�roulera la partie.
	
	public Roi roiBlanc; // R�f�rences des 2 rois de la partie
	public Roi roiNoir;
	Rectangle2D screenResolution = Screen.getPrimary().getVisualBounds(); // On prend la r�solution d'�cran de l'utilisateur


	public static void main(String[] args) {
        Application.launch(Jeu.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu d'�checs");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/roiBlanc.png")));
        
        
        final Menu menuFichier = new Menu("Fichier");
        //final Menu menuAnnuler = new Menu("Annuler un coup");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menuFichier);
        SeparatorMenuItem separateur = new SeparatorMenuItem();
        MenuItem menuCharge = new MenuItem("Charger la partie");
        MenuItem menuSauvegarde = new MenuItem("Sauvegarder la partie");
        MenuItem menuQuitter = new MenuItem("Quitter");
        MenuItem menuSupprimer = new MenuItem("Supprimer une partie");
        MenuItem menuReset = new MenuItem("Nouvelle partie");
        MenuItem menuAlea = new MenuItem("Partie al�atoire");
        menuFichier.getItems().add(menuCharge);
        menuFichier.getItems().add(menuSauvegarde);
        menuFichier.getItems().add(menuSupprimer);
        menuFichier.getItems().add(menuReset);
        menuFichier.getItems().add(menuAlea);
        menuFichier.getItems().add(separateur);
        menuFichier.getItems().add(menuQuitter);
        
        
       
        
        Stop[] stops = new Stop[] { new Stop(0, Color.WHITE),new Stop(1, Color.LIGHTBLUE)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        // Cr�ation d'un d�gard� variant du blanc au bleu ciel
        
        Group root = new Group();
        Scene scene = new Scene(root, 800, 1000, lg1);
        
        menuBar.setMinWidth(scene.getWidth());
        
        Echiquier ech = new Echiquier(); // Echiquier de la partie
        Joueur j1 = new Joueur("blanc", Couleur.BLANC, ech); // Joueurs de la partie
        Joueur j2 = new Joueur("noir", Couleur.NOIR, ech);
        
        
        Pile pile = new Pile(); // Initialisation de la pile de coups
        
        
        j1.InitEchiquier(ech); // On pose les pi�ces des joueurs sur l'�chiquier
        j2.InitEchiquier(ech);
        
        
        // M�morisation des r�f�rences des deux rois
        for(Piece p : j1.getPieces()) {
        	if(p instanceof Roi)
        		 roiBlanc = (Roi) p;
        }
        for(Piece p : j2.getPieces()) {
        	if(p instanceof Roi)
        		 roiNoir = (Roi) p;
        }
        
        
        

        
        EchiquierView view = new EchiquierView(80, 80 , ech , scene); // Affichage de l'�chiquier
        

        
        Button boutonAnnulation = new Button("Annuler un coup"); // Bouton permettant d'annuler un ou plusieurs coups
        
        boutonAnnulation.setTranslateX(view.getPosX());
        boutonAnnulation.setTranslateY(view.getPosY() + EchiquierView.largeurEchiquier + 50);
        boutonAnnulation.setDisable(true);
        
        Text etat = new Text(); // Message indiquant l'�tat de la partie
        etat.setTranslateX(view.getPosX() + EchiquierView.largeurEchiquier / 2);
        etat.setTranslateY(view.getPosY() - 10);
        etat.setText("");
        view.setOnMouseClicked(new EventHandler<MouseEvent>(){ // D�placement d'une pi�ce si 2 clics sur l'�chiquier

		    public void handle(MouseEvent me){
		    	
		    	if(view.getNbClics() % 2 == 0) {
		    		view.setxDep((int) (me.getY() / EchiquierView.largeurCase));
		    		view.setyDep((int) (me.getX() / EchiquierView.largeurCase));
		    		if(view.getCases()[view.getxDep()][view.getyDep()].getImage() != null)
		    			view.getCases()[view.getxDep()][view.getyDep()].getImage().setEffect(new DropShadow(EchiquierView.largeurCase/2, Color.GREEN));
		    		//view.montrerDeplacementsPossibles(ech, view.getxDep(), view.getyDep());
		    	}
		    	else {
		    		view.setxArr((int) (me.getY() / EchiquierView.largeurCase));
		    		view.setyArr((int) (me.getX() / EchiquierView.largeurCase));
		    		if((ech.getPieces()[view.getxDep()][view.getyDep()] != null) &&
		    			(ech.getPieces()[view.getxDep()][view.getyDep()].getCoul() == ech.getJoueurActuel())) {
		    			pile.empiler(ech);
		    			
		    			
		    			ech.getPieces()[view.getxDep()][view.getyDep()].seDeplacer(view.getxArr()
		    				, view.getyArr());
		    			if((Pile.coupsEgaux(pile.getCoups().get(pile.getCoups().size()-1),
		    					ech.getPieces())) || (roiBlanc.estEnEchec() && ech.getJoueurActuel() == Couleur.BLANC)
		    					|| (roiNoir.estEnEchec() && ech.getJoueurActuel() == Couleur.NOIR))
		    				/* Si le coup pr�c�dent est �gal � la situation actuelle 
		    				 * (c.�.d un d�placement invalide a �t� jou�) , 
		    				 * ou bien que le d�placement am�ne � une situation d'�chec dans son propre camp , alors
		    				 * Suppression du coup redondant ou interdit */
		    				pile.depiler(ech);
		    				// Sinon mise � jour du joueur actuel
		    			else {
		    				ech.mettreAJourJoueurActuel();
		    				view.animerPiece(view.getxDep(), view.getyDep(), view.getxArr(), view.getyArr(), ech);
		    				int i;
		    				for(i=0 ; i<8 ; i++) { /* Si un pion blanc se trouve sur la rang�e tout en haut ou
		    					                    * si un pion noir se trouve sur la rang�e tout en bas
		    					                    * alors promotion de ce pion */
		    					if ( (ech.getPieces()[0][i] != null) && (ech.getPieces()[0][i] instanceof Pion)
		    						&& (ech.getPieces()[0][i].getCoul() == Couleur.BLANC) ) {
		    						Pion p = (Pion) ech.getPieces()[0][i];
		    						p.promouvoir(pile);
		    						view.rafraichirAffichage(ech);
		    					}
		    					if ( (ech.getPieces()[7][i] != null) && (ech.getPieces()[7][i] instanceof Pion)
			    						&& (ech.getPieces()[7][i].getCoul() == Couleur.NOIR) ) {
			    						Pion p = (Pion) ech.getPieces()[7][i];
			    						p.promouvoir(pile);
			    						view.rafraichirAffichage(ech);
			    					}
		    				}
		    				if(ech.insufficanceMaterielle() == true) {
    							afficherNul("Insuffisance mat�rielle");
    						}
		    				
		    				
		    			}
		    			
		    			if(pile.getCoups().size() == 0)
				    		boutonAnnulation.setDisable(true);
				    	else
				    		boutonAnnulation.setDisable(false);// (R�)activation du bouton si la pile n'est pas vide
		    			
		    			if(roiBlanc.estEchecEtMat()) {
		    				afficherEchecEtMat();
		    				ech.setJoueurActuel(null); // Plus de joueur actuel , d�placements de pi�ces d�sactiv�
		    				boutonAnnulation.setDisable(true); // D�sactivation du bouton d'annulation , la partie est termin�e
		    			}
		    			else if(roiNoir.estEchecEtMat()) {
		    				afficherEchecEtMat();
		    				ech.setJoueurActuel(null); // Plus de joueur actuel , d�placements de pi�ces d�sactiv�
		    				boutonAnnulation.setDisable(true); // D�sactivation du bouton d'annulation , la partie est termin�e
		    			}
		    			else if(roiBlanc.estEnEchec())
		    				etat.setText("Les blancs sont en �chec !");
		    			else if(roiNoir.estEnEchec())
		    				etat.setText("Les noirs sont en �chec !");
		    			else if(ech.pat() == true) {
	    					afficherNul("Pat");
	    				}
		    			
		    			else
		    				etat.setText("");
		    		}
		    		//view.rafraichirAffichage(ech);
		    		view.animerPiece(view.getxDep(), view.getyDep(), view.getxArr(), view.getyArr() , ech);
		    	}
		        view.setNbClics(view.getNbClics() + 1);
		        

		    }

		});
        
        
        boutonAnnulation.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton d'annulation
		    public void handle(ActionEvent ae){
		    	pile.depiler(ech); // On revient au coup pr�c�dent
		    	ech.mettreAJourJoueurActuel(); // mise � jour du joueur actuel
		    	view.rafraichirAffichage(ech);
		    	if(pile.getCoups().size() == 0) // D�sactivation du bouton si la pile est vide
		    		boutonAnnulation.setDisable(true);
		    	else
		    		boutonAnnulation.setDisable(false);
		    }

		});
        
        menuQuitter.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Quitter"
		    public void handle(ActionEvent ae){
		    	primaryStage.close(); // Fermeture de la fen�tre
		    }

		});
        
        menuSauvegarde.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Sauvegarder"
		    public void handle(ActionEvent ae){
		    	sauvegarderPartie(ech, pile, j1, j2);
		    }

		});
        
        menuCharge.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Charger"
		    public void handle(ActionEvent ae){
		    	chargerPartie(ech, pile, j1, j2);
		    	view.rafraichirAffichage(ech);
		    	for(Piece p : j1.getPieces()) { // Actualisation des r�f�rences des 2 rois (blanc et noir)
		    		if(p instanceof Roi)
		    			roiBlanc = (Roi) p;
		    	}
		    	for(Piece p : j2.getPieces()) {
		    		if(p instanceof Roi)
		    			roiNoir = (Roi) p;
		    	}
		    	if(pile.getCoups().size() == 0)
		    		boutonAnnulation.setDisable(true); // D�sactivation du bouton si la pile est vide
		    	else
		    		boutonAnnulation.setDisable(false); // (R�)activation du bouton si la pile n'est pas vide
		    }

		});
        menuSupprimer.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Charger"
		    public void handle(ActionEvent ae){
		    	supprimerPartie();
		    }
        });
        menuReset.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Charger"
		    public void handle(ActionEvent ae){
		    	//resetPartie(ech, j1, j2, pile);
		    	resetPartie(ech, j1, j2, pile);
		    	view.rafraichirAffichage(ech);
		    	for(Piece p : j1.getPieces()) { // Actualisation des r�f�rences des 2 rois (blanc et noir)
		    		if(p instanceof Roi)
		    			roiBlanc = (Roi) p;
		    	}
		    	for(Piece p : j2.getPieces()) {
		    		if(p instanceof Roi)
		    			roiNoir = (Roi) p;
		    	}
		    	if(pile.getCoups().size() == 0)
		    		boutonAnnulation.setDisable(true); // D�sactivation du bouton si la pile est vide
		    	else
		    		boutonAnnulation.setDisable(false); // (R�)activation du bouton si la pile n'est pas vide
		    }
        });
        menuAlea.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions � effectuer si on presse le bouton "Charger"
		    public void handle(ActionEvent ae){
		    	//resetPartie(ech, j1, j2, pile);
		    	partieAleatoire(ech, j1, j2, pile);
		    	view.rafraichirAffichage(ech);
		    	for(Piece p : j1.getPieces()) { // Actualisation des r�f�rences des 2 rois (blanc et noir)
		    		if(p instanceof Roi)
		    			roiBlanc = (Roi) p;
		    	}
		    	for(Piece p : j2.getPieces()) {
		    		if(p instanceof Roi)
		    			roiNoir = (Roi) p;
		    	}
		    	if(pile.getCoups().size() == 0)
		    		boutonAnnulation.setDisable(true); // D�sactivation du bouton si la pile est vide
		    	else
		    		boutonAnnulation.setDisable(false); // (R�)activation du bouton si la pile n'est pas vide
		    }
        });
        
        root.getChildren().add(view);
        root.getChildren().add(boutonAnnulation);
        root.getChildren().add(menuBar);
        root.getChildren().add(etat);
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show(); // Affichage de l'interface graphique
        
        //Pion pion = (Pion) ech.getPieces()[6][0];
        //pion.promouvoir();
        
    }
    
    public void sauvegarderPartie(Echiquier ech , Pile p , Joueur j1 , Joueur j2) { /* Permet de sauvegarder l'�chiquier , la pile de coups et les joueurs
		 * dans un fichier */
    	
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Sauvegarder la partie");
    	//dialog.setHeaderText("Look, a Text Input Dialog");
    	dialog.setContentText("Entrez le nom du fichier : ");

 
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){ /* Si l'utilisateur a saisi le nom du fichier , alors cr�ation de ce fichier (s'il n'existe pas)
    								et sauvegarde des objets(�chiquier , pi�ces , joueurs et pile) */
    		ObjectOutputStream flux;
    		getClass().getResource("sauvegardes");
    		String nomFichier = "src/sauvegardes/" + result.get();
    		try {
    			flux = new ObjectOutputStream(
    					new BufferedOutputStream(
    						new FileOutputStream(
    							new File(nomFichier))));
    				flux.writeObject(ech);
    				flux.writeObject(p);
    				flux.writeObject(j1);
    				flux.writeObject(j2);
    				flux.close();
    				}
    		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	  
    }
    
    public void chargerPartie(Echiquier ech , Pile p , Joueur j1 , Joueur j2) { /* Permet de charger le fichier comprenant
		 * l'�chiquier , la pile de coups et les joueurs */
    	
    	String repertoire = "src/sauvegardes";
		String[] listeParties = null;
		
		File f = new File(repertoire);
		if(f.isDirectory()) {
			listeParties = f.list();
			if(listeParties.length == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setContentText("Aucune partie sauvegard�e. Impossible de charger une partie.");
				alert.showAndWait();
				return; // Annulation du chargement
			}
			
		}
		
		ArrayList<String> choices = new ArrayList<String>();
		for (String s : listeParties)
			choices.add(s);
		
    	
    	ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
    	dialog.setTitle("Charger une partie");
    	//dialog.setHeaderText("Look, a Text Input Dialog");
    	dialog.setContentText("Choisissez la partie � charger :");

 
    	Optional<String> result = dialog.showAndWait();
    	if(result.isPresent()) {
    		ObjectInputStream flux;
    		String chemin = repertoire + "/" + result.get();
    		
			try {
				
				flux = new ObjectInputStream(
						new BufferedInputStream(
							new FileInputStream(
								new File(chemin))));
				
				Echiquier echACopier = (Echiquier) flux.readObject();
				Pile pileACopier = (Pile) flux.readObject();
				Joueur j1ACopier = (Joueur) flux.readObject();
				Joueur j2ACopier = (Joueur) flux.readObject();
				
				// Copie � la main des attributs des objets lus vers leurs emplacements de destination
				ech.setPieces(echACopier.getPieces());
				ech.setJoueurActuel(echACopier.getJoueurActuel());
				
				p.setCoups(pileACopier.getCoups());
				
				j1.setCoul(j1ACopier.getCoul());
				j1.setNom(j1ACopier.getNom());
				j1.setPieces(j1ACopier.getPieces());
				
				j2.setCoul(j2ACopier.getCoul());
				j2.setNom(j2ACopier.getNom());
				j2.setPieces(j2ACopier.getPieces());
				
				int i,j;
				for(i=0 ; i<8 ; i++)
					for(j=0 ; j<8 ; j++) { /* Mise � jour des joueurs et �chiquier de chaque pi�ce
											* car les r�f�rences ont chang� */
						if(ech.getPieces()[i][j] != null) {
							ech.getPieces()[i][j].setEch(ech);
    						if(ech.getPieces()[i][j].getCoul() == Couleur.BLANC)
    							ech.getPieces()[i][j].setJ(j1);
    						else
    							ech.getPieces()[i][j].setJ(j2);
						}
						
					}
						
				
				ech.mettreAJourCoordonnesPieces();
				ech.mettreAJourListeJoueurs();
				
				flux.close();
			}
			catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				
				
				alert.setContentText("Le fichier \"" + chemin + "\" est introuvable.");

				alert.showAndWait();
				
			} catch (IOException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {

				e.printStackTrace();

			}
    	}
    		
    }
    
    public void supprimerPartie() { /* Permet de supprimer une partie */
    	
    	String repertoire = "src/sauvegardes";
		String[] listeParties = null;
		
		File f = new File(repertoire);
		if(f.isDirectory()) {
			listeParties = f.list();
			if(listeParties.length == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setContentText("Aucune partie sauvegard�e. Impossible de supprimer une partie.");
				alert.showAndWait();
				return; // Annulation du chargement
			}
			
		}
		
		ArrayList<String> choices = new ArrayList<String>();
		for (String s : listeParties)
			choices.add(s);
		
    	
    	ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
    	dialog.setTitle("Supprimer une partie");
    	//dialog.setHeaderText("Look, a Text Input Dialog");
    	dialog.setContentText("Choisissez la partie � effacer :");

 
    	Optional<String> result = dialog.showAndWait();
    	if(result.isPresent()) {
    		String chemin = repertoire + "/" + result.get();
    		
			File fichierASuppr = new File(chemin);
			fichierASuppr.delete();
    	}
    		
    }
    
    public void partieAleatoire (Echiquier ech ,  Joueur j1  , Joueur j2 , Pile p) {
    	// G�n�re une partie al�atoire de Chess960.
    	resetPartie(ech, j1, j2, p);
    	ech.setPieces(new Piece[8][8]);
    	
    	int i;
    	for(i=0 ; i<8 ; i++) { // Position 0 � 7 dans les listes : les pions
    		ech.getPieces()[6][i] = j1.getPieces().get(i);
    		ech.getPieces()[1][i] = j2.getPieces().get(i);	
    	}
    	// Placement du 1er fou
    	int y = ((int) (4 * Math.random())) * 2 + 1;
    	ech.getPieces()[7][y] = j1.getPieces().get(12);
    	ech.getPieces()[0][y] = j2.getPieces().get(12);
    	
    	// Placement du 2e fou
    	y = ((int) (4 * Math.random())) * 2;
    	ech.getPieces()[7][y] = j1.getPieces().get(13);
    	ech.getPieces()[0][y] = j2.getPieces().get(13);
    	
    	// Placement des tours
    	boolean placePourLeRoi = false;
    	int y1 = (int) (8 * Math.random());
    	int y2 = (int) (8 * Math.random());
    	while((ech.getPieces()[0][y1] != null) || (ech.getPieces()[0][y2] != null) || (Math.abs(y2 - y1) < 2) || (placePourLeRoi == false)) {
    		y1 = (int) (8 * Math.random());
        	y2 = (int) (8 * Math.random());
        	for(i=y1+1 ; i<y2 ; i++) {
        		if(ech.getPieces()[0][i] == null)
        			placePourLeRoi = true;
        	}
    	}
    	ech.getPieces()[7][y1] = j1.getPieces().get(8);
    	ech.getPieces()[7][y2] = j1.getPieces().get(9);
    	ech.getPieces()[0][y1] = j2.getPieces().get(8);
    	ech.getPieces()[0][y2] = j2.getPieces().get(9);
    	
    	// Placement du roi
    	if(Math.abs(y2 - y1) == 2)
    		y = (y1+y2)/2;
    	else {
    		if(y1 > y2) {
    			int tampon = y1;
    			y1 = y2;
    			y2 = tampon;
    		}
    		y = (int) (Math.random() * (y2-y1-1)) + y1 + 1;
    		while(ech.getPieces()[0][y] != null)
    			y = (int) (Math.random() * (y2-y1-1)) + y1 + 1;
    	}
    	ech.getPieces()[7][y] = j1.getPieces().get(15);
    	ech.getPieces()[0][y] = j2.getPieces().get(15);
    	
    	// Placement des cavaliers
    	y = (int) (8 * Math.random());
    	while(ech.getPieces()[0][y] != null)
    		y = (int) (8 * Math.random());
    	ech.getPieces()[7][y] = j1.getPieces().get(10);
    	ech.getPieces()[0][y] = j2.getPieces().get(10);
    	y = (int) (8 * Math.random());
    	while(ech.getPieces()[0][y] != null)
    		y = (int) (8 * Math.random());
    	ech.getPieces()[7][y] = j1.getPieces().get(11);
    	ech.getPieces()[0][y] = j2.getPieces().get(11);
    	
    	// Placement de la dame
    	y = (int) (8 * Math.random());
    	while(ech.getPieces()[0][y] != null)
    		y = (int) (8 * Math.random());
    	ech.getPieces()[7][y] = j1.getPieces().get(14);
    	ech.getPieces()[0][y] = j2.getPieces().get(14);
    	
    	
    	
    	ech.mettreAJourCoordonnesPieces();
    	
    	
    	
    	
    }
    
    public void resetPartie(Echiquier ech ,  Joueur j1  , Joueur j2 , Pile p) {
    	Echiquier echACopier = new Echiquier();
		Pile pileACopier = new Pile();
		Joueur j1ACopier = new Joueur("blanc" , Couleur.BLANC , echACopier);
		Joueur j2ACopier = new Joueur("noir" , Couleur.NOIR , echACopier);
		j1ACopier.InitEchiquier(echACopier);
		j2ACopier.InitEchiquier(echACopier);
		
		// Copie � la main des attributs des objets cr��s vers leurs emplacements de destination
		ech.setPieces(echACopier.getPieces());
		ech.setJoueurActuel(echACopier.getJoueurActuel());
		
		p.setCoups(pileACopier.getCoups());
		
		j1.setCoul(j1ACopier.getCoul());
		j1.setNom(j1ACopier.getNom());
		j1.setPieces(j1ACopier.getPieces());
		
		j2.setCoul(j2ACopier.getCoul());
		j2.setNom(j2ACopier.getNom());
		j2.setPieces(j2ACopier.getPieces());
		
		int i,j;
		for(i=0 ; i<8 ; i++)
			for(j=0 ; j<8 ; j++) { /* Mise � jour des joueurs et �chiquier de chaque pi�ce
									* car les r�f�rences ont chang� */
				if(ech.getPieces()[i][j] != null) {
					ech.getPieces()[i][j].setEch(ech);
					if(ech.getPieces()[i][j].getCoul() == Couleur.BLANC)
						ech.getPieces()[i][j].setJ(j1);
					else
						ech.getPieces()[i][j].setJ(j2);
				}
				
			}
				
		
		ech.mettreAJourCoordonnesPieces();
		ech.mettreAJourListeJoueurs();
    }


    public void afficherEchecEtMat() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Echec et mat");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/roiBlanc.png")));
		alert.setContentText("Echec et mat !");

		alert.show();
    }
    public void afficherNul(String message) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Nul");
		alert.setHeaderText("Nul");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/roiBlanc.png")));
		alert.setContentText(message);

		alert.showAndWait();
    }
    
    

}
