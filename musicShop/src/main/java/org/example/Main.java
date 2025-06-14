package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.AvailableSettings.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.example.Entity.*;
import org.example.Design.*;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        BorderPane root = new BorderPane();

        SideField sidefield = new SideField("param","param1");
        root.setLeft(sidefield);

        MainField mainField = new MainField();
        root.setCenter(mainField);

        sidefield.setListOfQueries(mainField);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("MusicShop");
        stage.setFullScreen(true);

        stage.show();
        }
}