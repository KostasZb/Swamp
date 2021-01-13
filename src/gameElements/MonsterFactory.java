package gameElements;

//using the factory pattern to generate monster instances for the game
public class MonsterFactory {

	public Monster getMonster(String monsterType) {
		if (monsterType == null) {
			return null;
		}
		if (monsterType.equals(MonsterNames.PARROT.toString())) {
			return new Parrot();
		} else if (monsterType.equals(MonsterNames.DONKEY.toString())) {
			return new Donkey();
		} else if (monsterType.equals(MonsterNames.SNAKE.toString())) {
			return new Snake();
		}
		return null;
	}

}
