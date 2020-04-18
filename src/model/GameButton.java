package model;

import javafx.scene.text.Font;
import music.Sound;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class GameButton extends Button {
	private final static String FONT_PATH = FontStyle.FONT_PATH;
	private final static String btn1 = ClassLoader.getSystemResource("button1.png").toString();
	private final static String btn2 = ClassLoader.getSystemResource("button2.png").toString();
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url(" + btn1
			+ ")";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url(" + btn2
			+ ")";

	public GameButton(String text) {
		setTextFill(Color.web("#FFFFFF"));
		setText(text);
		setButtonFont();
		setPrefWidth(190);
		setPrefHeight(49);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonLusteners();
	}

	private void setButtonFont() {

		try {

			setFont(Font.loadFont(FONT_PATH, 23));

		} catch (Exception e) {

			System.out.println("Cannot Get Font   :   " + FONT_PATH);
			setFont(Font.font("Verdana", 23));
		}
	}

	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);
		new Sound("mouse_click", 1);
	}

	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(49);
		setLayoutY(getLayoutY() - 4);
	}

	private void setButtonEntered() {
		setStyle(BUTTON_PRESSED_STYLE);
		setEffect(new DropShadow());
		new Sound("mouseover", 2);
	}

	private void setButtonExited() {
		setStyle(BUTTON_FREE_STYLE);
		setEffect(null);
		new Sound("mouseout", 0.2);
	}

	private void initializeButtonLusteners() {

		setOnMousePressed(e -> setButtonPressedStyle());
		setOnMouseReleased(e -> setButtonReleasedStyle());
		setOnMouseEntered(e -> setButtonEntered());
		setOnMouseExited(e -> setButtonExited());

	}

	public void setButtonPos(int x, int y) {
		x -= this.getPrefWidth() / 2;
		y -= this.getPrefHeight() / 2;
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

}
