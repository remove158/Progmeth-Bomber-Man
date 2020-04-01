package model;


import java.io.FileNotFoundException;



import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class GameButton extends Button {
	private final String FONT_PATH = "res/VAGRoundedBT-Regular.otf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('button2.png')";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('button1.png')";
  
    public GameButton(String text) {
 
    	setText(text);
    	setButtonFont();
    	setPrefWidth(190);
    	setPrefHeight(49);
    	setStyle(BUTTON_FREE_STYLE);
    	initializeButtonLusteners();
    }
    

	private void setButtonFont() {
    	try {
    		setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
    		
    	}catch(FileNotFoundException e) {
    		System.out.println("Cannot Get Font");
    		setFont(Font.font("Verdana",23));
    	}
    }
    
    private void setButtonPressedStyle() {
    	setStyle(BUTTON_PRESSED_STYLE);
    	setPrefHeight(45);
    	setLayoutY(getLayoutY()+4);
    }
    private void setButtonReleasedStyle() {
    	setStyle(BUTTON_FREE_STYLE);
    	setPrefHeight(49);
    	setLayoutY(getLayoutY()-4);
    }
    

   private void initializeButtonLusteners() {

	   setOnMousePressed(e -> setButtonPressedStyle());
	   setOnMouseReleased(e -> setButtonReleasedStyle());
	   setOnMouseEntered(e -> setEffect(new DropShadow()));
	   setOnMouseExited(e -> setEffect(null));
   }
   public void setButtonPos(int x,int y) {
		x -= this.getPrefWidth()/2;
		y -= this.getPrefHeight()/2;
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
    
}
