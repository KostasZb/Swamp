package gameElements;

import java.io.Serializable;

public class PlainAttack implements AttackBehaviour, Serializable {

	private static final long serialVersionUID = 5748016097655746250L;

	@Override
	public int attack(int strength) {
		return strength;
	}

	@Override
	public int attackPoints() {
		return 1;
	}
}
