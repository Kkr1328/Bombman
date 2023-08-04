package entity;

import drawing.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Key extends Item {

	// Key's Image
	private Image keySprite = new Image(ClassLoader.getSystemResource("Key.png").toString());
	private WritableImage croppedKeyImage = new WritableImage(keySprite.getPixelReader(), 32, 16);

	public Key(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {
		if (interact()) {
			destroyed();
			GameScreen.getPlayer().setHaveKey(true);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedKeyImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
				this.getY() * Map.getMAP_ELEMENT_SIZE() + 8);
	}

}
