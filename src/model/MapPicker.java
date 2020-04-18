package model;

import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import music.Sound;

public class MapPicker extends VBox {

	private ImageView circleImage;
	private ImageView MapImage;
	private final static String circleNotChoosen  = ClassLoader.getSystemResource("circle.png").toString();
	private final static String circleChoosen  = ClassLoader.getSystemResource("choosed.png").toString();


	private MAP map;
	private boolean isCircleChoosen;

	public MapPicker(MAP map) {
		
	
		circleImage = new ImageView(circleNotChoosen);

		MapImage = new ImageView(map.getUrlMap());
		
		this.map = map;
		isCircleChoosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(MapImage);
		setOnMouseEntered(e -> {setEffect(new Glow());new Sound("mouse_over",1);});
		setOnMousePressed(e -> {new Sound("mouse_click",1);});
		setOnMouseExited(e -> {setEffect(null);});
		
	}
	public MAP getMap() {
		return map;
	}
	public boolean getIsCircleChoosen() {
		return isCircleChoosen;
		
	}
	
	public void setIsCirCleChoosen(boolean isCircleChoosen) {
		
		this.isCircleChoosen = isCircleChoosen;
		String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
		circleImage.setImage(new Image(imageToSet));
	}
}
