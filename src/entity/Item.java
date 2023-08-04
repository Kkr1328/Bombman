package entity;

import drawing.GameScreen;
import entity.base.Destroyable;
import entity.base.Entity;
import entity.base.Interactable;
import entity.base.Updatable;
import javafx.scene.canvas.GraphicsContext;

public abstract class Item extends Entity implements Updatable, Destroyable, Interactable {

	public Item(int x, int y) {
		super(x, y);
	}

	@Override
	public void destroyed() {
		Map.map[this.getX()][this.getY()] = "F";
		Map.itemList.remove(this);
	}

	@Override
	public boolean interact() {
		int upperPlayer = GameScreen.getPlayer().getY();
		int lowerPlayer = GameScreen.getPlayer().getY() + GameScreen.getPlayer().getHeight();
		int leftPlayer = GameScreen.getPlayer().getX();
		int rightPlayer = GameScreen.getPlayer().getX() + GameScreen.getPlayer().getWidth();
		return ((GameScreen.getPlayer().getHeight()
				+ Map.getMAP_ELEMENT_SIZE() >= Math.abs(upperPlayer - this.getY() * Map.getMAP_ELEMENT_SIZE())
						+ Math.abs(lowerPlayer - (this.getY() + 1) * Map.getMAP_ELEMENT_SIZE()))
				&& (GameScreen.getPlayer().getWidth()
						+ Map.getMAP_ELEMENT_SIZE() >= Math.abs(leftPlayer - this.getX() * Map.getMAP_ELEMENT_SIZE())
								+ Math.abs(rightPlayer - (this.getX() + 1) * Map.getMAP_ELEMENT_SIZE())));
	}

	@Override
	public abstract void update();

	@Override
	public abstract void draw(GraphicsContext gc);
}
