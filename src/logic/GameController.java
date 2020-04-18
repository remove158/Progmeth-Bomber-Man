package logic;

import java.awt.Color;

import entity.Player;
import javafx.animation.RotateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import view.Game;

public class GameController extends Scene {
	boolean up = false, down = false, right = false, left = false;
	boolean w = false, a = false, s = false, d = false;
	private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private Game game;

	public GameController(Parent arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub

	}

	public GameController(AnchorPane root, int width, int height) {
		// TODO Auto-generated constructor stub
		super(root, width, height);

	}

	public void update() {

		Player player1 = game.getPlayer1(), player2 = game.getPlayer2();
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

	public void initialize(Game game) {
		this.game = game;
		// TODO Auto-generated method stub
		createKeyListenner();
	}

	private void createKeyListenner() {
		// TODO Auto-generated method stub
		this.setOnMouseClicked(e -> {
			int x = (int) (e.getSceneX() - 65 * 4) / 65;
			int y = (int) (e.getSceneY()) / 65;
			if (e.getButton() == MouseButton.PRIMARY) {

				//game.getgameLogic().bombThis(x, y);
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				game.getgameLogic().setTime(26);
			}

		});
		this.setOnKeyPressed(e -> {
		
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
			if (e.getCode() == KeyCode.S) {
				s = true;
			}
			if (e.getCode() == KeyCode.SPACE) {

				game.getPlayer1().setBomb();
			}

			if (e.getCode() == KeyCode.ENTER) {
				game.getPlayer2().setBomb();
			}
			if (e.getCode() == KeyCode.R) {
				game.gameRestart();

			}

			if (e.getCode() == KeyCode.ESCAPE) {
				game.stop();
			}

		});

		this.setOnKeyReleased(e -> {
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

}
