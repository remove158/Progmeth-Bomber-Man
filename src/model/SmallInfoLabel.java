package model;

import javafx.scene.paint.Color;
import entity.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends AnchorPane {
	private final static String FONT_PATH  = ClassLoader.getSystemResource("VAGRoundedBT-Regular.otf").toString();
	private final int CELL_WIDTH = 65;
	private Label bomb,speed,radius;
	private Player myPlayer;
	public SmallInfoLabel(Player player) {
		
		myPlayer = player;
		bomb = new Label("" +player.getBombMax());
		speed = new Label("" +(player.getSpeed()-3));
		radius = new Label("" +player.getBombRadius());
		setLabelFont(bomb);
		setLabelFont(speed);
		setLabelFont(radius);
		setPrefHeight(65*3);
		setPrefWidth(65*4);
		String BG_PATH = ClassLoader.getSystemResource( "score.png").toString();
		
		BackgroundImage bg = new BackgroundImage(new Image(BG_PATH,CELL_WIDTH*3+65,CELL_WIDTH*3,false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		setBackground(new Background(bg));
		
		
		
		String PLAYER_PATH = ClassLoader.getSystemResource( player.getImgUrl()).toString();
		ImageView myImg = new ImageView(PLAYER_PATH); 
		
		myImg.setLayoutX(20);
		myImg.setLayoutY(30);
		speed.setAlignment(Pos.BASELINE_RIGHT);
		radius.setAlignment(Pos.BASELINE_RIGHT);
		bomb.setAlignment(Pos.BASELINE_RIGHT);
		
		speed.setLayoutX(150);
		speed.setLayoutY(45);
		
		radius.setLayoutY(75);
		radius.setLayoutX(150);
		
		bomb.setLayoutY(105);
		bomb.setLayoutX(150);
		
		this.getChildren().addAll(bomb,speed,radius,myImg);
		
		/*
		setPrefWidth(CELL_WIDTH*3+65);
		setPrefHeight(CELL_WIDTH*3);
		setAlignment(Pos.TOP_LEFT);
		
		setTextFill(Color.web("#000000"));
		setLabelFont();
		setText(text);*/
		
		
		
	}
	
	public void update() {
		
		bomb.setText(""+myPlayer.getBombMax());
		speed.setText(""+(myPlayer.getSpeed()-3));
		radius.setText(""+myPlayer.getBombRadius());
	}
	
	
	private void setLabelFont(Label text) {
		try {
			
			text.setFont(Font.loadFont(FONT_PATH, 16));
		} catch(Exception e) {
			text.setFont(Font.font("verdana",16));
		}
		text.setTextFill(Color.web("#000000"));
	}
}
