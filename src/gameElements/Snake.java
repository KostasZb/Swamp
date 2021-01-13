package gameElements;

public class Snake extends Monster {

	private static final long serialVersionUID = -8948880496308039717L;
	private String name = MonsterNames.SNAKE.toString();

	public Snake() {
		this.setName(name);
	}
}
