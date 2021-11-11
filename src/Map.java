import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Reads and contains in memory the map of the game.

public class Map {
	
	//Representation of the map
	private char[][] map;
	
	//Map name
	private String mapName;
	
	//Gold required for the human player to win
	private int goldRequired;
	private int goldRemaining;
	public int rows;
	public int columns;
	private boolean isExit;
	BufferedReader reader;
	ArrayList<String> mapArrList;
	
	//Default constructor, creates the default map "Very small Labyrinth of doom".
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
		rows = map.length;
		columns = map[0].length;
	}
	

	//Constructor that accepts a map to read in from.
	//@param : The filename of the map file.
	public Map(String fileName) {
		mapArrList = readMap(fileName);
		
		mapName = mapArrList.get(0).substring(5);
		goldRequired = Integer.parseInt(mapArrList.get(1).substring(4));
		//removes the top two lines, leaving just the map
		mapArrList.remove(1);
		mapArrList.remove(0);
		
		rows = mapArrList.size();
		columns = maxMapWidth();//specify map is rectangle
		char[][] buildMap = new char[rows][columns];
		
		//converts the array list into a 2D char array
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				buildMap[i][j] = tile(i, j);
			}
		}
		map = buildMap;
	}
	
	//returns a char that buildMap[i][j] should be assigned
	private char tile(int i, int j) {
		//if the current line is not as long as the maximum column value, a '#' is added to that row
		if(j >= mapArrList.get(i).length())
			return '#';
		//a '#' is added if an invalid char makes up the map
		else if(mapArrList.get(i).charAt(j) != '#' && mapArrList.get(i).charAt(j) != '.' && mapArrList.get(i).charAt(j) != 'E' && mapArrList.get(i).charAt(j) != 'G')
			return '#';
		else return mapArrList.get(i).charAt(j);
	}
	
	//returns the maximum width of the map
	private int maxMapWidth() {
		int maxWidth = 0;
		for(int i = 0; i < mapArrList.size(); i++) {
			if(mapArrList.get(i).length() > maxWidth) {
				maxWidth = mapArrList.get(i).length();
			}
		}
		return maxWidth;
	}
	
	//checks the validity of the map
	public void checkMap(){
		goldRemaining = 0;
		isExit = false;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(map[i][j] == 'G') goldRemaining++;
				if(map[i][j] == 'E') isExit = true;
			}
		}
		
		if(goldRemaining < goldRequired) {
			System.out.println("Gold required to win is too high");
			System.out.println("Please modify map or load another");
			System.exit(2);
		}
		if(!isExit) {
			System.out.println("Map does not contain an exit");
			System.out.println("Please modify map or load another");
			System.exit(3);
		}
	}
	
	//@return : Gold required to exit the current map.
	protected int getGoldRequired() {
		return goldRequired;
	}
	
	 //@return : The map as stored in memory.
	protected char[][] getMap() {
		char [][] myMap = new char[map.length][];
		for(int i = 0; i < map.length; i++)
			myMap[i] = map[i].clone();
		return myMap;
	}	
	
	 //@return : The name of the current map.
	protected String getMapName() {
		return mapName;
	}
	
	//Reads the map from file.
	//@param : Name of the map's file.
	//Jie He helped me with this method
	protected ArrayList<String> readMap(String fileName) {
		
		try {
			File mapToLoad = new File(fileName);
			FileReader fileReader = new FileReader(mapToLoad);
			BufferedReader br = new BufferedReader(fileReader);
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			
			while ((line = br.readLine()) != null){
				lines.add(line);
			}
			fileReader.close();
			return lines;
			
		}catch (IOException e) {
			System.err.print(e);
		}
		
		return null;	
	}
	
	//checks each element in the map array for a '.' or an 'E'
	//returns a list of all the valid spawn locations on the map
	protected List<String> getValidSpawnString() {
		List<String> coordList = new ArrayList<String>();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(map[i][j] == '.' || map[i][j] == 'E') {
					coordList.add("" + j);
					coordList.add("" + i);
				}
			}
		}
		return coordList;
	}
	
	//returns the amount of gold on the map
	protected int getGoldRemaining() {
		return goldRemaining;
	}
	
	//returns the value of the char at a specified point
	public char checkTile(int y, int x) {
		return map[y][x];
	}
	
	//turns the char value of a tile into a '.'
	public void clearTile(int y, int x) {
		map[y][x] = '.';
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
}