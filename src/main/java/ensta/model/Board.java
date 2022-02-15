package ensta.model;

import ensta.model.ship.AbstractShip;
import ensta.util.Orientation;

public class Board implements IBoard {

	private static final int DEFAULT_SIZE = 10;

	private String name;
	private int size;
	private char[][] ships;
	private boolean[][] strikes;
	
	public Board(String name, int size) {
		this.name = name;
		this.size = size;
		this.ships = new char[size][size];
		this.strikes = new boolean[size][size];
	}
	public Board(String name){
		this.name = name;
		this.size = DEFAULT_SIZE;
		this.ships = new char[DEFAULT_SIZE][DEFAULT_SIZE];
		this.strikes = new boolean[DEFAULT_SIZE][DEFAULT_SIZE];
	}

	public void print() {
		// Print labels
		String Spaces = "";
		for (int i=0; i<2*size-8+3+4; i++) Spaces += " ";
		System.out.println("Navires:"+Spaces+"Frappes:");
		String xLabel = "";
		String [] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
									"S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (int i=0; i<size; i++) xLabel+=alphabet[i]+" ";
		System.out.println("   "+xLabel+"    "+"   "+xLabel);
		//Print grid
		for (int i=0; i<size; i++)
		{
			String line=Integer.toString(i+1)+" ";
			if (i<9) line+=" ";
			for (int j=0; j<size; j++) {
				if (ships[i][j] == '\u0000')
					line += ". ";
				else
					line += ships[i][j]+" ";
			}
			line += "    ";
			line += Integer.toString(i+1)+" ";
			if (i<9) line+=" ";
			for (int j=0; j<size; j++)
				if (strikes[i][j]==false)
					line += ". ";
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
						ships[coords.getY()][coords.getX() + i] = ship.getLabel();
					}
				}
				else if (ship.getOrientation()==Orientation.WEST) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY()][coords.getX() - i] = ship.getLabel();
					}
				}
				else if (ship.getOrientation()==Orientation.SOUTH) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY() + i][coords.getX()] = ship.getLabel();
					}
				}
				else if (ship.getOrientation()==Orientation.NORTH) {
					for (int i=0; i<ship.getLength(); i++) {
						ships[coords.getY() - i][coords.getX()] = ship.getLabel();
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasShip(Coords coords) {
		return ships[coords.getY()][coords.getX()]!='\u0000';
	}

	@Override
	public void setHit(boolean hit, Coords coords) {

	}

	@Override
	public Boolean getHit(Coords coords) {
		return null;
	}

	@Override
	public Hit sendHit(Coords res) {
		return null;
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
