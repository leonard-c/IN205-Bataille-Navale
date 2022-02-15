package ensta;

import ensta.controller.Game;
import ensta.model.Board;
import ensta.model.Coords;
import ensta.model.ship.BattleShip;
import ensta.model.ship.Submarine;
import ensta.model.ship.Carrier;
import ensta.util.Orientation;

public class Main {

	public static void main(String args[]) {
        //new Game().init().run();
        Board b = new Board("Test");
        b.putShip(new BattleShip(), new Coords(3,1));
        b.putShip(new Submarine(Orientation.SOUTH), new Coords(4,4));
        b.putShip(new Carrier(Orientation.WEST), new Coords(9,9));
        b.putShip(new Carrier(), new Coords(2,4));
        b.putShip(new Submarine(Orientation.EAST), new Coords(7,2));
        b.print();
    }

}
