import java.util.Random;

public class Horse {
    
    private String name;
    private String warCry;
    private int age;
    private Condition condition;
    private double speed = 0;
    private HorseGroup horseGroup;
    private int lap = 0;
    private double distance;

    private Condition getRandCondition() {
        Random random = new Random();
        return random.nextBoolean() ? Condition.HEALTHY : Condition.NOT_HEALTHY;
    }
    private int getRandSpeed(int min, int max){
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public Horse(String name, String warCry, int age, double distance) {
        this.name = name;
        this.warCry = warCry;
        this.age = age;
        condition = getRandCondition();
        horseGroup = HorseGroup.DEFAULT;
        lap = 1;
        this.distance = distance;

    }



    public String finished(){
        return warCry;
    }
    private void setSpeed(int generation){
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
        if(condition.equals(Condition.NOT_HEALTHY)) {
            System.out.println(name + " is Unable to run due to being unhealthy");
            return;
        }


        setSpeed(lap);
        distance -= speed;
        if(distance <= 0){
            distance = 0;
            System.out.println(name + " ran " + speed + " remaining " + distance);
            System.out.println(warCry);
        } else {
            System.out.println(name + " ran " + speed + " remaining " + distance);
        }
        lap++;
    }

    public double getDistance(){
        return distance;
    }
    public int getAge(){
        return age;
    }

    public void setHorseGroup(HorseGroup horseGroup){
        this.horseGroup = horseGroup;
    }
    public HorseGroup getHorseGroup(){
        return horseGroup;
    }
    public String getHorseName(){
        return name;
    }
    public Condition getCondition(){
        return condition;
    }
    @Override
    public String toString() {
        if(distance <= 0) {
            distance = 0;
        }
        return name + " ran " + speed + " remaining " + distance;
    }
}
