package ensta.model.ship;

import ensta.util.Orientation;

public class AbstractShip {

    private Orientation orientation;
    private  int length;
    private String name;

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSunk() {
        return false;
    }
}
