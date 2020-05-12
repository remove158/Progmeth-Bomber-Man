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
	private final static String circleNotChoosen = ClassLoader.getSystemResource("circle.png").toString();
	private final static String circleChoosen = ClassLoader.getSystemResource("choosed.png").toString();

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
		setOnMouseEntered(e -> setFxMouseEnter());
		setOnMousePressed(e -> setFxMousePressed());
		setOnMouseExited(e -> setFxMouseExited());

	}

	private void setFxMouseEnter() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				setEffect(new Glow());
				new Sound("mouse_over", 1);
			}
		});
		t.start();
		
		
	}

	private void setFxMousePressed() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				new Sound("pick",1);
			}
		});
		t.start();
	}

	private void setFxMouseExited() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				setEffect(null);
				new Sound("mouseout", 0.2);
			}
		});
		t.start();
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
