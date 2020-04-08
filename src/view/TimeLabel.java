package view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class TimeLabel extends Label {
	private final static String FONT_PATH = "res/VAGRoundedBT-Regular.otf";
	
	public TimeLabel(String text,int w,int h) {

		setAlignment(Pos.TOP_CENTER);
		setText(text);
		
		
		setTextFill(Color.web("#FFFFFF"));
	
		
		setLayoutX(150);
		setLayoutY(660-60);
		setLabelFont();
	
		
	}
	
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 40));
		} catch(FileNotFoundException e) {
			setFont(Font.font("verdana",40));
		}
	}
}
