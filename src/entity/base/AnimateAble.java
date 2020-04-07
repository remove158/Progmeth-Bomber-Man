package entity.base;

import java.io.FileNotFoundException;

public interface  AnimateAble {
	
	
	public  void update() throws FileNotFoundException;
	public  boolean tick();
}
