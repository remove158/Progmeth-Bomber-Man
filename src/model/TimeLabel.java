package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class TimeLabel extends Label {
	private final static String FONT_PATH = FontStyle.FONT_PATH;
	
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
			setFont(Font.loadFont(FONT_PATH, 40));
		} catch(Exception e) {
			setFont(Font.font("verdana",40));
		}
	}
}
