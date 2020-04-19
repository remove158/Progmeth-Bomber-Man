package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

import entity.Bomb;
import entity.Element;
import entity.Player;
import entity.Smoke;
import entity.base.AnimateAble;
import exception.UseItemException;
import item.Item;
import javafx.animation.TranslateTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import music.Sound;
import view.Game;


public class Animate {
	List<Cell> itemList;
	List<Cell> smokeList;
	List<Cell> bombList;
	Game game;
	Player player1, player2;
	
	public Animate() {
		itemList = new ArrayList<Cell>();
		smokeList = new ArrayList<Cell>();
		bombList = new ArrayList<Cell>();
		
	
	}

	public void update() {
		itemUpdate();
		smokeAnimate();
		bombAnimate();
	}

	public void initialize(Game game) {
		this.game = game;
		player1 = game.getPlayer1();
		player2 = game.getPlayer2();
		
		
		
	}
	
	boolean rot = false;
	
	private void shake() {
	
	
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.millis(30));
		transition.setNode(game.getGamePane());
		transition.setAutoReverse(true);
		transition.setCycleCount(25);
		if(rot) {
			transition.setToY(10);
		}else {
			transition.setToY(0);
		}
		
	
		transition.playFromStart();
		
		
	}
	
	private void bombAnimate() {
		// TODO Auto-generated method stub
		int nx = 0, ny = 0;
		Boolean removed = false;
		Cell[][] gameCell = game.getGameCell();
		int r = 0;
		// code for set image;
		
		for (Cell tmp : bombList) {
			removed = false;

			if (tmp.getEntity() instanceof Bomb) {
				removed = ((Bomb) tmp.getEntity()).tick();
				if (removed) {
					
					r = ((Bomb) tmp.getEntity()).getRadius();
					((Bomb) tmp.getEntity()).decreaseBombCount();
					nx = tmp.getEntity().getX();
					ny = tmp.getEntity().getY();
					tmp.removeEntity();
					
					
					if(rot) {
						game.rotate(0);
						rot = false;
					}else {
						game.rotate(0);
						rot = true;
					}
					shake();
					CreateSoundBomb();
					break;
				}
			}

		}

		if (removed) {
			bombList.remove(gameCell[ny][nx]);
			game.getgameLogic().bombThis(nx, ny);
			int radius = r;
			// two time bomb because if bomb first it will set to Smoke
			for (int i = 1; i <= radius; i++) {

				if (ny + i >= 10
						|| !(gameCell[ny + i][nx].getIsEmpty() || gameCell[ny + i][nx].getEntity() instanceof Bomb
								|| gameCell[ny + i][nx].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx, ny + i);
					break;
				}
				game.getgameLogic().bombThis(nx, ny + i);

			}

			for (int i = 1; i <= radius; i++) {

				if (ny - i <= 0
						|| !(gameCell[ny - i][nx].getIsEmpty() || gameCell[ny - i][nx].getEntity() instanceof Bomb
								|| gameCell[ny - i][nx].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx, ny - i);
					break;
				}
				game.getgameLogic().bombThis(nx, ny - i);

			}
			for (int i = 1; i <= radius; i++) {

				if (nx + i >= 15
						|| !(gameCell[ny][nx + i].getIsEmpty() || gameCell[ny][nx + i].getEntity() instanceof Bomb
								|| gameCell[ny][nx + i].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx + i, ny);
					break;
				}
				game.getgameLogic().bombThis(nx + i, ny);
			}
			for (int i = 1; i <= radius; i++) {

				if (nx - i <= 0
						|| !(gameCell[ny][nx - i].getIsEmpty() || gameCell[ny][nx - i].getEntity() instanceof Bomb
								|| gameCell[ny][nx - i].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx - i, ny);
					break;
				}
				game.getgameLogic().bombThis(nx - i, ny);

			}

			
		}
	}
	
	

	private void CreateSoundBomb() {
		/*
		if(soundBomb.size() > 5) {
			soundBomb.remove(0);
			soundBomb.remove(0);
			soundBomb.remove(0);
			soundBomb.remove(0);
		}
		soundBomb.add(new Sound("boom"));*/
		new Sound("boom");
		
	}

	private void smokeAnimate() {
		// TODO Auto-generated method stub
		for (Cell tmp : smokeList) {
			AnimateAble anm = (AnimateAble) tmp.getEntity();
			Boolean used = anm.tick();
			if (anm instanceof Smoke) {
				// used smoke = smoke time out
				if (used) {
					tmp.removeEntity();
					smokeList.remove(tmp);
				}
			}
			if (anm instanceof Element) {
				if (used) {
					smokeList.remove(tmp);
				}
			}

			if (used)
				break;
		}
	}

	private void itemUpdate() {
		// TODO Auto-generated method stub
		for (Cell tmp : itemList) {

			AnimateAble anm = (AnimateAble) tmp.getEntity();

			Boolean used = anm.tick();

			if (anm instanceof Item) {
				used = checkUseItem(player1, player2, tmp);
			}

			if (used) {
				createSoundItem();
				break;
			}
				

		}

	}

	private Boolean checkUseItem(Player player1, Player player2, Cell item) {
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
	


	private void createSoundItem() {
		// TODO Auto-generated method stub
		/*
	
		if(soundItem.size() > 5) {
			soundItem.remove(0);
			soundItem.remove(0);
			soundItem.remove(0);
			soundItem.remove(0);
		}
	
		soundItem.add(new Sound("pick1",0.5));*/
		new Sound("pick1",0.5);
			
			
		
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

		game.InfoUpdate();
	}
	public List<Cell> getBomb(){
		return this.bombList;
	}
	public void add(Cell tmp) {

		if (tmp.getEntity() instanceof Item) {
			itemList.add(tmp);
		}  else if (tmp.getEntity() instanceof Bomb) {
			bombList.add(tmp);
		}else  { // case smoke
			smokeList.add(tmp);
		}
	}

	public void remove(Cell tmp) {

		if (tmp.getEntity() instanceof Item) {
			itemList.remove(tmp);
		} else if (tmp.getEntity() instanceof Bomb) {
			bombList.remove(tmp);
		} else  {//case smoke;
			smokeList.remove(tmp);
		}
	}

	public void clear() {
		smokeList.removeAll(smokeList);
		itemList.removeAll(itemList);
		bombList.removeAll(bombList);
	}

}
