package entity;

import drawing.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Door extends Item {

	// Door's Image
	private Image doorSprite = new Image(ClassLoader.getSystemResource("Door.png").toString());
	private WritableImage croppedDoorImage = new WritableImage(doorSprite.getPixelReader(), 19, 32);

	public Door(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {
		if (interact() && GameScreen.getPlayer().isHaveKey()) {
			destroyed();
			GameScreen.getPlayer().setWin(true);
			GameScreen.getPlayer().setNextLevel(true);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedDoorImage, this.getX() * Map.getMAP_ELEMENT_SIZE() + 6,
				this.getY() * Map.getMAP_ELEMENT_SIZE());
	}

}
