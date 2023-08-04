package entity.base;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

	// Object's Position
	private int x;
	private int y;

	protected String[] defualtDir = { "right", "up", "down", "left" };

	protected boolean isStruck = false;

	public Entity() {
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void draw(GraphicsContext gc);

	// Getter and Setter
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
