package view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class EndLabel extends Label {
	private final static String FONT_PATH = ClassLoader.getSystemResource("VAGRoundedBT-Regular.otf").toString();
	
	public EndLabel(String text,int w,int h) {

		
		setText(text);
		setLabelFont();
		setPadding(new Insets(0,0,0,0));
		setPrefWidth(w);
		setPrefHeight(300);
		setAlignment(Pos.CENTER);
		setLayoutY(h/2 -120);
		setTextFill(Color.web("#FFFFFF"));
		
		
	
		
	}
	
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(FONT_PATH, 200));
		} catch(Exception e) {
			setFont(Font.font("verdana",40));
		}
	}
}
