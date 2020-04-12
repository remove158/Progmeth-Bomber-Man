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
		doCalculate();
	}

	public void run() {

		update();

	}

	int notrun = 0;
	public void update() {
		if (!myPlayer.isDie()) {

			if (!moveList.isEmpty()) {
				notrun++;
				boolean success = move(moveList.get(0));
				if (success) {
					moveList.remove(0);
					set = false;
					
					notrun=0;
				}
				if(notrun >=30) {
					moveList.removeAll(moveList);
				}
				
			} else {

				if (myPlayer.getBombCount()==0) {
					myPlayer.setBomb();
				} 
				if (bombCheck(myPlayer.getX(), myPlayer.getY()) > 0) {
					doCalculate();
				}
				

			}
			
		}
	}

	List<Integer> sample;

	private void doCalculate() {
		sample = new ArrayList<>();
		System.out.print("Bot :");
		moveList.addAll(calculate(myPlayer.getX(), myPlayer.getY(), sample));
		System.out.println("");
	}

	private List<Integer> calculate(int x, int y, List<Integer> e) {
		Random n = new Random();
		/*
		 * tmp is list of sample space that player can go
		 * if tmp is empty it will random form another
		 * another contain sample that isn't bestway
		 */
		if (e.size() <= myPlayer.getBombRadius() * 2 + n.nextInt(3) + 1) {
			List<Integer> tmp = new ArrayList<>();
			List<Integer> another = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				if (checkCanGo(x, y, i)) { // check it can go
					if (e.size() != 0) {
						int last = e.get(e.size() - 1);
						if (!((last + 2) % 4 == i || (i + 2) % 4 == last)) { //isn't the way player come
							if (checkBest(x, y, i)) { //case best  
								tmp.add(i);
							} else { //case if have bomb
								another.add(i);
							}
						}
					} else { // case start calculate
						if (checkBest(x, y, i)) {//case best  
							tmp.add(i);
						} else {//case if have bomb
							another.add(i); 
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
			System.out.print("->" + IntToString(select));
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
			return "U";
		} else if (x == 1) {
			return "R";
		} else if (x == 2) {
			return "D";
		}
		return "L";

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
				//System.out.print(i);
				count += 1;
			}
		}
		//System.out.print("{"+ direc + "}BOMB:(" +bombCheck(x, y) + ")SUM:(" + count +")  " );
		count -= bombCheck(x, y);
		
		return count >= 0;
	}

	public int bombCheck(int x, int y) {
		int count = 0;
		for (Cell tmp : game.getAnimate().getBomb()) {
			int r= ((Bomb) tmp.getEntity()).getRadius();
			if(y==tmp.getY()) {
				if((tmp.getX() - r  <= x) && (x <= tmp.getX() + r)){
					count++;
				}
			}else if(x==tmp.getX()) {
				if((tmp.getY() - r  <= y) && (y <= tmp.getY() + r)){
					count++;
				}
			}	
		}

		return count;
	}

	private boolean checkCanGo(int x, int y, int direction) {
		Cell[][] gameCell = game.getGameCell();
		if (direction == 0) {
			y--;
		} else if (direction == 1) {
			x++;
		} else if (direction == 2) {
			y++;
		} else if (direction == 3) {
			x--;
		}
		if (gameCell[y][x].getEntity() instanceof Bomb) {
			return false;
		}
		return gameCell[y][x].getIsEmpty();
		
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
