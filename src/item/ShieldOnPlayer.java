package item;

import entity.Player;
import javafx.scene.layout.Pane;
import logic.Sprite;

public class ShieldOnPlayer extends Item {

	public ShieldOnPlayer(Pane gamePane, int x, int y, String mapStyle) {
		super(gamePane, x, y, mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(false);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.SHIELD;
	}

	public void use(Player player) {
		// TODO Auto-generated method stub
		player.setImmune(true);
	}
}
