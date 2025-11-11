package com.exist.utilities;

import com.exist.model.NumberType;
import com.exist.model.SortOrder;
import com.exist.model.UserInputDimensions;
import java.io.File;
import java.util.Scanner;

public class InputUtility {
    private static final Scanner scanner = new Scanner(System.in);
    public static boolean debug = true;


    public static int getValidNumberInput(NumberType type, int rowTable, String prompt) {
        String userInput;
        boolean isValid = false;
        int userInputInt = 0;
        
        while (!isValid) {
            System.out.print(prompt + " ");
            userInput = scanner.nextLine().trim();
            
            try {
                userInputInt = Integer.parseInt(userInput);
                
                if (type == NumberType.VALID_NUM) {
                    if (userInputInt > 0) {
                        isValid = true;
                    } else {
                        System.out.println("Please input a value greater than 0");
                    }
                } else if (type == NumberType.VALID_ROW_NUM) {
                    if (userInputInt >= 0 && userInputInt < rowTable) {
                        isValid = true;
                    } else {
                        System.out.println("Please input a row between 0 and " + (rowTable - 1));
                    }
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid integer");
            }
        }
        return userInputInt;
    }


    public static String getValidFileName(String inputFileName) {
        File file;
        
        // If filename was provided as argument, validate it
        if (inputFileName != null && !inputFileName.trim().isEmpty()) {
            try {
                file = new File(inputFileName);
                if (file.exists()) {
                    return inputFileName;
                } else {
                    System.out.println("File doesn't exist: " + inputFileName);
                }
            } catch (Exception e) {
                System.out.println("Invalid file name provided");
            }
        }
        
        // Get filename from user input
        boolean isFileValid = false;
        while (!isFileValid) {
            System.out.print("Please input a file name (don't put .txt): ");
            inputFileName = scanner.nextLine().trim() + ".txt";
            
            try {
                file = new File(inputFileName);
                if (file.exists()) {
                    isFileValid = true;
                } else {
                    System.out.println("File doesn't exist: " + inputFileName);
                }
            } catch (NullPointerException e) {
                System.out.println("Please input a valid file name");
            }
        }
        return inputFileName;
    }


    public static SortOrder getValidSortOrder() {
        String userInput;
        SortOrder order = SortOrder.ASC;
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print("Sort order (ASC or DESC): ");
            userInput = scanner.nextLine().trim().toUpperCase();
            
            if (userInput.equals(SortOrder.ASC.toString())) {
                isValid = true;
                order = SortOrder.ASC;
            } else if (userInput.equals(SortOrder.DESC.toString())) {
                isValid = true;
                order = SortOrder.DESC;
            } else {
                System.out.println("Please input either ASC or DESC");
            }
            
            if (debug) {
                System.out.println("debug: order: " + order);
                System.out.println("debug: userinput: " + userInput);
            }
        }
        return order;
    }


    public static String getValidSearchTarget() {
        String userInput="";
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print("What are we searching for (key, value, both): ");
            userInput = scanner.nextLine().trim().toUpperCase();
            
            if (userInput.equals("KEY") || userInput.equals("VALUE") || userInput.equals("BOTH")) {
                isValid = true;
            } else {
                System.out.println("Invalid answer. Choose from: key, value, both");
            }
        }
        return userInput;
    }


    public static String getValidEditTarget() {
        String userInput="";
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print("What are we editing (key, value, both): ");
            userInput = scanner.nextLine().trim().toUpperCase();
            
            if (userInput.equals("KEY") || userInput.equals("VALUE") || userInput.equals("BOTH")) {
                isValid = true;
            } else {
                System.out.println("Invalid answer. Choose from: key, value, both");
            }
        }
        return userInput;
    }

    public static UserInputDimensions getValidTableDimensions() {
        UserInputDimensions dimensions = new UserInputDimensions();
        boolean isInputValid = false;
        
        while (!isInputValid) {
            System.out.println("Enter table dimensions (format: 3x3, 4x4, 5x5, etc.)");
            System.out.print("Dimensions: ");
            String userInput = scanner.nextLine().trim();
            
            if (userInput.length() != 3) {
                System.out.println("Length must be 3 characters (e.g., 3x3)");
                continue;
            }
            
            if (userInput.charAt(1) != 'x' && userInput.charAt(1) != 'X') {
                System.out.println("Format must be: number x number (e.g., 3x3)");
                continue;
            }
            
            try {
                dimensions.setRow(Integer.parseInt(String.valueOf(userInput.charAt(0))));
                dimensions.setCol(Integer.parseInt(String.valueOf(userInput.charAt(2))));
                
                if (dimensions.getRow() > 0 && dimensions.getCol() > 0) {
                    isInputValid = true;
                } else {
                    System.out.println("Both dimensions must be greater than 0");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integers (e.g., 3x3 or 4x4)");
            }
        }
        return dimensions;
    }


    public static int[] getValidCellPosition(int maxRows, int maxCols) {
        int row = -1;
        int col = -1;
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print("Row to edit (0-" + (maxRows - 1) + "): ");
            String rowInput = scanner.nextLine().trim();
            System.out.print("Column to edit (0-" + (maxCols - 1) + "): ");
            String colInput = scanner.nextLine().trim();
            
            try {
                row = Integer.parseInt(rowInput);
                col = Integer.parseInt(colInput);
                
                if (row >= 0 && row < maxRows && col >= 0 && col < maxCols) {
                    isValid = true;
                } else {
                    System.out.println("Out of bounds. Please input row between 0-" + (maxRows - 1) + 
                                     " and column between 0-" + (maxCols - 1));
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers");
            }
        }
        return new int[]{row, col};
    }


    public static String getValidSearchInput(String prompt) {
        String userInput = "";
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print(prompt);
            userInput = scanner.nextLine().trim();
            
            if (userInput.isEmpty()) {
                System.out.println("Input cannot be blank. Please enter 1-3 characters.");
            } else if (userInput.length() > 3) {
                System.out.println("Input too long. Please enter 1-3 characters.");
            } else {
                isValid = true;
            }
        }
        return userInput;
    }
    public static String getValidThreeCharInput(String prompt) {
        String userInput="";
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print(prompt);
            userInput = scanner.nextLine().trim();
            
            if (userInput.length() == 3) {
                isValid = true;
            } else {
                System.out.println("Invalid length. Please input exactly 3 characters");
            }
        }
        return userInput;
    }


    public static String[] getValidKeyValueInput() {
        String userInput;
        boolean isValid = false;
        String[] result = new String[2];
        
        while (!isValid) {
            System.out.print("Enter key and value to replace (format: ABC DEF): ");
            userInput = scanner.nextLine().trim();
            
            if (userInput.length() == 7 && userInput.charAt(3) == ' ') {
                result[0] = userInput.substring(0, 3);
                result[1] = userInput.substring(4, 7);
                isValid = true;
            } else {
                System.out.println("Invalid format. Please input 7 characters with space in between (e.g., ABC DEF)");
            }
        }
        return result;
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

}