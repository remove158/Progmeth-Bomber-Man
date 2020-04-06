package view;

import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label {
	private final static String FONT_PATH = "res/VAGRoundedBT-Regular.otf";
	private final int CELL_WIDTH = 65;
	public SmallInfoLabel(String text) {

		setPrefWidth(CELL_WIDTH*3);
		setPrefHeight(CELL_WIDTH*3);
		setAlignment(Pos.TOP_LEFT);
		setPadding(new Insets(75,25,25,40));
		setTextFill(Color.web("#000000"));
		setLabelFont();
		setText(text);
		
		BackgroundImage bg = new BackgroundImage(new Image("/score.png",CELL_WIDTH*3,CELL_WIDTH*3,false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		setBackground(new Background(bg));
	}
	
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 14));
		} catch(FileNotFoundException e) {
			setFont(Font.font("verdana",14));
		}
	}
}
