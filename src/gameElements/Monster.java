package gameElements;

public abstract class Monster extends GameElement {
	private int SpawnChance = 3;

	private static final long serialVersionUID = -571854027857412024L;

	public Monster() {
		this.attackBehaviour = new PlainAttack();
		this.defenceBehaviour = new PlainDefense();
	}

	public int getSpawnChance() {
		return this.SpawnChance;
	}

}
