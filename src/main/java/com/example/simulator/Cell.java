package com.example.simulator;

// Model class for a grid cell
public class Cell {
    private String type;  // e.g., "Animal", "Empty", "Infected"
    private String symbol; // Optional symbol to display in cell
    private int x;
    private int y;

    // Constructors, getters, and setters
    public Cell(String type, String symbol, int x, int y) {
        this.type = type;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    public void setType(String type) {
        this.type = type;
    }
}

