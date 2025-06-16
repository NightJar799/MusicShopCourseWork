package org.example.Design;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.example.Hibernate.DbRequests;
import org.example.Hibernate.QueryResult;
import org.example.Hibernate.Constants;

import java.util.Map;
import java.util.HashMap;

public class MainField extends VBox {
    private final TableView<Map<String, Object>> resultTable;
    private final GridPane inputFields;
    private final Label resultLabel;
    private final TableView<Map<String, Object>> resultTableForNonSel;
    private final Label resultLabelForNonSel;
    private final Label nameOfQuery;
    private final Text errorText = new Text();

    public MainField() {
        inputFields = new GridPane();
        inputFields.setHgap(10);
        inputFields.setVgap(10);

        errorText.setFill(Color.RED);
        errorText.setVisible(false);

        resultTable = new TableView<>();
        resultTable.setMaxWidth(1400.);
        resultTableForNonSel = new TableView<>();
        resultTableForNonSel.setMaxWidth(1400.);
        resultTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        resultTableForNonSel.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        nameOfQuery = new Label("Выберете запрос");
        resultLabel = new Label("Results:");
        resultLabelForNonSel = new Label("After Change:");

        getChildren().addAll(nameOfQuery, errorText, inputFields, resultLabel, resultTable, resultLabelForNonSel, resultTableForNonSel);

        resultLabelForNonSel.setVisible(false);
        resultTableForNonSel.setVisible(false);
    }

    public void loadFieldsForQuery(Integer numberOfQuery) {
        inputFields.getChildren().clear();
        switch (numberOfQuery) {
            case (0):
                nameOfQuery.setText("Вывести все лейблы");
                break;
            case (1):
                nameOfQuery.setText("Вывести все альбомы");
                break;
            case (2):
                nameOfQuery.setText("Вывести все группы");
                break;
            case (3):
                nameOfQuery.setText("Вывести все композиции");
                break;
            case (4):
                nameOfQuery.setText("Вывести все инструменты");
                break;
            case (5):
                nameOfQuery.setText("Вывести все персоны");
                break;
            case (6):
                nameOfQuery.setText("Вывести таблицу участников");
                break;
            case (7):
                nameOfQuery.setText("Получить минимальную стоимость альбома с определённого лейбла с определённым фронтменом");
                addInputField("Сокращённое имя лейбла", Constants.LABELSHORTNAME);
                addInputField("Прозвище фронтмена", Constants.NICKNAME);
                break;
            case (8):
                nameOfQuery.setText("Получить среднюю стоимость альбома определённого жанра с определённым инструментом");
                addInputField("Жанр", Constants.GENRE);
                addInputField("Название инструмента", Constants.INSTRUMENTNAME);
                break;
            case (9):
                nameOfQuery.setText("Получить альбомы по группе и лейблу");
                addInputField("Название группы", Constants.GROUPNAME);
                addInputField("Сокращённое имя лейбла", Constants.LABELSHORTNAME);
                break;
            case (10):
                nameOfQuery.setText("Обновить лейбл альбома");
                addInputField("EAN альбома", Constants.EAN);
                addInputField("Сокращённое имя лейбла", Constants.LABELSHORTNAME);
                break;
            case (11):
                nameOfQuery.setText("Обновить участника группы");
                addInputField("Прозвище", Constants.NICKNAME);
                addInputField("Id группы", Constants.GROUPNAME);
                addInputField("Id инструмента", Constants.INSTRUMENTNAME);
                break;
            case (12):
                nameOfQuery.setText("Удалить альбомы определённой личности");
                addInputField("Прозвище", Constants.NICKNAME);
                break;
            case (13):
                nameOfQuery.setText("Удалить альбомы лейбла");
                addInputField("Сокращённое имя лейбла", Constants.LABELSHORTNAME);
                break;
            default:
                nameOfQuery.setText("Ошибка");
                break;
        }

        Button executeBtn = new Button("Execute");
        executeBtn.setOnAction(e -> executeQuery(numberOfQuery));
        inputFields.add(executeBtn, 0, inputFields.getChildren().size() / 2);
    }

