package com.exist.model;
import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<Cell> cells;

    public Row() {
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        if (cells.stream().anyMatch(c -> c.getKey().equals(cell.getKey()))) {
            throw new IllegalArgumentException("Key '" + cell.getKey() + "' already exists in this row");
        }
        cells.add(cell);
    }

    public void setCell(int index, Cell cell) {
        if (index < 0 || index > cells.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + cells.size());
        }

        if (cells.stream().anyMatch(c -> c.getKey().equals(cell.getKey()))) {
            throw new IllegalArgumentException("Key '" + cell.getKey() + "' already exists in this row");
        }

        if (index == cells.size()) {
            cells.add(cell);
        } else {
            cells.set(index, cell);
        }
    }

    public List<Cell> getRow() {
        return cells;
    }
    
    public Cell getCell(int index) {
        if (index >= 0 && index < cells.size()) {
            return cells.get(index);
        }
        return null;
    }
    
    public int getCellCount() {
        return cells.size();
    }
}