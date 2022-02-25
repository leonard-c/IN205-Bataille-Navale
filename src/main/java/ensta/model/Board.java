package ensta.model;

import ensta.exceptions.WrongEntryException;
import ensta.model.ship.AbstractShip;
import ensta.model.ship.ShipState;
import ensta.util.ColorUtil;
import ensta.util.Orientation;

public class Board implements IBoard {

	private static final int DEFAULT_SIZE = 10;

	private String name;
	private int size;
	private ShipState[][] ships;
	private Boolean[][] strikes;
	private boolean[][] alreadyStruck;
	
	public Board(String name, int size) {
		this.name = name;
		this.size = size;
		this.ships = new ShipState[size][size];
		this.strikes = new Boolean[size][size];
		this.alreadyStruck = new boolean[size][size];
	}
	public Board(String name){
		this.name = name;
		this.size = DEFAULT_SIZE;
		this.ships = new ShipState[DEFAULT_SIZE][DEFAULT_SIZE];
		this.strikes = new Boolean[DEFAULT_SIZE][DEFAULT_SIZE];
		this.alreadyStruck = new boolean[DEFAULT_SIZE][DEFAULT_SIZE];
	}

	public void print() {
		// Print labels
		StringBuilder Spaces = new StringBuilder();
		for (int i=0; i<2*size-8+3+4; i++) Spaces.append(" ");
		System.out.println("Navires:"+Spaces+"Frappes:");
		StringBuilder xLabel = new StringBuilder();
		String [] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
									"S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (int i=0; i<size; i++) xLabel.append(alphabet[i]).append(" ");
		System.out.println("   "+xLabel+"    "+"   "+xLabel);
		//Print grid
		for (int i=0; i<size; i++)
		{
			StringBuilder line= new StringBuilder(Integer.toString(i + 1) + " ");
			if (i<9) line.append(" ");
			for (int j=0; j<size; j++) {
				if (ships[i][j] == null)
					line.append(". ");
				else
					line.append(ships[i][j].toString()).append(" ");
			}
			line.append("    ");
			line.append(Integer.toString(i + 1)).append(" ");
			if (i<9) line.append(" ");
			for (int j=0; j<size; j++) {
				if (strikes[i][j] == null)
					line.append(". ");
				else if (strikes[i][j])
					line.append(ColorUtil.colorize("X ", ColorUtil.Color.RED));
				else
					line.append("X ");
			}
			System.out.println(line);
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean putShip(AbstractShip ship, Coords coords) {
		if (coords.isInBoard(size)) {
			if (canPutShip(ship, coords)) {
				if (ship.getOrientation()==Orientation.EAST) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY()][coords.getX() + i] = new ShipState(ship);
					}
				}
				else if (ship.getOrientation()==Orientation.WEST) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY()][coords.getX() - i] = new ShipState(ship);
					}
				}
				else if (ship.getOrientation()==Orientation.SOUTH) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY() + i][coords.getX()] = new ShipState(ship);
					}
				}
				else if (ship.getOrientation()==Orientation.NORTH) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY() - i][coords.getX()] = new ShipState(ship);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasShip(Coords coords) {
		return ships[coords.getY()][coords.getX()]!=null;
	}

	@Override
	public void setHit(boolean hit, Coords coords) {
		strikes[coords.getY()][coords.getX()]=hit;
	}

	@Override
	public Boolean getHit(Coords coords) {
		return strikes[coords.getY()][coords.getX()];
	}

	@Override
	public Hit sendHit(Coords res) {
		try {
			if (alreadyStruck[res.getY()][res.getX()]) {
				throw new WrongEntryException("Case déjà visée, tirez sur une nouvelle");
			}
			alreadyStruck[res.getY()][res.getX()] = true;
			if (ships[res.getY()][res.getX()] == null) {
				return Hit.MISS;
			}
			ships[res.getY()][res.getX()].addStrike();
			if (ships[res.getY()][res.getX()].isSunk()) {
				switch (ships[res.getY()][res.getX()].getShip().getLabel()) {
					case 'C':
						return Hit.CARRIER;
					case 'D':
						return Hit.DESTROYER;
					case 'S':
						return Hit.SUBMARINE;
					case 'B':
						return Hit.BATTLESHIP;
				}
			}
			return Hit.STRIKE;
		}
		catch (WrongEntryException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Hit sendHit(int x, int y) {
		return sendHit(new Coords(x,y));
	}

	public boolean canPutShip(AbstractShip ship, Coords coords) {
		Orientation o = ship.getOrientation();
		int dx = 0, dy = 0;
		if (o == Orientation.EAST) {
			if (coords.getX() + ship.getLength() > this.size) {
				return false;
			}
			dx = 1;
		} else if (o == Orientation.SOUTH) {
			if (coords.getY() + ship.getLength() > this.size) {
				return false;
			}
			dy = 1;
		} else if (o == Orientation.NORTH) {
			if (coords.getY() + 1 - ship.getLength() < 0) {
				return false;
			}
			dy = -1;
		} else if (o == Orientation.WEST) {
			if (coords.getX() + 1 - ship.getLength() < 0) {
				return false;
			}
			dx = -1;
		}

		Coords iCoords = new Coords(coords);

		for (int i = 0; i < ship.getLength(); ++i) {
			if (this.hasShip(iCoords)) {
				return false;
			}
			iCoords.setX(iCoords.getX() + dx);
			iCoords.setY(iCoords.getY() + dy);
		}

		return true;
	}
}
