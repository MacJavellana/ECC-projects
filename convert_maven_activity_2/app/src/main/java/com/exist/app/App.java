package com.exist.app;

import com.exist.model.*;
import com.exist.service.TableService;
import com.exist.utilities.FileUtility;
import com.exist.utilities.InputUtility;

public class App {
    private static Table table;
    private static String fileName;
    private static boolean debug = true;

    public static void main(String[] args) {
        initializeTable(args);
        runApplication();
    }

    private static void initializeTable(String[] args) {
        // Use default data.txt if no filename provided
        fileName = (args.length > 0 && !args[0].trim().isEmpty()) ? args[0] : "data.txt";
        
        // Try to read table from file
        table = FileUtility.readTableFromFile(fileName);
        
        // If file reading failed or file was empty, generate new table
        if (table == null) {
            System.out.println("Creating new table...");
            UserInputDimensions dimensions = InputUtility.getValidTableDimensions();
            table = TableService.generateTable(dimensions.getRow(), dimensions.getCol());
            FileUtility.writeTableToFile(fileName, table);
        }
        
        displayTable();
    }

    private static void runApplication() {
        boolean exit = false;
        
        while (!exit) {
            printMenu();
            String userInput = InputUtility.getStringInput("Choose an option: ");
            
            switch (userInput) {
                case "1":
                    handleSearch();
                    break;
                case "2":
                    handleEdit();
                    break;
                case "3":
                    handleAddRow();
                    break;
                case "4":
                    handleSort();
                    break;
                case "5":
                    displayTable();
                    break;
                case "6":
                    handleReset();
                    break;
                case "7":
                    exit = true;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-7.");
                    break;
            }
        }
        

    }

    private static void handleSearch() {
        String searchTarget = InputUtility.getValidSearchTarget();
        SearchTarget target = SearchTarget.valueOf(searchTarget);
        
        switch (target) {
            case KEY:
                String key = InputUtility.getValidSearchInput("Key to search: ");
                SearchResult keyResult = TableService.searchByKey(table, key);
                System.out.println(keyResult.getOccurrences() + " occurrence(s) at " + keyResult.getLocations());
                break;
                
            case VALUE:
                String value = InputUtility.getValidSearchInput("Value to search: ");
                SearchResult valueResult = TableService.searchByValue(table, value);
                System.out.println(valueResult.getOccurrences() + " occurrence(s) at " + valueResult.getLocations());
                break;
                
            case BOTH:
                String searchKey = InputUtility.getValidSearchInput("Key to search: ");
                String searchValue = InputUtility.getValidSearchInput("Value to search: ");
                BothSearchResult bothResult = TableService.searchBoth(table, searchKey, searchValue);
                System.out.println("Key: " + bothResult.getKeyResults().getOccurrences() + " occurrence(s) at " + bothResult.getKeyResults().getLocations());
                System.out.println("Value: " + bothResult.getValueResults().getOccurrences() + " occurrence(s) at " + bothResult.getValueResults().getLocations());
                break;
        }
    }

    private static void handleEdit() {
        int maxRows = TableService.getRowCount(table);
        if (maxRows == 0) {
            System.out.println("Table is empty. Nothing to edit.");
            return;
        }
        
        int maxCols = TableService.getColumnCount(table, 0); // Assuming all rows have same columns
        int[] position = InputUtility.getValidCellPosition(maxRows, maxCols);
        int row = position[0];
        int col = position[1];
        
        String editTarget = InputUtility.getValidEditTarget();
        SearchTarget target = SearchTarget.valueOf(editTarget);
        
        boolean success = false;
        switch (target) {
            case KEY:
                String newKey = InputUtility.getValidThreeCharInput("New key: ");
                success = TableService.editKey(table, row, col, newKey);
                break;
                
            case VALUE:
                String newValue = InputUtility.getValidThreeCharInput("New value: ");
                success = TableService.editValue(table, row, col, newValue);
                break;
                
            case BOTH:
                String[] keyValue = InputUtility.getValidKeyValueInput();
                success = TableService.editBoth(table, row, col, keyValue[0], keyValue[1]);
                break;
        }
        
        if (success) {
            FileUtility.writeTableToFile(fileName, table);
            displayTable();
        } else {
            System.out.println("Edit failed. Please try again.");
        }
    }

    private static void handleAddRow() {
        int numCells = InputUtility.getValidNumberInput(NumberType.VALID_NUM, 0, "How many cells to add: ");
        TableService.addRow(table, numCells);
        FileUtility.writeTableToFile(fileName, table);
        displayTable();
    }

    private static void handleSort() {
        int maxRows = TableService.getRowCount(table);
        if (maxRows == 0) {
            System.out.println("Table is empty. Nothing to sort.");
            return;
        }
        
        String prompt = "Which Row to sort" + " (0-" +(maxRows-1) +")";
        int rowToSort = InputUtility.getValidNumberInput(NumberType.VALID_ROW_NUM, maxRows, prompt);
        SortOrder order = InputUtility.getValidSortOrder();
        
        boolean success = TableService.sortRow(table, rowToSort, order);
        if (success) {
            FileUtility.writeTableToFile(fileName, table);
            displayTable();
        } else {
            System.out.println("Sort failed due to duplicate keys.");
        }
    }

    private static void handleReset() {
        UserInputDimensions dimensions = InputUtility.getValidTableDimensions();
        table = TableService.resetTable(dimensions.getRow(), dimensions.getCol());
        FileUtility.writeTableToFile(fileName, table);
        displayTable();
    }

    private static void displayTable() {
        System.out.println("\nCurrent Table:");
        TableService.displayTable(table);
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("\n=== Table Manager ===");
        System.out.println("1. Search");
        System.out.println("2. Edit");
        System.out.println("3. Add Row");
        System.out.println("4. Sort");
        System.out.println("5. Print Table");
        System.out.println("6. Reset Table");
        System.out.println("7. Exit");
        System.out.println("====================");
    }
}