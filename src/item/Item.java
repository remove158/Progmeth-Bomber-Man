package item;

import entity.Player;
import entity.base.AnimateAble;
import entity.base.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Sprite;

public abstract class Item extends Entity implements AnimateAble  {
	private int counter = 0;
	private int speed = 14;
	private boolean used= false;
	private boolean smoke = false;
	public Item(Pane gamePane, int x, int y, String mapStyle) {
		super(gamePane, x, y, mapStyle);
		// TODO Auto-generated constructor stub
	}
	
	public boolean tick() {
		counter += 1;
		
		if (counter % speed == 0) {
			if(smoke && this.getframe()==5) {
				smoke=false;
				speed=14;
			}
			update();
			counter=0;
			
		}
		return used;
	}
	

	public void setSmoke() {
		this.smoke = true;
		this.setframe(0);
		this.speed=4;
		update();
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		String show = smoke ? Sprite.SMOKE : getSymbol();
		
		ImageView a = this.getImage();
		countframe();
		a.setImage(new Image(getMapStyle() + show + this.getframe() % 4 + ".png"));

	}
	
	@Override
	abstract public String getSymbol();
	
	abstract public void use(Player player) throws UseItemException;

}
