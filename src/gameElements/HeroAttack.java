package gameElements;

import java.io.Serializable;

public class HeroAttack implements AttackBehaviour, Serializable {

	private static final long serialVersionUID = -5159058773649314632L;

	@Override
	public int attack(int strength) {
		return strength;
	}

	@Override
	public int attackPoints() {
		return 2;
	}
}
