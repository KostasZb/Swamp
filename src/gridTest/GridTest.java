package gridTest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import grid.Grid;
import grid.Square;

public class GridTest {

	@Test
	public void movingInTheGrid() {
		Grid grid = new Grid(4);
		Square currentSquare = grid.getTheSquare(0, 0);
		ArrayList<Square> newPossibleSquares = new ArrayList<Square>();
		newPossibleSquares.add(grid.getTheSquare(1, 1));
		newPossibleSquares.add(grid.getTheSquare(0, 1));
		newPossibleSquares.add(grid.getTheSquare(1, 0));
		Square newSquare = grid.movingInTheGrid(currentSquare);
		Boolean actual = newPossibleSquares.contains(newSquare);
		Boolean expected = true;
		assertTrue(actual == expected);

	}

	@Test
	public void testGetTheSquare() {
		Grid grid = new Grid(4);
		int positionSquare = 0;
		int positionRow = 0;
		Square square = grid.getGrid().get(positionRow).getRow().get(positionSquare);
		Square actual = grid.getTheSquare(positionSquare, positionRow);
		Square expected = square;
		assertTrue(actual == expected);
	}

}
