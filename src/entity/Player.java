package entity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import entity.base.Destroyable;
import entity.base.Entity;
import entity.base.Movable;
import entity.base.Updatable;
import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;

public class Player extends Entity implements Updatable, Destroyable, Movable {

	// Player's Dimensions
	private final int PLAYER_WIDTH = 16;
	private final int PLAYER_HEIGHT = 24;
	private final int SPEED = 2;
	// Player's Image
	private Image playerSprite = new Image(ClassLoader.getSystemResource("Player.png").toString());
	private WritableImage croppedImage;

	// Player's Status Sound
	private AudioClip loseSound = new AudioClip(ClassLoader.getSystemResource("LoseSound.mp3").toString());
	private AudioClip nextLevelSound = new AudioClip(ClassLoader.getSystemResource("NextLevelSound.mp3").toString());
	private AudioClip victorySound = new AudioClip(ClassLoader.getSystemResource("VictorySound.mp3").toString());

	// Controlling Animation Picture Variable
	private double xPic;
	private int yPic;

	// Player's Status
	private int bombNumber;
	private int bombRadius;
	private boolean haveKey;
	private String playerName;
	private int level;

	// Exist Condition
	private int deadTick;
	private boolean isEnd;
	private boolean isWin;
	private boolean isNextLevel;
	private boolean isAlive;
	private boolean isNewGame;

	// List of Bomb
	public static ArrayList<Bomb> bombList = new ArrayList<Bomb>();

	public Player(int x, int y) {
		super(x, y);

		// Set Default Player's Posture
		xPic = 0;
		croppedImage = new WritableImage(playerSprite.getPixelReader(), 0, 1 * PLAYER_HEIGHT, PLAYER_WIDTH,
				PLAYER_HEIGHT);

		// Set Default Player's Status
		bombNumber = 1;
		bombRadius = 1;
		haveKey = false;

		// Set Exist Condition
		isAlive = true;
		deadTick = 0;
		isEnd = false;
		isWin = false;
		isNewGame = false;
		level = 1;
		bombList = new ArrayList<Bomb>();
	}

	public void plantBomb() {
		int targetX = (int) ((this.getX() + (PLAYER_WIDTH / 2)) / Map.getMAP_ELEMENT_SIZE());
		int targetY = (int) ((this.getY() + (PLAYER_HEIGHT / 2)) / Map.getMAP_ELEMENT_SIZE());
		if (Map.map[targetX][targetY].equals("F") && bombList.size() < bombNumber) {
			Map.map[targetX][targetY] = "G";
			bombList.add(new Bomb(targetX, targetY));
		}
	}

	@Override
	public void update() {

		// Get Input
		xPic += 0.125;
		if (InputUtility.getKeyPressed(KeyCode.W)) {
			yPic = 0;
			this.move("up");
		} else if (InputUtility.getKeyPressed(KeyCode.S)) {
			yPic = 1;
			this.move("down");
		} else if (InputUtility.getKeyPressed(KeyCode.A)) {
			yPic = 2;
			this.move("left");
		} else if (InputUtility.getKeyPressed(KeyCode.D)) {
			yPic = 3;
			this.move("right");
		} else {
			xPic = 0;
		}
		if (InputUtility.getKeyPressed(KeyCode.SPACE) && isAlive) {
			this.plantBomb();
		}

		// Update Bomb
		if (!bombList.isEmpty()) {
			CopyOnWriteArrayList<Bomb> copyBombList = new CopyOnWriteArrayList<Bomb>(bombList);
			for (Bomb bomb : copyBombList) {
				bomb.update();
			}
		}

		// Check Alive Condition
		destroyed();

		// Play Sound
		playSound();
	}

