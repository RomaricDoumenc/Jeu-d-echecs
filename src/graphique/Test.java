package graphique;


import classes.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application { // Tester les différentes classes.

    public static void main(String[] args) {
        Application.launch(Test.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu d'échecs");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800, Color.LIGHTGREEN);
        
       
        Echiquier ech = new Echiquier();
        Joueur j1 = new Joueur("blanc", Couleur.BLANC, ech);
        Joueur j2 = new Joueur("noir", Couleur.NOIR, ech);
        j1.InitEchiquier(ech);
        j2.InitEchiquier(ech);
        
        
        EchiquierView view = new EchiquierView(80, 80 , ech);
        root.getChildren().add(view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
