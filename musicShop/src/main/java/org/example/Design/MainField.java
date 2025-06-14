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
    private TableView<Map<String, Object>> resultTable;
    private GridPane inputFields;
    private Label resultLabel;
    private final Map<Integer, Map<String, QueryColumnConfig>> queryConfigs;

    public MainField() {
        inputFields = new GridPane();
        inputFields.setHgap(10);
        inputFields.setVgap(10);

        resultTable = new TableView<>();
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        resultLabel = new Label("Results:");

        getChildren().addAll(inputFields, resultLabel, resultTable);

        queryConfigs = new HashMap<>();
        setUpQueryConf();
    }

    private void setUpQueryConf() {
        Map<String, QueryColumnConfig> queryConfigOfSelLabels = new HashMap<>();
        queryConfigOfSelLabels.put("id", new QueryColumnConfig("ID", 30, 1));
        queryConfigOfSelLabels.put("shortname", new QueryColumnConfig("Shortname", 100, 5));
        queryConfigOfSelLabels.put("legal name", new QueryColumnConfig("Legal name", 250, 4));
        queryConfigOfSelLabels.put("legal Address", new QueryColumnConfig("Address", 350, 3));
        queryConfigOfSelLabels.put("year of Funding", new QueryColumnConfig("Year of fund", 50, 6));
        queryConfigOfSelLabels.put("country", new QueryColumnConfig("Country", 50, 2));
        queryConfigs.put(0, queryConfigOfSelLabels);
    }

    public void loadFieldsForQuery(Integer numberOfQuery) {
        inputFields.getChildren().clear();
        switch (numberOfQuery) {
            case (0):
                break;
            case (1):
                break;
            case (2):
                break;
            case (3):
                break;
            case (4):
                break;
            case (5):
                break;
            case (6):
                addInputField("Сокращённое имя лейбла", "labelShortname");
                addInputField("Прозвище фронтмена", "personalityNickname");
                break;
            case (7):
                addInputField("Жанр", "genre");
                addInputField("Название инструмента", "instrumentName");
                break;
            case (8):
                addInputField("Название инструмента", "instrumentNames");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (9):
                addInputField("Название группы", "groupName");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (10):
                addInputField("EAN альбома", "ean");
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
            case (11):
                addInputField("Прозвище", "nickname");
                addInputField("Id группы", "groupId");
                addInputField("Id инструмента", "instrumentId");
                break;
            case (12):
                addInputField("Прозвище", "nickname");
                break;
            case (13):
                addInputField("Сокращённое имя лейбла", "labelShortName");
                break;
//            case (13):
//                return insertNewAlbumWithCompositions(HibernateUtil.getSessionFactory(),inputs);
//            case (14):
//                return insertNewPersonalityAndParticipation(HibernateUtil.getSessionFactory(),inputs);
            default:
                break;
        }

        Button executeBtn = new Button("Execute");
        executeBtn.setOnAction(e -> executeQuery(numberOfQuery));
        inputFields.add(executeBtn, 0, inputFields.getChildren().size() / 2);
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

        if (queryResult.isSelectQuery()) {
            // Handle SELECT queries
            resultLabel.setText("Query Results:");

            // Create columns dynamically based on the first row's keys
            if (!queryResult.getResults().isEmpty()) {
                Map<String, Object> firstRow = queryResult.getResults().get(0);
                Map<String, QueryColumnConfig> config = queryConfigs.get(queryNumber);

                if (config != null) {
                    config.entrySet().stream()
                            .sorted(Comparator.comparingInt(e -> e.getValue().getOrder()))
                            .forEach(entry -> {
                                String dbColumnName = entry.getKey();
                                QueryColumnConfig columnConfig = entry.getValue();

                                if (firstRow.containsKey(dbColumnName)) {
                                    TableColumn<Map<String, Object>, Object> column =
                                            new TableColumn<>(columnConfig.getDisplayName());
                                    column.setCellValueFactory(new MapValueFactory(dbColumnName));
                                    column.setPrefWidth(columnConfig.getWidth());
                                    resultTable.getColumns().add(column);

                                }
                            });
                    firstRow.keySet().stream()
                            .filter(dbColumnName -> !config.containsKey(dbColumnName))
                            .forEach(dbColumnName -> {
                                TableColumn<Map<String, Object>, Object> column =
                                        new TableColumn<>(dbColumnName);  // Use original name
                                column.setCellValueFactory(new MapValueFactory(dbColumnName));
                                column.setPrefWidth(50);  // Default width
                                resultTable.getColumns().add(column);
                            });
                }else {
                    for (String dbColumnName : firstRow.keySet()) {
                        TableColumn<Map<String, Object>, Object> column =
                                new TableColumn<>(dbColumnName);
                        column.setCellValueFactory(new MapValueFactory(dbColumnName));
                        column.setPrefWidth(150);
                        resultTable.getColumns().add(column);
                    }
                    }

//                for (String columnName : firstRow.keySet()) {
//                    TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
//                    column.setCellValueFactory(new MapValueFactory(columnName));
//                    resultTable.getColumns().add(column);
//                }
            }

            // Convert results to observable list and set to table
            ObservableList<Map<String, Object>> items = FXCollections.observableArrayList(queryResult.getResults());
            resultTable.setItems(items);
        } else {
            // Handle UPDATE/INSERT/DELETE queries - show before/after states
            resultLabel.setText("Database Changes:");

            // Create columns for before/after comparison
            if (!queryResult.getBeforeState().isEmpty()) {
                Map<String, Object> firstBeforeRow = queryResult.getBeforeState().get(0);
                Map<String, QueryColumnConfig> config = queryConfigs.get(queryNumber);
                // Add before state columns
                TableColumn<Map<String, Object>, Object> beforeCol = new TableColumn<>("Before");
                resultTable.getColumns().add(beforeCol);

                if (config != null) {
                    // Create before columns according to configuration
                    config.entrySet().stream()
                            .sorted(Comparator.comparingInt(e -> e.getValue().getOrder()))
                            .forEach(entry -> {
                                String dbColumnName = entry.getKey();
                                if (firstBeforeRow.containsKey(dbColumnName)) {
                                    QueryColumnConfig columnConfig = entry.getValue();
                                    TableColumn<Map<String, Object>, Object> column =
                                            new TableColumn<>(columnConfig.getDisplayName());
                                    column.setCellValueFactory(new MapValueFactory("before_" + dbColumnName));
                                    column.setPrefWidth(columnConfig.getWidth());
                                    beforeCol.getColumns().add(column);
                                }
                            });

                    // Add remaining columns
                    firstBeforeRow.keySet().stream()
                            .filter(dbColumnName -> !config.containsKey(dbColumnName))
                            .forEach(dbColumnName -> {
                                TableColumn<Map<String, Object>, Object> column =
                                        new TableColumn<>(dbColumnName);
                                column.setCellValueFactory(new MapValueFactory("before_" + dbColumnName));
                                column.setPrefWidth(150);
                                beforeCol.getColumns().add(column);
                            });
                } else {
                    // No configuration - default behavior
                    for (String dbColumnName : firstBeforeRow.keySet()) {
                        TableColumn<Map<String, Object>, Object> column =
                                new TableColumn<>(dbColumnName);
                        column.setCellValueFactory(new MapValueFactory("before_" + dbColumnName));
                        column.setPrefWidth(150);
                        beforeCol.getColumns().add(column);
                    }
                }

                if (!queryResult.getAfterState().isEmpty()) {
                    TableColumn<Map<String, Object>, Object> afterCol = new TableColumn<>("After");
                    resultTable.getColumns().add(afterCol);

                    if (config != null) {
                        // Create before columns according to configuration
                        config.entrySet().stream()
                                .sorted(Comparator.comparingInt(e -> e.getValue().getOrder()))
                                .forEach(entry -> {
                                    String dbColumnName = entry.getKey();
                                    if (firstBeforeRow.containsKey(dbColumnName)) {
                                        QueryColumnConfig columnConfig = entry.getValue();
                                        TableColumn<Map<String, Object>, Object> column =
                                                new TableColumn<>(columnConfig.getDisplayName());
                                        column.setCellValueFactory(new MapValueFactory("after_" + dbColumnName));
                                        column.setPrefWidth(columnConfig.getWidth());
                                        beforeCol.getColumns().add(column);
                                    }
                                });

                        // Add remaining columns
                        firstBeforeRow.keySet().stream()
                                .filter(dbColumnName -> !config.containsKey(dbColumnName))
                                .forEach(dbColumnName -> {
                                    TableColumn<Map<String, Object>, Object> column =
                                            new TableColumn<>(dbColumnName);
                                    column.setCellValueFactory(new MapValueFactory("after_" + dbColumnName));
                                    column.setPrefWidth(150);
                                    beforeCol.getColumns().add(column);
                                });
                    } else {
                        // No configuration - default behavior
                        for (String dbColumnName : firstBeforeRow.keySet()) {
                            TableColumn<Map<String, Object>, Object> column =
                                    new TableColumn<>(dbColumnName);
                            column.setCellValueFactory(new MapValueFactory("after_" + dbColumnName));
                            column.setPrefWidth(150);
                            beforeCol.getColumns().add(column);
                        }
                    }

                    // ... repeat the same logic for after columns ...
                }

                // Add after state columns

                // Combine before and after states for display
                ObservableList<Map<String, Object>> combinedItems = FXCollections.observableArrayList();
                resultTable.setItems(combinedItems);
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
