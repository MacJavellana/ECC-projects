import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noHorses = 0;
        int distance = 0;
        boolean isNoofHorsesValid = false;
        while(!isNoofHorsesValid){
            System.out.print("Number of horses: ");
            String userInput = scanner.nextLine();
            try {
                noHorses = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
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



        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < noHorses; i++) {
            System.out.println("Horse " + (i+1));
            System.out.print("name: " );
            String name = scanner.nextLine();

            System.out.print("War Cry: " );
            String warCry = scanner.nextLine();

            boolean isAgeValid = false;
            int age =0;
            while(!isAgeValid){
                System.out.print("Age: " );
                String ageString = scanner.nextLine();
                try {
                    age = Integer.parseInt(ageString);
                } catch (Exception e) {
                    System.out.println("please enter a valid age");
                    continue;
                }
                isAgeValid = true;
            }
            
            horses.add(new Horse(name, warCry, age, distance));
        }
        



        HorseRace horseRace = new HorseRace(distance, noHorses, horses);
        horseRace.startRace();
    }
}
