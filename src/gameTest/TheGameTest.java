package gameTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import game.TheGame;
import gameElements.AttackBehaviour;
import gameElements.GameElement;
import gameElements.Hero;
import gameElements.HeroAttack;
import gameElements.Monster;
import gameElements.PlainAttack;
import gameElements.Snake;
import grid.Square;

public class TheGameTest {

	@Test
	public void testChangeHeroMood() {
		TheGame game = new TheGame();
		game.heroSpawn();
		AttackBehaviour behaviourStart = new PlainAttack();
		AttackBehaviour behaviourEnd = new HeroAttack();
		game.getHero().setAttackBehaviour(behaviourStart);
		game.changeHeroMood();
		AttackBehaviour actual = game.getHero().getAttackBehaviour();
		AttackBehaviour expected = behaviourEnd;
		assertTrue(actual.getClass().equals(expected.getClass()));

	}

	@Test
	public void testFightit() {
		TheGame game = new TheGame();
		game.heroSpawn();
		GameElement element = new Snake();
		game.addElementsInGame(element, game.findHeroSquare());
		game.fightit();
		Boolean actual = game.getAllTheGameElementsInASquare(game.findHeroSquare()).contains(element);
		Boolean expected = false;
		assertTrue(actual == expected);
	}

	@Test
	public void testFindHeroSquare() {
		TheGame game = new TheGame();
		GameElement element = new Hero();
		Square square = game.getGrid().getTheSquare(0, 0);
		game.addElementsInGame(element, square);
		Square actual = game.findHeroSquare();
		Square expected = square;
		assertTrue(actual == expected);

	}

	@Test
	public void testEnemySpawn() {
		TheGame game = new TheGame();
		do {
			game.enemySpawn();
		} while (game.getAllPositionedElements().size() == 0);
		Square square = game.getGrid().getTheSquare(0, 0);
		GameElement element = game.getAllTheGameElementsInASquare(square).get(0);
		Boolean actual = (element instanceof Monster);
		Boolean expected = true;
		assertTrue(actual == expected);
	}

	@Test
	public void testHeroSpawn() {
		TheGame game = new TheGame();
		game.heroSpawn();
		Square square = game.findHeroSquare();
		GameElement element = game.getAllTheGameElementsInASquare(square).get(0);
		Boolean actual = (element instanceof Hero);
		Boolean expected = true;
		assertTrue(actual == expected);

	}

	@Test
	public void testGetAllTheGameElementsInASquare() {
		TheGame game = new TheGame();
		Square square = game.getGrid().getTheSquare(0, 0);
		GameElement element = new Hero();
		game.addElementsInGame(element, square);
		ArrayList<GameElement> gameElementsInSquare = game.getAllTheGameElementsInASquare(square);
		GameElement actual = gameElementsInSquare.get(0);
		GameElement expected = element;
		assertTrue(actual == expected);
	}

	@Test
	public void getHero() {
		TheGame game = new TheGame();
		GameElement element = new Hero();
		Square square = game.getGrid().getTheSquare(0, 0);
		game.addElementsInGame(element, square);
		GameElement actual = game.getHero();
		GameElement expected = element;
		assertTrue(actual == expected);
	}
}
