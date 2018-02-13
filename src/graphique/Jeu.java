package graphique;

import classes.Couleur;
import classes.Echiquier;
import classes.Joueur;
import classes.Pile;
import classes.Pion;
import classes.Roi;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Jeu extends Application { // Boucle principale où se déroulera la partie.

	public static void main(String[] args) {
        Application.launch(Jeu.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu d'échecs");
        
        Group root = new Group();
        Scene scene = new Scene(root, 800, 1000, Color.LIGHTBLUE);
        
       
        Echiquier ech = new Echiquier(); // Echiquier de la partie
        Joueur j1 = new Joueur("blanc", Couleur.BLANC, ech); // Joueurs de la partie
        Joueur j2 = new Joueur("noir", Couleur.NOIR, ech);
        
        Pile pile = new Pile();
        
        
        j1.InitEchiquier(ech); // On pose les pièces des joueurs sur l'échiquier
        j2.InitEchiquier(ech);
        
        
        Roi roiBlanc = (Roi) ech.getPieces()[7][4];
        Roi roiNoir = (Roi) ech.getPieces()[0][4];
        
        EchiquierView view = new EchiquierView(80, 80 , ech); // Affichage de l'échiquier
        
        Button boutonAnnulation = new Button();
        boutonAnnulation.setTranslateX(view.getPosX());
        boutonAnnulation.setTranslateY(view.getPosY() + EchiquierView.largeurEchiquier + 50);
        boutonAnnulation.setText("Annuler un coup");
        boutonAnnulation.setDisable(true);
        
        Text etat = new Text();
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
		    		if(ech.getPieces()[view.getxDep()][view.getyDep()] != null) {
		    			pile.empiler(ech);
		    			
		    			
		    			ech.getPieces()[view.getxDep()][view.getyDep()].seDeplacer(view.getxArr()
		    				, view.getyArr());
		    			if(Pile.coupsEgaux(pile.getCoups().get(pile.getCoups().size()-1),
		    					ech.getPieces()))
		    				// Si 2 coups de suite concéscutifs sont égaux (c.à.d un déplacement invalide a été joué)
		    				// Suppression du coup redondant
		    				pile.depiler(ech);
		    			
		    			if(pile.getCoups().size() == 0)
				    		boutonAnnulation.setDisable(true);
				    	else
				    		boutonAnnulation.setDisable(false);// (Ré)activation du bouton si la pile n'est pas vide
		    			
		    			if(roiBlanc.estEchecEtMat())
		    				etat.setText("Les blancs sont échec et mat !");
		    			else if(roiNoir.estEchecEtMat())
		    				etat.setText("Les noirs sont échec et mat !");
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
		    public void handle(ActionEvent ae){
		    	pile.depiler(ech);
		    	view.rafraichirAffichage(ech);
		    	if(pile.getCoups().size() == 0) // Désactivation du bouton si la pile est vide
		    		boutonAnnulation.setDisable(true);
		    	else
		    		boutonAnnulation.setDisable(false);
		    }

		});
        
        root.getChildren().add(view);
        root.getChildren().add(boutonAnnulation);
        root.getChildren().add(etat);
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Pion pion = (Pion) ech.getPieces()[6][0];
        //pion.promouvoir();
        
    }

}
