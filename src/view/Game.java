package view;

import application.Main;
import entity.*;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.Animate;
import logic.Bot;
import logic.Cell;
import logic.GameController;
import logic.GameLogic;
import logic.GameMap;
import model.EndLabel;
import model.GameButton;
import model.GameSubScene;
import model.MAP;
import model.Pause;
import model.SmallInfoLabel;
import music.Sound;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.util.Duration;

public class Game {

	private static String BACKGROUND_IMAGE;
	private AnchorPane gamePane;
	private AnchorPane playerInfo;
	private GameController gameScene;
	private static final int CELL_WIDTH = 65;
	private static final int WIDTH = 17 * CELL_WIDTH;
	private static final int HEIGHT = 11 * CELL_WIDTH + 40;
	private static final int PLAYER1_X_SET = 3;
	private static final int PLAYER1_Y_SET = 2;
	private static final int PLAYER2_X_SET = 9;
	private static final int PLAYER2_Y_SET = 9;
	private Stage menuStage;
	private AnimationTimer timer;
	private static Cell[][] gameCell;
	private MAP choosenMap;
	private Player player1, player2;

	private Game gameManager;
	private SmallInfoLabel player1Label, player2Label;
	private Label winlabel;
	private Animate animate;
	private Sound music;
	private GameLogic gameLogic;
	private Bot gameBot1, gameBot2;
	private boolean running;

	public Game(MAP choosenMap) {

		this.animate = new Animate();
		this.choosenMap = choosenMap;
		this.BACKGROUND_IMAGE = ClassLoader.getSystemResource(choosenMap.getMap() + "background1.png").toString();
		initializeStage();
		createGameLoop();
		running = true;

	}

	private void createGameElements() {

		gameLogic = new GameLogic();
		gameLogic.initialize(this);
		animate.initialize(this);
		playerInfo.getChildren().add(gameLogic.getTime());
		gameLogic.addPlayer(player1);
		gameLogic.addPlayer(player2);
		player1Label = new SmallInfoLabel(player1);
		player1Label.setLayoutX(25);
		player1Label.setLayoutY(50);
		player2Label = new SmallInfoLabel(player2);
		player2Label.setLayoutX(25);
		player2Label.setLayoutY(50 + 65 * 3);
		playerInfo.getChildren().add(player1Label);
		playerInfo.getChildren().add(player2Label);
		GameButton pause = new GameButton("pause");
		pause.setButtonPos(130, 710);
		pause.setOnMouseClicked(e -> {
			stop();
		});
		playerInfo.getChildren().add(pause);

	}

	public void showwin(String text) {
		System.out.println(text);
		winlabel.setText(text);
	}

