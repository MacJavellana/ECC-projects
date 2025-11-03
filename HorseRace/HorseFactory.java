import java.util.ArrayList;
import java.util.List;

public class HorseFactory {
    static private String ListOfHorseNames(int index) {
        
        List<String> horseNames = List.of(
                            "L.B. (Little Buddy)", "Bandit","Thunder","Scout",
                                        "Rowdy","Cash","Levi","Fury","Tex","Winston","Finn",
                                        "Jasper","Colt","Ryder","Blaze","Shadow","Duke",
                                        "Maverick","Tucker","Blue Jeans","Dash","Jax",
                                        "Ranger","Storm","Teddy","Boone","Dusty","Felix",
                                        "Gatsby","Chip","Elvis","Russell","Leo","Billy",
                                        "Harley","Ace","Tank","Gus","Champ","Rusty","Bo",
                                        "Buddy","Trigger","Oakley","Pal","Amigo","Ed","Prince",
                                        "Chance","Lucky","Kai","Denver","Zero","Dante","Journey","Ford","Atlas"
                                        );
        return horseNames.get(index);
    }
    static private String ListOfWarCry(int index) {
        List<String> HorseWarCry = List.of(
                            "Small but deadly!",
                                        "Hide your gold, I’m coming for it!",
                                        "Feel the roar before the storm!",
                                        "Eyes sharp, target locked!",
                                        "Raise some hell!",
                                        "Bet your life, I’m all in!",
                                        "Stand tall, strike hard!",
                                        "Unleash the chaos!",
                                        "For the Lone Star and glory!",
                                        "Courage before comfort!",
                                        "Swift as the tide, sharp as steel!",
                                        "Ain’t luck — it’s skill!",
                                        "Fast hands, faster bullets!",
                                        "Ride or die!",
                                        "Burn bright, burn fierce!",
                                        "Strike unseen, vanish clean.",
                                        "The name’s respect — remember it!",
                                        "Rules? Never heard of ‘em!",
                                        "Keep up or get left behind!",
                                        "Old style, new fight!",
                                        "Too fast to catch!",
                                        "Break bones, not promises!",
                                        "Watch the horizon — that’s me coming!",
                                        "The sky’s wrath rides with me!",
                                        "Don’t let the name fool you!",
                                        "Born to survive, bred to win!",
                                        "From dirt I rise — and you’ll fall!",
                                        "Luck’s on my side — too bad for you!",
                                        "Classy fight, deadly bite!",
                                        "Let’s raise the stakes!",
                                        "Rock and roll ‘til you drop!",
                                        "No noise, just impact.",
                                        "Hear me roar!",
                                        "I shoot straight, talk faster!",
                                        "Loud pipes, louder fight!",
                                        "Always first, never last!",
                                        "Can’t stop what can’t be broken!",
                                        "Old bones, new tricks!",
                                        "Born for the top — and staying there!",
                                        "Old metal, sharp edge!",
                                        "Quick hands, quiet words.",
                                        "With friends like me, who needs luck?",
                                        "One pull, one victory.",
                                        "Eyes of a hawk, aim of a legend!",
                                        "You’ll remember me — one way or another.",
                                        "Let’s dance, compadre!",
                                        "Simple name, savage game!",
                                        "Born royal, fights loyal!",
                                        "You took yours — I took it better!",
                                        "I make my own fate!",
                                        "Waves obey me!",
                                        "From the mountains, I strike!",
                                        "From nothing to everything!",
                                        "Hell’s fire burns with me!",
                                        "Every step ends in victory!",
                                        "Built tough, born tougher!",
                                        "The world’s on my shoulders — and I’m still fighting!"
                                        );
        

        return HorseWarCry.get(index);
    }


    static private int getRandNum(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    static private boolean containsName(final List<Horse> list, final String toFind) {
        return list.stream().anyMatch(name -> name.getName().equals(toFind));
    }
    static private boolean containsWarCry(final List<Horse> list, final String toFind) {
        return list.stream().anyMatch(warcry -> warcry.getWarCry().equals(toFind));
    }
    static public List<Horse> GenerateHorses(int noOfHorses, int maxAge, int distance) {
        List<Horse> listOfHorses = new ArrayList<>();

        for(int i = 0; i < noOfHorses;i ++) {
            String GeneratedHorseName = ListOfHorseNames(getRandNum(0, 56));
            while(containsName(listOfHorses, GeneratedHorseName)){
                System.out.println("duplicate detected for name " + GeneratedHorseName);
                GeneratedHorseName = ListOfHorseNames(getRandNum(0, 56));
            }

            String GeneratedWarCry = ListOfWarCry(getRandNum(0, 56));
            while(containsWarCry(listOfHorses, GeneratedWarCry)) {
                System.out.println("duplicate detected for warcry " + GeneratedWarCry);
                GeneratedWarCry = ListOfHorseNames(getRandNum(0, 56));
            }

            int age = getRandNum(1, maxAge);
            listOfHorses.add(new Horse(GeneratedHorseName, GeneratedWarCry, age, distance));
        }


        System.out.println("===========================================================");
        System.out.println("generated horses");
        for(int i =0; i < listOfHorses.size(); i++) {
            Horse horse = listOfHorses.get(i);
            System.out.println(" Horse no " + (i+1) + ", name: " + horse.getName() + ", warcry: " + horse.getWarCry() + ", age " + horse.getAge() + ", condition " + horse.getCondition().toString() );
        }


        System.out.println("===========================================================");
        System.out.println("healthy horses");

        for(int i =0; i < listOfHorses.size(); i++) {
            Horse horse = listOfHorses.get(i);
            if(horse.getCondition().equals(HorseCondition.HEALTHY)) System.out.println(" Horse no " + (i+1) + ", name: " + horse.getName() + ", warcry: " + horse.getWarCry() + ", age " + horse.getAge() + ", condition " + horse.getCondition().toString() );
        }
        System.out.println("===========================================================");


        return listOfHorses;
    }
}
