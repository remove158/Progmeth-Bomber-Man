package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Block;
import entity.Box;
import entity.Element;
import entity.Player;
import entity.Smoke;
import entity.Tree;
import exception.SetSmokeException;
import item.AddBomb;
import item.AddRadius;
import item.Item;
import item.ShieldOnPlayer;
import item.Speed;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.EndLabel;
import model.MAP;
import model.TimeLabel;
import view.Game;

public class GameLogic {
	private Cell[][] gameCell;
	private Player player1, player2;
	private Game game;
	private Label win, smallwin;;// show win label;
	private Boolean end_game, game_win, out_game;
	private Label showDanger;
	private int sec = 180;
	int count = 0;
	private Label time = new TimeLabel("" + sec, 13 * CELL_WIDTH, 11 * CELL_WIDTH);;
	int bombx = 0;
	private static final int CELL_WIDTH = 65;
	private List<Cell> out;
	private static final int WIDTH = 17 * CELL_WIDTH;
	private static final int HEIGHT = 11 * CELL_WIDTH + 40;
	private Animate animate;
	private List<Player> allPlayer;

	public GameLogic() {
		out_game = false;
		game_win = false;
		end_game = false;
		allPlayer = new ArrayList<Player>();

	}

	public Label getTime() {
		return this.time;
	}

	public void initialize(Game game) {
		this.game = game;
		this.gameCell = game.getGameCell();
		player1 = game.getPlayer1();
		player2 = game.getPlayer2();
		animate = game.getAnimate();
		out = getShell();

		out.remove(97);

	}

	public List<Cell> getShell() {
		int start_x = 1, start_y = 2;
		List<Cell> outed = new ArrayList<Cell>();

		int x = 11, y = 9;
		// loop for shell
		for (int i = 0; i < (int) (y / 2); i++) {
			for (int j = i; j < x - i; j++) {
				outed.add(gameCell[start_y + i][start_x + j]);

			}
			for (int j = i + 1; j < y - i - 1; j++) {
				outed.add(gameCell[start_y + j][start_x + x - i - 1]);

			}
			for (int j = x - i - 1; j > i - 1; j--) {
				outed.add(gameCell[start_y + y - 1 - i][start_x + j]);

			}
			for (int j = y - i - 2; j > i; j--) {
				outed.add(gameCell[start_y + j][start_x + i]);

			}
			if (i == (int) (y / 2) - 1 && y % 2 == 1) {
				for (int j = i + 1; j < x - i - 1; j++) {
					outed.add(gameCell[start_y + i + 1][start_x + j]);

				}
			}
		}

		return outed;

	}

	public void setItem(int x, int y) {
		// TODO Auto-generated method stub
		Random rd = new Random();
		int num = rd.nextInt(4) + 1;
		AnchorPane gamePane = game.getGamePane();
		Animate animate = game.getAnimate();
		if (num == 1) {
			gameCell[y][x].setEntity(new Speed(gamePane, x, y, "map1/"));
			animate.add(gameCell[y][x]);

		} else if (num == 2) {
			gameCell[y][x].setEntity(new AddBomb(gamePane, x, y, "map1/"));
			animate.add(gameCell[y][x]);

		} else if (num == 3) {
			gameCell[y][x].setEntity(new AddRadius(gamePane, x, y, "map1/"));
			animate.add(gameCell[y][x]);

		} else if (num == 4) {
			gameCell[y][x].setEntity(new ShieldOnPlayer(gamePane, x, y, "map1/"));
			animate.add(gameCell[y][x]);
		}

		((Item) gameCell[y][x].getEntity()).setSmoke(); // showSmoke1Loop
	}

