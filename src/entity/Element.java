package entity;

import entity.base.AnimateAble;
import entity.base.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Sprite;

public abstract class Element extends Entity implements AnimateAble  {
	int counter=0;
	int speed=14;
	boolean smoke = false;
	boolean used = false;
	public Element(Pane gamePane, int x, int y, String mapStyle) {
		super(gamePane, x, y, mapStyle);
		// TODO Auto-generated constructor stub
	}

	public boolean tick() {
		counter += 1;
		
		if (counter % speed == 0) {
			update();
			counter=0;
			speed++;
	
			if (speed ==8) {
				
				speed=14;
				counter=0;
				this.smoke=false;
				update();
				return true;
			}
		}
		return false;
	}
	

	public void setSmoke() {
		this.smoke = true;
		this.setframe(0);
		this.speed=4;
		System.out.println("setSmoke");
		update();
		
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		String show = smoke ? Sprite.SMOKE : getSymbol();
	
		ImageView a = this.getImage();
		countframe();
		if(!smoke) this.setframe(0);
		System.out.println("" + speed);
		a.setImage(new Image(getMapStyle() + show + this.getframe() % 4 + ".png"));

	}

	@Override
	public abstract String getSymbol();

}
