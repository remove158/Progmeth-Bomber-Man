package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import logic.Cell;
import view.Game;

public class Player {
	private ImageView avatar;
	private int x = 0, y = 0;
	private String img_url;
	private Pane gamePane;
	Cell[][] gameCell;
	private final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private int CELL_WIDTH = 65;
	private int count_bomb=0;
	private int BOMB_MAX=1;
	private boolean immune=false;
	private int imTime;
	private int frameCount;
	private Game game;
	private List<Cell> animate; //[
	private int LIFE = 1;
	private Boolean isDie;
	private int bombRadius =1;
	private int direction;  //add
	private ColorAdjust colorAdjust;

	
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
		this.imTime = 0;
		this.frameCount = 0;
		colorAdjust = new ColorAdjust();
	}
	public void setCell(Cell[][] gameCell) {
		this.gameCell =gameCell;
	}
	public void getHurt() {
		if(!immune)	LIFE--;
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
		this.LIFE = 0;
	}
	public Boolean isDie() {
		return isDie;
	}

	public void drawPlayer() {

		avatar = new ImageView(new Image(ClassLoader.getSystemResource( img_url).toString()));
		avatar.setLayoutX(65 * x);
		avatar.setLayoutY(65 * y - 25);
		gamePane.getChildren().add(avatar);
		
	}
	
	public String getImgUrl() {
		return this.img_url;
	}
	
	public void setBomb() {
		//i cell[][].getElement == null can set bomb;
		
		if (gameCell[y][x].getEntity() == null && count_bomb < BOMB_MAX) {
			System.out.println("Bomb (" + x +","+ y +")");
			gameCell[y][x] = new Cell(x,y);
			gameCell[y][x].setEntity(new Bomb(gamePane, x,y, "map1/"));
			rewrite(x,y);
			animate.add(gameCell[y][x]);
			count_bomb += 1;
		}
	}
	public void addAnimate(Cell e) {
		this.animate.add(e);
	}
	int speed = 30;
	public void Animate() {
		frameCount++;
		if(this.frameCount%speed == 0) {
			if(immune) {
				Random n = new Random();
				colorAdjust.setHue(-n.nextDouble());
				if(speed > 3)speed = speed-1;
			}
			
		
		}
		if (this.frameCount % 60 == 0) {
			if(immune) this.imTime--;
			if(imTime <= 0) {
				setImmune(false);
				avatar.setEffect(null);
			}
			this.frameCount=0;
			
		}
	
		
		int nx = 0,ny=0;
		Boolean removed = false;
	
		
		//code for set image;
		for (Cell tmp : animate) {
			removed = false;
		
			
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

	

		}
		
		
		if (removed) {
			game.getgameLogic().bombThis(nx,ny);
			int radius = bombRadius;
			// two time bomb because if bomb first it will set to Smoke 
			for(int i=1 ; i<=radius;i++) {
		
				if(ny+i >=10|| ! (gameCell[ny+i][nx].getIsEmpty() || gameCell[ny+i][nx].getEntity()  instanceof Bomb ||  gameCell[ny+i][nx].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx,ny+i);
					break;
				}
				game.getgameLogic().bombThis(nx,ny+i);
				
			
			}
			
			for(int i=1 ; i<=radius;i++) {
				
				if(ny-i <= 0 || !( gameCell[ny-i][nx].getIsEmpty()  || gameCell[ny-i][nx].getEntity()  instanceof Bomb ||  gameCell[ny-i][nx].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx,ny-i);
					break;
				}
				game.getgameLogic().bombThis(nx,ny-i);
				
				
			}
			for(int i=1; i<=radius;i++) {
				
				if(nx+i >=15|| ! (gameCell[ny][nx+i].getIsEmpty() || gameCell[ny][nx+i].getEntity()  instanceof Bomb ||  gameCell[ny][nx+i].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx+i,ny);
					break;
				}
				game.getgameLogic().bombThis(nx+i,ny);
			}
			for(int i=1 ; i<=radius;i++) {
				
				if(nx-i<=0 || ! (gameCell[ny][nx-i].getIsEmpty() || gameCell[ny][nx-i].getEntity()  instanceof Bomb ||  gameCell[ny][nx-i].getEntity() instanceof Smoke)) {
					game.getgameLogic().bombThis(nx-i,ny);
					break;
				}
				game.getgameLogic().bombThis(nx-i,ny);
				
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
		Image tmp = new Image(ClassLoader.getSystemResource( a).toString());
		avatar.setImage(tmp);
		this.direction=dir;
	}

	public void rewrite(int x, int y) {
		if(!isDie) {
			game.getgameLogic().rewrite(x, y);	
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
		int corner=2;
		if (dir == UP) {
			set(UP);
			int ny = (pos_top - 1 ) / CELL_WIDTH;
			
			return (gameCell[ny][x].getIsEmpty()) && (gameCell[ny][((pos_left + corner) / CELL_WIDTH)].getIsEmpty())
					&& (gameCell[ny][(pos_right - corner) / CELL_WIDTH].getIsEmpty());

		} //
		if (dir == RIGHT) {
			set(RIGHT);
			int nx = (pos_right + 1 ) / CELL_WIDTH;

			return (gameCell[y][nx].getIsEmpty()) && (gameCell[((pos_top + corner) / CELL_WIDTH)][nx].getIsEmpty())
					&& (gameCell[(pos_down - corner) / CELL_WIDTH][nx].getIsEmpty());

		}
		if (dir == DOWN) {
			set(DOWN);
			int ny = (pos_down + 1 ) / CELL_WIDTH;

			return (gameCell[ny][x].getIsEmpty()) && (gameCell[ny][((pos_left + corner) / CELL_WIDTH)].getIsEmpty())
					&& (gameCell[ny][(pos_right - corner) / CELL_WIDTH].getIsEmpty());

		}
		if (dir == LEFT) {
			set(LEFT);
			int nx = (pos_left - 1 ) / CELL_WIDTH;

			return (gameCell[y][nx].getIsEmpty()) && (gameCell[((pos_top + corner) / CELL_WIDTH)][nx].getIsEmpty())
					&& (gameCell[(pos_down - corner) / CELL_WIDTH][nx].getIsEmpty());

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

		for(int i =0;i<speedNorm;i++) {
			if (direction == UP && checkMove(UP))
				avatar.setLayoutY(avatar.getLayoutY() - 1);
				

			if (direction == DOWN && checkMove(DOWN))
				avatar.setLayoutY(avatar.getLayoutY() + 1);

			if (direction == RIGHT && checkMove(RIGHT))
				avatar.setLayoutX(avatar.getLayoutX() + 1);

			if (direction == LEFT && checkMove(LEFT))
				avatar.setLayoutX(avatar.getLayoutX() - 1);
		}
		
		int ox=x,oy=y;
		updatePosition();
		if(ox!=x || oy!= y) {
			if(gameCell[oy][ox].getEntity() instanceof Bomb) {
				gameCell[oy][ox].getEntity().setSolid(true);
			}
			//System.out.println("Player move" +"(" +ox +","+ oy+") -> ("  + x+ ","+ y+ ")");
		}
		return true;
	}
	
	public int getCurrentDir() {    //add
		return this.direction;
	}
	
	public void Immuned() {   //add
	
		
		Random n = new Random();
		colorAdjust.setHue(-n.nextDouble());
		colorAdjust.setSaturation(0.2);
		if(immune) {
			
			avatar.setEffect(colorAdjust);
		}
	}
	
	public void setImmune(boolean op) {
		this.immune = op;
		if(op) {
			this.Immuned();
			this.imTime=7;
			this.frameCount=0;
			this.speed=30;
		}
	}
	
	
	public boolean isImmune() {
		return this.immune;
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
