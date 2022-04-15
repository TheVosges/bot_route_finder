package edu.ib.botRoutePlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
    private int x;
    private int y;
    private int n;
    private String[][] map;
    private List<Product> products;

    public Grid(int x, int y, int n, String[][] map, List<Product> products) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.map = map;
        this.products = products;
    }

    public Grid(String setup) {

        String[] splitter = setup.split("\n");
        String[] params = splitter[0].split(" ");

        //BASIC PARMS
        this.x = Integer.parseInt(params[0]);
        this.y = Integer.parseInt(params[1]);
        this.n = Integer.parseInt(params[2]);

        //MAP OF LETTERS
        this.map = new String[this.y][this.x];
        for (int i = 1; i<=y; i++){
            String line = splitter[i];
            for (int j = 0; j<(x); j++){
                this.map[i-1][j] = String.valueOf(line.charAt(j));
            }
        }

        //LIST OF PRODUCTS
        this.products = new ArrayList<>();
        for (int i = y+1; i<splitter.length; i++){
            String[] data = splitter[i].split(" ");
            Product product = new Product(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]));
            this.products.add(product);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getN() {
        return n;
    }

    public String[][] getMap() {
        return map;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void listProducts(){
        for (Product p : this.products){
            System.out.println(p);
        }
    }

    public void initializeDestinationModule(String setup){
        String[] moduleCords = setup.split("\n")[1].split(" ");
        this.map[Integer.parseInt(moduleCords[0])][Integer.parseInt(moduleCords[1])] = "P";
    }

    public void soutMap() {
        for (String[] s : this.map){
            System.out.println(Arrays.toString(s));
        }
    }
}
