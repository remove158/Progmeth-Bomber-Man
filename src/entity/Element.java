package entity;

import java.io.FileNotFoundException;

import entity.base.AnimateAble;
import entity.base.Entity;
import exception.SetSmokeException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Sprite;

public abstract class Element extends Entity implements AnimateAble {
	int counter = 0;
	int speed = 14;
	boolean smoke = false;
	boolean used = false;

	public Element(Pane gamePane, int x, int y, String mapStyle) {
		super(gamePane, x, y, mapStyle);
		// TODO Auto-generated constructor stub
	}

	public boolean tick() {
		counter += 1;

		if (counter % speed == 0) {
			try {
				update();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Cannot tick");
			}
			counter = 0;
			speed++;

			if (speed == 8) {

				speed = 14;
				counter = 0;
				this.smoke = false;
				try {
					update();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Cannot tick");
				}
				return true;
			}
		}
		return false;
	}

	public void setSmoke() throws SetSmokeException {
		this.smoke = true;
		this.setframe(0);
		this.speed = 4;
		try {
			
			update();
		} catch (FileNotFoundException e) {
			// TODO: handle exception

			throw new SetSmokeException("path File Fail.");
		}

	}

	private void setBasic() {
		String show = getSymbol();
		ImageView a = this.getImage();
		a.setImage(new Image(getMapStyle() + show + 0 + ".png"));
	}

	@Override
	public void update() throws FileNotFoundException {
		// TODO Auto-generated method stub
	
		String show = smoke ? Sprite.SMOKE : getSymbol();

		ImageView a = this.getImage();
		countframe();
		if (!smoke)
			this.setframe(0);
		try {
			
			a.setImage(new Image(ClassLoader.getSystemResource( getMapStyle() + show + this.getframe() % 4 + ".png").toString()));
		} catch (Exception e) {
			setBasic();
			throw new FileNotFoundException();
		}

	}

	@Override
	public abstract String getSymbol();

}
