package grid;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable {
	private static final long serialVersionUID = -4935721915773805739L;
	private ArrayList<Row> grid = new ArrayList<Row>();

	public Grid(int gridSize) {
		for (int counter = 0; counter < gridSize; counter++) {
			this.grid.add(new Row(gridSize));
		}
	}

	// Using the square that an element is currently at and the pace of the
	// element's pace to find a a new square for the element to be moved to
	public Square move(Square square, int pace) {
		Square newSquare = square;
		for (int counter = 0; counter < pace; counter++) {
			newSquare = this.movingInTheGrid(newSquare);
		}
		return newSquare;
	}

	/*
	 * Using the Moves enumerator to pick and return a random square based a Square
	 * input. An exception is thrown if the new square is outside the borders of the
	 * grid and a recursion is done in order to find a new square that is inside the
	 * grid
	 */
	public Square movingInTheGrid(Square square) {
		Square newSquare = new Square();
		int currentRow = 0;
		int currentSquare = 0;
		for (Row tempRow : this.grid) {
			for (Square tempSquare : tempRow.getRow()) {
				if (tempSquare == square) {
					currentRow = this.grid.indexOf(tempRow);
					currentSquare = tempRow.getRow().indexOf(tempSquare);
					Moves move = Moves.randomMove();
					switch (move) {
					case UP:
						currentRow++;
						break;
					case DOWN:
						currentRow--;
						break;
					case RIGHT:
						currentSquare++;
						break;
					case LEFT:
						currentSquare--;

						break;
					case DOWNLEFT:
						currentRow--;
						currentSquare--;
						break;
					case DOWNRIGHT:
						currentRow--;
						currentSquare++;
						break;
					case UPLEFT:
						currentRow++;
						currentSquare--;
						break;
					case UPRIGHT:
						currentRow++;
						currentSquare++;
						break;
					}
				}
			}
		}

		try {
			newSquare = getTheSquare(currentSquare, currentRow);
		} catch (Exception e) {
			newSquare = movingInTheGrid(square);

		}
		return newSquare;
	}

	// Getting a square from the grid by specifying its square and row numbers
	public Square getTheSquare(int positionSquare, int positionRow) {
		return this.grid.get(positionRow).getSquare(positionSquare);
	}

	public ArrayList<Row> getGrid() {
		return this.grid;
	}

	public void setGrid(ArrayList<Row> grid) {
		this.grid = grid;
	}

}