    public double levelerOfColumns(Integer numberOfQuery){
        double lenghtOfColumn = 0.;
        switch (numberOfQuery) {
            case (1), (3), (10), (12), (13):
                lenghtOfColumn = 200.;
                break;
            case (0):
                lenghtOfColumn = 233.9;
                break;
            case (2):
                lenghtOfColumn = 280.;
                break;
            case (4), (9):
                lenghtOfColumn = 350.;
                break;
            case (5):
                lenghtOfColumn = 155.9;
                break;
            case (6), (11):
                lenghtOfColumn = 466.9;
                break;
            case (7), (8):
                lenghtOfColumn = 1400.;
                break;
            default:
                nameOfQuery.setText("Ошибка");
                break;
        }
        return lenghtOfColumn;
    }

    private void addInputField(String labelOfField, String fieldId) {
        int row = inputFields.getChildren().size() / 2;
        Label label = new Label(labelOfField);
        TextField textField = new TextField();
        textField.setId(fieldId);
        inputFields.add(label, 0, row);
        inputFields.add(textField, 1, row);
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setVisible(true);
    }

    private void executeQuery(int queryNumber) {
        errorText.setVisible(false);

        Map<String, String> inputs = collectInputData();
        QueryResult queryResult = DbRequests.executeQuery(queryNumber,inputs);

        if (queryResult.getErrorMessage() != null) {
            showError(queryResult.getErrorMessage());
            return;
        }

        resultTable.getColumns().clear();
        resultTableForNonSel.getColumns().clear();

        if (queryResult.isSelectQuery()) {
            resultLabelForNonSel.setVisible(false);
            resultTableForNonSel.setVisible(false);
            resultLabel.setText("Результат запроса:");

            if (!queryResult.getResults().isEmpty()) {
                Map<String, Object> firstBeforeRow = queryResult.getResults().get(0);

                for (String columnName : firstBeforeRow.keySet()) {
                    TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(new MapValueFactory(columnName));
                    column.setPrefWidth(levelerOfColumns(queryNumber));
                    resultTable.getColumns().add(column);
                }

                ObservableList<Map<String, Object>> beforeItems = FXCollections.observableArrayList(queryResult.getResults());
                resultTable.setItems(beforeItems);
            }
        } else {
            resultTableForNonSel.setVisible(true);
            resultLabelForNonSel.setVisible(true);

            resultLabel.setText("До изменений:");
            resultLabelForNonSel.setText("После изменений:");

            if (!queryResult.getBeforeState().isEmpty()) {
                Map<String, Object> firstBeforeRow = queryResult.getBeforeState().get(0);

                for (String columnName : firstBeforeRow.keySet()) {
                    TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(new MapValueFactory(columnName));
                    column.setPrefWidth(levelerOfColumns(queryNumber));
                    resultTable.getColumns().add(column);
                }

                ObservableList<Map<String, Object>> beforeItems = FXCollections.observableArrayList(queryResult.getBeforeState());
                resultTable.setItems(beforeItems);
            }

            if (!queryResult.getAfterState().isEmpty()) {
                Map<String, Object> firstAfterRow = queryResult.getAfterState().get(0);

                for (String columnName : firstAfterRow.keySet()) {
                    TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(new MapValueFactory(columnName));
                    column.setPrefWidth(levelerOfColumns(queryNumber));
                    resultTableForNonSel.getColumns().add(column);
                }

                ObservableList<Map<String, Object>> afterItems = FXCollections.observableArrayList(queryResult.getAfterState());
                resultTableForNonSel.setItems(afterItems);
            }
        }
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

    public static class MapValueFactory implements javafx.util.Callback<TableColumn.CellDataFeatures<Map<String, Object>, Object>, ObservableValue<Object>> {
        private final String key;

        public MapValueFactory(String key) {
            this.key = key;
        }

        @Override
        public ObservableValue<Object> call(TableColumn.CellDataFeatures<Map<String, Object>, Object> data) {
            return new javafx.beans.property.SimpleObjectProperty<>(data.getValue().get(key));
        }
    }
}
