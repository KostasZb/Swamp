package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

//creating a custom Jpanel 
public class CustomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3877011740913495303L;
	private int rowNumber;
	private int squareNumber;

	public CustomPanel(int rowNumber, int SquareNumber) {
		this.rowNumber = rowNumber;
		this.squareNumber = SquareNumber;
		this.setLayout(new GridLayout());
		this.setOpaque(false);
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
