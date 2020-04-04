package view;

import java.util.ArrayList;
import java.util.List;

import entity.*;
import entity.base.AnimateAble;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Cell;
import logic.GameMap;
import model.MAP;
import item.*;

public class Game {
	private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private final static String FONT_PATH = "res/VAGRoundedBT-Regular.otf";
	private static String BACKGROUND_IMAGE;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private static final int CELL_WIDTH = 65;
	private static final int WIDTH = 16 * CELL_WIDTH;
	private static final int HEIGHT = 11 * CELL_WIDTH;
	private static final int PLAYER1_X_SET = 6;
	private static final int PLAYER1_Y_SET = 1;
	private static final int PLAYER2_X_SET = 12;
	private static final int PLAYER2_Y_SET = 8;
	private Stage menuStage;
	private AnimationTimer timer;
	boolean up = false, down = false, right = false, left = false;
	boolean w = false, a = false, s = false, d = false;
	private static Cell[][] gameCell;
	private GraphicsContext gc;
	private MAP choosenMap;

	int pos_top, pos_down, pos_left, pos_right, p1_x, p1_y;
	private Player player1, player2;
	private static List<Player> allPlayer;
	private Game gameManager;
	private SmallInfoLabel player1Label, player2Label;
	private ImageView[] player1lifes, player2lifes;
	private int player1life, player2life;
	private List<Cell> itemList;
	public Game(MAP choosenMap) {
		this.itemList = new ArrayList<Cell>();
		this.allPlayer = new ArrayList<Player>();

		this.choosenMap = choosenMap;
		this.BACKGROUND_IMAGE = choosenMap.getUrlMap().substring(0, 5) + "background1.png";
		initializeStage();
		createKeyListeners();
		createGameLoop();

	}

	private void createGameElements() {
		// System.out.println("Chreating Element....");
		// TODO Auto-generated method stub
		player1life = player1.getLife();
		player2life = player2.getLife();
		player1lifes = new ImageView[player1life];
		player2lifes = new ImageView[player2life];
		player1Label = new SmallInfoLabel("PLAYER1 BOMB : " + player1.getBombMax());
		player1Label.setLayoutX(0);
		player1Label.setLayoutY(0);

		player2Label = new SmallInfoLabel("PLAYER2 BOMB : " + player2.getBombMax());
		player2Label.setLayoutX(0);
		player2Label.setLayoutY(65 * 3);

		for (int i = 0; i < player1.getLife(); i++) {
			player1lifes[i] = new ImageView(choosenMap.getLife());
			player1lifes[i].setLayoutY(90);
			player1lifes[i].setLayoutX(30 + CELL_WIDTH * i);
			gamePane.getChildren().add(player1lifes[i]);
		}
		for (int i = 0; i < player2.getLife(); i++) {
			player2lifes[i] = new ImageView(choosenMap.getLife());
			player2lifes[i].setLayoutY(90 + CELL_WIDTH * 3);
			player2lifes[i].setLayoutX(30 + CELL_WIDTH * i);
			gamePane.getChildren().add(player2lifes[i]);
		}

		gamePane.getChildren().add(0, player2Label);
		gamePane.getChildren().add(0, player1Label);

		player1.movePlayer(DOWN);
		player2.movePlayer(DOWN);
		// System.out.println("Chreate Element");
	}

	private void removePlayer1Life() {
		gamePane.getChildren().remove(player1lifes[player1.getLife() - 1]);
		player1.getHurt();
		if (player1.getLife() == 0) {
			player1.die();
		}
	}

	private void removePlayer2Life() {
		gamePane.getChildren().remove(player2lifes[player2.getLife() - 1]);
		player2.getHurt();
		if (player2.getLife() == 0) {
			player2.die();
		}
	}

