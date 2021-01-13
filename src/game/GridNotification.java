package game;

public class GridNotification extends Notification {
	int rowNumber;
	int squareNumber;

	public GridNotification(int rowNumber, int squareNumber, String contains) {
		this.setContains(contains);
		this.setRowNumber(rowNumber);
		this.setSquareNumber(squareNumber);
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getSquareNumber() {
		return squareNumber;
	}

	public void setSquareNumber(int squareNumber) {
		this.squareNumber = squareNumber;
	}

}
