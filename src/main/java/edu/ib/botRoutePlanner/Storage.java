package edu.ib.botRoutePlanner;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

//import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    private Grid grid;
    private Bot bot;
    private Job currentJob;

    public Storage(Grid grid, Bot bot, Job currentJob) {
        this.grid = grid;
        this.bot = bot;
        this.currentJob = currentJob;
    }

    public Storage(Grid grid, Bot bot) {
        this.grid = grid;
        this.bot = bot;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public Product findNearestProduct(){
        Product nearestProduct = null;
        Double minDistance = null;

        for (Product p : this.grid.getProducts()){
            if (p.getName().equals(this.currentJob.getName())){
                //Calculating shortest distance
                Double distance = Math.sqrt(Math.pow(p.getX() - this.bot.getX(),2) + Math.pow(p.getY() - this.bot.getY(),2));
                if (minDistance == null){
                    nearestProduct = p;
                    minDistance = distance;
                }
                if (minDistance > distance){
                    nearestProduct = p;
                    minDistance = distance;
                }
            }
        }
        try {
            return nearestProduct;
        } catch (Exception e) {
            System.out.println("There's no product in storage with id: " + this.currentJob.getName());
            return null;
        }
    }

    public double calculateTimeOfStep(int[] step){
        double time;
        ModuleType from = ModuleType.valueOf(this.grid.getMap()[this.bot.getY()][this.bot.getX()]);
        ModuleType to = ModuleType.valueOf(this.grid.getMap()[this.bot.getY() + step[1]][this.bot.getX() + step[0]]);
        Double timeFrom = from.getTime();
        Double timeTo = to.getTime();
        time = Double.max(timeFrom,timeTo);
        return time;
    }

    public void getProductFromModule(Product product){
        ModuleType currentModule = ModuleType.valueOf(this.grid.getMap()[this.bot.getY()][this.bot.getX()]);
        String mathEqu = currentModule.getEquation();
        Argument n = new Argument("n = " + product.getN());
        Expression e = new Expression(mathEqu, n);
        double time = e.calculate();
        this.getCurrentJob().addTime(time);
    }

    public Point findNearestStation() {
        Point nearestStation = null;
        Double minDistance = null;

        for (int y = 0; y < this.grid.getY(); y += 1) {
            for (int x = 0; x < this.grid.getX(); x += 1) {
                if (this.grid.getMap()[y][x].equals("P")) {
                    Double distance = Math.sqrt(Math.pow(x - this.bot.getX(), 2) + Math.pow(y - this.bot.getY(), 2));
                    if (minDistance == null) {
                        nearestStation = new Point(x, y);
                        minDistance = distance;
                    }
                }
            }
        }
        try {
//            System.out.println(nearestStation.toString());
            return nearestStation;
        } catch (Exception e) {
            System.out.println("There's no station!");
            return null;
        }

    }

    public List<int[]> findRouteToStation(Point point){

        //Initializing walkable map
        int[][] walkable = new int[this.grid.getY()][this.grid.getX()];
        for (int y = 0; y<this.grid.getY(); y+=1){
            for(int x = 0; x<this.grid.getX(); x+=1){
                if (!this.grid.getMap()[y][x].equals("O"))
                    walkable[y][x] = 1;
                else walkable[y][x] = 0;
            }
        }

//        for(int i = 0; i< walkable.length; i+=1){
//            System.out.println(Arrays.toString(walkable[i]));
//        }


        // pathfinding package - https://github.com/xaguzman/pathfinding
        GridCell[][] cells = new GridCell[walkable[0].length+2][walkable.length+2];

        for (int y = 0; y < walkable.length+1; y+=1)
            for (int x = 0; x < walkable[0].length+1; x+=1){
                boolean isWalkable = true;
                try{
                    isWalkable = walkable[y][x]>0;

                } catch (Exception e){
                    isWalkable = false;
                }
                GridCell cell = new GridCell(x, y, isWalkable);
                cells[cell.x][cell.y] = cell;

            }

        NavigationGrid<GridCell> navigationGrid = new NavigationGrid(cells);

        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = false;

        AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class, opt);

        List<GridCell> pathToEnd = finder.findPath(this.bot.getX(), this.bot.getY(), point.x, point.y, navigationGrid);
        List<int[]> output = new ArrayList<>();

        for (int i = 0; i< pathToEnd.size(); i+=1){
            output.add(new int[]{pathToEnd.get(i).x, pathToEnd.get(i).y});
        }

//        for (int i = 0; i< output.size(); i+=1){
//            System.out.println(Arrays.toString(output.get(i)));
//        }
//
//        System.out.println("----------------");

        return output;
    }

    public void makeStepWithBot(List<int[]> path){
        for (int[] step : path){
            int[] thisStep = {step[0] - this.bot.getX(), step[1] - this.bot.getY()};
            this.getCurrentJob().addTime(calculateTimeOfStep(thisStep));
            this.bot.makeStep(thisStep);
        }
    }
}