	@Override
	public void destroyed() {
		if (Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY() / Map.getMAP_ELEMENT_SIZE())]
				.equals("Y")
				|| Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) ((this.getY() + PLAYER_HEIGHT)
						/ Map.getMAP_ELEMENT_SIZE())].equals("Y")
				|| Map.map[(int) ((this.getX() + PLAYER_WIDTH) / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY()
						/ Map.getMAP_ELEMENT_SIZE())].equals("Y")
				|| Map.map[(int) ((this.getX() + PLAYER_WIDTH)
						/ Map.getMAP_ELEMENT_SIZE())][(int) ((this.getY() + PLAYER_HEIGHT) / Map.getMAP_ELEMENT_SIZE())]
								.equals("Y")) {
			isAlive = false;
		}
		if (!isAlive) {
			if (deadTick++ == 98) {
				isEnd = true;
			}
		}
	}

	@Override
	public String move(String dir) {
		if (isAlive) {
			if (isMoveDir(dir)) {
				if (dir.equals("right")) {
					this.setX(this.getX() + SPEED);
				} else if (dir.equals("left")) {
					this.setX(this.getX() - SPEED);
				} else if (dir.equals("up")) {
					this.setY(this.getY() - SPEED);
				} else if (dir.equals("down")) {
					this.setY(this.getY() + SPEED);
				}
				this.isStruck = false;
			} else {
				if (dir.equals("right")) {
					this.setX(((int) (this.getX() / Map.getMAP_ELEMENT_SIZE()) + 1) * Map.getMAP_ELEMENT_SIZE()
							- PLAYER_WIDTH - 1);
				} else if (dir.equals("left")) {
					this.setX(((int) (this.getX() / Map.getMAP_ELEMENT_SIZE())) * Map.getMAP_ELEMENT_SIZE() + 1);
				} else if (dir.equals("up")) {
					this.setY(((int) (this.getY()) / Map.getMAP_ELEMENT_SIZE()) * Map.getMAP_ELEMENT_SIZE() + 1);
				} else if (dir.equals("down")) {
					this.setY(((int) (this.getY() / Map.getMAP_ELEMENT_SIZE()) + 1) * Map.getMAP_ELEMENT_SIZE()
							- PLAYER_HEIGHT - 1);
				}
				this.isStruck = true;
			}
		}
		return dir;
	}

	@Override
	public boolean isMoveDir(String dir) {
		int targetX = this.getX();
		int targetY = this.getY();
		if (dir.equals("right")) {
			targetX += SPEED;
		} else if (dir.equals("left")) {
			targetX -= SPEED;
		} else if (dir.equals("up")) {
			targetY -= SPEED;
		} else if (dir.equals("down")) {
			targetY += SPEED;
		}
		if (!isMovePossible(this.getX(), this.getY(), "G")) {
			return isMovePossible(targetX, targetY, "W") && isMovePossible(targetX, targetY, "B");
		} else {
			return isMovePossible(targetX, targetY, "W") && isMovePossible(targetX, targetY, "B")
					&& isMovePossible(targetX, targetY, "G");
		}
	}

	@Override
	public boolean isMovePossible(int targetx, int targety, String sprite) {
		return !(Map.map[(int) (targetx / Map.getMAP_ELEMENT_SIZE())][(int) (targety / Map.getMAP_ELEMENT_SIZE())]
				.equals(sprite))
				&& !(Map.map[(int) (targetx / Map.getMAP_ELEMENT_SIZE())][(int) ((targety + PLAYER_HEIGHT)
						/ Map.getMAP_ELEMENT_SIZE())].equals(sprite))
				&& !(Map.map[(int) ((targetx + PLAYER_WIDTH) / Map.getMAP_ELEMENT_SIZE())][(int) (targety
						/ Map.getMAP_ELEMENT_SIZE())].equals(sprite))
				&& !(Map.map[(int) ((targetx + PLAYER_WIDTH)
						/ Map.getMAP_ELEMENT_SIZE())][(int) ((targety + PLAYER_HEIGHT) / Map.getMAP_ELEMENT_SIZE())]
								.equals(sprite));
	}

	@Override
	public void draw(GraphicsContext gc) {
		// Draw Bomb
		for (Bomb bomb : bombList) {
			bomb.draw(gc);
		}

		// Draw Player
		if (isAlive) {
			croppedImage = new WritableImage(playerSprite.getPixelReader(), (int) (xPic % 4) * PLAYER_WIDTH,
					yPic * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		} else {
			croppedImage = new WritableImage(playerSprite.getPixelReader(), (int) (deadTick / 25) * PLAYER_WIDTH,
					4 * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		gc.drawImage(croppedImage, this.getX(), this.getY());
	}

	public void playSound() {
		if (isEnd || isNextLevel) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isEnd) {
				loseSound.play();
			} else if (isNextLevel) {
				if (isLastLevel()) {
					victorySound.play();
				} else {
					nextLevelSound.play();
				}
			}
		}
	}

	// Getter and Setter
	public int getHeight() {
		return PLAYER_HEIGHT;
	}

	public int getWidth() {
		return PLAYER_WIDTH;
	}

	public int getBombNumber() {
		return this.bombNumber;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
	}

	public int getBombRadius() {
		return bombRadius;
	}

	public void setBombRadius(int bombRadius) {
		this.bombRadius = bombRadius;
	}

	public boolean isHaveKey() {
		return haveKey;
	}

	public void setHaveKey(boolean haveKey) {
		this.haveKey = haveKey;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isNextLevel() {
		return isNextLevel;
	}

	public void setNextLevel(boolean isNextLevel) {
		this.isNextLevel = isNextLevel;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public boolean isWin() {
		return isWin;
	}

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLastLevel() {
		return this.getLevel() == 5;
	}

}
