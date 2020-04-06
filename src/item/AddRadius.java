package item;


import entity.Player;
import javafx.scene.layout.Pane;
import logic.Sprite;

public class AddRadius extends Item {

	public AddRadius(Pane gamePane, int x, int y,String mapStyle) {
		super(gamePane, x, y,mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(false);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.ADDRADIUS;
	}

	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub
		player.addBombRadius();
		
	}


}
