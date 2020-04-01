package logic;

import entity.*;
import entity.base.*;
public class Cell {
	
	private Entity myEntity;
	private Boolean isEmpty;
	private Boolean isItem;
	
	public Cell() {
		isEmpty = true;
		isItem = false;
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
