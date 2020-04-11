# CPCU Bomber
###  Final Project 2110105
### Updated  4/11/2020 (Refactor Code) 

# Contents
* [Application](#Application)
* [Entity](#Entity)
* [Exception](#Exception)
* [Item](#Item)
* [Model](#Model)
* [View](#View)


#  Application
- main.java 

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
    public void setSmoke() {...}; //setsmoke for 1 Loop
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
    public void setSmoke() {...};  //setsmoke 1 time
    public void update() {...};

    
    ```




# Logic
- Animate.java
- Cell.java
- CSVParser.java
- GameControllet.java
- GameLogic.java
- GameMap.java
- Sprite.java  
        
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
        String[][] map= CSVParser.readCSV(mapStyle);
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

# Model
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
            root.getChildren().addAll(gamePane, border, playerInfo);
            drawGameBoard();
            createAvatar(MAP choosenMap);
        }

        
    }
    

    ```


