package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class IMGParser {
	public static String[][] readIMG(String filename) {

		try {
			
			
			
			String path  =  ClassLoader.getSystemResource("map/" + filename.substring(0,4) + ".png").toString();
			Image img = new Image(path);
			ArrayList<String[]> result = new ArrayList<String[]>();
			PixelReader read = img.getPixelReader();

			for (int y = 0; y < (int) img.getHeight(); y++) {
				String[] col = new String[(int) img.getWidth()];
				
				for (int x = 0; x < (int) img.getWidth(); x++) {
					int color = read.getArgb(x, y);
					int red = (color >> 16) & 0xff;
					int green = (color >> 8) & 0xff;
				

					if (red == 255 && green == 100) {
						col[x]= ("b");
					} else if (red == 255 && green == 0) {
						col[x]= ("i");
					} else if (red == 0 && green == 255) {
						col[x]= ("t");
					} else {
						col[x]= ("0");
					}

				}
				result.add(col);
			}
			String[][] result2 = result.toArray(new String[result.size()][]);

			return result2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
