package org.example.Design;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SideField extends VBox {
    private MainField listOfQueries;

    public SideField(String ... labelsOfQuery) {
        listOfQueries = new MainField();

        int i = 0;
        for (String labelOfQuery : labelsOfQuery){
            addQueryBtn(labelOfQuery, i);
            i++;
        }
    }

    private void addQueryBtn(String labelOfBtn, int numOfQuery){
        int row = listOfQueries.getChildren().size() / 2;

        Button btn = new Button(labelOfBtn);

        btn.setMaxWidth(200.);

        btn.setOnAction(e -> listOfQueries.loadFieldsForQuery(numOfQuery));

        getChildren().add(btn);
    }

    public void setListOfQueries(MainField listOfQueries) {
        this.listOfQueries = listOfQueries;
    }
}
