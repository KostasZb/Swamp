package gui;

/*
 * The Monsters enum is used in the UI to provide the images for each monster
 */
public enum Monsters {
	SNAKE(), PARROT(), DONKEY();

	public static String[] getAllMonstersNames() {
		String[] monstersNamesList = new String[Monsters.values().length];
		int counter = 0;
		for (Monsters tempMonster : Monsters.values()) {
			monstersNamesList[counter] = tempMonster.toString();
			counter++;
		}
		return monstersNamesList;
	}

	public String imageSource() {
		String imageSource = "";
		switch (this) {
		case SNAKE: {
			imageSource = "SnakeImage.gif";
			break;
		}
		case PARROT: {
			imageSource = "ParrotImage.gif";
			break;
		}
		case DONKEY: {
			imageSource = "DonkeyImage.gif";
			break;
		}
		}
		return imageSource;
	}

}