	public boolean randomItem(int x, int y, float percent) {
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

	public void rewrite(int x, int y) {
		AnchorPane gamePane = game.getGamePane();
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

	public Boolean getEnd_game() {
		return end_game;
	}

	public void setEnd_game(Boolean end_game) {
		this.end_game = end_game;
	}

	public Boolean getGame_win() {
		return game_win;
	}

	public void setGame_win(Boolean game_win) {
		this.game_win = game_win;
	}

	public Boolean getOut_game() {
		return out_game;
	}

	public void setOut_game(Boolean out_game) {
		this.out_game = out_game;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public void counttime() {
		// TODO Auto-generated method stub
		AnchorPane gamePane = game.getGamePane();
		count++;
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

	public void endgame() {
		// TODO Auto-generated method stub
		if (!end_game && !game_win && !out_game) {
			end();
		}

	}

	private void end() {
		// TODO Auto-generated method stub
		try {
			if (sec <= 20 && count % 12 == 0) {
				if (bombx < 98) {

					int xx = out.get(bombx).getx(), yy = out.get(bombx).getY();
					int p1_x = player1.getX();
					int p1_y = player1.getY();
					int p2_x = player2.getX();
					int p2_y = player2.getY();
					if (p1_x == xx && p1_y == yy)
						player1.die();
					if (p2_x == xx && p2_y == yy)
						player2.die();
					if (gameCell[yy][xx].getEntity() == null) {
						bombThis(xx, yy);

					} else if (gameCell[yy][xx].getEntity() instanceof Block) {
						
						Element element = (Element) gameCell[yy][xx].getEntity();
						try {
							element.setSmoke();
						} catch (SetSmokeException e) {
							System.out.println("Cannot SetSmoke ," + e.message);
						}

						animate.add(gameCell[yy][xx]);

					} else if (gameCell[yy][xx].getEntity() instanceof Element) {
						bombThis(xx, yy);

					} else if (gameCell[yy][xx].getEntity() instanceof Item) {
						bombThis(xx, yy);

					}

				}

				if (sec <= 19 && bombx - 3 < 98) {
					AnchorPane gamePane = game.getGamePane();
					int xx = out.get(bombx - 3).getx(), yy = out.get(bombx - 3).getY();
					bombThis(xx, yy);
					MAP choosenMap = game.getChoosenMap();
					if (bombx == 5) {
						bombThis(xx - 1, yy);
						bombThis(xx - 2, yy);
						gameCell[yy][xx - 1].removeEntity();
						gameCell[yy][xx - 1].setEntity(new Block(gamePane, xx - 1, yy, choosenMap.getMap()));
						gameCell[yy][xx - 2].removeEntity();
						gameCell[yy][xx - 2].setEntity(new Block(gamePane, xx - 2, yy, choosenMap.getMap()));
					}
					if (gameCell[yy][xx].getEntity() == null) {

						gameCell[yy][xx].setEntity(new Block(gamePane, xx, yy, choosenMap.getMap()));

					} else if (gameCell[yy][xx].getEntity() instanceof Block) {

					} else if (gameCell[yy][xx].getEntity() instanceof Element) {
						gameCell[yy][xx].removeEntity();
						gameCell[yy][xx].setEntity(new Block(gamePane, xx, yy, choosenMap.getMap()));
					} else if (gameCell[yy][xx].getEntity() instanceof Item) {

						gameCell[yy][xx].removeEntity();
						gameCell[yy][xx].setEntity(new Block(gamePane, xx, yy, choosenMap.getMap()));

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

	public void showwin(String text) {

		AnchorPane gamePane = game.getGamePane();
		win = new EndLabel(text, 13 * CELL_WIDTH, 11 * CELL_WIDTH);
		smallwin = new Label("<< Press R to regame , Press ESC to select map >>");
		smallwin.setLayoutX(WIDTH / 2 - 200);
		smallwin.setLayoutY(HEIGHT / 2 + 65 * 2 - 25);
		smallwin.setTextFill(Color.WHITE);
		game_win = true;
		gamePane.getChildren().add(win);
		gamePane.getChildren().add(smallwin);

	}

	public void restore() {

		count = 0;
		bombx = 0;

		game_win = false;
		end_game = false;
		out_game = false;
		animate.clear();

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

	public void bombThis(int x, int y) {
		AnchorPane gamePane = game.getGamePane();

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

				animate.remove(gameCell[y][x]);
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

	public void setTime(int time) {
		this.sec = time;
	}

	public void addPlayer(Player player) {
		allPlayer.add(player);
	}
}
