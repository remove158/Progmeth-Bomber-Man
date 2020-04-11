package logic;

import java.util.ArrayList;
import java.util.List;

import entity.Element;
import entity.Player;
import entity.Smoke;
import entity.base.AnimateAble;
import exception.UseItemException;
import item.Item;
import view.Game;

public class Animate {
	List<Cell> itemList;
	List<Cell> smokeList;
	Game game;
	Player player1, player2;
	
	public Animate() {
		itemList = new ArrayList<Cell>();
		smokeList = new ArrayList<Cell>();
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

	private void bombAnimate() {
		// TODO Auto-generated method stub
		
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

			if (used)
				break;

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

	public void addItem(Cell item) {
		itemList.add(item);
	}

	public void addSmoke(Cell bomb) {
		smokeList.add(bomb);
	}

	public void removeItem(Cell item) {
		itemList.remove(item);
	}

	public void removeSmoke(Cell bomb) {
		smokeList.remove(bomb);
	}

	public void clear() {
		smokeList.removeAll(smokeList);
		itemList.removeAll(itemList);
	}

}
