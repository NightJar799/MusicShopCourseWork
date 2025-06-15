package org.example.Design;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.Entity.QueryColumnConfig;
import org.example.Hibernate.DbRequests;

import java.util.*;

import org.example.Hibernate.HibernateUtil;
import org.example.Hibernate.QueryResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MainField extends VBox {
    private final TableView<Map<String, Object>> resultTable;
    private final GridPane inputFields;
    private final Label resultLabel;
    private final TableView<Map<String, Object>> resultTableForNonSel;
    private final Label resultLabelForNonSel;
    private final Map<Integer, Map<String, QueryColumnConfig>> queryConfigs;
    private final Label nameOfQuery;

    public MainField() {
        inputFields = new GridPane();
        inputFields.setHgap(10);
        inputFields.setVgap(10);

        resultTable = new TableView<>();
        resultTable.setMaxWidth(1400.);
        resultTableForNonSel = new TableView<>();
        resultTableForNonSel.setMaxWidth(1400.);
        resultTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        resultTableForNonSel.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        nameOfQuery = new Label("Выберете запрос");
        resultLabel = new Label("Results:");
        resultLabelForNonSel = new Label("After Change:");

        getChildren().addAll(nameOfQuery, inputFields, resultLabel, resultTable, resultLabelForNonSel, resultTableForNonSel);

        resultLabelForNonSel.setVisible(false);
        resultTableForNonSel.setVisible(false);

        queryConfigs = new HashMap<>();
        setUpQueryConf();
    }

    private void setUpQueryConf() {
        resultTable.setMaxWidth(1400.);
        Map<String, QueryColumnConfig> queryConfigOfSelLabels = new HashMap<>();
        queryConfigOfSelLabels.put("id", new QueryColumnConfig("ID", 100., 1));
        queryConfigOfSelLabels.put("shortname", new QueryColumnConfig("Shortname", 150., 5));
        queryConfigOfSelLabels.put("legal name", new QueryColumnConfig("Legal name", 450., 4));
        queryConfigOfSelLabels.put("legal Address", new QueryColumnConfig("Address", 450., 3));
        queryConfigOfSelLabels.put("year of Funding", new QueryColumnConfig("Year of fund", 105., 6));
        queryConfigOfSelLabels.put("country", new QueryColumnConfig("Country", 150., 2));
        queryConfigs.put(0, queryConfigOfSelLabels);
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
                addInputField("Сокращённое имя лейбла", "labelShortname");
                addInputField("Прозвище фронтмена", "personalityNickname");
                break;
            case (8):
                nameOfQuery.setText("Получить среднюю стоимость альбома определённого жанра с определённым инструментом");
                addInputField("Жанр", "genre");
                addInputField("Название инструмента", "instrumentName");
                break;
            case (9):
                nameOfQuery.setText("Получить все композиции с определённым инструментом из определённого лейбла");
                addInputField("Название инструмента", "instrumentNames");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (10):
                nameOfQuery.setText("Получить альбомы по группе и лейблу");
                addInputField("Название группы", "groupName");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (11):
                nameOfQuery.setText("Обновить лейбл альбома");
                addInputField("EAN альбома", "ean");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (12):
                nameOfQuery.setText("Обновить участника группы");
                addInputField("Прозвище", "nickname");
                addInputField("Id группы", "groupId");
                addInputField("Id инструмента", "instrumentId");
                break;
            case (13):
                nameOfQuery.setText("Удалить альбомы определённой личности");
                addInputField("Прозвище", "nickname");
                break;
            case (14):
                nameOfQuery.setText("Удалить альбомы лейбла");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
//            case (15):
//                return insertNewAlbumWithCompositions(HibernateUtil.getSessionFactory(),inputs);
//            case (16):
//                return insertNewPersonalityAndParticipation(HibernateUtil.getSessionFactory(),inputs);
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
            case (0), (2):
                lenghtOfColumn = 280.;
                break;
            case (1), (3), (9), (11), (13), (14):
                lenghtOfColumn = 200.;
                break;
            case (4), (10):
                lenghtOfColumn = 350.;
                break;
            case (5):
                lenghtOfColumn = 155.9;
                break;
            case (6), (12):
                lenghtOfColumn = 466.9;
                break;
            case (7), (8):
                lenghtOfColumn = 1400.;
                break;
            //            case (15):
//                return insertNewAlbumWithCompositions(HibernateUtil.getSessionFactory(),inputs);
//            case (16):
//                return insertNewPersonalityAndParticipation(HibernateUtil.getSessionFactory(),inputs);
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

    private void executeQuery(int queryNumber) {
        Map<String, String> inputs = collectInputData();
        QueryResult queryResult = DbRequests.executeQuery(queryNumber,inputs);

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
            // Handle UPDATE/INSERT/DELETE queries - show before/after states
            resultLabel.setText("До изменений:");
            resultLabelForNonSel.setText("После изменений:");

            // Configure before state table
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

            // Configure after state table
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

    // Helper class for cell value factory
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
