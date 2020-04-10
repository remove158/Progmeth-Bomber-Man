package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.*;
import entity.base.AnimateAble;

import javafx.animation.AnimationTimer;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import javafx.scene.paint.Color;

import javafx.stage.Stage;

import logic.Cell;
import logic.GameMap;
import model.MAP;
import item.*;

public class Game {
	private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

	private static String BACKGROUND_IMAGE;
	private AnchorPane gamePane;
	private AnchorPane playerInfo;
	private Scene gameScene;
	private Stage gameStage;
	private static final int CELL_WIDTH = 65;
	private static final int WIDTH = 17 * CELL_WIDTH;
	private static final int HEIGHT = 11 * CELL_WIDTH + 40;
	private static final int PLAYER1_X_SET = 3;
	private static final int PLAYER1_Y_SET = 2;
	private static final int PLAYER2_X_SET = 9;
	private static final int PLAYER2_Y_SET = 9;
	private Stage menuStage;
	private AnimationTimer timer;
	boolean up = false, down = false, right = false, left = false;
	boolean w = false, a = false, s = false, d = false;
	private static Cell[][] gameCell;
	private MAP choosenMap;
	int pos_top, pos_down, pos_left, pos_right, p1_x, p1_y;
	private Player player1, player2;
	private List<Player> allPlayer;
	private Game gameManager;
	private SmallInfoLabel player1Label, player2Label;

	private List<Cell> itemList;
	private List<Cell> animate;
	private Label time;
	private List<Cell> out;
	private int sec = 180;
	int count = 0;
	private Label win, smallwin;;// show win label;
	private Boolean end_game, game_win, out_game;
	private Label showDanger;

	public Game(MAP choosenMap) {

		out_game = false;
		game_win = false;
		end_game = false;
		this.itemList = new ArrayList<Cell>();
		this.allPlayer = new ArrayList<Player>();
		this.animate = new ArrayList<Cell>();
		this.choosenMap = choosenMap;
		this.BACKGROUND_IMAGE = ClassLoader.getSystemResource(choosenMap.getMap()+ "background1.png").toString();
		
		initializeStage();
		
		
		createKeyListeners();

		createGameLoop();

		out = out();
		out.remove(97);
		

	}

	private void createGameElements() {
		// System.out.println("Chreating Element....");
		// TODO Auto-generated method stub

		time = new TimeLabel("" + sec, 13 * CELL_WIDTH, 11 * CELL_WIDTH);
		playerInfo.getChildren().add(time);

		player1Label = new SmallInfoLabel(player1);
		player1Label.setLayoutX(25);
		player1Label.setLayoutY(50);
		player2Label = new SmallInfoLabel(player2);
		player2Label.setLayoutX(25);
		player2Label.setLayoutY(50 + 65 * 3);
		playerInfo.getChildren().add(player1Label);
		playerInfo.getChildren().add(player2Label);

		// System.out.println("Chreate Element");
	}

	private void removePlayer1Life() {

		player1.getHurt();

		if (player1.getLife() == 0) {
			player1.die();
			showwin("WIN");
		}
	}

	private void removePlayer2Life() {

		player2.getHurt();
		if (player2.getLife() == 0) {
			player2.die();
			showwin("WIN");
		}
	}

	int bombx = 0;

	private void createKeyListeners() {
		gameScene.setOnMouseClicked(e -> {
			int x = (int) (e.getSceneX() - playerInfo.getPrefWidth()) / CELL_WIDTH;
			int y = (int) (e.getSceneY()) / CELL_WIDTH;
			if (e.getButton() == MouseButton.PRIMARY) {
				bombThis(x, y);
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				if (!end_game) {
					this.sec = 26;
				}
				/*
				 * Element element = (Element) gameCell[yy][xx].getEntity(); element.setSmoke();
				 * animate.add(gameCell[yy][xx]);
				 */

			}

		});

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
				timer.stop();
				gameStage.close();

				gameManager = new Game(choosenMap);
				gameManager.createNewGame(menuStage);

				restore();

			}

			if (e.getCode() == KeyCode.ESCAPE) {
				timer.stop();
				restore();
				gameStage.close();
				menuStage.show();
				out_game = true;
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
		AnchorPane root = new AnchorPane();

		playerInfo = new AnchorPane();
		playerInfo.setPrefWidth(65 * 4);
		gamePane = new AnchorPane();
		ImageView border = new ImageView(ClassLoader.getSystemResource("map1/border.png").toString());
		gamePane.setLayoutX(playerInfo.getPrefWidth());

		root.getChildren().addAll(gamePane, border, playerInfo);

		gameScene = new Scene(root, WIDTH, HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);

		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 65, 130, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(image));

