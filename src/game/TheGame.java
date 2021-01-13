package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import gameElements.*;
import grid.*;

public class TheGame implements Serializable, Playable {

	private static final long serialVersionUID = -8715265812368193133L;
	private boolean gameOver = false;
	private int gridSize = 4;
	private Grid grid = new Grid(gridSize);
	private static MonsterFactory monsterFactory = new MonsterFactory();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private HashMap<GameElement, Square> gameElementsPositioned = new HashMap<GameElement, Square>();

	// Constructor for the game
	public TheGame() {

	}

	// Creating the hero and positioning it on the grid, then notifying the
	// observers
	@Override
	public void startGame() {
		this.heroSpawn();
		this.notifyForAllGrid();
	}

	/*
	 * Taking turns by moving all the in-game elements in the grid,then trying to
	 * spawn a new enemy and finally resolving the fights that might occur. The
	 * observers are notified after each action to update the UI. A Lamda expression
	 * is used to select and move all the game elements currently in-game.
	 */
	@Override
	public void takingTurns() {
		if (this.gameOver == false) {

			gameElementsPositioned.forEach((gameElement, square) -> {
				Square newSquare = this.grid.move(square, gameElement.getPace());
				gameElementsPositioned.replace(gameElement, square, newSquare);
				Notification notification = new TextNotification(gameElement.getName() + " is moving");
				this.notifyObserver(notification);
				this.notifyForAllGrid();
			});
			this.enemySpawn();
			this.notifyForAllGrid();
			this.fightit();
			this.notifyForAllGrid();
			;
		} else {
			Notification notification = new TextNotification(
					"The Hero has died, please start a new game or load the last saved game");
			this.notifyObserver(notification);
		}

	}

	/*
	 * Using the implemented strategy design pattern to change the hero's mood by
	 * changing its attack behaviour. A stream together with a lamda expression are
	 * used to filter all the game elements that are currently in the same square as
	 * the hero, and pick the hero instance.
	 */
	@Override
	public void changeHeroMood() {
		Notification notification;
		GameElement hero = this.getHero();
		if (hero.getAttackBehaviour() instanceof HeroAttack) {
			hero.setAttackBehaviour(new PlainAttack());
			notification = new TextNotification("The Hero has calmed down");
		} else {
			hero.setAttackBehaviour(new HeroAttack());
			notification = new TextNotification("The Hero has gotten grumpy");
		}
		this.notifyObserver(notification);
	}

	/*
	 * Getting all game elements currently in the same square as the hero, ensuring
	 * that the hero is the first one to attack and then putting all the game
	 * elements in the square to take turns in attacking. Finally removing the
	 * objects that have 0 or less health points and turning this game's game over
	 * to true if the hero has died. Streams with a lamda expression are used in
	 * order to check if the hero has survived the fight sequence.
	 */

	public void fightit() {
		ArrayList<GameElement> heroSquareGameElements = this.getAllTheGameElementsInASquare(this.findHeroSquare());
		// Ensuring that the hero is going to be the first to attack
		if (heroSquareGameElements.get(0) instanceof Hero == false) {
			Collections.swap(heroSquareGameElements, 0, heroSquareGameElements.indexOf(this.getHero()));
		}
		// going through all the elements in the hero's square and having each one of
		// them attack the rest of the game elements.
		for (int counter = 0; counter < heroSquareGameElements.size(); counter++) {
			GameElement attacker = heroSquareGameElements.get(counter);
			if (attacker.getHealthPoints() > 0) {
				int attackPoints = attacker.getAttackPoints();
				for (int counterTwo = 0; counterTwo < heroSquareGameElements.size(); counterTwo++) {
					GameElement defender = heroSquareGameElements.get(counterTwo);
					if (defender.equals(attacker) == false && defender.getClass() != attacker.getClass()) {
						while (attackPoints > 0 && defender.getHealthPoints() > 0) {
							Notification notification = new TextNotification(
									attacker.getName() + " is attacking " + defender.getName());
							this.notifyObserver(notification);
							defender.setHealthPoints(defender.defend() - attacker.attack());
							attackPoints--;
						}
						;
						if (attackPoints <= 0) {
							break;
						}
					}
				}
			}
		}
		// Removing the game elements with zero health from the game
		for (GameElement element : heroSquareGameElements) {
			if (element.getHealthPoints() <= 0) {
				this.gameElementsPositioned.remove(element);
			}
		}
		// checking if the hero survived the fights
		if (this.gameElementsPositioned.keySet().stream().anyMatch(o -> (o instanceof Hero)) == false) {
			this.gameOver = true;
			Notification notification = new TextNotification("The hero has been killed");
			this.notifyObserver(notification);
		}
	}

