package edu.ib.botRoutePlanner;

public class Product {
    private String name;
    private int x;
    private int y;
    private int n;

    public Product(String name, int x, int y, int n) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", n=" + n +
                '}';
    }
}
