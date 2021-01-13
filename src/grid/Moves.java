package grid;

import java.util.Random;

/*
 * The Moves enum is used to provide the possible directions that an element can move in the grid.
 * An enumerator was used since the the directions that an element of the grid can move to, are known in advance 
 */
public enum Moves {
	LEFT, RIGHT, UP, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT;

	// Picking a random value from the Enumerator
	public static Moves randomMove() {

		Moves[] possibleMoves = Moves.values();
		Random random = new Random();
		int randomIndex = random.nextInt(possibleMoves.length);
		Moves move = possibleMoves[randomIndex];
		return move;
	}

}
