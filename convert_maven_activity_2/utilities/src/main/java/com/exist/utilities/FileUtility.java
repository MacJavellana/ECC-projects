package com.exist.utilities;

import com.exist.model.Table;
import com.exist.model.Row;
import com.exist.model.Cell;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtility {
    public static boolean debug = true;


    public static Table readTableFromFile(String fileName) {
        // If no filename provided, use default data.txt in root
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "data.txt";
        }
        
        // Ensure .txt extension if not provided
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName = fileName + ".txt";
        }

        try {
            File file = new File(fileName);
            
            if (!file.exists()) {
                System.out.println("File doesn't exist: " + fileName + ". Creating new file...");
                // Create the file automatically
                if (createFile(fileName)) {
                    return null; // Return null so calling code can generate new table
                } else {
                    return null;
                }
            }
            
            if (!file.canRead()) {
                System.out.println("Can't read file. Please change permissions: " + fileName);
                return null;
            }

            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            
            if (lines.isEmpty()) {
                System.out.println("File is empty: " + fileName);
                return null;
            }

            Table table = new Table();
            
            if (debug) System.out.println("debug: file content lines: " + lines.size());
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue; // Skip empty lines
                
                Row row = new Row();
                
                if (debug) System.out.println("debug: processing line: " + line);
                if (debug) System.out.println("debug: line length: " + line.length());

                for (int j = 0; j < line.length(); j += 8) {
                    if (debug) System.out.println("debug: processing cell at position: " + j);

                    if (j + 6 >= line.length()) {
                        int remaining = line.length() - j;
                        if (remaining >= 7) {
                            String key = line.substring(j, j + 3);
                            String value = line.substring(j + 4, j + 7);
                            
                            Cell cell = new Cell();
                            cell.setKey(key);
                            cell.setValue(value);
                            row.addCell(cell);
                        }
                        break;
                    }

                    if (line.charAt(j + 3) != ' ') {
                        System.out.println("Invalid table format: no space between elements at line " + (i + 1) + ", position " + (j + 4));
                        return null;
                    }

                    String key = line.substring(j, j + 3);
                    String value = line.substring(j + 4, j + 7);
                    
                    Cell cell = new Cell();
                    cell.setKey(key);
                    cell.setValue(value);
                    row.addCell(cell);
                    
                    if (debug) {
                        System.out.println("debug: added cell - key: " + key + ", value: " + value);
                    }
                }
                
                table.addRow(row);
            }
            
            if (debug) System.out.println("debug: successfully read table with " + table.getRowCount() + " rows");
            return table;
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Invalid table format: " + e.getMessage());
            return null;
        }
    }


    public static boolean writeTableToFile(String fileName, Table table) {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "data.txt"; // Default filename
        }
        
        // Ensure .txt extension if not provided
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName = fileName + ".txt";
        }

        if (table == null || table.getTable().isEmpty()) {
            System.out.println("Table is empty. Nothing to write.");
            return false;
        }

        try {
            File file = new File(fileName);
            
            // Create file if it doesn't exist
            if (!file.exists()) {
                System.out.println("File doesn't exist. Creating: " + fileName);
                if (!createFile(fileName)) {
                    return false;
                }
            }
            
            if (!file.canWrite()) {
                System.out.println("Can't write to file. Please change permissions: " + fileName);
                return false;
            }

            List<String> lines = new java.util.ArrayList<>();
            List<Row> rows = table.getTable();
            
            for (Row row : rows) {
                StringBuilder line = new StringBuilder();
                List<Cell> cells = row.getCells();
                
                for (Cell cell : cells) {
                    line.append(cell.getKey()).append(" ").append(cell.getValue()).append(" ");
                }
                
                lines.add(line.toString().trim());
            }

            FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), lines);
            
            if (debug) System.out.println("debug: successfully wrote " + lines.size() + " lines to file: " + fileName);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }


    public static boolean createFile(String fileName) {
        try {
            File file = new File(fileName);
            // Create parent directories if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            if (file.createNewFile()) {
                System.out.println("Created new file: " + fileName);
                return true;
            } else {
                System.out.println("File already exists: " + fileName);
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return false;
        }
    }


    public static String getDefaultDataFile() {
        return "data.txt";
    }


    public static boolean isValidFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "data.txt"; // Default filename
        }
        
        File file = new File(fileName);
        return file.exists() && file.canRead() && file.isFile();
    }


    public static boolean isWritable(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "data.txt"; // Default filename
        }
        
        File file = new File(fileName);
        if (!file.exists()) {
            return createFile(fileName);
        }
        return file.canWrite() && file.isFile();
    }

    public static long getFileSize(String fileName) {
        try {
            File file = new File(fileName);
            return FileUtils.sizeOf(file);
        } catch (Exception e) {
            return -1;
        }
    }
}