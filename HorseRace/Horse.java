import java.util.Random;

public class Horse implements Runnable {
    enum CONDITION {
        HEALTHY,
        NOT_HEALTHY
    }
    private String name;
    private String warCry;
    private int age;
    private String condition;
    private double speed;



    private String getRandCondition() {
        Random random = new Random();
        return random.nextBoolean() ? CONDITION.HEALTHY.toString() : CONDITION.NOT_HEALTHY.toString();
    }
    private int getRandSpeed(int min, int max){
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public Horse(String name, String warCry, int age) {
        this.name = name;
        this.warCry = warCry;
        this.age = age;
        condition = getRandCondition();
    }



    public String finished(){
        return warCry;
    }
    public void setSpeed(int generation){
        HorseGroup horseGroup = null;
        try {
            horseGroup = HorseGroup.valueOf(condition);
        } catch (Exception e) {
            System.out.println("something went wrong horse group went to default");
            horseGroup = HorseGroup.BEGINNER;
        }

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
                
                break;
            default:
                System.out.println("Something went wrong there shouldn't be a default");
                break;
        }
    }

    public void run(){
        if(condition.equals(CONDITION.NOT_HEALTHY.toString())) {
            System.out.println("Unable to run due to being unhealthy");
            return;
        }
    }

    
    @Override
    public String toString() {
        return "name: " + name + " warcry: " + warCry + " age: " + age + " condition: " + condition + " speed: " + speed;
    }
}
