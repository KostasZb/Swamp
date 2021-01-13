package gameElements;

import java.io.Serializable;

public class Hero extends GameElement implements Serializable {

	private static final long serialVersionUID = 2719918059198531252L;

	public Hero() {
		this.setName("HERO");
		this.attackBehaviour = new PlainAttack();
		this.defenceBehaviour = new PlainDefense();
	}
}
