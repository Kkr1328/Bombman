package entity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import drawing.GameScreen;
import entity.base.Entity;
import entity.base.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Map extends Entity implements Updatable {

	// Map's Element Dimension
	private final static int MAP_ELEMENT_SIZE = 32;

	// Layout of MAP
	public static String[][] map;

	// Unique Element Exist Status
	private static boolean isKeyExist;
	private static boolean isDoorExist;

	// List of Box and Item
	public static ArrayList<Box> boxList = new ArrayList<Box>();
	public static ArrayList<Item> itemList = new ArrayList<Item>();
	public static ArrayList<Wall> wallList = new ArrayList<Wall>();

	// Floor's Image
	private Image floorSprite1 = new Image(ClassLoader.getSystemResource("DarkGreenFloor.png").toString());
	private WritableImage croppedFloorImage1 = new WritableImage(floorSprite1.getPixelReader(), MAP_ELEMENT_SIZE,
			MAP_ELEMENT_SIZE);
	private Image floorSprite2 = new Image(ClassLoader.getSystemResource("LightBlueFloor.png").toString());
	private WritableImage croppedFloorImage2 = new WritableImage(floorSprite2.getPixelReader(), MAP_ELEMENT_SIZE,
			MAP_ELEMENT_SIZE);
	private Image floorSprite3 = new Image(ClassLoader.getSystemResource("YellowFloor.png").toString());
	private WritableImage croppedFloorImage3 = new WritableImage(floorSprite3.getPixelReader(), MAP_ELEMENT_SIZE,
			MAP_ELEMENT_SIZE);
	private Image floorSprite4 = new Image(ClassLoader.getSystemResource("OrangeFloor.png").toString());
	private WritableImage croppedFloorImage4 = new WritableImage(floorSprite4.getPixelReader(), MAP_ELEMENT_SIZE,
			MAP_ELEMENT_SIZE);
	private Image floorSprite5 = new Image(ClassLoader.getSystemResource("RedFloor.png").toString());
	private WritableImage croppedFloorImage5 = new WritableImage(floorSprite5.getPixelReader(), MAP_ELEMENT_SIZE,
			MAP_ELEMENT_SIZE);
	private WritableImage croppedFloorImage;

	public Map() {
		map = new String[21][21];
		isKeyExist = false;
		isDoorExist = false;
		boxList = new ArrayList<Box>();
		itemList = new ArrayList<Item>();
		sketchMap();
		placeBox();
	}

	public void sketchMap() {
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (i == 0 || i == map[0].length - 1 || j == 0 || j == map.length - 1 || (i % 2 == 0 && j % 2 == 0)) {
					map[i][j] = "W"; // "W" refers to "Wall"
					wallList.add(new Wall(i, j));
				} else {
					map[i][j] = "F"; // "F" refers to "Floor"
				}
			}
		}
	}

	public void placeBox() {
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j].equals("F") && (i > 2 || j > 2)) {
					if (Math.random() > 0.6) {
						map[i][j] = "B"; // "B" refers to "Box"
						boxList.add(new Box(i, j));
					}
				}
			}
		}
	}

	@Override
	public void update() {

		// Update Box
		if (!boxList.isEmpty()) {
			CopyOnWriteArrayList<Box> copyBoxList = new CopyOnWriteArrayList<Box>(boxList);
			for (Box box : copyBoxList) {
				box.update();
			}
		}

		// Update Item
		if (!itemList.isEmpty()) {
			CopyOnWriteArrayList<Item> copyItemList = new CopyOnWriteArrayList<Item>(itemList);
			for (Item item : copyItemList) {
				item.update();
			}
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		// Draw Floor
		for (int x = 0; x < map[0].length; x++) {
			for (int y = 0; y < map.length; y++) {
				if (map[x][y].equals("F") || map[x][y].equals("G")) {
					floorImage();
					gc.drawImage(croppedFloorImage, x * getMAP_ELEMENT_SIZE(), y * getMAP_ELEMENT_SIZE());
				}
			}
		}

		// Draw Wall
		for (Wall wall : wallList) {
			wall.draw(gc);
		}

		// Draw Box
		for (Box box : boxList) {
			box.draw(gc);
		}

		// Draw Item
		for (Item item : itemList) {
			item.draw(gc);
		}
	}

	public void floorImage() {
		if (GameScreen.getPlayer().getLevel() == 1) {
			croppedFloorImage = croppedFloorImage1;
		} else if (GameScreen.getPlayer().getLevel() == 2) {
			croppedFloorImage = croppedFloorImage2;
		} else if (GameScreen.getPlayer().getLevel() == 3) {
			croppedFloorImage = croppedFloorImage3;
		} else if (GameScreen.getPlayer().getLevel() == 4) {
			croppedFloorImage = croppedFloorImage4;
		} else {
			croppedFloorImage = croppedFloorImage5;
		}
	}

	// Getter and Setter
	public static boolean isKeyExist() {
		return isKeyExist;
	}

	public static void setKeyExist(boolean newKeyExist) {
		isKeyExist = newKeyExist;
	}

	public static boolean isDoorExist() {
		return isDoorExist;
	}

	public static void setDoorExist(boolean newDoorExist) {
		isDoorExist = newDoorExist;
	}

	public static int getMAP_ELEMENT_SIZE() {
		return MAP_ELEMENT_SIZE;
	}
}
