package ensta;

import ensta.ai.BattleShipsAI;
import ensta.controller.Game;
import ensta.model.Board;
import ensta.model.Coords;
import ensta.model.Hit;
import ensta.model.ship.*;
import ensta.util.Orientation;

import java.util.Random;

public class Main {

	public static void main(String args[]) {
        //new Game().init().run();

        /* Test Board */
//        Board b = new Board("Test");
//        b.putShip(new BattleShip(), new Coords(3,1));
//        b.putShip(new Submarine(Orientation.SOUTH), new Coords(4,4));
//        b.putShip(new Carrier(Orientation.WEST), new Coords(9,9));
//        b.putShip(new Carrier(), new Coords(2,4));
//        b.putShip(new Submarine(Orientation.EAST), new Coords(7,2));
//        b.print();

//        b.sendHit(4,5);
//        b.sendHit(3,1);
//        b.sendHit(1,1);

//        b.sendHit(4,4);
//        Hit hit = b.sendHit(4, 6);

//        b.print();

//        System.out.println(hit.toString());

        /* Test AI */
        Board board = new Board("Test AI");
        AbstractShip ships[] = new AbstractShip[5];
        ships[0] = new Submarine();
        ships[1] = new Submarine();
        ships[2] = new Carrier();
        ships[3] = new Destroyer();
        ships[4] = new BattleShip();
        BattleShipsAI ai = new BattleShipsAI(board, board);
        ai.putShips(ships);

        int destroyedShipsCount = 0;
        Random random = new Random();
        while (destroyedShipsCount<5) {
                Coords hitCoords = new Coords();
                Hit hit = ai.sendHit(hitCoords);
                System.out.println("Tir en "+hitCoords.toString()+" "+hit.toString());
                board.print();
                if ((hit != Hit.MISS)&&(hit !=Hit.STRIKE))
                        destroyedShipsCount++;
        }
    }

}
