package view;

import model.MAP;
import model.MapPicker;
import music.Sound;
import model.GameButton;
import model.GameSubScene;
import model.InfoLabel;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewManager {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = WIDTH * 3 / 4;
	private AnchorPane mainPane;
	private static Scene  mainScene;
	private Stage mainStage;

	private final static int MENU_BUTTON_START_X = 175;
	private final static int MENU_BUTTON_START_Y = 300;
	List<GameButton> menuButton;
	List<GameSubScene> allPanel;
	List<MapPicker> mapList;
	private MAP choosenMap;
	private GameSubScene creditsSubScene;
	private GameSubScene helpSubScene;
	private GameSubScene chooseSubScene;
	static Sound  music;
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		menuButton = new ArrayList<GameButton>();
		allPanel = new ArrayList<GameSubScene>();
		mainStage.setTitle("FINAL Project ");
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		
		createMusic();
		createSubSCenes();
		createBackground();
		createMenuButton();
		createLogo();

	}

	private void createMusic() {
		music = new Sound("Stuff",0.1);
	}
	public static void startMusic() {
		music.start();
	}

	private HBox createMapToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		box.setPadding(new Insets(0, 50, 50, 50));
		mapList = new ArrayList<>();
		box.setLayoutX(500);

		for (MAP e : MAP.values()) {
			
			MapPicker mapToPick = new MapPicker(e);
			box.getChildren().add(mapToPick);
			mapList.add(mapToPick);
			mapToPick.setOnMouseClicked(f -> {
				for (MapPicker tmp : mapList) {
					tmp.setIsCirCleChoosen(false);
				}
				mapToPick.setIsCirCleChoosen(true);
				choosenMap = mapToPick.getMap();
			});
		}
		
		box.setLayoutX(300 - (148 * 2));
		box.setLayoutY(100);
		return box;
	}

	private void createMapChooserSubScene() {
		// TODO Auto-generated method stub
		chooseSubScene = new GameSubScene();
		mainPane.getChildren().add(chooseSubScene);
		allPanel.add(chooseSubScene);
		InfoLabel chooseCharLabel = new InfoLabel("CHOOSE MAP");
		chooseCharLabel.setLayoutX(110);
		chooseCharLabel.setLayoutY(25);

		GameButton go = new GameButton("GO!");
		go.setButtonPos(450, 350);

		go.setOnAction(e -> {
			if (choosenMap != null) {
				music.stop();
				System.out.println("-> " + choosenMap.getMap());
				Game gameManager = new Game(choosenMap);

				gameManager.createNewGame(mainStage);

			}

		});

		chooseSubScene.getPane().getChildren().add(chooseCharLabel);
		chooseSubScene.getPane().getChildren().add(createMapToChoose());
		chooseSubScene.getPane().getChildren().add(go);
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	private void showSubScene(GameSubScene subScene) {

		for (GameSubScene e : allPanel) {
			if (!e.getHidde() && e != subScene) {
				e.moveSubScene();
			}
		}
		subScene.moveSubScene();

	}

	private void createSubSCenes() {
		// TODO Auto-generated method stub
		creditsSubScene = new GameSubScene();
		helpSubScene = new GameSubScene();

		createMapChooserSubScene();

		mainPane.getChildren().add(creditsSubScene);
		mainPane.getChildren().add(helpSubScene);


		allPanel.add(creditsSubScene);
		allPanel.add(helpSubScene);

	}

	public Stage getMainStage() {
		return mainStage;
	}

	private void createMenuButton() {
		createButtons("START").setOnAction(e -> showSubScene(chooseSubScene));
		createButtons("HELP").setOnAction(e -> showSubScene(helpSubScene));
		createButtons("CREDITS").setOnAction(e -> showSubScene(creditsSubScene));
		createButtons("EXIT").setOnAction(e -> System.exit(0));

	}

	private GameButton createButtons(String text) {
		GameButton button = new GameButton(text);
		button.setButtonPos(MENU_BUTTON_START_X, MENU_BUTTON_START_Y + menuButton.size() * 100);
		mainPane.getChildren().add(button);
		menuButton.add(button);
		return button;

	}

	private void createLogo() {

		String image_path = ClassLoader.getSystemResource("logo.gif").toString();
		ImageView logo = new ImageView(image_path);
		logo.setLayoutX(WIDTH / 2 - 679 / 2 + 20);
		logo.setLayoutY(50);
		logo.setOnMouseEntered(e -> logo.setEffect(new Glow()));
		logo.setOnMouseExited(e -> logo.setEffect(null));
		mainPane.getChildren().addAll(logo);
	}

	private void createBackground() {
		String image_path = ClassLoader.getSystemResource("background.png").toString();
		Image backgroundImage = new Image(image_path, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
		mainPane.setBackground(new Background(background));

	}
}
