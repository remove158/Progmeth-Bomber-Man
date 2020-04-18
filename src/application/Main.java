package application;
	


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewManager;



public class Main extends Application {
	private static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			ViewManager manager = new ViewManager();
			this.primaryStage = manager.getMainStage();
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setScene(Scene usingScene) {
		primaryStage.setScene(usingScene);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
