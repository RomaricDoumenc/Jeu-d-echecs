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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Jeu extends Application { // Boucle principale où se déroulera la partie.
	
	public Roi roiBlanc; // Références des 2 rois de la partie
	public Roi roiNoir;

	public static void main(String[] args) {
        Application.launch(Jeu.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu d'échecs");
        primaryStage.getIcons().add(new Image("file:images/roiBlanc.png"));
        
        Group root = new Group();
        Scene scene = new Scene(root, 800, 1000, Color.LIGHTBLUE);
        
       
        Echiquier ech = new Echiquier(); // Echiquier de la partie
        Joueur j1 = new Joueur("blanc", Couleur.BLANC, ech); // Joueurs de la partie
        Joueur j2 = new Joueur("noir", Couleur.NOIR, ech);
        
        
        Pile pile = new Pile(); // Initialisation de la pile de coups
        
        
        j1.InitEchiquier(ech); // On pose les pièces des joueurs sur l'échiquier
        j2.InitEchiquier(ech);
        
        
        roiBlanc = (Roi) ech.getPieces()[7][4]; // Mémorisation des références des deux rois
        roiNoir = (Roi) ech.getPieces()[0][4];
        
        EchiquierView view = new EchiquierView(80, 80 , ech); // Affichage de l'échiquier
        
        Button boutonAnnulation = new Button("Annuler un coup"); // Bouton permettant d'annuler un ou plusieurs coups
        Button boutonQuitter = new Button("Quitter"); // Bouton permettant de quitter le jeu
        Button boutonSauvegarde = new Button("Sauvegarder la partie");
        Button boutonCharge = new Button("Charger la partie");
        
        boutonAnnulation.setTranslateX(view.getPosX());
        boutonAnnulation.setTranslateY(view.getPosY() + EchiquierView.largeurEchiquier + 50);
        boutonQuitter.setTranslateX(boutonAnnulation.getTranslateX());
        boutonQuitter.setTranslateY(boutonAnnulation.getTranslateY() + 150);
        boutonSauvegarde.setTranslateX(boutonAnnulation.getTranslateX());
        boutonSauvegarde.setTranslateY(boutonAnnulation.getTranslateY() + 100);
        boutonCharge.setTranslateX(boutonAnnulation.getTranslateX());
        boutonCharge.setTranslateY(boutonAnnulation.getTranslateY() + 50);
        boutonAnnulation.setDisable(true);
        
        Text etat = new Text(); // Message indiquant l'état de la partie
        etat.setTranslateX(view.getPosX() + EchiquierView.largeurEchiquier / 2);
        etat.setTranslateY(view.getPosY() - 10);
        etat.setText("");
        view.setOnMouseClicked(new EventHandler<MouseEvent>(){ // Déplacement d'une pièce si 2 clics sur l'échiquier

		    public void handle(MouseEvent me){
		    	
		    	if(view.getNbClics() % 2 == 0) {
		    		view.setxDep((int) (me.getY() / EchiquierView.largeurCase));
		    		view.setyDep((int) (me.getX() / EchiquierView.largeurCase));
		    		view.getCases()[view.getxDep()][view.getyDep()].getFond().setStroke(Color.GREEN);
		    		view.getCases()[view.getxDep()][view.getyDep()].getFond().setStrokeWidth(5);
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
		    				/* Si le coup précédent est égal à la situation actuelle 
		    				 * (c.à.d un déplacement invalide a été joué) , 
		    				 * ou bien que le déplacement amène à une situation d'échec dans son propre camp , alors
		    				 * Suppression du coup redondant */
		    				pile.depiler(ech);
		    				// Sinon mise à jour du joueur actuel
		    			else 
		    				ech.mettreAJourJoueurActuel();
		    			
		    			if(pile.getCoups().size() == 0)
				    		boutonAnnulation.setDisable(true);
				    	else
				    		boutonAnnulation.setDisable(false);// (Ré)activation du bouton si la pile n'est pas vide
		    			
		    			if(roiBlanc.estEchecEtMat()) {
		    				etat.setText("Les blancs sont échec et mat !");
		    				ech.setJoueurActuel(null); // Plus de joueur actuel , déplacements de pièces désactivé
		    				boutonAnnulation.setDisable(true); // Désactivation du bouton d'annulation , la partie est terminée
		    			}
		    			else if(roiNoir.estEchecEtMat()) {
		    				etat.setText("Les noirs sont échec et mat !");
		    				ech.setJoueurActuel(null); // Plus de joueur actuel , déplacements de pièces désactivé
		    				boutonAnnulation.setDisable(true); // Désactivation du bouton d'annulation , la partie est terminée
		    			}
		    			else if(roiBlanc.estEnEchec())
		    				etat.setText("Les blancs sont en échec !");
		    			else if(roiNoir.estEnEchec())
		    				etat.setText("Les noirs sont en échec !");
		    			
		    			else
		    				etat.setText("");
		    		}
		    		view.rafraichirAffichage(ech);
		    	}
		        view.setNbClics(view.getNbClics() + 1);
		        

		    }

		});
        
        
        boutonAnnulation.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions à effectuer si on presse le bouton d'annulation
		    public void handle(ActionEvent ae){
		    	pile.depiler(ech); // On revient au coup précédent
		    	ech.mettreAJourJoueurActuel(); // mise à jour du joueur actuel
		    	view.rafraichirAffichage(ech);
		    	if(pile.getCoups().size() == 0) // Désactivation du bouton si la pile est vide
		    		boutonAnnulation.setDisable(true);
		    	else
		    		boutonAnnulation.setDisable(false);
		    }

		});
        
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions à effectuer si on presse le bouton "Quitter"
		    public void handle(ActionEvent ae){
		    	primaryStage.close(); // Fermeture de la fenêtre
		    }

		});
        
        boutonSauvegarde.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions à effectuer si on presse le bouton "Sauvegarder"
		    public void handle(ActionEvent ae){
		    	sauvegarderPartie(ech, pile, j1, j2);
		    }

		});
        
        boutonCharge.setOnAction(new EventHandler<ActionEvent>(){
        	// Actions à effectuer si on presse le bouton "Charger"
		    public void handle(ActionEvent ae){
		    	chargerPartie(ech, pile, j1, j2);
		    	view.rafraichirAffichage(ech);
		    	for(Piece p : j1.getPieces()) { // Actualisation des références des 2 rois (blanc et noir)
		    		if(p instanceof Roi)
		    			roiBlanc = (Roi) p;
		    	}
		    	for(Piece p : j2.getPieces()) {
		    		if(p instanceof Roi)
		    			roiNoir = (Roi) p;
		    	}
		    	if(pile.getCoups().size() == 0)
		    		boutonAnnulation.setDisable(true); // Désactivation du bouton si la pile est vide
		    	else
		    		boutonAnnulation.setDisable(false); // (Ré)activation du bouton si la pile n'est pas vide
		    }

		});
        
        root.getChildren().add(view);
        root.getChildren().add(boutonAnnulation);
        root.getChildren().add(boutonSauvegarde);
        root.getChildren().add(boutonCharge);
        root.getChildren().add(boutonQuitter);
        root.getChildren().add(etat);
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show(); // Affichage de l'interface graphique
        
        //Pion pion = (Pion) ech.getPieces()[6][0];
        //pion.promouvoir();
        
    }
    
    public void sauvegarderPartie(Echiquier ech , Pile p , Joueur j1 , Joueur j2) { /* Permet de sauvegarder l'échiquier , la pile de coups et les joueurs
		 * dans un fichier */
    	ObjectOutputStream flux;
    		try {
    			flux = new ObjectOutputStream(
    					new BufferedOutputStream(
    						new FileOutputStream(
    							new File("partie"))));
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
    
    public void chargerPartie(Echiquier ech , Pile p , Joueur j1 , Joueur j2) { /* Permet de charger le fichier comprenant
		 * l'échiquier , la pile de coups et les joueurs */
    		ObjectInputStream flux;
    			try {
    				flux = new ObjectInputStream(
    						new BufferedInputStream(
    							new FileInputStream(
    								new File("partie"))));
    				
    				Echiquier echACopier = (Echiquier) flux.readObject();
    				Pile pileACopier = (Pile) flux.readObject();
    				Joueur j1ACopier = (Joueur) flux.readObject();
    				Joueur j2ACopier = (Joueur) flux.readObject();
    				
    				// Copie à la main des attributs des objets lus vers leurs emplacements de destination
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
    					for(j=0 ; j<8 ; j++) { /* Mise à jour des joueurs et échiquier de chaque pièce
    											* car les références ont changé */
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
    					e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}  catch (ClassNotFoundException e) {

    				e.printStackTrace();

    			}
    }

}
