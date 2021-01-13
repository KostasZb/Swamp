package game;

public interface Playable extends Observable {
	public void startGame();

	public void takingTurns();

	public int getGridSize();

	public int checkInput(String contains, String regex);

	public void changeHeroMood();

	public void serialize();

	public TheGame deserialize();

	public void notifyForAllGrid();
}
