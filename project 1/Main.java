import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.*;

enum NumberType{
    VALID_NUM,
    VALID_ROW_NUM
}
enum SortOrder {
    ASC,
    DESC
}
enum SearchTarget {
    KEY,
    VALUE,
    BOTH
}
class UserInputDimensions {
    int row;
    int col;
}

public class Main {    
    public static int rowTable;
    public static int colTable;
    public static List<LinkedHashMap<String, String>> table;
    public static String fileName;
    public static boolean debug = true;
    private static final Scanner scanner = new Scanner(System.in);


    
    static void fileReaderUtility(){
        List<LinkedHashMap<String, String>> tempTable = new ArrayList<>();
        List<String> unFilteredFileContent;
        
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
                if(!file.canRead()) {
                    System.out.println("Can't read file please change permission");
                    return;
                }

                unFilteredFileContent = Files.readAllLines(Paths.get(fileName));
            
                //System.out.println("unfiltered " + unFilteredFileContent);
                if(unFilteredFileContent.isEmpty()){
                    System.out.println("File is empty please put elements in the file");
                    return;
                }
                rowTable = unFilteredFileContent.size();

                tempTable = new ArrayList<LinkedHashMap<String, String>>();

                if (debug) System.out.println("debug: unfilteredfielcontent size" + unFilteredFileContent.size());
                for(int i = 0; i < unFilteredFileContent.size(); i++){
                    tempTable.add(new LinkedHashMap<>());
                    String row = unFilteredFileContent.get(i);
                    if (debug) System.out.println("debug: length = " + row.length());
                    if (debug) System.out.println("debug: charrow value: " + row);

                    for(int j = 0; j < row.length(); j= j+8){
                        if (debug) System.out.println("debug: i = " + i);
                        if (debug) System.out.println("debug: j = " + j);
                        if(j+6 >= row.length()) {
                                String key = ""+row.charAt(j) +row.charAt(j+1) + row.charAt(j+2);
                                String value = ""+ row.charAt(j+4)+ row.charAt(j+5)+ row.charAt(j+6);
                            tempTable.get(i).put(key, value);
                            break;
                        };

                        if((row.charAt(j+3) != ' ' || (j+7 > row.length() && row.charAt(j+7) != ' '))){
                            System.out.println("Invalid table due to no space between elements (every 3 elements should have a space)");
                            return;
                        }
                        String key = ""+row.charAt(j) +row.charAt(j+1) + row.charAt(j+2);
                        String value = ""+ row.charAt(j+4)+ row.charAt(j+5)+ row.charAt(j+6);
                        tempTable.get(i).put(key, value);
                        if(debug) System.out.println("key: " + key);
                        if(debug) System.out.println("value: " + value);


                        
                    }
                }
            }catch(Exception e){
                System.out.println("invalid table format please check your table");
                return;
            }
        table = tempTable;
        
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
            for(int i =0; i < rowTable; i++){
                StringBuilder line = new StringBuilder();
                for (Map.Entry<String, String> entry : table.get(i).entrySet()) {
                    line.append(" ").append(entry.getKey()).append(" ").append(entry.getValue());
                }
                lines.add(line.toString().trim());
            }

