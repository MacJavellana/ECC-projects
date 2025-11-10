package com.exist.service;

import com.exist.model.Table;
import com.exist.model.Row;
import com.exist.model.Cell;
import com.exist.utilities.RandomUtility;
import java.util.*;

public class TableService {
    private static boolean debug = true;

    public static Table generateTable(int rows, int cols) {
        Table table = new Table();
        
        for (int i = 0; i < rows; i++) {
            Row row = new Row();
            
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell();
                String key = RandomUtility.randChars();
                

                boolean keyExists;
                do {
                    keyExists = false;
                    String finalKey = key;

                    
                    if (row.getRow().stream().anyMatch(c -> c.getKey().equals(finalKey))) {
                        keyExists = true;
                        key = RandomUtility.randChars();
                        if (debug) System.out.println("Duplicate key detected, generating new key: " + key);
                    }
                } while (keyExists);
                
                cell.setKey(key);
                cell.setValue(RandomUtility.randChars());
                row.addCell(cell);
            }
            
            table.getTable().add(row);
        }
        return table;
    }

    public static void displayTable(Table table) {
        List<Row> rows = table.getTable();
        
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            List<Cell> cells = row.getRow();
            
            for (Cell cell : cells) {
                System.out.print(cell.getKey() + " " + cell.getValue() + "    ");
            }
            System.out.println();
        }
    }


    public static void addRow(Table table, int numOfCells) {
        Row newRow = new Row();
        
        for (int j = 0; j < numOfCells; j++) {
            Cell cell = new Cell();
            String key = RandomUtility.randChars();
            

            boolean keyExists;
            do {
                keyExists = false;
                String finalKey = key;
                if (newRow.getRow().stream().anyMatch(c -> c.getKey().equals(finalKey))) {
                    keyExists = true;
                    key = RandomUtility.randChars();
                    if (debug) System.out.println("Duplicate key detected, generating new key: " + key);
                }
            } while (keyExists);
            
            cell.setKey(key);
            cell.setValue(RandomUtility.randChars());
            newRow.addCell(cell);
        }
        
        table.getTable().add(newRow);
    }

    public static Cell getCellAt(Table table, int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= table.getTable().size()) {
            return null;
        }
        
        Row row = table.getTable().get(rowIndex);
        if (colIndex < 0 || colIndex >= row.getRow().size()) {
            return null;
        }
        
        return row.getRow().get(colIndex);
    }

    public static void updateCell(Table table, int rowIndex, int colIndex, String newKey, String newValue) {
        Cell cell = getCellAt(table, rowIndex, colIndex);
        if (cell != null) {
            cell.setKey(newKey);
            cell.setValue(newValue);
        }
    }

    public static int getRowCount(Table table) {
        return table.getTable().size();
    }

    public static int getColumnCount(Table table, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= table.getTable().size()) {
            return 0;
        }
        return table.getTable().get(rowIndex).getRow().size();
    }
}