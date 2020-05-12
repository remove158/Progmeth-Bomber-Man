# CPCU Bomber
    Final Project 2110205   
    - Updated  11/4/2020 (3 Gamemap , 1 Character,  map from image)
    - Updated 12/4/2020 (add bot , fix bug show GameEnd)
    - Updated 19/4/2020 (add sound fx)

# Contents
* [Application](#Application)
* [Entity](#Entity)
* [Exception](#Exception)
* [Item](#Item)
* [Logic](#Logic)
* [Model](#Model)
* [Music](#Music)
* [View](#View)


#  Application 

-  main.java

        Main.java is show PrimaryStage that come from ViewManager.getMainStage()
        
    ```Java
    public void start(Stage primaryStage) {
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    ```

# Entity

- Base
    - Entity.java
    - AnimateAble.java
- Block.java
- Bomb.java
- Box.java
- Element.java
- Player.java
- Smoke.java
- Tree.java  
       
        Every Class Extends Element.java  ( Except Player ) 
        Element.java Extend Entity.java implements AnimateAble
        

    ```Java

    ## Element.java

    public abstract class Element extends Entity implements AnimateAble {
        public Element(Pane gamePane, int x, int y, String mapStyle) {
            super(gamePane, x, y, mapStyle);
        }
    }

    
    public void tick() {...};
    public void setSmoke() {...}; //set Blok to red box (that use in game end)
    public void update() {...};
    
    ```
        

        


# Exception
- SetSmokeException.java
- UseItemException.java  

        Every Class Extends Exception       

    ```Java

    ## GameException.java

    public class SetSmokeException extends Exception  {

        public String message;
        public SetSmokeException(String message) {
            super();
            this.message = message;
        }
    }
    
    ```


    
# Item
- AddBomb.java
- AddRadius.java
- Item.java
- SheildOnPlayer.java
- Speed.java  

        Every Class Extends Item    
        Item.java Extend Entity.java implements AnimateAble   


    ```Java

    ## Item.java

    public abstract class Item extends Entity implements AnimateAble  {
        public Item(Pane gamePane, int x, int y, String mapStyle) {
            super(gamePane, x, y, mapStyle);
        }
    }

    public void tick() {...};
    public void setSmoke() {...};  //setsmoke 1 time (use to show smoke before show item)
    public void update() {...};

    
    ```




# Logic
- Animate.java
- Cell.java
- IMGParser.java
- GameControllet.java
- GameLogic.java
- GameMap.java
- Sprite.java  
- Bot.java
        
    ```Java

    ## Animate.java

    public void update() {
		itemUpdate();
		smokeAnimate();
        bombAnimate();
	}
    ```

     ```Java

    ## GameMap.java

    public GameMap(Pane gamePane,String mapStyle) {
        String[][] map= IMGParser.readIMG(mapStyle);
    }
    ```

    ```Java

    ## Cell.java

    public Boolean getIsEmpty(){...};
    public void removeEntity(){...};
    public void setIsEmpty(Boolean isEmpty){...};
	
    ```
    ```Java

    ## GameControllet.java
    
    public void  update() {}; //movePlayer

    private void createKeyListenner() {
		setOnMouseClicked(...); //forShortkey
		setOnKeyPressed(...);
		setOnKeyReleased(...);
	}
    ```
    ```Java

    ## GameLogic.java
    
    public List<Cell> getShell() {...}; //getShell that use EndGame
    public void setItem(int x, int y) {...}; //setItem X,Y
    public boolean randomItem(int x, int y, float percent) {...} //return getItem or not 
    public void rewrite(int x, int y) {...} //swap Layer Image for  right view
    
	}
    ```

     ```Java

    ## Bot.java
    
    public void update() {
		if (!myPlayer.isDie()) {
			if (!moveList.isEmpty()) {
				boolean success = move(moveList.get(0));
				if (success) {
					moveList.remove(0);
				}
			} else {
				if (myPlayer.getBombCount() == 0) {
					myPlayer.setBomb();
					moveList.addAll(calculate(myPlayer.getX(), myPlayer.getY(), sample));
				}
			}

		}
	}
    ```

# Model
- FontStyle.java
- Endlabel.java
- GameButton.java
- GameSubScene.java
- InfoLabel.java
- MAP.java
- MapPicker.java
- SmaillInfoLabel.java
- Sprite.java
- TimeLabel.java

    ```Java

    ## EveryLabel
    
    public class EndLabel extends Label {
        public EndLabel(String text,int w,int h) {...}	;
        private void setLabelFont() {...};
    }

    ```

    ```Java

    ## GameButton.java
    
    public class GameButton extends Button {
        private void setButtonFont() {...};
        private void initializeButtonLusteners() {
            setOnMousePressed(...);
            setOnMouseReleased(...);
            setOnMouseEntered(...);
            setOnMouseExited(...);
        }
    }

    ```

# Music
- Sound.java
    ```Java

    ## Sound.java

   	public Sound(String path, double x) {

		musicFile = "music/" + path + ".wav"; // For exampl
		musicFile = ClassLoader.getSystemResource(musicFile).toString();
		sound = new Media(musicFile);
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(x);
		mediaPlayer.play();

	}

    ```

# View
- Game.java


- ViewManager.java
    ```Java

    ## ViewManager.java
    
    public class ViewManager {
        public ViewManager() {
            createSubSCenes();
            createBackground();
            createMenuButton();
            createLogo();
	    }
    }

    ```

    ```Java

    ## Game.java
    
    public class Game {
        public Game(){
            initializeStage(); 
		    createGameLoop();
        }
        
        private void initializeStage() {
            root.getChildren().addAll(gamePane, border, playerInfo,winPane);
            drawGameBoard();
            createAvatar(MAP choosenMap);
        }
        private void createGameLoop() {
            
            timer = new AnimationTimer() {
                public void handle(long arg0) {
				gameBot1.run();
                //gameBot2.run();
                gameLogic.counttime();
				gameLogic.endgame();
				gameScene.update();
				player1.Animate();
				player2.Animate();
				animate.update();
                }
            }

        }
        
    }
    

    ```