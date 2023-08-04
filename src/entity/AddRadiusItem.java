package entity;

import drawing.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AddRadiusItem extends Item {

	// AddRadiuusItem's Image
	private Image addRadiusSprite = new Image(ClassLoader.getSystemResource("AddRadius.png").toString());
	private WritableImage croppedAddRadiusImage = new WritableImage(addRadiusSprite.getPixelReader(),
			Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());

	private int lifeTime;

	public AddRadiusItem(int x, int y) {
		super(x, y);
		lifeTime = 0;
	}

	@Override
	public void update() {
		if (Map.map[this.getX()][this.getY()].equals("Y") && lifeTime++ > 16) {
			destroyed();
		}
		if (interact()) {
			destroyed();
			if (GameScreen.getPlayer().getBombRadius() < 5) {
				GameScreen.getPlayer().setBombRadius(GameScreen.getPlayer().getBombRadius() + 1);
			}
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedAddRadiusImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
				this.getY() * Map.getMAP_ELEMENT_SIZE());
	}
}
