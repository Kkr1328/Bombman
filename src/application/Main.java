package application;

import drawing.GameScreen;
import drawing.Status;
import gui.StartWindow;
import gui.ResultWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage window;
	private static Scene startScene, gameScene, winScene, loseScene, endScene;
	private static GameScreen gameScreen;
	private StartWindow startWindow;
	private ResultWindow winWindow, loseWindow, victoryWindow;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		window = stage;
		window.setResizable(false);

		// start scene

		StackPane startRoot = new StackPane();

		Image bg = new Image(ClassLoader.getSystemResource("Background.png").toString());
		ImageView bgImage = new ImageView();
		bgImage.setImage(bg);

		startWindow = new StartWindow(5);

		startRoot.getChildren().addAll(bgImage, startWindow);
		startScene = new Scene(startRoot, 512, 307);

		// game scene

		gameScreen = new GameScreen(672, 672);

		VBox gameRoot = new VBox();
		Status status = new Status();

		gameRoot.getChildren().add(status);
		gameRoot.getChildren().add(gameScreen);

		gameScene = new Scene(gameRoot);
		gameScreen.requestFocus();

		// win scene

		StackPane winRoot = new StackPane();

		winWindow = new ResultWindow(20, "win");

		Image nextLevelBackground = new Image(ClassLoader.getSystemResource("NextLevel.png").toString());
		ImageView nextLevelImage = new ImageView();
		nextLevelImage.setImage(nextLevelBackground);

		winRoot.getChildren().addAll(nextLevelImage, winWindow);
		winRoot.setAlignment(Pos.CENTER);
		winScene = new Scene(winRoot, 672, 378);

		// lose scene

		StackPane loseRoot = new StackPane();

		loseWindow = new ResultWindow(0, "lose");

		Image loseBackground = new Image(ClassLoader.getSystemResource("GameOver.png").toString());
		ImageView loseImage = new ImageView();
		loseImage.setImage(loseBackground);

		loseRoot.getChildren().addAll(loseImage, loseWindow);
		loseRoot.setAlignment(Pos.CENTER);
		loseScene = new Scene(loseRoot, 630, 447);

		// finish game scene

		StackPane endRoot = new StackPane();

		victoryWindow = new ResultWindow(0, "victory");
		victoryWindow.setAlignment(Pos.CENTER);

		Image victory = new Image(ClassLoader.getSystemResource("Victory.png").toString());
		ImageView victoryImage = new ImageView();
		victoryImage.setImage(victory);

		endRoot.getChildren().addAll(victoryImage, victoryWindow);
		endRoot.setAlignment(Pos.CENTER);
		endScene = new Scene(endRoot, 640, 448);

		// start window showing

		window.setTitle("Bombman");
		window.setScene(startScene);

		window.show();

		AnimationTimer animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (!GameScreen.getPlayer().isEnd()) {
					if (GameScreen.getPlayer().isNextLevel()) {
						if (GameScreen.getPlayer().isLastLevel()) {
							window.setScene(endScene);
						} else {
							window.setScene(winScene);
						}
					} else {
						gameScreen.paintComponent();
						status.update();
					}

				} else {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					window.setScene(loseScene);
				}
			}
		};
		animation.start();
	}

	public static Stage getWindow() {
		return window;
	}

	public static Scene getStartScene() {
		return startScene;
	}

	public static Scene getGameScene() {
		return gameScene;
	}

	public static GameScreen getGameScreen() {
		return gameScreen;
	}

}
