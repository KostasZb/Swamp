package gridTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import grid.Row;
import grid.Square;

public class RowTest {

	@Test
	public void testGetSquare() {
		Row row = new Row(4);
		ArrayList<Square> theRow = new ArrayList<Square>();
		Square square = new Square();
		theRow.add(0, square);
		row.setRow(theRow);
		Square actual = row.getSquare(0);
		Square expected = square;
		assertTrue(actual == expected);
	}

	@Test
	public void testGetRow() {
		Row row = new Row(4);
		ArrayList<Square> theRow = new ArrayList<Square>();
		row.setRow(theRow);
		ArrayList<Square> actual = row.getRow();
		ArrayList<Square> expected = theRow;
		assertTrue(actual == expected);

	}

	@Test
	public void testSetRow() {
		Row row = new Row(4);
		ArrayList<Square> theRow = new ArrayList<Square>();
		row.setRow(theRow);
		ArrayList<Square> actual = row.getRow();
		ArrayList<Square> expected = theRow;
		assertTrue(actual == expected);
	}

}
