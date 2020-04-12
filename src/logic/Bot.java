package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Bomb;
import entity.Player;
import view.Game;

public class Bot {
	Player myPlayer;
	private final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private List<Integer> moveList;
	private Game game;

	public Bot(Player player, Game game) {
		this.myPlayer = player;

		this.game = game;
		moveList = new ArrayList<Integer>();
		sample = new ArrayList<>();
		System.out.print("Bot :");
		moveList.addAll(calculate(myPlayer.getX(), myPlayer.getY(), sample));
		System.out.println("");
	}

	public void run() {

		update();

	}

	int count = 0;

	public void update() {
		if (!myPlayer.isDie()) {

			if (!moveList.isEmpty()) {

				boolean success = move(moveList.get(0));
				if (success) {
					moveList.remove(0);
					set = false;
				}
			} else {
				if (myPlayer.getBombCount() == 0) {
					myPlayer.setBomb();
					sample = new ArrayList<>();
					System.out.print("Bot :");
					moveList.addAll(calculate(myPlayer.getX(), myPlayer.getY(), sample));
					System.out.println("");
				}
			}

		}
	}

	List<Integer> sample;

	private List<Integer> calculate(int x, int y, List<Integer> e) {
		Random n = new Random();
		if (e.size() <= myPlayer.getBombRadius() * 2 + n.nextInt(3)+1) {
			List<Integer> tmp = new ArrayList<>();
			List<Integer> another = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				if (checkCanGo(x, y, i)) {
					if (sample.size() != 0) {
						int last = sample.get(sample.size() - 1);
						if (!((last + 2) % 4 == i || (i + 2) % 4 == last)) {
							if (checkBest(x, y, i)) {
								tmp.add(i);
							} else {
								another.add(i);
							}
						}
					} else {
						if (checkBest(x, y, i)) {
							tmp.add(i);
						}
					}

				}
			}

			if (tmp.size() == 0) {
				if (another.size() == 0) {
					return e;
				} else {
					tmp.add(another.get(n.nextInt(another.size())));
				}
			}

			int select = tmp.get(n.nextInt(tmp.size()));
			System.out.print("-> " + IntToString(select));
			sample.add(select);
			if (select == 0) {
				calculate(x, y - 1, sample);
			} else if (select == 1) {
				calculate(x + 1, y, sample);
			} else if (select == 2) {
				calculate(x, y + 1, sample);
			} else if (select == 3) {
				calculate(x - 1, y, sample);
			}

		}
		return e;
	}

	public String IntToString(int x) {
		if (x == 0) {
			return "UP";
		} else if (x == 1) {
			return "RIGHT";
		} else if (x == 2) {
			return "DOWN";
		}
		return "LEFT";

	}

	private boolean checkBest(int x, int y, int direc) {
		if (direc == 0) {
			y--;
		} else if (direc == 1) {
			x++;
		} else if (direc == 2) {
			y++;
		} else if (direc == 3) {
			x--;
		}

		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (checkCanGo(x, y, i)) {
				count += 2;
			}
		}
		for (Cell tmp : game.getAnimate().getBomb()) {
			if (tmp.getx() - ((Bomb) tmp.getEntity()).getRadius() <= x
					&& x <= tmp.getx() + ((Bomb) tmp.getEntity()).getRadius()) {
				if (tmp.getY() - ((Bomb) tmp.getEntity()).getRadius() <= x
						&& x <= tmp.getY() + ((Bomb) tmp.getEntity()).getRadius()) {
					count--;
				} else {
					count--;
				}
			}
		}
		return count > 1;
	}

	private boolean checkCanGo(int x, int y, int direction) {
		Cell[][] gameCell = game.getGameCell();

		if (direction == UP) {
			return gameCell[y - 1][x].getIsEmpty();
		} else if (direction == RIGHT) {
			return gameCell[y][x + 1].getIsEmpty();
		} else if (direction == DOWN) {
			return gameCell[y + 1][x].getIsEmpty();
		} else {
			return gameCell[y][x - 1].getIsEmpty();
		}
	}

	boolean set = false;
	int ox, oy;
	int CELL_WIDTH = 65;

	private boolean move(int direction) {
		if (!set) {
			ox = myPlayer.getX();
			oy = myPlayer.getY();
			set = true;
		}

		myPlayer.movePlayer(direction);
		int x = 0, y = 0;

		if (direction == UP) {
			x = myPlayer.getX();
			y = (int) ((myPlayer.getDown() - 2) / CELL_WIDTH);
		} else if (direction == RIGHT) {
			x = (int) ((myPlayer.getLeft() + 2) / CELL_WIDTH);
			y = myPlayer.getY();
		} else if (direction == DOWN) {
			x = myPlayer.getX();
			y = (int) ((myPlayer.getTop() + 2) / CELL_WIDTH);
		} else {
			x = (int) ((myPlayer.getRight() - 2) / CELL_WIDTH);
			y = myPlayer.getY();

		}

		return !((x == ox) && (y == oy));

	}
}
