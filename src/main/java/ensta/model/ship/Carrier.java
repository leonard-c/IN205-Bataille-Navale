package ensta.model.ship;

import ensta.util.Orientation;

public class Carrier extends AbstractShip {
    public  Carrier() {
        super('C', "Aircraft-Carrier", 5, Orientation.EAST);
    }
    public Carrier(Orientation orientation) {
        super('C', "Aircraft-Carrier", 5, orientation);
    }
}
