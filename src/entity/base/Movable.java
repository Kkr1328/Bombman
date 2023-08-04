package entity.base;

public interface Movable {
	public String move(String dir);

	public boolean isMoveDir(String dir);

	public boolean isMovePossible(int targetX, int targetY, String sprite);
}
