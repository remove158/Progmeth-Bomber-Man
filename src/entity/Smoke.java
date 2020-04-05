package entity;

import entity.base.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Sprite;

public class Smoke extends Entity implements AnimateAble {
	private int counter = 0;
	private int speed = 4;

	public Smoke(Pane gamePane, int x, int y,String mapStyle) {
		super(gamePane, x, y,mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(false);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.SMOKE;
	}
	
	public boolean tick() {
		counter += 1;
		
		if (counter % speed == 0) {
			update();
			counter=0;
			speed++;
	
			if (speed == 8) {
					
				
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	
		ImageView a = this.getImage();
		countframe();
		a.setImage(new Image(getMapStyle() + getSymbol() + this.getframe() % 4 + ".png"));
		
		

	}

}
