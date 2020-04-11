package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class InfoLabel extends Label {
	private final static String FONT_PATH = ClassLoader.getSystemResource("VAGRoundedBT-Regular.otf").toString();
	private final static String BACKGROUND_IMAGE = ClassLoader.getSystemResource("title.png").toString();

	public InfoLabel(String text) {
		setPrefWidth(380);
		setPrefHeight(49);
		setText(text);
		setWrapText(true);
		setLabelFont();
		setAlignment(Pos.CENTER);
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 380, 49, false, true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		setBackground(new Background(backgroundImage));
	}
	
	private void setLabelFont() {
		
		try {
			setFont(Font.loadFont(FONT_PATH, 23));
		} catch (Exception e) {
			setFont(Font.font("Verdana",23));
		}
		
	}
}
