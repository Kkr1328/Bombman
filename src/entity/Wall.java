package entity;

import entity.base.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Wall extends Entity {

	// Wall's Image
	private Image wallSprite = new Image(ClassLoader.getSystemResource("Wall.png").toString());
	private WritableImage croppedWallImage;

	public Wall(int x, int y) {
		super(x, y);
		croppedWallImage = new WritableImage(wallSprite.getPixelReader(),
				((int) ((x + 18.5) / 19)) * Map.getMAP_ELEMENT_SIZE(),
				((int) ((y + 18.5) / 19)) * Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE(),
				Map.getMAP_ELEMENT_SIZE());
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedWallImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
				this.getY() * Map.getMAP_ELEMENT_SIZE());
	}

}