	public MAP getChoosenMap() {
		return this.choosenMap;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	private void createMusic() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				music = new Sound("ingame", 0.06);

			}
		});
		t.start();

	}

	private AnchorPane root;
	private AnchorPane stopPane;

	private void initializeStage() {
		// TODO Auto-generated method stub
		createMusic();
		root = new AnchorPane();
		playerInfo = new AnchorPane();
		playerInfo.setPrefWidth(65 * 4);
		gamePane = new AnchorPane();
		createStopPane();

		Rectangle rec = new Rectangle();
		rec.setFill(javafx.scene.paint.Color.BLACK);
		rec.setHeight(1500);
		rec.setWidth(1500);
		root.getChildren().add(0, rec);

		AnchorPane winPane = new AnchorPane();
		winPane.setLayoutX(playerInfo.getPrefWidth() / 2);
		winlabel = new EndLabel("", WIDTH, HEIGHT);
		winPane.getChildren().add(winlabel);
		ImageView border = new ImageView(ClassLoader.getSystemResource("map1/border.png").toString());

		gamePane.setLayoutX(playerInfo.getPrefWidth());

		root.getChildren().addAll(gamePane, border, playerInfo, winPane);

		gameScene = new GameController(root, WIDTH, HEIGHT);
		gameScene.initialize(this);

		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 65, 130, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(image));

		drawGameBoard();

	}

	private Pause pauseScene;

	private void createStopPane() {
		// TODO Auto-generated method stub
		stopPane = new AnchorPane();
		pauseScene = new Pause();
		stopPane.getChildren().add(pauseScene);
		GameButton btn_menu, btx_resume, btn_restart;
		btn_menu = new GameButton("exit");
		btn_menu.setButtonPos(300, 300);
		btn_menu.setOnAction(e -> {
			
		
					pauseScene.moveSubScene();
					gameEscape();
		
		});

		btx_resume = new GameButton("resume");
		btx_resume.setButtonPos(300, 100);
		btx_resume.setOnAction(e -> {
			stop();
		});

		btn_restart = new GameButton("restart");
		btn_restart.setButtonPos(300, 200);
		btn_restart.setOnAction(e -> {
			pauseScene.moveSubScene();
			
			this.gameRestart();
			
		});

		pauseScene.getPane().getChildren().addAll(btn_menu, btn_restart, btx_resume);

		stopPane.setLayoutX(0);
		stopPane.setLayoutY(0);
		String BACKGROUND_IMAGE = ClassLoader.getSystemResource("pause.png").toString();
		BackgroundImage bg = new BackgroundImage(new Image(BACKGROUND_IMAGE, 500, 500, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		stopPane.setBackground(new Background(bg));
		Label tmp = new Label("");
		tmp.setLayoutX(1200);
		tmp.setLayoutY(1200);
		stopPane.getChildren().add(tmp);
	}

	public void stop() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				new Sound("swosh", 0.5);
				pauseScene.moveSubScene();
				
			}
		});
		t.start();
		
		
		if (running) {
			root.getChildren().remove(stopPane);
			root.getChildren().add(stopPane);
			music.stop();
			timer.stop();
			running = false;
		} else {
			root.getChildren().remove(stopPane);
			music.start();
			timer = createLoop();
			timer.start();
			running = true;
		}

		
	}

	public GameLogic getgameLogic() {
		return this.gameLogic;
	}

	private int count = 0;
	private int angle = 0;
	private boolean rotate = false;

	private void createGameLoop() {
		timer = createLoop();
		timer.start();

	}

	public AnimationTimer createLoop() {
		AnimationTimer tmp = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				count++;
				// gameBot1.run();
				if(ViewManager.isBotOn) {
					gameBot2.run();
				}
				if (count % 5 == 0) {
					if (rotate) {
						if (angle > 0) {
							gamePane.setRotate(angle);
							angle -= 2;

						}
						if (angle < 0) {
							gamePane.setRotate(angle);
							angle += 2;
						}

						if (angle == 0) {
							rotate = false;
							gamePane.setRotate(0);
						}

					}
					count = 0;

				}
				gameLogic.counttime();
				gameLogic.endgame();
				gameScene.update(); // move player
				player1.Animate(); // animate player1
				player2.Animate(); // animate player2
				animate.update();

			}

		};
		return tmp;

	}

	public void rotate(int angle) {
		this.rotate = true;
		this.angle = angle;
	}

	public void InfoUpdate() {
		player1Label.update();
		player2Label.update();
	}

	public void gameRestart() {

		music.stop();

		timer.stop();

		gameManager = new Game(choosenMap);
		gameManager.createNewGame(menuStage);

		gameLogic.restore();
		gameBot2 = new Bot(player2, this);
		gameBot1 = new Bot(player1, this);
	}

	public void gameEscape() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				new Sound("swosh", 0.5);

			}
		});
		t.start();
		
		ViewManager.startMusic();
		music.stop();
		timer.stop();
		gameLogic.restore();
		menuStage.hide();
		menuStage.setScene(ViewManager.getMainScene());
		menuStage.show();
		gameLogic.setOut_game(true);

		gameLogic.restore();
		gameBot2 = new Bot(player2, this);
		gameBot1 = new Bot(player1, this);
	}

	private GameMap map;

	private void drawGameBoard() {
		String mapStyle = choosenMap.getMap();
		map = new GameMap(gamePane, mapStyle);
		gameCell = map.getCell();

	}

	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		menuStage.hide();
		menuStage.setScene(gameScene);
		menuStage.show();
		createAvatar(choosenMap);

	}

	public Cell[][] getGameCell() {
		return this.gameCell;
	}

	public Animate getAnimate() {
		return this.animate;
	}

	private void createAvatar(MAP choosenMap) {

		player1 = new Player(gameCell, gamePane, PLAYER1_X_SET, PLAYER1_Y_SET, choosenMap.getAvatar1(), this);
		player2 = new Player(gameCell, gamePane, PLAYER2_X_SET, PLAYER2_Y_SET, choosenMap.getAvatar2(), this);

		createGameElements();
		gameLogic.rewrite(0, 0);
		gameBot2 = new Bot(player2, this);
		gameBot1 = new Bot(player1, this);
	}

	public AnchorPane getGamePane() {
		return this.gamePane;
	}

}