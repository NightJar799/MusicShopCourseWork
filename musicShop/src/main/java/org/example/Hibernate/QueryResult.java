package org.example.Hibernate;

import java.util.List;
import java.util.Map;

public class QueryResult {
    private boolean selectQuery;
    private List<Map<String, Object>> results;
    private List<Map<String, Object>> beforeState;
    private List<Map<String, Object>> afterState;
    private String errorMessage;

    public QueryResult(boolean isSelectQuery, List<Map<String, Object>> results) {
        this.selectQuery = isSelectQuery;
        this.results = results;
    }

    public QueryResult(boolean isSelectQuery, List<Map<String, Object>> beforeState, List<Map<String, Object>> afterState) {
        this.selectQuery = isSelectQuery;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    public QueryResult(String errorMessage) {
        this.selectQuery = false;
        this.errorMessage = errorMessage;
    }

    // Getters
    public boolean isSelectQuery() { return selectQuery; }
    public List<Map<String, Object>> getResults() { return results; }
    public List<Map<String, Object>> getBeforeState() { return beforeState; }
    public List<Map<String, Object>> getAfterState() { return afterState; }
    public String getErrorMessage() { return errorMessage; }
}