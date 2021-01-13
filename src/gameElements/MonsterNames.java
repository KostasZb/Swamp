package gameElements;

import java.util.Random;

/*
 * The Monsters enum is used for providing the monster names both upon their instantiation and when deciding which type of monster will be created(with the monsterFactory)
 */
public enum MonsterNames {
	SNAKE(), PARROT(), DONKEY();

	public static MonsterNames randomMonster() {
		MonsterNames[] possibleMonsters = MonsterNames.values();
		Random random = new Random();
		int randomIndex = random.nextInt(possibleMonsters.length);
		MonsterNames monster = possibleMonsters[randomIndex];
		return monster;
	}

}
