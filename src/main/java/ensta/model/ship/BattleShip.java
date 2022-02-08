package ensta.model.ship;

import ensta.util.Orientation;

public class BattleShip extends AbstractShip {
    public BattleShip() {
        super("B", "BattleShip", 4, Orientation.EAST);
    }
    public BattleShip(Orientation orientation) {
        super("B", "BattleShip", 4, orientation);
    }
}
