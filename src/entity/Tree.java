package entity;


import javafx.scene.layout.Pane;
import logic.Sprite;

public class Tree extends Element {

	public Tree(Pane gamePane ,int x, int y,String mapStyle) {
		super(gamePane,x,y,mapStyle);
		// TODO Auto-generated constructor stub
		setSolid(true);
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return Sprite.TREE;
	}



}
