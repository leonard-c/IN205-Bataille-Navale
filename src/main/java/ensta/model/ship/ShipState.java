package ensta.model.ship;

import ensta.exceptions.InvalidInstructionException;
import ensta.util.ColorUtil;

public class ShipState {
    private AbstractShip ship;
    private boolean struck;

    public ShipState(AbstractShip ship) {
        this.ship = ship;
        struck = false;
    }

    public void addStrike() {
        try {
            if (struck)
                throw new InvalidInstructionException("This cell has already been struck.");
            ship.addStrike();
            struck = true;
        } catch (InvalidInstructionException e) {
            e.printStackTrace();
        }
    }

    public AbstractShip getShip() {
        return this.ship;
    }

    public boolean isStruck() {
        return struck;
    }
    public boolean isSunk() {
        return ship.isSunk();
    }

    public String toString() {
        if (struck)
            return ColorUtil.colorize(""+ship.getLabel(), ColorUtil.Color.RED);
        else
            return ""+ship.getLabel();
    }
}
