package org.example.Design;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;

import org.example.Hibernate.DbRequests;

import java.util.HashMap;
import java.util.Map;

public class MainField extends VBox{
    private TextArea resultArea;
    private GridPane inputFields;

    public MainField() {
        inputFields = new GridPane();

        inputFields.setHgap(10);
        inputFields.setVgap(10);

        resultArea = new TextArea();

        resultArea.setEditable(false);

        getChildren().addAll(inputFields, new Label("Results:"), resultArea);

    }

    public void loadFieldsForQuery(Integer numberOfQuery){
        inputFields.getChildren().clear();
        switch (numberOfQuery) {
            case 0:
                addInputField("parameter: 1","param1");
                addInputField("parameter 2","param2");
                break;
        }
        Button executeBtn = new Button("Execute");
        executeBtn.setOnAction(e -> executeQuery(numberOfQuery));

        inputFields.add(executeBtn, 0, inputFields.getChildren().size() / 2);
    }

    private void addInputField(String labelOfField, String fieldId){
        int row = inputFields.getChildren().size() / 2;

        Label label = new Label(labelOfField);

        TextField textField = new TextField();
        textField.setId(fieldId);
        inputFields.add(label, 0, row);
        inputFields.add(textField, 1, row);
    }

    private void executeQuery(int queryNumber) {
        // Здесь логика выполнения запроса через Hibernate
        String result = DbRequests.executeQuery(queryNumber);
        resultArea.setText(result);
    }

    private Map<String, String> collectInputData() {
        Map<String, String> inputs = new HashMap<>();

        for (Node node : inputFields.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                inputs.put(textField.getId(), textField.getText());
            }
        }
        return inputs;
    }
}
