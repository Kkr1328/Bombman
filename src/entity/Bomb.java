package entity;

import drawing.GameScreen;
import entity.base.Destroyable;
import entity.base.Entity;
import entity.base.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;

public class Bomb extends Entity implements Updatable, Destroyable {

	// Bomb's Status
	private int bombTimer;
	private int bombRadius;
	private boolean isExploded;

	// Explosion Area
	private int leftRadius, rightRadius, upRadius, downRadius;

	// Bomb's Image
	private Image bombSprite = new Image(ClassLoader.getSystemResource("Bomb.png").toString());
	private Image explodeSprite = new Image(ClassLoader.getSystemResource("Explosion.png").toString());
	private WritableImage croppedBombImage;

	// Bomb's Sound
	private AudioClip bombSound = new AudioClip(ClassLoader.getSystemResource("BombSound.mp3").toString());

	public Bomb(int x, int y) {
		super(x, y);
		bombTimer = 250;
		bombRadius = GameScreen.getPlayer().getBombRadius();
		isExploded = false;
		setFourRadius();
	}

	public void setFourRadius() {
		// Set upRadius and downRadius
		if (this.getX() % 2 == 0) {
			upRadius = 0;
			downRadius = 0;
		} else {
			if (this.getY() - bombRadius < 1) {
				upRadius = this.getY() - 1;
			} else {
				upRadius = bombRadius;
			}
			if (this.getY() + bombRadius > 19) {
				downRadius = 19 - this.getY();
			} else {
				downRadius = bombRadius;
			}
		}

		// Set leftRadius and rightRadius
		if (this.getY() % 2 == 0) {
			leftRadius = 0;
			rightRadius = 0;
		} else {
			if (this.getX() - bombRadius < 1) {
				leftRadius = this.getX() - 1;
			} else {
				leftRadius = bombRadius;
			}
			if (this.getX() + bombRadius > 19) {
				rightRadius = 19 - this.getX();
			} else {
				rightRadius = bombRadius;
			}
		}
	}

	public void explode() {
		// Change Floor to be Destroyed Area
		for (int i = this.getX() - leftRadius; i <= this.getX() + rightRadius; i++) {
			Map.map[i][this.getY()] = "Y";
		}
		for (int j = this.getY() - upRadius; j <= this.getY() + downRadius; j++) {
			Map.map[this.getX()][j] = "Y";
		}
		isExploded = true;
		bombSound.play(0.2);
	}

	@Override
	public void update() {
		if (bombTimer-- == 18) {
			explode();
		} else if (bombTimer == 0) {
			destroyed();
		}
	}

	@Override
	public void destroyed() {
		Player.bombList.remove(this);
		for (int i = this.getX() - leftRadius; i <= this.getX() + rightRadius; i++) {
			Map.map[i][this.getY()] = "F";
		}
		for (int j = this.getY() - upRadius; j <= this.getY() + downRadius; j++) {
			Map.map[this.getX()][j] = "F";
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (!isExploded) {
			croppedBombImage = new WritableImage(bombSprite.getPixelReader(),
					((int) (bombTimer / 2) % 16) * Map.getMAP_ELEMENT_SIZE(), 0, Map.getMAP_ELEMENT_SIZE(),
					Map.getMAP_ELEMENT_SIZE());
			gc.drawImage(croppedBombImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
					this.getY() * Map.getMAP_ELEMENT_SIZE());
		} else {
			if (leftRadius > 0 || rightRadius > 0) {
				for (int i = -leftRadius + 1; i <= rightRadius - 1; i++) {
					WritableImage croppedVImage = new WritableImage(explodeSprite.getPixelReader(),
							((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 1 * Map.getMAP_ELEMENT_SIZE(),
							Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
					gc.drawImage(croppedVImage, (this.getX() + i) * Map.getMAP_ELEMENT_SIZE(),
							this.getY() * Map.getMAP_ELEMENT_SIZE());
				}
			}
			if (upRadius > 0 || downRadius > 0) {
				for (int i = -upRadius + 1; i <= downRadius - 1; i++) {
					WritableImage croppedHImage = new WritableImage(explodeSprite.getPixelReader(),
							((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 2 * Map.getMAP_ELEMENT_SIZE(),
							Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
					gc.drawImage(croppedHImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
							(this.getY() + i) * Map.getMAP_ELEMENT_SIZE());
				}
			}
			if (leftRadius > 0) {
				WritableImage croppedLeftImage = new WritableImage(explodeSprite.getPixelReader(),
						((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 3 * Map.getMAP_ELEMENT_SIZE(),
						Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
				gc.drawImage(croppedLeftImage, (this.getX() - leftRadius) * Map.getMAP_ELEMENT_SIZE(),
						this.getY() * Map.getMAP_ELEMENT_SIZE());
			}
			if (rightRadius > 0) {
				WritableImage croppedRightImage = new WritableImage(explodeSprite.getPixelReader(),
						((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 4 * Map.getMAP_ELEMENT_SIZE(),
						Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
				gc.drawImage(croppedRightImage, (this.getX() + rightRadius) * Map.getMAP_ELEMENT_SIZE(),
						this.getY() * Map.getMAP_ELEMENT_SIZE());
			}
			if (upRadius > 0) {
				WritableImage croppedUpImage = new WritableImage(explodeSprite.getPixelReader(),
						((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 5 * Map.getMAP_ELEMENT_SIZE(),
						Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
				gc.drawImage(croppedUpImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
						(this.getY() - upRadius) * Map.getMAP_ELEMENT_SIZE());
			}
			if (downRadius > 0) {
				WritableImage croppedDownImage = new WritableImage(explodeSprite.getPixelReader(),
						((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 6 * Map.getMAP_ELEMENT_SIZE(),
						Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
				gc.drawImage(croppedDownImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
						(this.getY() + downRadius) * Map.getMAP_ELEMENT_SIZE());
			}
			WritableImage croppedCenterImage = new WritableImage(explodeSprite.getPixelReader(),
					((int) (bombTimer / 2) % 9) * Map.getMAP_ELEMENT_SIZE(), 0 * Map.getMAP_ELEMENT_SIZE(),
					Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());
			gc.drawImage(croppedCenterImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
					this.getY() * Map.getMAP_ELEMENT_SIZE());
		}
	}
}
