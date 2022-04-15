package edu.ib.botRoutePlanner;

/**
 * Class that represent one job - its product name and how much time it took to complete
 */
public class Job {
    private final String name;
    private double time;

    public Job(String setup) {
        this.name = setup.split("\n")[2];
        this.time = 0;
    }


    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Method that adds time to current variable of this.time
     * @param timeOfStep
     */
    public void addTime(double timeOfStep){
        this.time = this.time + timeOfStep;
    }
}
