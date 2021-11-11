import java.util.ArrayList;
import java.util.List;

//Contains the main logic part of the game, as it processes.
public class GameLogic {
	
	ArrayList<Player> PlayerList;
	Player player;
	private Map map;
	private boolean running, isWall, gameOver;
	private int xSpawn, ySpawn;
	
	//Default constructor
	public GameLogic() {
		map = new Map("myMap.txt");
		PlayerList  = new ArrayList<Player>();
		gameOver = false;
		
		validSpawn();
		PlayerList.add(new HumanPlayer('P', xSpawn, ySpawn));
		validSpawn();
		PlayerList.add(new Bot('B', xSpawn, ySpawn));
	}
	
	
	private void updateGame() {
		map.checkMap();
		running = true;
		while(gameRunning()) {
			for(Player p : PlayerList) {
				player = p;
				String output;
				
				//keeps getting an input as long as the input is invalid
				do {
				String command = player.getInput();
				output = doNextAction(command);
				} while (output.equals("Invalid"));
				
				player.analyse(output);
				
				//checks for win condition
				if(goldLeftToWin() == 0 && onExit()) {
					System.out.println("You win!");
					System.exit(0);
				}
				
				//prints message if player loses
				if(gameOver) {
					System.out.println("You lose :(");
					System.exit(0);
				}
			}
		}
		System.exit(0);
	}

	//@return if the game is running.
	protected boolean gameRunning() {
		return running;
	}
	
	//checks the command input from the player and returns the corresponding method
	public String doNextAction(String command) {
		if(command.equals("look")) return look();
		else if(command.equals("hello")) return hello();
		else if(command.equals("pickup")) return pickup();
		else if(command.equals("move n")) return move(0, -1);
		else if(command.equals("move e")) return move(1, 0);
		else if(command.equals("move s")) return move(0, 1);
		else if(command.equals("move w")) return move(-1, 0);
		else if(command.equals("quit"))	return quitGame();
		else return "Invalid";
	}
	
	//@return : Returns back gold player requires to exit the Dungeon.
	protected String hello() {
		return "Gold to win: " + goldLeftToWin();
	}
	
	//calculates the gold needed for the win condition
	private int goldLeftToWin() {
		int gold = map.getGoldRequired() - player.getGoldCount();
		if(gold > 0)
			return gold;
		return 0;
	}
	
	//Checks if movement is legal and updates player's location on the map.
	//@param direction : The direction of the movement.
	//@return : Protocol if success or not.
	protected String move(int x, int y) {
		checkTile(player.getY() + y, player.getX() + x);
		if(isWall) return "FAIL";
		if(x == 1) player.increaseX();
		else if(x == -1) player.decreaseX();
		else if(y == 1) player.increaseY();
		else if(y == -1) player.decreaseY();
		return "SUCCESS";
	}
	
	//sets values of 'isWall' and 'gameOver' depending on if the tile is a wall or a player
	private void checkTile(int y, int x) {
		if(map.checkTile(y, x) == '#')
			isWall = true;
		else isWall = false;
		if(isPlayer(y, x))
			gameOver = true;
		else gameOver = false;
	}
	
	//checks if the player is on an exit tile
	private boolean onExit() {
		int x = player.getX();
		int y = player.getY();
		
		if(map.getMap()[y][x] == 'E')
			return true;
		return false;
	}
	
	//checks if the tile (the player is moving to) is a player
	private boolean isPlayer(int y, int x) {
		for(Player p : PlayerList) {
			if(getMapCopy()[y][x] == p.name) {
				return true;
			}
		}
		return false;
	}
	
	//Converts the map from a 2D char array to a single string.
	//@return : A String representation of the game map.
	protected String look() {
		//topX and topY make up the top left coordinate of the players position;
		int topX = player.getX()-2;
		int topY = player.getY()-2;
		char[][] mapCopy = getMapCopy();
		String smallMapString = "";
		
		for(int i = topY; i < topY+5; i++) {
			if(i < 0 || i >= map.getRows())
				continue;
			for(int j = topX; j < topX+5; j++) {
				if(j < 0 || j >= map.getColumns()) {
					smallMapString += "#";
				}
				else smallMapString +=  mapCopy[i][j];
			}
			smallMapString += "\n";
		}
		return smallMapString;
	}
	
	//returns a copy of the map with the player locations on
	protected char[][] getMapCopy() {
		char[][] mapCopy = map.getMap();
		for(Player p : PlayerList) {
			mapCopy[p.getY()][p.getX()] = p.name;
		}
		return mapCopy;
	}
	
	//Processes the player's pickup command, updating the map and the player's gold amount.
	//@return If the player successfully picked-up gold or not.
	protected String pickup() {
		int x = player.getX();
		int y = player.getY();
		
		if(map.getMap()[y][x] == 'G') {
			//replaces the 'G' with a '.' is the gold is picked up
			map.clearTile(y, x);
			player.increaseGoldCount();
			System.out.println("Gold owned: " + player.getGoldCount());
			return "SUCCESS";
		}
		return "FAIL";
	}
	
	//Quits the game, shutting down the application.
	protected String quitGame() {
		running = false;
		return "";
	}
	
	//chooses a random x coordinate and the corresponding y coordinate from the getValidSpawnString list
	private void validSpawn() {
		List<String> mapList = map.getValidSpawnString();
		int rand = (int)(Math.random() * (mapList.size()/2 - 1));
		//2 * random number will correlate to a random x value in the list
		xSpawn = Integer.parseInt(mapList.get(2*rand));
		ySpawn = Integer.parseInt(mapList.get(2*rand+1));
	}
	
	public static void main(String[] args) {
		GameLogic gl = new  GameLogic();
		gl.running = true;
		gl.updateGame();
	}
}