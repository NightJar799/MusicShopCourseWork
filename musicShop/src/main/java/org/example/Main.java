package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.AvailableSettings.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.example.Entity.*;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        Label label1 = new Label("Label1");
        Label label2 = new Label("Label2");
        Label label3 = new Label("Label3");
        Label label4 = new Label("Label4");
        Label label5 = new Label("Label5");
        Label label6 = new Label("Label6");
        Label label7 = new Label("Label7");

        VBox root = new VBox(10, label1, label2, label3, label4, label5, label6, label7);
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);

        stage.setTitle("VBox in JavaFX");

        stage.show();
    }
//        var factory = new Configuration()
//                .addAnnotatedClass(LabelMS.class)
//                .addAnnotatedClass(Album.class)
//                .addAnnotatedClass(Composition.class)
//                .addAnnotatedClass(Group.class)
//                .addAnnotatedClass(Participation.class)
//                .addAnnotatedClass(Instrument.class)
//                .addAnnotatedClass(Personality.class)
//                .buildSessionFactory();
//    }
}