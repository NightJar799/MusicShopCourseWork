package org.example.Entity;

public class QueryColumnConfig {
    private String displayName;  // What to show in table header
    private double width;       // Column width
    private int order;          // Display order (lower numbers come first)

    public QueryColumnConfig(String displayName, double width, int order) {
        this.displayName = displayName;
        this.width = width;
        this.order = order;
    }

    // Getters
    public String getDisplayName() { return displayName; }
    public double getWidth() { return width; }
    public int getOrder() { return order; }
}
