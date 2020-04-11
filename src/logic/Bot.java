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
	
		moveList.add(DOWN);
		moveList.add(LEFT);
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
			}else {
				if(myPlayer.getBombCount() == 0) {
					myPlayer.setBomb();
					calculate();
				}
			}

		}
	}

	
	private void calculate() {
		
		// TODO Auto-generated method stub
		int x=myPlayer.getX();
		int y= myPlayer.getY();
		List<List<Integer>> sample = new ArrayList<>();
		for(int i=0;i<4;i++) {
			if(checkCanGo(x, y, i)) {
				List tmp = new ArrayList<Integer>();
				tmp.add(i);
				for(int j =0 ;j<4;j++) {
					
					if(!(( (i+2)%4 == j)  || (j+2)%4 == i)) {
						if(i==0) {
							if(checkCanGo(x, y-1, j)) {
								
								tmp.add(j);
								sample.add(tmp);
							}
						}else if(i==1) {
							if(checkCanGo(x+1, y, j)) {
							
								tmp.add(j);
								sample.add(tmp);
							}
						}else if(i==2) {
							if(checkCanGo(x, y+1, j)) {
							
								tmp.add(j);
								sample.add(tmp);
							}
						}else if(i==3) {
							if(checkCanGo(x-1, y, j)) {
								
								tmp.add(j);
								sample.add(tmp);
							}
						}
					}
				}
				
			}
		}
		
		Random num = new Random();
		int index = num.nextInt(sample.size());
		
		moveList.add((sample.get(index)).get(0));
		moveList.add((sample.get(index)).get(1));
	}
	private boolean checkCanGo(int x, int y,int direction) {
		Cell[][] gameCell = game.getGameCell();
	
		if (direction == UP) {
			return gameCell[y-1][x].getIsEmpty();
		} else if (direction == RIGHT) {
			return gameCell[y][x+1].getIsEmpty();
		} else if (direction == DOWN) {
			return gameCell[y+1][x].getIsEmpty();
		} else { 
			return gameCell[y][x-1].getIsEmpty();
		}	
	}

	boolean set = false;
	int ox, oy;
	int CELL_WIDTH =65;
	private boolean move(int direction) {
		if (!set) {
			ox = myPlayer.getX();
			oy = myPlayer.getY();
			set = true;
		}

		myPlayer.movePlayer(direction);
		int x=0,y=0;
		
		if (direction == UP) {
			x=myPlayer.getX();
			y=(int) ((myPlayer.getDown()-2)/ CELL_WIDTH);
		} else if (direction == RIGHT) {
			x=(int) ((myPlayer.getLeft()+2)/CELL_WIDTH);
			y=myPlayer.getY();
		} else if (direction == DOWN) {
			x=myPlayer.getX();
			y=(int)(( myPlayer.getTop()+2 )/CELL_WIDTH);
		} else { 
			x=(int) ((myPlayer.getRight()-2)/CELL_WIDTH);
			y=myPlayer.getY();
		
		}
	
		return !((x == ox) && (y == oy));

	}
}
