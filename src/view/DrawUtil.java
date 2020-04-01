package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class DrawUtil {

	private static String image_path ;
	private static Image mainsprites ;

	
	public static void drawSprite(GraphicsContext gc,int x ,int y,String type) {
		String path = "map1/" + type;
		image_path =    ClassLoader.getSystemResource(path).toString();
		
		System.out.println(image_path);
		mainsprites = new Image(image_path);
		
		WritableImage img = new WritableImage(mainsprites.getPixelReader(),0,0,65,90);
	
		gc.drawImage(img, x*65, y*65-25);
	}
	

}
