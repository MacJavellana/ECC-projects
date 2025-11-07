import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HorseRace {
    private int distance = 0;
    private int noHorses = 0;
    private List<Horse> horses;
    public HorseRace(int distance, int noHorses, List<Horse> horses){
        this.distance = distance;
        this.noHorses = noHorses;
        this.horses = new ArrayList<>();
        this.horses = horses;
    }
    public void startRace(){
        generateHorseGroups();

    }
    private void generateHorseGroups(){
        IntSummaryStatistics horseAgeStats = horses.stream()
                            .collect(Collectors.summarizingInt(Horse::getAge));
        Map<Integer, Long> ageFrequencies = horses
                            .stream()
                            .collect(Collectors.groupingBy(Horse::getAge, Collectors.counting()));
        Long maxFrequency = ageFrequencies
                            .values()
                            .stream()
                            .mapToLong(Long::longValue)
                            .max()
                            .orElse(0);
        Set<Integer> mostCommonAges = ageFrequencies
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() == maxFrequency)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet());
        for (Horse horse : horses) {
            int currHorseAge = horse.getAge();
            if(mostCommonAges.contains(currHorseAge)) {
                horse.setHorseGroup(HorseGroup.ADVANCED);
            } else if (currHorseAge >= horseAgeStats.getAverage()) {
                horse.setHorseGroup(HorseGroup.INTERMEDIATE);
            } else {
                horse.setHorseGroup(HorseGroup.BEGINNER);
            }
        }
        horses = horses.stream().filter(horse ->horse.getCondition().equals(HorseCondition.HEALTHY)).collect(Collectors.toList());

        AtomicInteger placement = new AtomicInteger(0);
        horses.parallelStream().forEach(horse ->{
                while(horse.getDistance() > 0){
                    horse.run();
                }
                horse.setRank (placement.incrementAndGet());
            }
        );
        horses.stream().sorted((h1, h2) -> h1.getRank().compareTo(h2.getRank())).forEach( horse ->{
            System.out.println(horse.getName() + " is ranked " + horse.getRank());
        });
    }

}