	// Using a stream and a lamda expression to get an arraylist of game elements
	// that are currently in a specific square
	public ArrayList<GameElement> getAllTheGameElementsInASquare(Square square) {
		ArrayList<GameElement> squareGameElements = new ArrayList<GameElement>();
		this.gameElementsPositioned.entrySet().stream().filter((entry) -> (entry.getValue() == square))
				.forEach(entry -> squareGameElements.add(entry.getKey()));
		return squareGameElements;
	}

	// Using a stream and lamda expression to get the hero game element
	public GameElement getHero() {
		return this.gameElementsPositioned.entrySet().stream().filter((entry) -> (entry.getKey() instanceof Hero))
				.findAny().get().getKey();
	}

	// Finding the Square currently occupied by the hero
	public Square findHeroSquare() {
		Square heroSquare = this.gameElementsPositioned.get(this.getHero());
		return heroSquare;
	}

	/*
	 * Deciding if a new enemy will be created and if yes, placing it on the grid
	 * The random monster is created by getting one random entry of the enumerator
	 * Monsters and using it with the monsterFactory class to get a Monster instance
	 * The random monster enumerator used here can also be used in the user
	 * interface if needed
	 */

	public void enemySpawn() {
		Monster newMonster = monsterFactory.getMonster(MonsterNames.randomMonster().toString());
		Random random = new Random();
		int chance = random.nextInt(newMonster.getSpawnChance());
		if (chance == 0) {
			Square squareToSpawn = this.grid.getTheSquare(0, 0);
			this.gameElementsPositioned.put(newMonster, squareToSpawn);
			Notification notification = new TextNotification("A " + newMonster.getName() + " has entered the swamp!");
			this.notifyObserver(notification);
		}
	}

	// Creating the Hero and placing him on the grid, on a random square.
	public void heroSpawn() {
		GameElement hero = new Hero();
		Random random = new Random();
		int randomSquare = 0;
		int randomRow = 0;
		do {
			randomSquare = random.nextInt(this.grid.getGrid().size());
			randomRow = random.nextInt(this.grid.getGrid().size());
		} while (randomSquare == 0 && randomRow == 0);
		Square squareToSpawn = this.grid.getTheSquare(randomSquare, randomRow);
		this.gameElementsPositioned.put(hero, squareToSpawn);

	}

	/*
	 * Observable interface implementation, adding all the needed observers to be
	 * used to display the details, then either sending text notifications or
	 * notifications of the coordinates of each square present in the grid used for
	 * the game and a string of the names of each game element present in the
	 * specific square. The latter notifications are used in conjunction with the
	 * checkInput function to count the times a game element's name appears in the
	 * given string. The idea is to have one for loop that translates the contents
	 * of the string sent.
	 */
	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObserver(Notification notification) {
		for (Observer observer : this.observers) {
			observer.update(notification);
		}
	}

	// Creating a new notification for all the squares of the grid and notifying the
	// observers
	public void notifyForAllGrid() {
		for (Row row : this.grid.getGrid()) {
			for (Square square : row.getRow()) {
				String contains = "";
				for (GameElement element : this.getAllTheGameElementsInASquare(square)) {
					contains = contains + element.getName().toString();
				}
				Notification notification = new GridNotification(this.grid.getGrid().indexOf(row),
						row.getRow().indexOf(square), contains);
				this.notifyObserver(notification);
			}
		}
	}

	/*
	 * Using regular expressions to count the occurrences of a String in the string
	 * of the contents of a square
	 */
	public int checkInput(String contains, String regex) {

		int counter = 0;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(contains);
		while (matcher.find()) {
			counter++;
		}
		return counter;
	}

	// Serializing the game
	@Override
	public void serialize() {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream("game.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Deserializing a saved game
	@Override
	public TheGame deserialize() {
		TheGame game = new TheGame();
		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream("game.ser");
			ois = new ObjectInputStream(fis);
			game = (TheGame) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return game;
	}

	public int getGridSize() {
		return this.gridSize;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public void addElementsInGame(GameElement gameElement, Square square) {
		this.gameElementsPositioned.put(gameElement, square);
	}

	public HashMap<GameElement, Square> getAllPositionedElements() {
		return this.gameElementsPositioned;
	}
}
