package ensta.model.ship;

import ensta.model.Coords;
import ensta.util.Orientation;

public abstract class AbstractShip {

    private char label;
    private String name;
    private int length;
    private Orientation orientation;
    private int strikeCount;

    public AbstractShip(char label, String name, int length, Orientation orientation) {
        this.label = label;
        this.name = name;
        this.length = length;
        this.orientation = orientation;
        this.strikeCount = 0;
    }


    public char getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isInBoard(Coords coords, int size) {
        return (coords.isInBoard(size))&&(
                ((orientation==Orientation.EAST)&&(coords.getX()+length<size))||
                        ((orientation==Orientation.WEST)&&(coords.getX()-length>=0))||
                        ((orientation==Orientation.SOUTH)&&(coords.getY()+length<size))||
                        ((orientation==Orientation.NORTH)&&(coords.getY()-length>=0))
        );
    }

    public void addStrike() {
        if (strikeCount<this.length) {
            this.strikeCount++;
        }
    }
    public boolean isSunk() {
        return this.strikeCount==this.length;
    }

    public int getStrikeCount() {
        return strikeCount;
    }
}
