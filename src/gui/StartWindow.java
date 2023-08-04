package gui;

import application.Main;
import drawing.GameScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartWindow extends VBox {

	private Image logo;
	private ImageView logoImage;
	private StackPane nameBox;
	private TextField nameTextField;
	private Button clearButton;
	private Image startImage;
	private ImageView startBtnImage;
	private Hyperlink startButton;

	public StartWindow(double spacing) {
		this.setSpacing(spacing);
		// logo
		logo = new Image(ClassLoader.getSystemResource("Logo.png").toString());
		logoImage = new ImageView();
		logoImage.setImage(logo);
		logoImage.setFitWidth(400);
		logoImage.setPreserveRatio(true);

		// name box
		nameBox = new StackPane();

		Rectangle rect = new Rectangle();
		rect.setWidth(240);
		rect.setHeight(70);
		rect.setArcWidth(40);
		rect.setArcHeight(40);
		rect.setFill(Color.web("#C39B77"));

		Text name = new Text("Enter Your Name :");
		name.setFont(new Font(20));
		name.setFill(Color.WHITE);
		name.setStroke(Color.WHITE);

		GridPane enterName = new GridPane();
		enterName.setAlignment(Pos.CENTER);
		enterName.setHgap(10);

		nameTextField = new TextField();
		nameTextField.setMaxWidth(200);

		clearButton = new Button("Clear");
		clearButton.setOnAction(e -> {
			nameTextField.setText("");
		});

		enterName.add(name, 0, 0);
		enterName.add(nameTextField, 0, 1);
		enterName.add(clearButton, 1, 1);

		nameBox.getChildren().addAll(rect, enterName);

		startImage = new Image(ClassLoader.getSystemResource("Start.png").toString());
		startBtnImage = new ImageView();
		startBtnImage.setImage(startImage);
		startBtnImage.setFitWidth(150);
		startBtnImage.setPreserveRatio(true);

		startButton = new Hyperlink();
		startButton.setGraphic(startBtnImage);
		startButton.setPadding(Insets.EMPTY);

		startButton.setOnAction(e -> {
			startGame(nameTextField);
		});

		nameTextField.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				startGame(nameTextField);
			}
		});

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(logoImage, nameBox, startButton);
	}

	public void startGame(TextField nameTextField) {
		if (nameTextField.getText().length() > 7 || nameTextField.getText().length() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Name Error");
			if (nameTextField.getText().length() > 7) {
				alert.setHeaderText("Name Too Long!");
			} else if (nameTextField.getText().length() == 0) {
				alert.setHeaderText("Name Too Short!");
			}
			alert.setContentText("Please Change Your Name\nName Can Be Between 1-7 Characters");
			alert.showAndWait();
		} else {
			nameTextField.setText(nameTextField.getText());
			GameScreen.getPlayer().setPlayerName(nameTextField.getText());
			Main.getWindow().setScene(Main.getGameScene());
		}
	}
}
