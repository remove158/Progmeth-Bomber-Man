package entity;

import java.util.ArrayList;
import java.util.List;

import entity.base.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Cell;
import logic.Sprite;
import view.Game;

public class Player {
	private ImageView avatar;
	private int x = 0, y = 0;
	private String img_url;
	private Pane gamePane;
	Cell[][] gameCell;
	private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private int CELL_WIDTH = 65;
	private static final int WIDTH = 16 * 65;
	private static final int HEIGHT = 11 * 65;
	private int count_bomb=0;
	private  int BOMB_MAX=2;
	private Game game;
	private List<Cell> animate;
	private int LIFE = 2;
	private Boolean isDie;
	private int bombRadius =1;
	
	private int speedNorm = 3;
	public Player(Cell[][] gameCell, Pane gamePane, int x, int y, String img_url,Game game) {
		this.game = game;
		this.gamePane = gamePane;
		this.img_url = img_url;
		this.x = x;
		this.y = y;
		drawPlayer();
		this.gameCell = gameCell;
		this.animate = new ArrayList<Cell>();
		this.isDie = false;
	}
	public void setCell(Cell[][] gameCell) {
		this.gameCell =gameCell;
	}
	public void getHurt() {
		LIFE--;
	}
	public int getLife() {
		return this.LIFE;
	}
	public void addBombRadius() {
		this.bombRadius +=1;
	}
	public int getBombRadius() {
		return this.bombRadius;
	}
	public void die() {
		gamePane.getChildren().remove(avatar);
		BOMB_MAX = 0;
		isDie = true;
	}
	public Boolean isDie() {
		return isDie;
	}

	public void drawPlayer() {

		avatar = new ImageView(img_url);
		avatar.setLayoutX(65 * x);
		avatar.setLayoutY(65 * y - 25);
		gamePane.getChildren().add(avatar);

	}
	
	public void setBomb() {
		//i cell[][].getElement == null can set bomb;
		
		if (gameCell[y][x].getEntity() == null && count_bomb < BOMB_MAX) {
			System.out.println("setBomb (" + x +","+ y +")");
			gameCell[y][x] = new Cell();
			gameCell[y][x].setEntity(new Bomb(gamePane, x,y, "map1/"));
			rewrite(x,y);
			animate.add(gameCell[y][x]);
			count_bomb += 1;
		}
	}
	public void addAnimate(Cell e) {
		this.animate.add(e);
	}
	
	public void bombAnimate() {
		int nx = 0,ny=0;
		Boolean removed = false;
		Boolean smoke = false;
		for (Cell tmp : animate) {
			removed = false;
			smoke = false;
			
			if (tmp.getEntity() instanceof Bomb) {
				removed = ((Bomb) tmp.getEntity()).tick();
				if (removed) {
					nx=tmp.getEntity().getX();
					ny=tmp.getEntity().getY();
					tmp.removeEntity();
					count_bomb--;
					break;
				}
			}
			if (tmp.getEntity() instanceof Smoke) {
				smoke = ((Smoke) tmp.getEntity()).tick();
				if (smoke) {
					tmp.removeEntity();
					break;
				}
			}
	

		}
		if (removed) {
			game.bombThis(nx,ny);
			int radius = bombRadius;
			for(int i=1 ; i<=radius;i++) {
				if(ny+i >=10|| ! (gameCell[ny+i][nx].getIsEmpty() || gameCell[ny+i][nx].getEntity()  instanceof Bomb ||  gameCell[ny+i][nx].getEntity() instanceof Smoke)) {
					game.bombThis(nx,ny+i);
					break;
				}
				game.bombThis(nx,ny+i);
			
			}
			
			for(int i=1 ; i<=radius;i++) {
				if(ny-i <= 0 || !( gameCell[ny-i][nx].getIsEmpty()  || gameCell[ny-i][nx].getEntity()  instanceof Bomb ||  gameCell[ny-i][nx].getEntity() instanceof Smoke)) {
					game.bombThis(nx,ny-i);
					break;
				}
				game.bombThis(nx,ny-i);
				
			}
			for(int i=1; i<=radius;i++) {
				if(nx+i >=15|| ! (gameCell[ny][nx+i].getIsEmpty() || gameCell[ny][nx+i].getEntity()  instanceof Bomb ||  gameCell[ny][nx+i].getEntity() instanceof Smoke)) {
					game.bombThis(nx+i,ny);
					break;
				}
				game.bombThis(nx+i,ny);
			}
			for(int i=1 ; i<=radius;i++) {
				if(nx-i<=0 || ! (gameCell[ny][nx-i].getIsEmpty() || gameCell[ny][nx-i].getEntity()  instanceof Bomb ||  gameCell[ny][nx-i].getEntity() instanceof Smoke)) {
					game.bombThis(nx-i,ny);
					break;
				}
				game.bombThis(nx-i,ny);
			}
		
			animate.remove(gameCell[ny][nx]);
		}
			

		

	}
	public int getBombMax() {
		return this.BOMB_MAX;
	}
	public void addBombMax() {
		this.BOMB_MAX+=1;
	}
	
