package gui;

import application.Main;
import drawing.GameScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ResultWindow extends HBox {

	// continue button
	private Image continueBtn;
	private ImageView continueBtnImage;
	private Hyperlink nextLevel;

	// new game button
	private Image newGameBtn;
	private ImageView newGameBtnImage;
	private Hyperlink newGame;

	public ResultWindow(double spacing, String condition) {
		this.setSpacing(spacing);

		continueBtn = new Image(ClassLoader.getSystemResource("Continue.png").toString());
		continueBtnImage = new ImageView();
		continueBtnImage.setImage(continueBtn);
		continueBtnImage.setFitHeight(50);
		continueBtnImage.setPreserveRatio(true);

		nextLevel = new Hyperlink();
		nextLevel.setGraphic(continueBtnImage);
		nextLevel.setPadding(Insets.EMPTY);

		nextLevel.setOnAction(e -> {
			Main.getGameScreen().paintComponent();
			Main.getWindow().setScene(Main.getGameScene());
		});

		newGameBtn = new Image(ClassLoader.getSystemResource("NewGame.png").toString());
		newGameBtnImage = new ImageView();
		newGameBtnImage.setImage(newGameBtn);
		newGameBtnImage.setFitHeight(50);
		newGameBtnImage.setPreserveRatio(true);

		newGame = new Hyperlink();
		newGame.setGraphic(newGameBtnImage);
		newGame.setPadding(Insets.EMPTY);

		newGame.setOnAction(e -> {
			GameScreen.getPlayer().setNextLevel(false);
			GameScreen.getPlayer().setEnd(false);
			GameScreen.getPlayer().setNewGame(true);
			Main.getWindow().setScene(Main.getStartScene());
		});

		this.setAlignment(Pos.BOTTOM_CENTER);
		this.setTranslateY(-80);

		if (condition.equals("win")) {
			this.getChildren().addAll(nextLevel, newGame);
		} else {
			this.getChildren().addAll(newGame);
		}
	}
}
