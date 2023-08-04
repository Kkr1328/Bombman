package drawing;

import java.util.ArrayList;

import entity.Map;
import entity.Monster;
import entity.Player;
import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GameScreen extends Canvas {
	private Map map = new Map();
	private static Player player;
	private static ArrayList<Monster> monsterList = new ArrayList<Monster>();
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	// for level 5
	private Monster monster4;
	private Monster monster5;

	public GameScreen(double width, double height) {
		super(width, height);
		map = new Map();
		addListener();
		player = new Player(33, 33);
		monsterList = new ArrayList<Monster>();
		initializeMonsters();
	}

	public void initializeMonsters() {
		if (player.getLevel() == 1) {
			monster1 = new Monster(1);
			monster2 = new Monster(1);
			monster3 = new Monster(1);
		} else if (player.getLevel() == 2) {
			monster1 = new Monster(1);
			monster2 = new Monster(1);
			monster3 = new Monster(2);
		} else if (player.getLevel() == 3) {
			monster1 = new Monster(1);
			monster2 = new Monster(2);
			monster3 = new Monster(2);
		} else if (player.getLevel() == 4) {
			monster1 = new Monster(1);
			monster2 = new Monster(2);
			monster3 = new Monster(3);
		} else {
			monster1 = new Monster(1);
			monster2 = new Monster(2);
			monster3 = new Monster(3);
			monster4 = new Monster(3);
			monster5 = new Monster(3);
			monsterList.add(monster4);
			monsterList.add(monster5);
		}
		monsterList.add(monster1);
		monsterList.add(monster2);
		monsterList.add(monster3);
	}

	public void addListener() {
		this.setOnKeyPressed((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), true);
		});
		this.setOnKeyReleased((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}

	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		if (player.isNextLevel() && !player.isLastLevel()) {
			map = new Map();
			InputUtility.setKeyPressed(KeyCode.W, false);
			InputUtility.setKeyPressed(KeyCode.A, false);
			InputUtility.setKeyPressed(KeyCode.S, false);
			InputUtility.setKeyPressed(KeyCode.D, false);
			player.setNextLevel(false);
			String name = player.getPlayerName();
			int level = player.getLevel();
			player = new Player(33, 33);
			player.setPlayerName(name);
			player.setLevel(level + 1);
			player.setNextLevel(false);
			monsterList = new ArrayList<Monster>();
			initializeMonsters();
		}
		if (player.isNewGame()) {
			InputUtility.setKeyPressed(KeyCode.W, false);
			InputUtility.setKeyPressed(KeyCode.A, false);
			InputUtility.setKeyPressed(KeyCode.S, false);
			InputUtility.setKeyPressed(KeyCode.D, false);
			player.setNextLevel(false);
			player.setEnd(false);
			map = new Map();
			player = new Player(33, 33);
			monsterList = new ArrayList<Monster>();
			initializeMonsters();
		}
		map.update();
		map.draw(gc);
		player.update();
		player.draw(gc);
		for (Monster monster : monsterList) {
			monster.update();
			monster.draw(gc);
		}
	}

	// Getter and Setter
	public static Player getPlayer() {
		return player;
	}
}
