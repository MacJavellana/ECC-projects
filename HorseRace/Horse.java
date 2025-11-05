import java.util.Random;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Horse {
    
    private String name;
    private String warCry;
    private int age;
    private HorseCondition condition;
    private double speed = 0;
    private HorseGroup horseGroup;
    private int cycle = 0;
    private double distance;
    private int rank = 0;

    private HorseCondition getRandCondition() {
        Random random = new Random();
        return random.nextBoolean() ? HorseCondition.HEALTHY : HorseCondition.NOT_HEALTHY;
    }
    private int getRandSpeed(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public Horse(String name, String warCry, int age, double distance) {
        this.name = name;
        this.warCry = warCry;
        this.age = age;
        condition = getRandCondition();
        horseGroup = HorseGroup.DEFAULT;
        cycle = 1;
        this.distance = distance;

    }



    private void setSpeed(int generation) {
        switch (horseGroup) {
            case HorseGroup.ADVANCED:
                if(generation >= 3) {
                    speed = getRandSpeed(5, 10);
                } else {
                    speed = getRandSpeed(1, 10);
                }
                break;
            case HorseGroup.INTERMEDIATE:
                speed = getRandSpeed(1, 10) ;
                if(generation >= 5) {
                    speed = speed + (speed * .1);
                }
                break;
            case HorseGroup.BEGINNER:
                if(speed <= 0){
                    speed = getRandSpeed(1, 10);
                }
                break;
            default:
                speed = getRandSpeed(1, 10);
                break;
        }
    }

    public void run(){
        if(condition.equals(HorseCondition.NOT_HEALTHY)) {
            System.out.println(name + " is Unable to run due to being unhealthy");
            return;
        }


        Timestamp timestamp = Timestamp.from(java.time.Instant.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");
        String formattedTimestamp = timestamp.toLocalDateTime().format(formatter); 
        setSpeed(cycle);
        distance -= speed;
        String formattedDistance;;
        if(distance <= 0){
            distance = 0;
            formattedDistance = String.format("%.2f", distance);
            System.out.println(formattedTimestamp+ "ms " +name + " ran " + speed + " remaining " + formattedDistance);
            System.out.println(warCry);
        } else {
            formattedDistance = String.format("%.2f", distance);
            System.out.println(formattedTimestamp+ "ms " + name + " ran " + speed + " remaining " + formattedDistance);
        }
        cycle++;
    }

    public double getDistance() {
        return distance;
    }
    public int getAge() {
        return age;
    }

    public void setHorseGroup(HorseGroup horseGroup) {
        this.horseGroup = horseGroup;
    }
    public HorseGroup getHorseGroup() {
        return horseGroup;
    }
    public String getName() {
        return name;
    }
    public HorseCondition getCondition() {
        return condition;
    }
    public String getWarCry() {
        return warCry;
    }
    public String getRank() {
        return ""+rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        if(distance <= 0) {
            distance = 0;
        }
        return name + " ran " + speed + " remaining " + distance;
    }
}
