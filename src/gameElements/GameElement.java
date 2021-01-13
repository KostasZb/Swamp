package gameElements;

import java.io.Serializable;

public abstract class GameElement implements Serializable {

	private static final long serialVersionUID = 7556219819482867239L;
	private int healthPoints = 1;
	private int strength = 1;
	private int pace = 1;
	private String name;
	protected AttackBehaviour attackBehaviour;
	protected DefenseBehaviour defenceBehaviour;

	// Allowing a game element to defend by using their defence behaviour
	public int defend() {
		return this.defenceBehaviour.defend(this.healthPoints);
	}

	// Allowing a game element to attack by using their attack behaviour
	public int attack() {
		return this.attackBehaviour.attack(this.strength);
	}

	// Getting a game element's attack points , based on their attack behaviour
	public int getAttackPoints() {
		return this.attackBehaviour.attackPoints();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getPace() {
		return this.pace;
	}

	public void setPace(int pace) {
		this.pace = pace;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getHealthPoints() {
		return this.healthPoints;
	}

	// Getters and setters for the attack and defence behaviour that are used
	// according to the strategy pattern
	public void setAttackBehaviour(AttackBehaviour attackBehaviour) {
		this.attackBehaviour = attackBehaviour;
	}

	public AttackBehaviour getAttackBehaviour() {
		return this.attackBehaviour;
	}

	public DefenseBehaviour getDefenceBehaviour() {
		return this.defenceBehaviour;
	}

	public void setDefenceBehaviour(DefenseBehaviour defenceBehaviour) {
		this.defenceBehaviour = defenceBehaviour;
	}

}
