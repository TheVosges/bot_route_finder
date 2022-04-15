package edu.ib.botRoutePlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot {
    private int x;
    private int y;
    private boolean withProductOnBoard;

    private List<int[]> path;

    public Bot(int x, int y) {
        this.x = x;
        this.y = y;
        this.withProductOnBoard = false;
        this.path = new ArrayList<>();
        int[] first = {x, y};
        path.add(first);
    }

    public Bot(String setup) {
        this.x = Integer.parseInt(setup.split("\n")[0].split(" ")[0]);
        this.y = Integer.parseInt(setup.split("\n")[0].split(" ")[1]);
        this.withProductOnBoard = false;
        this.path = new ArrayList<>();
        int[] first = {x, y};
        path.add(first);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<int[]> getPath() {
        return path;
    }

    public boolean isWithProductOnBoard() {
        return withProductOnBoard;
    }

    public void setWithProductOnBoard(boolean withProductOnBoard) {
        this.withProductOnBoard = withProductOnBoard;
    }

    public void soutPath(){
        for(int[] cords  : this.path){
            System.out.println(Arrays.toString(cords));
        }
    }

    public void makeStep(int [] step){
        this.setX(this.getX() + step[0]);
        this.setY(this.getY() + step[1]);
        this.path.add(new int[]{this.getX(), this.getY()});
    }

    @Override
    public String toString() {
        return "Bot{" +
                "x=" + x +
                ", y=" + y +
                ", withProductOnBoard=" + withProductOnBoard +
                ", path=" + path +
                '}';
    }
}
