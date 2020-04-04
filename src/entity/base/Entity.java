package entity.base;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

abstract public class Entity  {
	private int x,y;
	private ImageView  myImage ;
	abstract public String getSymbol();
	private Pane gamePane;
	private  int frame=0;
	private Boolean isSolid=true;
	private String mapStyle;
	public Entity(Pane gamePane,int x,int y,String mapStyle) {
		this.mapStyle = mapStyle;
		setX(x);
		setY(y);
		this.gamePane = gamePane;
		
		drawEntity();
	}
	public Pane getGamePane() {
		return this.gamePane;
	}
	public void setSolid(Boolean x) {
		this.isSolid = x;
	}
	public Boolean isSolid() {
		return isSolid;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y= y;
		
	}

	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void countframe() {
		if (frame>=100) {
			frame=0;
		}
		this.frame +=1;
	}
	public int getframe() {
		return this.frame;
	}
	public String getMapStyle() {
		return this.mapStyle;
	}
	
	public void drawEntity() {
		System.out.println("Put" + "("+mapStyle + getSymbol() + frame + ".png) -> (" + x +","+ y  +")" );
		myImage = new ImageView(mapStyle +  getSymbol() + frame +".png");
		setPos(myImage);
		gamePane.getChildren().add(myImage);
		
	}
	public void rewrite() {
		gamePane.getChildren().remove(myImage);
		gamePane.getChildren().add(myImage);

	}
	public void sendToBack() {
		gamePane.getChildren().remove(myImage);
		gamePane.getChildren().add(0,myImage);
	}
	
	public void remove() {
		gamePane.getChildren().remove(myImage);
	}
	
	private void setPos(ImageView todo) {
		todo.setLayoutX(65*x);
		todo.setLayoutY(65*y - 25);
	}
	
	public ImageView getImage() {
		return myImage;
	}
	
	
}
