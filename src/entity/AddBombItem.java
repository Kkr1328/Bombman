package entity;

import drawing.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AddBombItem extends Item {

	// AddBombItem's Image
	private Image addBombSprite = new Image(ClassLoader.getSystemResource("AddBomb.png").toString());
	private WritableImage croppedAddBombImage = new WritableImage(addBombSprite.getPixelReader(),
			Map.getMAP_ELEMENT_SIZE(), Map.getMAP_ELEMENT_SIZE());

	private int lifeTime;

	public AddBombItem(int x, int y) {
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
			if (GameScreen.getPlayer().getBombNumber() < 5) {
				GameScreen.getPlayer().setBombNumber(GameScreen.getPlayer().getBombNumber() + 1);
			}
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedAddBombImage, this.getX() * Map.getMAP_ELEMENT_SIZE(),
				this.getY() * Map.getMAP_ELEMENT_SIZE());
	}
}
