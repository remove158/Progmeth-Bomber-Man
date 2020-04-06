package logic;

import entity.*;
import entity.base.*;
public class Cell {
	
	private Entity myEntity;
	private Boolean isEmpty;
	private Boolean isItem;
	private int x,y;
	public Cell(int x,int y) {
		isEmpty = true;
		isItem = false;
		set(x,y);
		
		
	}
	
	public int getx() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public void set(int x, int y) {
		this.x = x;
		this.y=y;
	}
	public boolean setEntity(Entity e) {
		this.myEntity = e;
		isEmpty = myEntity.isSolid();
		
		return true;
		
	}
	
	
	public void setIsItem(Boolean x) {
		isItem = x;
	}
	
	
	public Boolean getIsEmpty() {
		if(myEntity == null) {
			return true;
		}else {
			return !myEntity.isSolid();
		}
		
	}

	public void setIsEmpty(Boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Entity getEntity() {
		return myEntity;
	}
	public boolean removeEntity() {
		myEntity.remove();
		myEntity = null;
		setIsEmpty(true);
		setIsItem(false);
		return true;
	}
	

}
