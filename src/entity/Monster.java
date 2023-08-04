package entity;

import java.util.ArrayList;

import drawing.GameScreen;
import entity.base.Destroyable;
import entity.base.Entity;
import entity.base.Interactable;
import entity.base.Movable;
import entity.base.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Monster extends Entity implements Updatable, Destroyable, Interactable, Movable {

	// Monster's Dimensions
	private final int MONSTER_WIDTH = 25;
	private final int MONSTER_HEIGHT = 25;
	private int speed;

	// Monster's Image
	// Monster 1
	private Image monster1Sprite = new Image(ClassLoader.getSystemResource("Monster1.png").toString());
	private WritableImage croppedImage1 = new WritableImage(monster1Sprite.getPixelReader(), MONSTER_WIDTH,
			MONSTER_HEIGHT);
	// Monster 2
	private Image monster2Sprite = new Image(ClassLoader.getSystemResource("Monster2.png").toString());
	private WritableImage croppedImage2 = new WritableImage(monster2Sprite.getPixelReader(), MONSTER_WIDTH,
			MONSTER_HEIGHT);
	// Monster 3
	private Image monster3Sprite = new Image(ClassLoader.getSystemResource("Monster3.png").toString());
	private WritableImage croppedImage3 = new WritableImage(monster3Sprite.getPixelReader(), MONSTER_WIDTH,
			MONSTER_HEIGHT);

	private WritableImage croppedImage;

	// Monster's Level
	private int monsterLevel;

	// Direction Attributes
	private String dir;
	private ArrayList<String> listDir;

	// Exist Condition
	private boolean isAlive;

	public Monster(int monsterLevel) {
		isAlive = true;
		this.randomStart();
		dir = this.move(defualtDir[(int) (4 * Math.random())]);
		this.monsterLevel = monsterLevel;
		speed = monsterLevel;
	}

	public void randomStart() {
		do {
			this.setX((int) (608 * Math.random()) + Map.getMAP_ELEMENT_SIZE());
			this.setY((int) (608 * Math.random()) + Map.getMAP_ELEMENT_SIZE());
		} while (!Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY()
				/ Map.getMAP_ELEMENT_SIZE())].equals("F")
				|| !(Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())
						+ 1][(int) (this.getY() / Map.getMAP_ELEMENT_SIZE())].equals("F")
						&& Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())
								- 1][(int) (this.getY() / Map.getMAP_ELEMENT_SIZE())].equals("F")
						&& Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY()
								/ Map.getMAP_ELEMENT_SIZE()) + 1].equals("F")
						&& Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY()
								/ Map.getMAP_ELEMENT_SIZE()) - 1].equals("F")));
	}

	public String randomMove(ArrayList<String> listDir) {
		return listDir.get((int) (listDir.size() * Math.random()));
	}

	@Override
	public void update() {
		listDir = new ArrayList<String>();
		for (String direction : defualtDir) {
			if (isMoveDir(direction)) {
				listDir.add(direction);
			}
		}
		if (this.isStruck) {
			dir = this.move(randomMove(listDir));
			this.isStruck = false;
		} else {
			this.move(dir);
		}
		if (interact() && isAlive) {
			GameScreen.getPlayer().setIsAlive(false);
		}
		destroyed();
	}

	@Override
	public void destroyed() {
		if (Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY() / Map.getMAP_ELEMENT_SIZE())]
				.equals("Y")
				|| Map.map[(int) (this.getX() / Map.getMAP_ELEMENT_SIZE())][(int) ((this.getY() + MONSTER_HEIGHT)
						/ Map.getMAP_ELEMENT_SIZE())].equals("Y")
				|| Map.map[(int) ((this.getX() + MONSTER_WIDTH) / Map.getMAP_ELEMENT_SIZE())][(int) (this.getY()
						/ Map.getMAP_ELEMENT_SIZE())].equals("Y")
				|| Map.map[(int) ((this.getX() + MONSTER_WIDTH)
						/ Map.getMAP_ELEMENT_SIZE())][(int) ((this.getY() + MONSTER_HEIGHT)
								/ Map.getMAP_ELEMENT_SIZE())].equals("Y")) {
			isAlive = false;
		}
	}

	@Override
	public boolean interact() {
		return ((GameScreen.getPlayer().getHeight()
				+ MONSTER_HEIGHT >= Math.abs(GameScreen.getPlayer().getY() - this.getY())
						+ Math.abs(GameScreen.getPlayer().getY() + GameScreen.getPlayer().getHeight()
								- (this.getY() + MONSTER_HEIGHT)))
				&& (GameScreen.getPlayer().getWidth()
						+ MONSTER_WIDTH >= Math.abs(GameScreen.getPlayer().getX() - this.getX())
								+ Math.abs(GameScreen.getPlayer().getX() + GameScreen.getPlayer().getWidth()
										- (this.getX() + MONSTER_WIDTH))));
	}

	@Override
	public String move(String dir) {
		if (isMoveDir(dir)) {
			if (dir.equals("right")) {
				this.setX(this.getX() + speed);
			} else if (dir.equals("left")) {
				this.setX(this.getX() - speed);
			} else if (dir.equals("up")) {
				this.setY(this.getY() - speed);
			} else if (dir.equals("down")) {
				this.setY(this.getY() + speed);
			}
			this.isStruck = false;
		} else {
			if (dir.equals("right")) {
				this.setX(((int) (this.getX() / Map.getMAP_ELEMENT_SIZE()) + 1) * Map.getMAP_ELEMENT_SIZE()
						- MONSTER_WIDTH - 1);
			} else if (dir.equals("left")) {
				this.setX(((int) (this.getX() / Map.getMAP_ELEMENT_SIZE())) * Map.getMAP_ELEMENT_SIZE() + 1);
			} else if (dir.equals("up")) {
				this.setY(((int) (this.getY()) / Map.getMAP_ELEMENT_SIZE()) * Map.getMAP_ELEMENT_SIZE() + 1);
			} else if (dir.equals("down")) {
				this.setY(((int) (this.getY() / Map.getMAP_ELEMENT_SIZE()) + 1) * Map.getMAP_ELEMENT_SIZE()
						- MONSTER_HEIGHT - 1);
			}
			this.isStruck = true;
		}
		return dir;
	}

	@Override
	public boolean isMoveDir(String dir) {
		int targetX = this.getX();
		int targetY = this.getY();
		if (dir.equals("right")) {
			targetX += speed;
		} else if (dir.equals("left")) {
			targetX -= speed;
		} else if (dir.equals("up")) {
			targetY -= speed;
		} else if (dir.equals("down")) {
			targetY += speed;
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
				&& !(Map.map[(int) (targetx / Map.getMAP_ELEMENT_SIZE())][(int) ((targety + MONSTER_HEIGHT)
						/ Map.getMAP_ELEMENT_SIZE())].equals(sprite))
				&& !(Map.map[(int) ((targetx + MONSTER_WIDTH) / Map.getMAP_ELEMENT_SIZE())][(int) (targety
						/ Map.getMAP_ELEMENT_SIZE())].equals(sprite))
				&& !(Map.map[(int) ((targetx + MONSTER_WIDTH)
						/ Map.getMAP_ELEMENT_SIZE())][(int) ((targety + MONSTER_HEIGHT) / Map.getMAP_ELEMENT_SIZE())]
								.equals(sprite));
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (this.isAlive) {
			monsterImg();
			gc.drawImage(croppedImage, this.getX(), this.getY());
		}

	}

	public void monsterImg() {
		if (monsterLevel == 1) {
			croppedImage = croppedImage1;
		} else if (monsterLevel == 2) {
			croppedImage = croppedImage2;
		} else if (monsterLevel == 3) {
			croppedImage = croppedImage3;
		}
	}

	// Getter and Setter
	public int getWidth() {
		return MONSTER_WIDTH;
	}

	public int getHeight() {
		return MONSTER_HEIGHT;
	}

}
