package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;
import music.Sound;

public class GameSubScene extends SubScene {

	private final static String BACKGROUND_IMAGE = ClassLoader.getSystemResource("panel.png").toString();
	private boolean isHidden;
	public GameSubScene() {
		
		super(new AnchorPane(),600,400);
		prefWidth(600);
		prefHeight(400);
	
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,600,400,false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		// TODO Auto-generated constructor stub
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		isHidden = true;
		setLayoutX(1024);
		setLayoutY(250);
		
	}
	   public boolean getHide() {
		   return isHidden;
	   }
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				new Sound("swosh",0.2);
				
				
				if(isHidden) {
					transition.setToX(-676);
					isHidden =false;
				}else {
					transition.setToX(0);
					isHidden =true;
				
				}
				
				
				transition.play();
			}
		});
		t.start();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
	
}
