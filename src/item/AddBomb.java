package item;


import entity.Player;
import javafx.scene.layout.Pane;
import logic.Sprite;

public class AddBomb extends Item {

	public AddBomb(Pane gamePane, int x, int y,String mapStyle) {
		super(gamePane, x, y,mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(false);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.ADDBOMB;
	}

	@Override
	public void use(Player player) throws UseItemException {
		if(player.getBombMax()  >=5) {
			throw new UseItemException("Bomb Limite At 5.");
		}else {
			player.addBombMax();
		}
		// TODO Auto-generated method stub
		
	}
	

}
