package drawing;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Status extends HBox {
	private Label playerName = new Label();
	private Label playerBomb = new Label();
	private Label keyStatus = new Label();
	private Label playerRadius = new Label();
	private Label playerLevel = new Label();

	public Status() {
		this.setPrefHeight(30);
		this.setSpacing(50);

		playerName.setFont(new Font(20));
		playerBomb.setFont(new Font(20));
		keyStatus.setFont(new Font(20));
		playerRadius.setFont(new Font(20));
		playerLevel.setFont(new Font(20));

		this.getChildren().add(playerName);
		this.getChildren().add(playerLevel);
		this.getChildren().add(playerBomb);
		this.getChildren().add(playerRadius);
		this.getChildren().add(keyStatus);
	}

	public void update() {
		setArmorLabelText();
		setBombRadius();
		setKeyStatus();
		setName();
		setLevel();
	}

	public void setArmorLabelText() {
		playerBomb.textProperty().setValue("Bomb : " + Integer.toString(GameScreen.getPlayer().getBombNumber()));
	}

	public void setLevel() {
		playerLevel.textProperty().setValue("Level : " + GameScreen.getPlayer().getLevel());
	}

	public void setKeyStatus() {
		keyStatus.textProperty().setValue("Key : " + Boolean.toString(GameScreen.getPlayer().isHaveKey()));
	}

	public void setBombRadius() {
		playerRadius.textProperty().setValue("Radius : " + Integer.toString(GameScreen.getPlayer().getBombRadius()));
	}

	public void setName() {
		playerName.textProperty().setValue("Name : " + GameScreen.getPlayer().getPlayerName());
	}

}