		drawGameBoard();

	}

	public void bombThis(int x, int y) {

		try {

			for (int i = 0; i < allPlayer.size(); i++) {

				Player tmp = allPlayer.get(i);
				if (tmp.getX() == x & tmp.getY() == y) {

					if (i == 0 && !tmp.isDie()) {
						removePlayer1Life();
						System.out.println("Player" + (i + 1) + " got damage ( has " + (tmp.getLife()) + ")");
					} else if (i == 1 && !tmp.isDie()) {
						removePlayer2Life();
						System.out.println("Player" + (i + 1) + " got damage ( has " + (tmp.getLife()) + ")");
					}
				}

			}

			// case null or smoke => set new Smoke(x,y);
			if (gameCell[y][x].getEntity() == null || gameCell[y][x].getEntity() instanceof Smoke) {
				if (gameCell[y][x].getEntity() instanceof Smoke) {
					animate.remove(gameCell[y][x]);
					gameCell[y][x].removeEntity();
				}
				gameCell[y][x].setEntity(new Smoke(gamePane, x, y, "map1/"));
				rewrite(x, y);
				animate.add(gameCell[y][x]);
			}

			// remove if it is item
			if (gameCell[y][x].getEntity() instanceof Item) {

				itemList.remove(gameCell[y][x]);
				gameCell[y][x].removeEntity();

				gameCell[y][x].setEntity(new Smoke(gamePane, x, y, "map1/"));
				animate.add(gameCell[y][x]);

				rewrite(x, y);
			}

			// if it can get item
			if (!gameCell[y][x].getIsEmpty()
					&& (gameCell[y][x].getEntity() instanceof Tree || gameCell[y][x].getEntity() instanceof Box)) {

				if (gameCell[y][x].getEntity() instanceof Box) {

					gameCell[y][x].removeEntity();
					setItem(x, y);

					// 100% box get item

				} else if (gameCell[y][x].getEntity() instanceof Tree) {

					gameCell[y][x].removeEntity();
					Boolean got = randomItem(x, y, 50);// random item with 50 percent

					if (!got) {

						gameCell[y][x].setEntity(new Smoke(gamePane, x, y, "map1/"));
						animate.add(gameCell[y][x]);
					}

				}
				rewrite(x, y);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cannot Bomb (" + x + "," + y + ") , index out of range.");
		}
		
		if (player1.getLife() == 0) {
			player1.die();
			showwin("WIN");
		}
		if (player2.getLife() == 0) {
			player2.die();
			showwin("WIN");
		}

	}

	private void setItem(int x, int y) {
		// TODO Auto-generated method stub
		Random rd = new Random();
		int num = rd.nextInt(4) + 1;
		if (num == 1) {
			gameCell[y][x].setEntity(new Speed(gamePane, x, y, "map1/"));
			itemList.add(gameCell[y][x]);

		} else if (num == 2) {
			gameCell[y][x].setEntity(new AddBomb(gamePane, x, y, "map1/"));
			itemList.add(gameCell[y][x]);

		} else if (num == 3) {
			gameCell[y][x].setEntity(new AddRadius(gamePane, x, y, "map1/"));
			itemList.add(gameCell[y][x]);

		} else if (num == 4) {
			gameCell[y][x].setEntity(new ShieldOnPlayer(gamePane, x, y, "map1/"));
			itemList.add(gameCell[y][x]);
		}
		((Item) gameCell[y][x].getEntity()).setSmoke();
	}

	private boolean randomItem(int x, int y, float percent) {
		// TODO Auto-generated method stub
		Random rd = new Random();
		float num = rd.nextInt(10000) / 100;
		System.out.println("Random Item (" + x + "," + y + ")");
		if (num < percent) {
			setItem(x, y);
			return true;
		}
		return false;
	}

	private void createGameLoop() {

		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub

				count++;
				counttime();
				if (!end_game && !game_win && !out_game) {
					endgame();
				}
				move();
				player1.Animate();
				player2.Animate();
				checkItemUse();
				animate();

			}

			private void counttime() {
				// TODO Auto-generated method stub

				if (count % 60 == 0) {
					sec--;
					if (sec < 0)
						sec = 0;
					time.setText("" + sec);

					count = 0;
					if (sec == 25) {
						time.setEffect(new Glow());
						time.setTextFill(Color.web("#FFA500"));

						showDanger = new EndLabel("DANGER", 13 * CELL_WIDTH, 11 * CELL_WIDTH);

						showDanger.setTextFill(Color.RED);
						if (!game_win)
							gamePane.getChildren().add(showDanger);
					}
					if (sec == 23) {
						gamePane.getChildren().remove(showDanger);
					}
					if (sec == 10) {
						time.setTextFill(Color.web("#FF0000"));
					}

				}
			}

			private void endgame() {
				// TODO Auto-generated method stub
				try {
					if (sec <= 20 && count % 12 == 0) {
						if (bombx < 98) {
								
							int xx = out.get(bombx).getx(), yy = out.get(bombx).getY();
							int p1_x = player1.getX();
							int p1_y = player1.getY();
							int p2_x = player2.getX();
							int p2_y = player2.getY();
							if(p1_x == xx && p1_y == yy) player1.die();
							if(p2_x == xx && p2_y == yy) player2.die();
							if (gameCell[yy][xx].getEntity() == null) {
								bombThis(xx, yy);

								// System.out.println("" + xx + "," + yy + "->" + " null ," +
								// gameCell[yy][xx].getIsEmpty());

							} else if (gameCell[yy][xx].getEntity() instanceof Block) {
								Element element = (Element) gameCell[yy][xx].getEntity();
								try {
									element.setSmoke();
								} catch (SetSmokeException e) {
									System.out.println("Cannot SetSmoke ," + e.message);
								}

								animate.add(gameCell[yy][xx]);
								// System.out.println("" + xx + "," + yy + "->" +
								// gameCell[yy][xx].getEntity().getClass() + ","+
								// gameCell[yy][xx].getIsEmpty());

							} else if (gameCell[yy][xx].getEntity() instanceof Element) {

								// System.out.println("" + xx + "," + yy + "->" +
								// gameCell[yy][xx].getEntity().getClass() + ","
								bombThis(xx, yy);

							} else if (gameCell[yy][xx].getEntity() instanceof Item) {

								// System.out.println("" + xx + "," + yy + "->" +
								// gameCell[yy][xx].getEntity().getClass() + ","
								bombThis(xx, yy);

							}

						}

						if (sec <= 19 && bombx - 3 < 98) {

							int xx = out.get(bombx - 3).getx(), yy = out.get(bombx - 3).getY();
							bombThis(xx, yy);

							if (bombx == 5) {
								bombThis(xx - 1, yy);
								bombThis(xx - 2, yy);
								gameCell[yy][xx - 1].removeEntity();
								gameCell[yy][xx - 1].setEntity(
										new Block(gamePane, xx - 1, yy, choosenMap.getMap()));
								gameCell[yy][xx - 2].removeEntity();
								gameCell[yy][xx - 2].setEntity(
										new Block(gamePane, xx - 2, yy, choosenMap.getMap()));
							}
							if (gameCell[yy][xx].getEntity() == null) {

								gameCell[yy][xx]
										.setEntity(new Block(gamePane, xx, yy,choosenMap.getMap()));

							} else if (gameCell[yy][xx].getEntity() instanceof Block) {

							} else if (gameCell[yy][xx].getEntity() instanceof Element) {
								gameCell[yy][xx].removeEntity();
								gameCell[yy][xx]
										.setEntity(new Block(gamePane, xx, yy,choosenMap.getMap()));
							} else if (gameCell[yy][xx].getEntity() instanceof Item) {

								gameCell[yy][xx].removeEntity();
								gameCell[yy][xx]
										.setEntity(new Block(gamePane, xx, yy,choosenMap.getMap()));

							}
							rewrite(xx, yy);
						}

						if (sec == 0) {

							showwin("TIMEOUT");
							end_game = true;
						}

						bombx++;

					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Cannot Set Endgame , Index out of range. ");
				}

			}

			private void animate() {
				// TODO Auto-generated method stub
				for (Cell tmp : animate) {
					AnimateAble anm = (AnimateAble) tmp.getEntity();
					Boolean used = anm.tick();
					if (anm instanceof Smoke) {
						// used smoke = smoke time out
						if (used) {
							tmp.removeEntity();
							animate.remove(tmp);
						}
					}
					if (anm instanceof Element) {
						if (used) {

							animate.remove(tmp);
						}
					}

					if (used)
						break;
				}

			}

			private void checkItemUse() {
				// TODO Auto-generated method stub
				for (Cell tmp : itemList) {

					AnimateAble anm = (AnimateAble) tmp.getEntity();
					Boolean used = anm.tick();

					if (anm instanceof Item) {

						used = checkUseItem(player1, player2, tmp);

					}

					if (used)
						break;

				}

			}

			private boolean checkUseItem(Player player1, Player player2, Cell item) {
				// TODO Auto-generated method stub
				int item_x = item.getEntity().getX();
				int item_y = item.getEntity().getY();
				int p1_x = player1.getX();
				int p1_y = player1.getY();
				int p2_x = player2.getX();
				int p2_y = player2.getY();

				if (item_x == p1_x && item_y == p1_y) {
					useitem(player1, item);

					itemList.remove(item);

					return true;
				}
				if (item_x == p2_x && item_y == p2_y) {

					useitem(player2, item);

					itemList.remove(item);

					return true;
				}
				return false;
			}

			private void useitem(Player player, Cell tmp) {
				// TODO Auto-generated method stub
				// player used item
				Item item = (Item) tmp.getEntity();
				try {
					item.use(player);
				} catch (UseItemException e) {
					System.out.println("Cannot Use Item ," + e.message);
				}

				tmp.removeEntity(); // remove item that used

				player1Label.update();
				player2Label.update();
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

	public void showwin(String text) {
		win = new EndLabel(text, 13 * CELL_WIDTH, 11 * CELL_WIDTH);
		smallwin = new Label("<< Press R to regame , Press ESC to select map >>");
		smallwin.setLayoutX(WIDTH / 2 - 200);
		smallwin.setLayoutY(HEIGHT / 2 + 65 * 2 - 25);
		smallwin.setTextFill(Color.WHITE);
		game_win = true;
		gamePane.getChildren().add(win);
		gamePane.getChildren().add(smallwin);

	}

	public void rewrite(int x, int y) {

		for (int i = y + 1; i < gameCell.length; i++) {
			for (int j = 1; j < gameCell[0].length; j++) {
				try {

					if (!gameCell[i][j].getIsEmpty())
						gameCell[i][j].getEntity().rewrite();
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("rewrite out of range.");
				}

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

		if (game_win) {
			gamePane.getChildren().remove(win);
			gamePane.getChildren().add(win);
			gamePane.getChildren().remove(smallwin);
			gamePane.getChildren().add(smallwin);
			gamePane.getChildren().remove(showDanger);
		}
		if (!game_win) {
			if (sec <= 25 && sec >= 23) {
				gamePane.getChildren().remove(showDanger);
				gamePane.getChildren().add(showDanger);
			}

		}
		if (sec == 23) {
			gamePane.getChildren().remove(showDanger);
		}

	}

	private List<Cell> out() {
		int start_x = 1, start_y = 2;
		List<Cell> out = new ArrayList<Cell>();

		int x = 11, y = 9;
		// loop for shell
		for (int i = 0; i < (int) (y / 2); i++) {
			for (int j = i; j < x - i; j++) {
				out.add(gameCell[start_y + i][start_x + j]);
				count++;

			}
			for (int j = i + 1; j < y - i - 1; j++) {
				out.add(gameCell[start_y + j][start_x + x - i - 1]);
				count++;

			}
			for (int j = x - i - 1; j > i - 1; j--) {
				out.add(gameCell[start_y + y - 1 - i][start_x + j]);
				count++;

			}
			for (int j = y - i - 2; j > i; j--) {
				out.add(gameCell[start_y + j][start_x + i]);
				count++;

			}
			if (i == (int) (y / 2) - 1 && y % 2 == 1) {
				for (int j = i + 1; j < x - i - 1; j++) {
					out.add(gameCell[start_y + i + 1][start_x + j]);
					count++;

				}
			}
		}

		return out;

	}

	GameMap map;

	private void drawGameBoard() {

		String mapStyle = choosenMap.getMap();
		map = new GameMap(gamePane, mapStyle);
		gameCell = map.getCell();

	}

	public Stage getGameStage() {
		return gameStage;
	}

	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		menuStage.close();

		
		createAvatar(choosenMap);
		gameStage.setTitle("FINAL Project");
		gameStage.show();

	}

	private void restore() {
		// timer.stop();

		count = 0;
		bombx = 0;

		game_win = false;
		end_game = false;
		animate.removeAll(animate);
		itemList.removeAll(itemList);

		out_game = false;

	}

	private void createAvatar(MAP choosenMap) {

		player1 = new Player(gameCell, gamePane, PLAYER1_X_SET, PLAYER1_Y_SET, choosenMap.getAvatar1(), this);
		player2 = new Player(gameCell, gamePane, PLAYER2_X_SET, PLAYER2_Y_SET, choosenMap.getAvatar2(), this);
		allPlayer.add(player1);
		allPlayer.add(player2);
		createGameElements();
		rewrite(0, 0);

	}

}
