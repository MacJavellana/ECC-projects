package com.exist.service;

import com.exist.model.Table;
import com.exist.model.Row;
import com.exist.model.SearchResult;
import com.exist.model.SortOrder;
import com.exist.model.BothSearchResult;
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

                    
                    if (row.getCells().stream().anyMatch(c -> c.getKey().equals(finalKey))) {
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
            List<Cell> cells = row.getCells();
            
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
                if (newRow.getCells().stream().anyMatch(c -> c.getKey().equals(finalKey))) {
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
        if (colIndex < 0 || colIndex >= row.getCells().size()) {
            return null;
        }
        
        return row.getCells().get(colIndex);
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
        return table.getTable().get(rowIndex).getCells().size();
    }



    public static SearchResult searchByKey(Table table, String key) {
        int instances = 0;
        StringBuilder locations = new StringBuilder();
        int searchLength = key.length();

        for (int i = 0; i < table.getTable().size(); i++) {
            Row row = table.getTable().get(i);
            List<Cell> cells = row.getCells();
            
            for (int j = 0; j < cells.size(); j++) {
                Cell cell = cells.get(j);
                boolean occurred = false;
                
                for (int k = 0; k < 3; k++) {
                    if (searchLength + k > 3) break;
                    if (cell.getKey().substring(k, searchLength + k).equals(key)) {
                        instances++;
                        if (!occurred) {
                            locations.append("[").append(i).append(",").append(j).append("]");
                            occurred = true;
                        }
                    }
                }
            }
        }

        // Create SearchResult object with constructor or setters
        SearchResult result = new SearchResult();
        result.setOccurrences(instances);
        result.setLocations(locations.toString());
        return result;
    }
    public static SearchResult searchByValue(Table table, String value) {
        int instances = 0;
        StringBuilder locations = new StringBuilder();
        int searchLength = value.length();

        for (int i = 0; i < table.getTable().size(); i++) {
            Row row = table.getTable().get(i);
            List<Cell> cells = row.getCells();
            
            for (int j = 0; j < cells.size(); j++) {
                Cell cell = cells.get(j);
                boolean occurred = false;
                
                for (int k = 0; k < 3; k++) {
                    if (searchLength + k > 3) break;
                    if (cell.getValue().substring(k, searchLength + k).equals(value)) {
                        instances++;
                        if (!occurred) {
                            locations.append("[").append(i).append(",").append(j).append("]");
                            occurred = true;
                        }
                    }
                }
            }
        }

        // Create SearchResult object with constructor or setters
        SearchResult result = new SearchResult();
        result.setOccurrences(instances);
        result.setLocations(locations.toString());
        return result;
    }
    public static BothSearchResult searchBoth(Table table, String key, String value) {
        SearchResult keyResult = searchByKey(table, key);
        SearchResult valueResult = searchByValue(table, value);
        return new BothSearchResult(keyResult, valueResult);
    }


    
    public static boolean editKey(Table table, int rowIndex, int colIndex, String newKey) {

        if (!isValidCellPosition(table, rowIndex, colIndex)) {
            System.out.println("invalid cell position");
            return false;
        }

        if (newKey.length() != 3) {
            System.out.println("Key must be exactly 3 characters");
            return false;
        }

        Row row = table.getTable().get(rowIndex);

        if (row.getCells().stream().anyMatch(cell -> cell.getKey().equals(newKey))) {
            System.out.println("Key already exists in this row");
            return false;
        }

        Cell oldCell = row.getCells().get(colIndex);
        Cell newCell = new Cell();
        newCell.setKey(newKey);
        newCell.setValue(oldCell.getValue());
        
        row.getCells().set(colIndex, newCell);
        return true;
    }
    public static boolean editValue(Table table, int rowIndex, int colIndex, String newValue) {

        if (!isValidCellPosition(table, rowIndex, colIndex)) {
            System.out.println("invalid cell position");
            return false;
        }

        if (newValue.length() != 3) {
            System.out.println("Value must be exactly 3 characters");
            return false;
        }

        Row row = table.getTable().get(rowIndex);
        row.getCells().get(colIndex).setValue(newValue);
        return true;
    }
    public static boolean editBoth(Table table, int rowIndex, int colIndex, String newKey, String newValue) {

        if (!isValidCellPosition(table, rowIndex, colIndex)) {
            System.out.println("invalid cell position");
            return false;
        }

        if (newKey.length() != 3 || newValue.length() != 3) {
            System.out.println("Both key and value must be exactly 3 characters");
            return false;
        }

        Row row = table.getTable().get(rowIndex);
        
        if (row.getCells().stream().anyMatch(cell -> cell.getKey().equals(newKey))) {
            System.out.println("Key already exists in this row");
            return false;
        }

        Cell newCell = new Cell();
        newCell.setKey(newKey);
        newCell.setValue(newValue);
        
        row.getCells().set(colIndex, newCell);
        return true;
    }
    
    
    public static boolean sortRow(Table table, int rowIndex, SortOrder order) {
        if (!isValidRowIndex(table, rowIndex)) {
            return false;
        }

        Row row = table.getTable().get(rowIndex);
        List<Cell> cells = new ArrayList<>(row.getCells());


        if (order == SortOrder.ASC) {
            cells.sort(Comparator.comparing(cell -> cell.getKey() + cell.getValue()));
        } else {
            cells.sort((c1, c2) -> (c2.getKey() + c2.getValue()).compareTo(c1.getKey() + c1.getValue()));
        }

        Set<String> keys = new HashSet<>();
        for (Cell cell : cells) {
            if (!keys.add(cell.getKey())) {
                System.out.println("Sort failed: duplicate key detected");
                return false;
            }
        }

        Row sortedRow = new Row();
        for (Cell cell : cells) {
            sortedRow.addCell(cell);
        }

        table.getTable().set(rowIndex, sortedRow);
        return true;
    }


    public static Table resetTable(int rows, int cols) {
        return generateTable(rows, cols);
    }


    private static boolean isValidRowIndex(Table table, int rowIndex) {
        return rowIndex >= 0 && rowIndex < table.getTable().size();
    }
    private static boolean isValidCellPosition(Table table, int rowIndex, int colIndex) {
        return isValidRowIndex(table, rowIndex) && 
               colIndex >= 0 && colIndex < table.getTable().get(rowIndex).getCells().size();
    }
}