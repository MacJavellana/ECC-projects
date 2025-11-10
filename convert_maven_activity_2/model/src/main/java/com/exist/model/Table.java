package com.exist.model;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Row> table;

    public Table() {
        this.table = new ArrayList<>();
    }
    
    public List<Row> getTable() {
        return table;
    }

    public void setTable(List<Row> table) {
        this.table = table;
    }
    
    public void addRow(Row row) {
        this.table.add(row);
    }
    
    public Row getRow(int index) {
        if (index >= 0 && index < table.size()) {
            return table.get(index);
        }
        return null;
    }
    
    public int getRowCount() {
        return table.size();
    }
}