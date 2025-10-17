import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

enum NumberType{
    VALID_NUM,
    VALID_ROW_NUM
}
enum SortOrderType {
    ASC,
    DESC
}
class UserInputDimensions {
    int row;
    int col;
}

public class Main {    
    public static int rowTable;
    public static int colTable;
    public static List<List<String>> table;
    public static String fileName;
    public static boolean debug = true;
    private static final Scanner scanner = new Scanner(System.in);

    static List<List<String>> fileReaderUtility(){
        List<List<String>> tempTable;
        if(fileName.trim().isEmpty()){
            System.out.println("Please enter a file name");
            return table;
        }
        try {
            File file = new File(fileName);
            
            if(!file.exists()) {
                System.out.println("File Doesn't exist");
                return table; 
            }
            if(!file.canRead()) {
                System.out.println("Can't read file please change permission");
                return table;
            }

            List<String> unFilteredFileContent = Files.readAllLines(Paths.get(fileName));
            //System.out.println("unfiltered " + unFilteredFileContent);
            if(unFilteredFileContent.isEmpty()){
                System.out.println("File is empty please put elements in the file");
                return table;
            }
            int rowLength = unFilteredFileContent.get(0).length();

            rowTable = unFilteredFileContent.size();
            colTable =  ((rowLength-1) / 3)-1;
            if(colTable <= -1) colTable = 0;
            if(debug) System.out.println("coltable: " + colTable);
            tempTable = new ArrayList<List<String>>();

            if (debug) System.out.println("debug: unfilteredfielcontent size" + unFilteredFileContent.size());
            for(int i = 0; i < unFilteredFileContent.size(); i++){
                tempTable.add(new ArrayList<>());
                String charRow = unFilteredFileContent.get(i);
                if (debug) System.out.println("debug: length = " + charRow.length());
                if (debug) System.out.println("debug: charrow value: " + charRow);

                for(int j = 0; j < charRow.length(); j +=4){
                    if (debug) System.out.println("debug: i = " + i);
                    if (debug) System.out.println("debug: j = " + j);
                    if(j+2>= charRow.length()) {
                        System.out.println("invalid table check element count (3 elements per cell)");
                    };
                    tempTable.get(i).add((charRow.charAt(j) + "" +charRow.charAt(j+1) + "" + charRow.charAt(j+2)));

                    if( !(j+3 >= charRow.length()) && charRow.charAt(j+3) != ' '){
                        System.out.println("Invalid table due to no space between elements (every 3 elements should have a space)");
                        return table;
                    }
                }
            }
        }catch(NumberFormatException e){
            System.out.println("invalid col length please check your table");
            return table;
        }catch(Exception e){
            System.out.println(e);
            return table;
        }

        return tempTable;
    }
    static void fileWriterUtility(){
        if(fileName.trim().isEmpty()){
            System.out.println("Please enter a file name");
            return;
        }
        try {
            File file = new File(fileName);
                        if(!file.exists()) {
                System.out.println("File Doesn't exist");
                return; 
            }
            if(!file.canWrite() ){
                System.out.println("Can't write to file please change permission");
                return;
            }
            List<String> lines = new ArrayList<>();
            for (List<String> list : table) {
                lines.add(String.join(" ", list));
            }
            Files.write(Paths.get(fileName), lines);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    static String randChars() {
        String aschii = "";
        //32 - 126 aschi char
        int min = 32;
        int max = 126;
        for (int i = 0; i < 3; i++) {
            int randomNum = (int)(Math.random() * (max - min + 1)) + min;
            aschii += (char)randomNum;
        }
        return aschii;
    }
    static void genTable() {
        table = new ArrayList<List<String>>();
        for (int i = 0; i < rowTable; i++) {
            table.add(i, new ArrayList<>());
            for (int j = 0; j < colTable; j++) {
                table.get(i).add(randChars());
            }
        }
    }
    static void displayTable() {
        for (int i = 0; i < rowTable; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                System.out.print(table.get(i).get(j) + "   ");
            }
            System.out.println("");
        }
    }
    static void printMenu(){
        System.out.println("Menu: ");
        System.out.println("1: Search");
        System.out.println("2: Edit");
        System.out.println("3: Add Row");
        System.out.println("4: Sort");
        System.out.println("5: Print");
        System.out.println("6: Reset");
        System.out.println("7: Exit");
    }

    static void userInputDimensionValid(UserInputDimensions userinput) {
        //TODO please use the other Input validation function
        //temporary fix make it a while loop until it gives a valid input

        boolean isInputValid = false;
        while (!isInputValid) {
            System.out.println("valid input (3x3, 4x4, 5x5, etc.)");
            System.out.print("Location: ");
            String userInput = scanner.nextLine();
        
            if(userInput.length() != 3) {
                System.out.println("length must be 3 characters");
                continue;
            }
            if(!(Character.toLowerCase(userInput.charAt(1)) == 'x')){
                continue;
            }
            try{
                userinput.row = Integer.parseInt(String.valueOf(userInput.charAt(0)));
                userinput.col = Integer.parseInt(String.valueOf(userInput.charAt(2)));
            }catch(NumberFormatException e) {
                System.out.println("Invalid input please enter an integer (i.e. 3x3 or 4x4)");
                continue;
            }
            break;
        }
        
    }

    
    
    static void searchTable(String toSearch) {
        int instance = 0;
        String instanceLocation = "";
        int toSearchLen = toSearch.length();
        if(toSearchLen > 3) {
            System.out.println("Invalid length please have a maximum of 3 characters");
            return;
        }
        for (int i = 0; i < rowTable; i++) {
            for (int j = 0; j < colTable; j++) {
                boolean isOccurred = false;
                for(int k = 0; k < 3; k++) {
                    
                    if(toSearchLen+k > 3) {
                        //System.out.println("debug: break");                        
                        break;
                    }
                    if(table.get(i).get(j).substring(k, toSearchLen+k).equals(toSearch)) {
                        instance++;
                        if(!isOccurred) {
                            instanceLocation += "[" + i + "," + j + "]";
                            isOccurred = true;
                        }
                    }
                    
                        

                }
                
            }
        }
        System.out.println(instance + " Occurence/s at " + instanceLocation);        
    }
    static void editTable(String toReplace, int row, int col) {

        if(toReplace.length() != 3) {
            System.out.println("Invalid length please input a word with a maximum of 3 characters");
            return;
        }
        if(0 > row || row >= rowTable) {
            System.out.println("out of bounce please input a valid row from 0 - " + (rowTable-1));
            return;
        }
        if(0 > col || col >= colTable) {
            System.out.println("out of bounce please input a valid col from 0 - " + (colTable-1));
            return;
        }
        System.out.print(table.get(row).get(col));
        table.get(row).set(col, toReplace);
        System.out.println(" " + table.get(row).get(col));
    }
    static void addRow(int numOfCell){
        
        table.add(new ArrayList<>());
        for(int i =0; i < numOfCell; i++){
            table.get(rowTable).add(randChars());
        }
        rowTable++;
    }
    static void sort(int rowToSort, SortOrderType order){
        List<String> row = new ArrayList<>();
        if(order.equals(SortOrderType.ASC)){
            row = table.get(rowToSort).stream()
                                    .map( c ->{
                                        char[] chars = c.toCharArray();
                                        Arrays.sort(chars);
                                        return new String(chars);
                                    })
                                    .sorted(Comparator.naturalOrder())
                                    .collect(Collectors.toList());
            table.set(rowToSort, row);
        }
        else if((order.equals(SortOrderType.DESC))){
            row = table.get(rowToSort).stream()
                                    .map( c ->{
                                        char[] chars = c.toCharArray();
                                        Character[] charObj = new Character[chars.length];
                                        for(int i =0; i < chars.length; i++){
                                            charObj[i] = chars[i];
                                        }
                                        Arrays.sort(charObj, Collections.reverseOrder());
                                        return new String(chars);
                                    })
                                    .sorted(Comparator.reverseOrder())
                                    .collect(Collectors.toList());
            
        }
        table.set(rowToSort, row);
    }


    
    static int getNumInput(NumberType type){
        String userInput;
        boolean isValid = false;
        int userInputInt = 0;
        while (!isValid) {
            userInput = scanner.nextLine();
            try {
                userInputInt = Integer.parseInt(userInput);
                if(type.equals(NumberType.VALID_NUM)){
                    if(userInputInt > 0){
                        isValid = true;
                    }else{
                        System.out.println("please input a value greater than 0");
                    }
                }else if(type.equals(NumberType.VALID_ROW_NUM)){
                    if(userInputInt >= 0 && userInputInt < rowTable){
                        isValid = true;
                    }else{
                        System.out.println("Please input a row between(inclusive) 0 and " + (rowTable - 1));
                    }
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please Input a valid integer");
            }
        }
        return userInputInt;
    }
    static String getFileNameInput(String inputFileName) {
        /*
         * To test
         * 1. check if user inputed something
         * 2. if none get its input and then check if its valid
         * 3. if there is check if the file name is valid
         * 4. return if file is valid
         */
        File file;
        if(!inputFileName.trim().isEmpty()){
            //check if it exist;
            //return if it does
            try {
                file = new File(inputFileName);
                if(file.exists()) {
                    return inputFileName;
                }else{
                    System.out.println("file doesn't exist!");
                }
            } catch (Exception e) {
                System.out.println("User didnt provide a file name");
            }
        } 
        //if it doesn't exist get user input
        boolean isFileValid = false;
        while(!isFileValid){
            System.out.print("Please input a file name (dont put .txt): ");
            inputFileName = scanner.nextLine() + ".txt";
            try {
                file = new File(inputFileName);   
                if(file.exists()) {
                    isFileValid = true;
                }else if(!file.exists()) {
                    System.out.println("File Doesn't exist re-input");
                }
            } catch (NullPointerException e) {
                System.out.println("User Input a file name");
            }
        }
        return inputFileName;
    }
    static SortOrderType getSortOrderInput(){
        String userInput="";
        SortOrderType order = SortOrderType.ASC;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("sort order (ASC or DESC): ");
            userInput = scanner.nextLine().toUpperCase();
            if(userInput.toUpperCase().equals(SortOrderType.ASC.toString())){
                isValid=true;
                order = SortOrderType.ASC;
            }else if(userInput.toUpperCase().equals(SortOrderType.DESC.toString())){
                isValid=true;
                order = SortOrderType.DESC;
            }
            else{
                System.out.println("Please input either ASC or DESC");
            }
            if(debug) System.out.println("order: " + order);
            if(debug) System.out.println("userinput: " + userInput);

        }
        return order;
    }

    public static void main(String[] args) {
        fileName = args.length > 0 ?  getFileNameInput(args[0]) : getFileNameInput("");
        table = fileReaderUtility();
        displayTable();

        UserInputDimensions userinputdimension = new UserInputDimensions();

        boolean exit = true;
        while(exit) {
            printMenu();
            System.out.print("Menu: ");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    //search
                    System.out.print("To Search: ");
                    userInput = scanner.nextLine();
                    searchTable(userInput);
                    break;
                case "2":
                    //edit
                    userInputDimensionValid(userinputdimension);
                    System.out.print("Input text to replaced: ");
                    userInput = scanner.nextLine();
                    editTable(userInput, userinputdimension.row, userinputdimension.col);
                    fileWriterUtility();
                    break;
                case "3":
                    //add row
                    System.out.print("How Many cells: ");
                    int numOfCell = getNumInput(NumberType.VALID_NUM);
                    addRow(numOfCell);
                    displayTable();
                    fileWriterUtility();
                    break;
                case "4":
                    //sort
                    System.out.print("Row to sort(0 - "+(rowTable-1) + "): ");
                    int rowToSort = getNumInput(NumberType.VALID_ROW_NUM);
                    SortOrderType order = getSortOrderInput();
                    sort(rowToSort, order);
                    displayTable();
                    fileWriterUtility();
                    break;
                case "5":
                    //print
                    displayTable(); 
                    break;
                case "6":
                    //reset
                    userInputDimensionValid(userinputdimension);
                    rowTable = userinputdimension.row;
                    colTable = userinputdimension.col;
                    genTable();
                    displayTable();
                    fileWriterUtility();
                    break;
                case "7":
                    //exit
                    scanner.close();
                    exit = false;
                    break;
                default:
                    System.out.println("please input a valid menu value");
                    break;
            }
        }
        
        
        
    }
}