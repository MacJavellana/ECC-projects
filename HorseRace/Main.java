import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noHorses = 0;
        int distance = 0;
        int maxAge = 0;
        boolean isNoofHorsesValid = false;
        while(!isNoofHorsesValid) {
            System.out.print("Number of horses (max 56): ");
            String userInput = scanner.nextLine();
            try {
                noHorses = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
                continue;
            }
            if(noHorses <= 0) {
                System.out.println("please input a number above 0");
                continue;
            }
            if(noHorses >= 57) {
                System.out.println("Please input a number between 0 - 57");
                continue;
            }
            isNoofHorsesValid=true;
        }


        boolean isDistanceValid = false;
        while(!isDistanceValid){
            System.out.print("Track Distance: ");
            String userInput = scanner.nextLine();
            try {
                distance = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
                continue;
            }
            isDistanceValid = true;
        }


        boolean isMaxAgeValid = false;
        while(!isMaxAgeValid){
            System.out.print("please input max horse age: ");
            String userInput = scanner.nextLine();
            try {
                maxAge = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
                continue;
            }
            isMaxAgeValid = true;
        }



        List<Horse> horses = new ArrayList<>();


        // use of horse factory class
        horses = HorseFactory.GenerateHorses(noHorses, maxAge, distance);


        // manual addition
        // for (int i = 0; i < noHorses; i++) {
        //     System.out.println("Horse " + (i+1));
        //     System.out.print("name: " );
        //     String name = scanner.nextLine();

        //     System.out.print("War Cry: " );
        //     String warCry = scanner.nextLine();

        //     boolean isAgeValid = false;
        //     int age =0;
        //     while(!isAgeValid) {
        //         System.out.print("Age: " );
        //         String ageString = scanner.nextLine();
        //         try {
        //             age = Integer.parseInt(ageString);
        //         } catch (Exception e) {
        //             System.out.println("please enter a valid age");
        //             continue;
        //         }
        //         isAgeValid = true;
        //     }
            
        //     horses.add(new Horse(name, warCry, age, distance));
        // }
        





        HorseRace horseRace = new HorseRace(distance, noHorses, horses);
        horseRace.startRace();
    }
}
