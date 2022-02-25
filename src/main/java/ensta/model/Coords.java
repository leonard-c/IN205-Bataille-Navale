package ensta.model;

import java.util.Random;

public class Coords {
    private int x;
    private int y;

    public Coords() {
        x = 0;
        y = 0;
    }
    public Coords(Coords coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coords randomCoords(int size) {
        Random random = new Random();
        return new Coords(random.nextInt(size), random.nextInt(size));
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

    public void setCoords(Coords coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }

    public boolean isInBoard(int size) {
        return ((x>=0)&&(x<size)&&(y>=0)&&(y<size));
    }

    public String toString() {
        return "("+x+","+y+")";
    }
}
