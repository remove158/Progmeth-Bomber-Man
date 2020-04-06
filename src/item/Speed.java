package item;


import entity.Player;
import javafx.scene.layout.Pane;
import logic.Sprite;

public class Speed extends Item {

	public Speed(Pane gamePane, int x, int y,String mapStyle) {
		super(gamePane, x, y,mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(false);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.SPEED;
	}

	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub
		if(player.getSpeed() ==3) {
			player.setSpeed(player.getSpeed() + 1);
		}
		
	}
	


}
