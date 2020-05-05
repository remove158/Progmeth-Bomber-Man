package model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Switch extends HBox{
	private ImageView img_swt;
	private Label myText;
	private final static String path_on = ClassLoader.getSystemResource("sw_on.png").toString();
	private final static String path_off = ClassLoader.getSystemResource("sw_off.png").toString();
	private boolean isOn ;
	private Tooltip tooltip ;

	public Switch(String text,String detail) {
		super();
		tooltip = new Tooltip();
		
		myText = new Label(text);
		this.setSpacing(20);
		myText.setFont(new Font(FontStyle.FONT_PATH,15));
		isOn = true;
		img_swt =  new ImageView(path_on);
		img_swt.setFitWidth(50);
		img_swt.setFitHeight(25);
		
		this.getChildren().addAll(myText,img_swt);
		
		
		this.setOnMouseEntered(e -> {
			tooltip.setText(detail);
			tooltip.show(this, e.getScreenX(), e.getScreenY()+10);
		
		});
		this.setOnMouseExited(e ->{
			tooltip.hide();
			
		});
		this.setOnMouseClicked(e ->{
			setStatus(!isOn);
		});
	}
	
	public void setStatus(boolean tmp) {
		isOn = tmp;
		String path = isOn ? path_on:path_off;
		img_swt.setImage((new Image(path)));
		
	}
	public boolean getStatus() {
		return isOn;
	}
	
	


}
