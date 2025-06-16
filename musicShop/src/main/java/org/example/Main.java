package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import org.example.Design.*;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        BorderPane root = new BorderPane();

        SideField sidefield = new SideField("Вывести все лейблы", "Вывести все альбомы", "Вывести все группы",
                "Вывести все композиции", "Вывести все инструменты", "Вывести все персоны", "Вывести таблицу участников",
                "Получить мин. стоимость альбома с определённого лейбла с определённым фронтменом", "Получить среднюю стоимость альбома " +
                "определённого жанра с определённым инструментом", "Получить все композиции с определённым инструмент" +
                "ом из определённого лейбла", "Получить альбомы по группе и лейблу", "Обновить лейбл альбома",
                "Обновить участника группы", "Удалить альбомы определённой личности", "Удалить альбомы лейбла");
        root.setLeft(sidefield);

        MainField mainField = new MainField();
        root.setCenter(mainField);

        sidefield.setListOfQueries(mainField);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MusicShop");
        stage.show();
        stage.setFullScreen(true);

        }
}