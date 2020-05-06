package view;

import model.MAP;
import model.MapPicker;
import model.Switch;
import music.Sound;
import model.FontStyle;
import model.GameButton;
import model.GameSubScene;
import model.InfoLabel;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ViewManager {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = WIDTH * 3 / 4;
	private AnchorPane mainPane;
	private static Scene mainScene;
	private Stage mainStage;

	private final static int MENU_BUTTON_START_X = 175;
	private final static int MENU_BUTTON_START_Y = 300;
	private final static String FONT_PATH = FontStyle.FONT_PATH;
	List<GameButton> menuButton;
	List<GameSubScene> allPanel;
	List<MapPicker> mapList;
	private MAP choosenMap;
	private GameSubScene creditsSubScene;
	private GameSubScene helpSubScene;
	private GameSubScene chooseSubScene;
	static Sound music;
	public static boolean isBotOn=true;
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		menuButton = new ArrayList<GameButton>();
		allPanel = new ArrayList<GameSubScene>();
		mainStage.setTitle("CPCU Bomber");
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);

		createMusic();
		createSubSCenes();
		createBackground();
		createMenuButton();
		createLogo();

	}

	private void createMusic() {
		music = new Sound("Stuff", 0.1);
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
		createMapChooserSubScene();
		createCreditsSubScene();
		createHelpSubScene();
	}
	
	private void createMapChooserSubScene() {
		// TODO Auto-generated method stub
		chooseSubScene = new GameSubScene();
		mainPane.getChildren().add(chooseSubScene);
		allPanel.add(chooseSubScene);
		InfoLabel chooseCharLabel = new InfoLabel("Choose Map");
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
		Switch BotControl = new Switch("Bot : ","Turn On/Off GameBot\nOn : 1 Player / 1 Bot\nOff : 2 Player");
		BotControl.setLayoutX(55);
		BotControl.setLayoutY(335);
		BotControl.setOnMouseClicked(e ->{
			BotControl.setStatus(!BotControl.getStatus());
			isBotOn = BotControl.getStatus();
		});
	
	
		chooseSubScene.getPane().getChildren().addAll(BotControl);
		
		chooseSubScene.getPane().getChildren().add(chooseCharLabel);
		chooseSubScene.getPane().getChildren().add(createMapToChoose());
		chooseSubScene.getPane().getChildren().add(go);
	}
	


	private void createHelpSubScene() {
	
		helpSubScene = new GameSubScene();
		mainPane.getChildren().add(helpSubScene);
		allPanel.add(helpSubScene);
		InfoLabel chooseCharLabel = new InfoLabel("HELP");
		chooseCharLabel.setLayoutX(110);
		chooseCharLabel.setLayoutY(25);
		
		Label help1 = new Label("PLAYER 1 : ALL KEYS");
		Label help2 = new Label("PLAYER 2 : ALL KEYS");
		Label help3 = new Label("ANOTHER KEYS");
		//help1.setPrefHeight(110); help1.setPrefWidth(120);
		//help2.setPrefHeight(110); help2.setPrefWidth(120);
		help1.setLayoutX(50);help1.setLayoutY(100); help2.setLayoutX(355);help2.setLayoutY(100); help3.setLayoutX(50);help3.setLayoutY(305); 
		help1.setFont(Font.loadFont(FONT_PATH, 19));help2.setFont(Font.loadFont(FONT_PATH, 19));help3.setFont(Font.loadFont(FONT_PATH, 19));
		help1.setStyle("-fx-border-color:red; -fx-border-width:3; -fx-padding:7px; -fx-background-color: rgba(255, 255, 255, 0.5)"); 
		help2.setStyle("-fx-border-color:blue; -fx-padding:7px; -fx-border-width:3; -fx-background-color: rgba(255, 255, 255, 0.5)");
		help3.setStyle("-fx-border-color:black; -fx-padding:7px; -fx-border-width:3; -fx-background-color: rgba(255, 255, 255, 0.5)");
		
		Label moveHelp = new Label();
		moveHelp.setText("W --> UP \nS --> DOWN \nA --> LEFT \nD --> RIGHT \nSPACEBAR --> PLANT THE BOMB");
		moveHelp.setFont(Font.loadFont(FONT_PATH, 16));
		moveHelp.setLayoutX(45);
		moveHelp.setLayoutY(160);
		
		Label moveHelp2 = new Label();
		//moveHelp2.setTextAlignment(TextAlignment.RIGHT);
		moveHelp2.setText("ARROW UP --> UP \nARROW DOWN --> DOWN \nARROW LEFT --> LEFT \nARROW RIGHT --> RIGHT \nENTER --> PLANT THE BOMB");
		moveHelp2.setFont(Font.loadFont(FONT_PATH, 16));
		moveHelp2.setLayoutX(350);
		moveHelp2.setLayoutY(160);
		
		Label moveHelp3 = new Label();
		//moveHelp2.setTextAlignment(TextAlignment.RIGHT);
		moveHelp3.setText("ESC --> MENU		R --> RESTART");
		moveHelp3.setFont(Font.loadFont(FONT_PATH, 16));
		moveHelp3.setLayoutX(100);
		moveHelp3.setLayoutY(360);
		
		helpSubScene.getPane().getChildren().add(help1);
		helpSubScene.getPane().getChildren().add(help2);
		helpSubScene.getPane().getChildren().add(help3);
		helpSubScene.getPane().getChildren().add(moveHelp);
		helpSubScene.getPane().getChildren().add(moveHelp2);
		helpSubScene.getPane().getChildren().add(moveHelp3);
		helpSubScene.getPane().getChildren().add(chooseCharLabel);
		
	}

	private void createCreditsSubScene() {
		creditsSubScene = new GameSubScene();

		allPanel.add(creditsSubScene);
		mainPane.getChildren().add(creditsSubScene);	
		
		InfoLabel chooseCharLabel = new InfoLabel("Credit");
		chooseCharLabel.setLayoutX(110);
		chooseCharLabel.setLayoutY(25);
		
		Label header = new Label("DEVELOPER TEAM");
		header.setFont(Font.loadFont(FONT_PATH, 19));
		header.setLayoutX(50);header.setLayoutY(100);
		header.setStyle("-fx-border-color:yellow; -fx-border-width:3; -fx-padding:7px; -fx-background-color: rgba(255, 255, 255, 0.5)");
		
		Label nameList = new Label();
		nameList.setText("Phiyaphat Pinyo \nID: 6231339321\nCPCU46 \n\nPunthat Siriwan \nID: 6231342121 \nCPCU46");
		nameList.setFont(Font.loadFont(FONT_PATH, 16));
		nameList.setLayoutX(45);
		nameList.setLayoutY(160);
		
		Label nickList = new Label();
		nickList.setText("  Plangton			  	Makk");
		nickList.setFont(Font.loadFont(FONT_PATH, 16));
		nickList.setLayoutX(265);
		nickList.setLayoutY(135);
		
		String pt_path = ClassLoader.getSystemResource("pt.png").toString();
		ImageView pt = new ImageView(pt_path);
		pt.setLayoutX(240); pt.setLayoutY(160);
		pt.setFitHeight(150);pt.setFitWidth(150);
		
		String m_path = ClassLoader.getSystemResource("makk.jpg").toString();
		ImageView makk = new ImageView(m_path);
		makk.setLayoutX(410); makk.setLayoutY(160);
		makk.setFitHeight(150);makk.setFitWidth(150);
		
		creditsSubScene.getPane().getChildren().addAll(pt);
		creditsSubScene.getPane().getChildren().addAll(makk);
		creditsSubScene.getPane().getChildren().add(nameList);
		creditsSubScene.getPane().getChildren().add(nickList);
		creditsSubScene.getPane().getChildren().add(header);
		creditsSubScene.getPane().getChildren().add(chooseCharLabel);
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
		
		logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.8, 0, 0)");
		logo.setLayoutX(WIDTH / 2 - 679 / 2 + 20);
		logo.setLayoutY(50);
		logo.setOnMouseEntered(e -> {
			logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.9, 0, 0);");
			new Sound("mouse_over", 2);
		});
		logo.setOnMouseExited(e -> {
			logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.8, 0, 0)");
			new Sound("mouseout", 2);
		});
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