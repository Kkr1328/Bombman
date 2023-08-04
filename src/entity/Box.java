package entity;

import entity.base.Destroyable;
import entity.base.Entity;
import entity.base.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Box extends Entity implements Updatable, Destroyable {

	private boolean isOccupied;

	// Box's Image
	private Image boxSprite = new Image(ClassLoader.getSystemResource("Box.png").toString());
	private WritableImage croppedBoxImage = new WritableImage(boxSprite.getPixelReader(), Map.getMAP_ELEMENT_SIZE(),
			Map.getMAP_ELEMENT_SIZE());

	public Box(int x, int y) {
		super(x, y);
		setItemInBox();
	}

	public void setItemInBox() {
		isOccupied = (Math.random() > 0.7);
	}

	@Override
	public void update() {
		if (Map.map[this.getX()][this.getY()].equals("Y")) {
			destroyed();
		}
	}

	@Override
	public void destroyed() {
		// Generate Item
		if (isOccupied) {
			double random = Math.random();
			if (random > 0.9 && !Map.isKeyExist()) {
				Map.itemList.add(new Key(this.getX(), this.getY()));
				Map.setKeyExist(true);
			} else if (random > 0.8 && !Map.isDoorExist()) {
				Map.itemList.add(new Door(this.getX(), this.getY()));
				Map.setDoorExist(true);
			} else if (random > 0.4) {
				Map.itemList.add(new AddBombItem(this.getX(), this.getY()));
			} else {
				Map.itemList.add(new AddRadiusItem(this.getX(), this.getY()));
			}
		}

		// Ensure that Door and Key are emerge
		if (Map.boxList.size() == 2 && !Map.isKeyExist() && !Map.isDoorExist()) {
			double prop = Math.random();
			if (prop > 0.5) {
				Map.itemList.add(new Door(this.getX(), this.getY()));
			} else {
				Map.itemList.add(new Key(this.getX(), this.getY()));
			}
		}
		if (Map.boxList.size() == 1 && !Map.isKeyExist()) {
			Map.itemList.add(new Key(this.getX(), this.getY()));
		}
		if (Map.boxList.size() == 1 && !Map.isDoorExist()) {
			Map.itemList.add(new Door(this.getX(), this.getY()));
		}

		// Destroy this Box
		Map.boxList.remove(this);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(croppedBoxImage, this.getX() * Map.getMAP_ELEMENT_SIZE(), this.getY() * Map.getMAP_ELEMENT_SIZE());
	}
}
