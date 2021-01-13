package gameElements;

import java.io.Serializable;

public class PlainDefense implements DefenseBehaviour, Serializable {

	private static final long serialVersionUID = -4651043935166739856L;

	@Override
	public int defend(int healthPoints) {
		return healthPoints;
	}

}