            Files.write(Paths.get(fileName), lines);
            
        } catch (Exception e) {
            System.out.println("Something went wrong when writing to a file");
        }
    }
    static String randChars() {
        String aschii = "";
        //33 - 126 aschi char
        int min = 33;
        int max = 126;
        for (int i = 0; i < 3; i++) {
            int randomNum = (int)(Math.random() * (max - min + 1)) + min;
            aschii += (char)randomNum;
        }
        return aschii;
    }
    static void genTable() {
        table = new ArrayList<LinkedHashMap<String, String>>();
        for (int i = 0; i < rowTable; i++) {
            table.add(i, new LinkedHashMap<String, String>());
            for (int j = 0; j < colTable; j++) {
                String key  = randChars();

                //Testing duplicate detection
                //if(i==0 && j == 0) key = "aaa";
                //if (i==0 && j==1) key = "aaa";

                while(table.get(i).containsKey(key)) {
                    String oldKey = key;
                    key = randChars();
                    if(debug) System.out.println("duplicate detected changed from " + oldKey + " to " + key);
                }
                table.get(i).put(key, randChars());
            }
        }
    }
    static void displayTable() {
        for (int i = 0; i < rowTable; i++) {
            table.get(i).forEach((key, value) ->System.out.print(key + "  " + value + "    "));
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
    


    static void searchTable(){
        //TODO "BOTH" not implemented properly it only checks user input but no search
        int instance = 0;
        String instanceLocation = "";
        boolean isInputValid = false;
        String userInput ="";
        while (!isInputValid) {
            System.out.print("What are we searching for (key, value, both): ");
            userInput = scanner.nextLine().toUpperCase();
            if(userInput.isEmpty()){
                System.out.println("Please input a command");
                continue;
            }
            if(!(userInput.equals(SearchTarget.KEY.toString()) || userInput.equals(SearchTarget.VALUE.toString()) || userInput.equals(SearchTarget.BOTH.toString()))) {
                System.out.println("Invalid answer, (Choose from Key, Value, Both)!");
                continue;
            }


            if(userInput.equals(SearchTarget.KEY.toString())) {
                System.out.print("key to search: ");
                String key = scanner.nextLine();
                int toSearchLen = key.length();
                int rowIteration=0;
                int colIteration =0;
                for (Map<String,String> map : table) {
                    colIteration=0;
                    for (String mapKey: map.keySet()) {
                        boolean isOccurred = false;
                        for(int k = 0; k < 3; k++){
                            if(toSearchLen+k > 3) {
                                if(debug) System.out.println("debug: break");                        
                                break;
                            }
                            if(mapKey.substring(k, toSearchLen+k).equals(key)) {
                                instance++;
                                if(!isOccurred) {
                                    instanceLocation += "[" + rowIteration + "," + colIteration + "]";
                                    isOccurred = true;
                                }
                                if(debug) System.out.println("occurrance at: " + rowIteration + " " +colIteration);                        


                            }
                        
                        }
                        colIteration++;

                    }
                    rowIteration++;

                }
                System.out.println(instance + " Occurence/s at " + instanceLocation);
                return;
            }else if(userInput.equals(SearchTarget.VALUE.toString())) {
                System.out.print("value to search: ");
                String value = scanner.nextLine();

                int toSearchLen = value.length();
                int rowIteration=0;
                int colIteration =0;
                for (Map<String,String> map : table) {
                    Collection<String> mapValues = map.values();
                    colIteration=0;
                    for (String mapValue: mapValues) {
                        boolean isOccurred = false;

                        for(int k = 0; k < 3; k++){
                            if(toSearchLen+k > 3) {
                                if(debug) System.out.println("debug: break");                        
                                break;
                            }
                            if(mapValue.substring(k, toSearchLen+k).equals(value)) {
                                instance++;
                                if(!isOccurred) {
                                    instanceLocation += "[" + rowIteration + "," + colIteration + "]";
                                    isOccurred = true;
                                }

                            }
                        }
                        colIteration++;

                    }
                    rowIteration++;

                }
                System.out.println(instance + " Occurence/s at " + instanceLocation);
                return;
            } else if(userInput.equals(SearchTarget.BOTH.toString())) {
                //TODO not yet implemented
                //only checks and validates userinput

                System.out.print("Key to search");
                String key = scanner.nextLine();


                System.out.print("value to search");
                String value = scanner.nextLine();


                isInputValid=true;
            }else {
                System.out.println("Error no User Input found");
                continue;
            }
        }
        
        
             
    }
    static void editTable() {
        boolean isInputValid = false;
        int rowInt = 0;
        int colInt = 0;
        while (!isInputValid) {
            System.out.print("row to edit: ");
            String rowInput =  scanner.nextLine();
            System.out.print("col to edit: ");
            String colInput =  scanner.nextLine();
            try {
                rowInt = Integer.parseInt(rowInput);
                colInt = Integer.parseInt(colInput);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
                continue;
            }
            if(0 > rowInt || rowInt >= rowTable) {
                System.out.println("out of bounce please input a valid row from 0 - " + (rowTable-1));
                continue;
            }
            int colLen = table.get(rowInt).size();
            if(0 > colInt || colInt >= colLen) {
                System.out.println("out of bounce please input a valid col from 0 - " + (colLen-1));
                continue;
            }


            System.out.print("What are we editing (Key, Value, Both) ");
            String choice = scanner.nextLine().toUpperCase();
            if(choice.isEmpty()){
                System.out.println("Please input a command");
                continue;
            }
            if(!(choice.equals(SearchTarget.KEY.toString()) || choice.equals(SearchTarget.VALUE.toString()) || choice.equals(SearchTarget.BOTH.toString()))) {
                System.out.println("Invalid answer, (Choose from Key, Value, Both)!");
                continue;
            }

            

            if(choice.equals(SearchTarget.KEY.toString())) {
                System.out.print("key to replace: ");
                String toReplace = scanner.nextLine();
                if(toReplace.length() != 3) {
                    System.out.println("Invalid length please input a word with a maximum of 3 characters");
                    continue;
                }
                if(table.get(rowInt).containsKey(toReplace)){
                    System.out.println("Key already exist for that row please input another one");
                    continue;
                }

                    
                int i =0;
                LinkedHashMap<String, String> updatedRow = new LinkedHashMap<>();
                
                for(Map.Entry<String, String> item : table.get(rowInt).entrySet()) {
                    if(i == colInt){
                        System.out.println("Replacing column " + i + " key: " + item.getKey() + " to " + toReplace);
                        updatedRow.put(toReplace, item.getValue());
                        isInputValid = true;
                    }else{
                        updatedRow.put(item.getKey(), item.getValue());
                    }
                    if(debug) System.out.println("debug: " +updatedRow);

                    i++;
                }
                table.set(rowInt, updatedRow);


            }else if(choice.equals(SearchTarget.VALUE.toString())) {
                System.out.print("key to replace: ");
                String toReplace = scanner.nextLine();
                if(toReplace.length() != 3) {
                    System.out.println("Invalid length please input a word with a maximum of 3 characters");
                    continue;
                }

                int i =0;
                
                for(Map.Entry<String, String> item : table.get(rowInt).entrySet()) {
                    if(i == colInt){
                        System.out.println("Replacing column " + i + " key: " + item.getKey() + " to " + toReplace);
                        table.get(rowInt).replace(item.getKey(), toReplace);
                        isInputValid = true;
                    }
                    i++;
                }   
            }else if(choice.equals(SearchTarget.BOTH.toString())) {
                System.out.print("key and value to replace (space seperated): ");
                String toReplace = scanner.nextLine();
                if(toReplace.length() != 7) {
                    System.out.println("Invalid length please input a word with a maximum of 7 characters (space included)");
                    continue;
                }
                if(toReplace.charAt(3) != ' '){
                    System.out.println("Please Input a space in between");
                }
                String key = toReplace.substring(0, 3);
                String value = toReplace.substring(4, 7);
                if(debug){
                    System.out.println("key: " + key + " value " + value);
                }
                if(table.get(rowInt).containsKey(key)){
                    System.out.println("Key already exist for that row please input another one");
                    continue;
                }

                int i =0;
                
                LinkedHashMap<String, String> updatedRow = new LinkedHashMap<>();
                
                for(Map.Entry<String, String> item : table.get(rowInt).entrySet()) {
                    if(i == colInt){
                        System.out.println("Replacing column " + i + " key: " + item.getKey() + " to " + key);
                        System.out.println("Replacing column " + i + " value: " + item.getValue() + " to " + value);
                        updatedRow.put(key, value);
                        isInputValid = true;
                    }else{
                        updatedRow.put(item.getKey(), item.getValue());
                    }
                    if(debug) System.out.println("debug: " +updatedRow);

                    i++;
                }
                table.set(rowInt, updatedRow);   
            }
        }
    }
    static void addRow(int numOfCell){
        
        table.add(new LinkedHashMap<String, String>());
        for (int j = 0; j < numOfCell; j++) {
            String key  = randChars();

            while(table.get(rowTable-1).containsKey(key)) {
                String oldKey = key;
                key = randChars();
                if(debug) System.out.println("duplicate detected changed from " + oldKey + " to " + key);
            }
            table.get(rowTable).put(key, randChars());
        }
        rowTable++;
    }
    static void sort(int rowToSort, SortOrder order){

        boolean isSortFailed = false;
        LinkedHashMap<String, String> row = new LinkedHashMap<>();
        if(order.equals(SortOrder.ASC)){
            List<String> updatedRow = new ArrayList<>();
            for (Map.Entry<String,String> item : table.get(rowToSort).entrySet()) {
                updatedRow.add(item.getKey()+item.getValue());
            }
            Collections.sort(updatedRow);
            for (String combinedKeyValue : updatedRow) {
                if(row.containsKey(combinedKeyValue.substring(0, 3))){
                    System.out.println("Sort fail due to detecting the same key");
                    isSortFailed = true;
                    break;
                }else{
                    row.put(combinedKeyValue.substring(0, 3),combinedKeyValue.substring(3, 6) );
                    if(debug) System.out.println("asc: row " + row);
                }
            }

        }
        else if((order.equals(SortOrder.DESC))){
              List<String> updatedRow = new ArrayList<>();
            for (Map.Entry<String,String> item : table.get(rowToSort).entrySet()) {
                updatedRow.add(item.getKey()+item.getValue());
            }
            Collections.sort(updatedRow, Collections.reverseOrder());
            for (String combinedKeyValue : updatedRow) {
                if(row.containsKey(combinedKeyValue.substring(0, 3))){
                    System.out.println("Sort fail due to detecting the same key");
                    isSortFailed = true;
                    break;
                }else{
                    row.put(combinedKeyValue.substring(0, 3),combinedKeyValue.substring(3, 6) );
                    if(debug) System.out.println("desc: row " + row);
                }
            }
        }
        if(!isSortFailed) {
            table.set(rowToSort, row);   

        }
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
    static SortOrder getSortOrderInput(){
        String userInput="";
        SortOrder order = SortOrder.ASC;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("sort order (ASC or DESC): ");
            userInput = scanner.nextLine().toUpperCase();
            if(userInput.toUpperCase().equals(SortOrder.ASC.toString())){
                isValid=true;
                order = SortOrder.ASC;
            }else if(userInput.toUpperCase().equals(SortOrder.DESC.toString())){
                isValid=true;
                order = SortOrder.DESC;
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
        
        while (table == null) {
            fileName = args.length > 0 ?  getFileNameInput(args[0]) : getFileNameInput("");
            fileReaderUtility();
        }
        
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
                    searchTable();
                    break;
                case "2":
                    //edit
                    editTable();
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
                    SortOrder order = getSortOrderInput();
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