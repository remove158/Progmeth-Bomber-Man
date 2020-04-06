package logic;


import entity.Block;
import entity.Box;
import entity.Tree;
import javafx.scene.layout.Pane;
import view.CSVParser;


public class GameMap {
	private Cell[][] myMap;
	public GameMap(Pane gamePane,String mapStyle) {
		String[][] map= CSVParser.readCSV(mapStyle+".csv");
		int y= map.length;
		int x = map[0].length;
		int countx=0,county=0;
		myMap = new Cell[y][x];
		for(String[] col : map) {
			countx = 0;
			for(String c : col) {
				
				//System.out.print(c);
				myMap[county][countx] = new Cell(countx,county);
				
				if(c.equals("b")) {
					myMap[county][countx].setEntity(new Block(gamePane,countx,county,mapStyle));
				}else if(c.equals("t")) {
					myMap[county][countx].setEntity(new Tree(gamePane,countx,county,mapStyle));
				}else if(c.equals("i")) {
					myMap[county][countx].setEntity(new Box(gamePane,countx,county,mapStyle));
				}
				
				countx++;
			}
			//System.out.println();
			county++;
		}
	}
	
	public Cell[][] getCell(){
		return myMap;
	}
	
}