	private void updatePosition() {
		this.x =(int) ((avatar.getLayoutX() + CELL_WIDTH/2)/CELL_WIDTH);
		this.y =(int)(( avatar.getLayoutY() +25 + CELL_WIDTH/2)/CELL_WIDTH);
	}

	private void set(int dir) {
		String a =img_url.substring(0,6) + dir + ".png";
		Image tmp = new Image(a);
		avatar.setImage(tmp);
		

	}

	public void rewrite(int x, int y) {
		if(!isDie) {
			game.rewrite(x, y);	
		}
		
	}

	private boolean checkMove(int dir) {
		int pos_left = ((int) avatar.getLayoutX());
		int pos_right = pos_left + CELL_WIDTH;
		int pos_top = ((int) avatar.getLayoutY())+25;
		int pos_down = pos_top + CELL_WIDTH;
		int x = (pos_left + pos_right) / 2 / CELL_WIDTH;
		int y = (pos_top  + pos_down) / 2 / CELL_WIDTH;
		
		gamePane.getChildren().remove(avatar);
		gamePane.getChildren().add(avatar);
		// bringAvatartoTop
		
		
		rewrite(x, y);
		// brinBelowCelltoTop
	
		if (dir == UP) {
			set(UP);
			int ny = (pos_top - speedNorm) / CELL_WIDTH;
			
			return (gameCell[ny][x].getIsEmpty()) && (gameCell[ny][((pos_left + speedNorm) / CELL_WIDTH)].getIsEmpty())
					&& (gameCell[ny][(pos_right - speedNorm) / CELL_WIDTH].getIsEmpty());

		} //
		if (dir == RIGHT) {
			set(RIGHT);
			int nx = (pos_right + speedNorm) / CELL_WIDTH;

			return (gameCell[y][nx].getIsEmpty()) && (gameCell[((pos_top + speedNorm) / CELL_WIDTH)][nx].getIsEmpty())
					&& (gameCell[(pos_down - speedNorm) / CELL_WIDTH][nx].getIsEmpty());

		}
		if (dir == DOWN) {
			set(DOWN);
			int ny = (pos_down + speedNorm) / CELL_WIDTH;

			return (gameCell[ny][x].getIsEmpty()) && (gameCell[ny][((pos_left + speedNorm) / CELL_WIDTH)].getIsEmpty())
					&& (gameCell[ny][(pos_right - speedNorm) / CELL_WIDTH].getIsEmpty());

		}
		if (dir == LEFT) {
			set(LEFT);
			int nx = (pos_left - speedNorm) / CELL_WIDTH;

			return (gameCell[y][nx].getIsEmpty()) && (gameCell[((pos_top + speedNorm) / CELL_WIDTH)][nx].getIsEmpty())
					&& (gameCell[(pos_down - speedNorm) / CELL_WIDTH][nx].getIsEmpty());

		}
		return false;

	}
	public int getSpeed() {
		return this.speedNorm;
	}
	public void setSpeedZero() {
		this.speedNorm = 3;
	}
	public void setSpeed(int x) {
		this.speedNorm =x;
	}
	public boolean movePlayer(int direction) {

		if (direction == UP && checkMove(UP))
			avatar.setLayoutY(avatar.getLayoutY() - speedNorm);
			

		if (direction == DOWN && checkMove(DOWN))
			avatar.setLayoutY(avatar.getLayoutY() + speedNorm);

		if (direction == RIGHT && checkMove(RIGHT))
			avatar.setLayoutX(avatar.getLayoutX() + speedNorm);

		if (direction == LEFT && checkMove(LEFT))
			avatar.setLayoutX(avatar.getLayoutX() - speedNorm);
		int ox=x,oy=y;
		updatePosition();
		if(ox!=x || oy!= y) {
			System.out.println("Player move" +"(" +ox +","+ oy+") -> ("  + x+ ","+ y+ ")");
		}
		return true;
	}

	public ImageView getAvatar() {
		return this.avatar;
	}
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

}
