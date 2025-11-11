package com.exist.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Row {
    private List<Cell> cells;

    public Row() {
        this.cells = new ArrayList<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
    
    public void addCell(Cell cell) {
        this.cells.add(cell);
    }
    
    public Optional<Cell> getCell(int index) {
        if (index >= 0 && index < cells.size()) {
            return Optional.of(cells.get(index));
        }
        return Optional.empty();
    }
    
    public int getCellCount() {
        return cells.size();
    }
}