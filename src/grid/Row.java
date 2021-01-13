package grid;

import java.io.Serializable;
import java.util.ArrayList;

public class Row implements Serializable {
	private static final long serialVersionUID = -3259256508262722116L;
	private ArrayList<Square> row = new ArrayList<Square>();

	public Row(int gridSize) {
		for (int counter = 0; counter < gridSize; counter++) {
			this.row.add(new Square());
		}
	}

	// getting a specific square of the row
	public Square getSquare(int position) {
		return this.row.get(position);
	}

	public ArrayList<Square> getRow() {
		return this.row;
	}

	public void setRow(ArrayList<Square> row) {
		this.row = row;
	}

}
