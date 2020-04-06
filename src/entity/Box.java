package entity;


import javafx.scene.layout.Pane;
import logic.Sprite;

public class Box extends Element {

	public Box(Pane gamePane ,int x, int y,String mapStyle) {
		super(gamePane,x,y,mapStyle);
		setSolid(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.BOX;
	}



}
