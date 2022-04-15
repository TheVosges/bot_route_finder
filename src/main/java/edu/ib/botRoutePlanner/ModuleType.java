package edu.ib.botRoutePlanner;

/**
 * Enum that stores data about diffrent modules
 */
public enum ModuleType {
    H (0.5, "3*n+4"), // HIGH SPEED TRANSIT
    B (1, "2*n+2"), // BALANCED
    S (2, "n+1"), // STORAGE ACCESS TIME
    O (0,"0"), //OUT OF SERVICE
    P (0 , "0"); //DOCKING STATION

    private final double time;
    private final String equation;
    ModuleType(double time, String equation) {
        this.time = time;
        this.equation = equation;
    }

    public double getTime() {
        return time;
    }

    public String getEquation() {
        return equation;
    }
}
