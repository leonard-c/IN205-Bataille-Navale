package ensta.model;

import java.io.Serializable;
import java.util.List;

import ensta.model.ship.AbstractShip;
import ensta.util.Orientation;
import ensta.view.InputHelper;

import ensta.exceptions.WrongEntryException;

public class Player {
	/*
	 * ** Attributs
	 */
	private Board board;
	protected Board opponentBoard;
	private int destroyedCount;
	protected AbstractShip[] ships;
	private boolean lose;

	/*
	 * ** Constructeur
	 */
	public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
		this.setBoard(board);
		this.ships = ships.toArray(new AbstractShip[0]);
		this.opponentBoard = opponentBoard;
	}

	/*
	 * ** Méthodes
	 */

	/**
	 * Read keyboard input to get ships coordinates. Place ships on given
	 * coodrinates.
	 */
	public void putShips() {
		boolean done = false;
		int i = 0;

		do {
			AbstractShip ship = ships[i];
			board.print();
			String msg = String.format("placer %d : %s(%d)", i + 1, ship.getName(), ship.getLength());
			System.out.println(msg);
			InputHelper.ShipInput res = InputHelper.readShipInput();
			try {
				switch (res.orientation) {
					case "north":
						ships[i].setOrientation(Orientation.NORTH);
						break;
					case "south":
						ships[i].setOrientation(Orientation.SOUTH);
						break;
					case "east":
						ships[i].setOrientation(Orientation.EAST);
						break;
					case "west":
						ships[i].setOrientation(Orientation.WEST);
						break;
					default:
						throw new WrongEntryException("Orientation is not valid.");
				}
				Coords inputCoords = new Coords(res.x, res.y);
				if (!inputCoords.isInBoard(board.getSize())) {
					throw new WrongEntryException("Coordinates are out of bounds.");
				}
				boolean shipIsPlaced = board.putShip(ships[i], inputCoords);
				if (shipIsPlaced) {
					++i;
					done = i == 5;
				}
				else {
					throw new WrongEntryException("Ship can't be placed here.");
				}
			}
			catch (WrongEntryException exception) {
				System.err.println(exception.getMessage());
			}
		} while (!done);
	}

	public Hit sendHit(Coords coords) {
		try {
			boolean done = false;
			Hit hit = null;

			do {
				System.out.println("Où frapper?");
				InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
				if ((hitInput.x < 0) || (hitInput.x >= opponentBoard.getSize()) ||
						(hitInput.y < 0) || (hitInput.y >= opponentBoard.getSize())) {
					throw new WrongEntryException("Coordonnées en dehors du plateau.");
				}
				hit = this.opponentBoard.sendHit(new Coords(hitInput.x, hitInput.y));
				if (hit != null)
					done = true;

				coords.setX(hitInput.x);
				coords.setY(hitInput.y);
			} while (!done);

			return hit;
		}
		catch (WrongEntryException e) {
			System.err.println(e.getMessage());
			return sendHit(coords);
		}
	}

	public AbstractShip[] getShips() {
		return ships;
	}

	public void setShips(AbstractShip[] ships) {
		this.ships = ships;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getDestroyedCount() {
		return destroyedCount;
	}

	public void setDestroyedCount(int destroyedCount) {
		this.destroyedCount = destroyedCount;
	}

	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
}