	private void createKeyListeners() {

		gameScene.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.LEFT)
				left = true;
			if (e.getCode() == KeyCode.RIGHT)
				right = true;
			if (e.getCode() == KeyCode.UP)
				up = true;
			if (e.getCode() == KeyCode.DOWN)
				down = true;
			if (e.getCode() == KeyCode.A)
				a = true;
			if (e.getCode() == KeyCode.D)
				d = true;
			if (e.getCode() == KeyCode.W)
				w = true;
			if (e.getCode() == KeyCode.S)
				s = true;
			if (e.getCode() == KeyCode.SPACE)
				player1.setBomb();
			if (e.getCode() == KeyCode.ENTER)
				player2.setBomb();

			if (e.getCode() == KeyCode.R) {
				gameStage.close();
				gameManager = new Game(choosenMap);
				gameManager.createNewGame(menuStage);
				restore();

			}
			if (e.getCode() == KeyCode.ESCAPE) {
				gameStage.close();
				menuStage.show();
				restore();
			}

		});

		gameScene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.LEFT)
				left = false;
			if (e.getCode() == KeyCode.RIGHT)
				right = false;
			if (e.getCode() == KeyCode.UP)
				up = false;
			if (e.getCode() == KeyCode.DOWN)
				down = false;
			if (e.getCode() == KeyCode.W)
				w = false;
			if (e.getCode() == KeyCode.A)
				a = false;
			if (e.getCode() == KeyCode.S)
				s = false;
			if (e.getCode() == KeyCode.D)
				d = false;

		});

	}

	private void initializeStage() {
		// TODO Auto-generated method stub
		gamePane = new AnchorPane();

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		gamePane.getChildren().add(canvas);

		gameScene = new Scene(gamePane, WIDTH, HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);

		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 65, 130, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(image));

		drawGameBoard(gc);

	}

	public void bombThis(int x, int y) {
		
		for (int i = 0; i < 2; i++) {
			Player tmp = allPlayer.get(i);
			if (tmp.getX() == x & tmp.getY() == y) {

				if (i == 0 && !tmp.isDie()) {
					removePlayer1Life();
					System.out.println("Player" + (i + 1) + "got damage ( has " + (tmp.getLife()) + ")");
				} else if (i == 1 && !tmp.isDie()) {
					removePlayer2Life();
					System.out.println("Player" + (i + 1) + "got damage ( has " + (tmp.getLife()) + ")");
				}
			}

		}
		
		if(gameCell[y][x].getEntity() == null) {
			gameCell[y][x].setEntity(new Smoke(gamePane,x,y,"map1/"));
			rewrite(x,y);
			player1.addAnimate(gameCell[y][x]);
		}

		if (!gameCell[y][x].getIsEmpty()&& (gameCell[y][x].getEntity() instanceof Tree || gameCell[y][x].getEntity() instanceof Box)) {
			if (gameCell[y][x].getEntity() instanceof Box) {
				gameCell[y][x].removeEntity();
				gameCell[y][x].setEntity(new Speed(gamePane, x,y, "map1/"));
				itemList.add(gameCell[y][x]);
				rewrite(x,y);
				System.out.println("add");
				
				
				/*player1.addBombMax();
				player1.addBombRadius();
				player1Label.setText("PLAYER 1 BOMB : " + player1.getBombMax());
				
				*/
			}else {
				gameCell[y][x].removeEntity();
				
				gameCell[y][x].setEntity(new Smoke(gamePane,x,y,"map1/"));
				rewrite(x,y);
				player1.addAnimate(gameCell[y][x]);
				
			}

			
		}
		
		
	}

	private void createGameLoop() {
		
		timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
	
	
				move();
				player1.Animate();
				player2.Animate();
				checkItemUse();
			}

			private void checkItemUse() {
				// TODO Auto-generated method stub
				for(Cell tmp : itemList) {
	
					AnimateAble anm = (AnimateAble) tmp.getEntity();
					Boolean used = anm.tick();
			
					if(anm instanceof Speed) {	
					
						used = checkUseItem(player1,player2,"Speed",tmp);
				
					}
					if(anm instanceof AddBomb ) {
						used =checkUseItem(player1,player2,"AddBomb",tmp);
					}
					
					if(used) break;
					
				
					
				}
			}

		

			private boolean checkUseItem(Player player1, Player player2, String type, Cell item) {
				// TODO Auto-generated method stub
				int item_x  = item.getEntity().getX();
				int item_y = item.getEntity().getY();
				int p1_x = player1.getX();
				int p1_y = player1.getY();
				int p2_x = player2.getX();
				int p2_y = player2.getY();
			
				if(item_x == p1_x && item_y == p1_y) {
					useitem(player1,type);
					itemList.remove(item);
					return true;
				}
				if(item_x == p2_x && item_y == p2_y) {
				
					useitem(player2,type);
			
					itemList.remove(item);
					item.removeEntity();
			
					return true;
				}
				return false;
			}

			private void useitem(Player player, String item) {
				// TODO Auto-generated method stub
				switch (item) {
				case "Speed": {
					System.out.println("Seting speed to 4");
					player.setSpeed(player.getSpeed()+ 1  );
					System.out.println("Seted speed to 4");
					break;
				}
				case "AddBomb":{
					player.addBombMax();
				}
				default:
					System.out.println("Unexpected value: " + item);
				}
			}

			private void move() {
				// TODO Auto-generated method stub
				if (!player1.isDie()) {
					if (w)
						player1.movePlayer(UP);
					if (d)
						player1.movePlayer(RIGHT);
					if (s)
						player1.movePlayer(DOWN);
					if (a)
						player1.movePlayer(LEFT);
				}
				if (!player2.isDie()) {
					if (up)
						player2.movePlayer(UP);
					if (right)
						player2.movePlayer(RIGHT);
					if (down)
						player2.movePlayer(DOWN);
					if (left)
						player2.movePlayer(LEFT);
				}
			}

		};

		timer.start();
	}
	


	public void rewrite(int x, int y) {

		for (int i = y + 1; i < gameCell.length; i++) {
			for (int j = 3; j <= 15; j++) {
				if (!gameCell[i][j].getIsEmpty())
					gameCell[i][j].getEntity().rewrite();

			}

			if (!player1.isDie()) {
				if (i == player1.getY()) {
					gamePane.getChildren().remove(player1.getAvatar());
					gamePane.getChildren().add(player1.getAvatar());
				}
			}

			if (!player2.isDie()) {
				if (i == player2.getY()) {
					gamePane.getChildren().remove(player2.getAvatar());
					gamePane.getChildren().add(player2.getAvatar());
				}

			}
		}

	}

	private void drawGameBoard(GraphicsContext gc) {

		String mapStyle = choosenMap.getUrlMap().substring(0, 4);
		GameMap map = new GameMap(gamePane, mapStyle);
		gameCell = map.getCell();

	}

	public Stage getGameStage() {
		return gameStage;
	}

	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		menuStage.close();

		createAvatar(choosenMap);

		gameStage.show();

	}

	private void restore() {
		player1 = new Player(gameCell, gamePane, PLAYER1_X_SET, PLAYER1_Y_SET, choosenMap.getAvatar1(), this);
		player2 = new Player(gameCell, gamePane, PLAYER2_X_SET, PLAYER2_Y_SET, choosenMap.getAvatar2(), this);
	}

	private void createAvatar(MAP choosenMap) {

		player1 = new Player(gameCell, gamePane, PLAYER1_X_SET, PLAYER1_Y_SET, choosenMap.getAvatar1(), this);
		player2 = new Player(gameCell, gamePane, PLAYER2_X_SET, PLAYER2_Y_SET, choosenMap.getAvatar2(), this);
		allPlayer.add(player1);
		allPlayer.add(player2);
		createGameElements();

	}

}
