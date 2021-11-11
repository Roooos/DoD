import java.util.Random;

public class Bot extends Player{
	
	int playerPos;
	int move, prevMove;
	int  n;
	//the positions in the string representation of the 5x5 map that will result in the bot moving north, east, south or west
	int[] nor = {0, 1, 2, 3, 7, 8};
	int[] eas = {4, 9, 10, 15, 16, 22};
	int[] sou = {20, 21, 25, 26, 27, 28};
	int[] wes = {6, 12, 13, 18, 19, 24};
	boolean firstGo;
	
	public Bot(char name, int x, int y) {
		super(name, x, y);
		firstGo = true;
	}
	
	//stores the location of the player in the string representation of the map if the map is passed through and the player is on the 5x5 map
	protected void analyse(String output) {
		if(output.contains("P")) {
			playerPos = output.indexOf("P");
		} else playerPos = -1;
	}
	
	//returns "look"  
	protected String getInput() {
		//on the bot's first go it will look
		if(firstGo) {
			firstGo = false;
			return "look";
		}
		//the bot moves twice for every time it looks
		if(move < 2){
			n = setN();
			
			move++;
			
			switch(n) {
			case 1: return "move n";
			case 2: return "move e";
			case 3: return "move s";
			case 4: return "move w";
			}
		}
		move = 0;
		prevMove = 0;
		return "look";
	}
	
	//sets n to a values depending if the player was seen, otherwise n is random between 1 and 4
	private int setN() {
		Random rand = new Random();
		//if the bot is on it's second move in it's 'look-move' cycle and it was previously chasing the player, it will move in the same direction as it's previous move
		if(prevMove > 0) return prevMove;
		//if the players location is unknown it will move in a random direction
		else if(playerPos == -1) {
			return rand.nextInt(4) + 1;
		}
		//checks the players position with the array of north, east, south and west values
		//will only set preMove if player was seen in previous turn
		else for(int i = 0; i < 6; i++){
			if(playerPos == nor[i]) {
				prevMove = 1;
				return 1;
			}
			else if(playerPos == eas[i]) {
				prevMove = 2;
				return 2;
			}
			else if(playerPos == sou[i]) {
				prevMove = 3;
				return 3;
			}
			else if(playerPos == wes[i]) {
				prevMove = 4;
				return 4;
			}	
		}
		return rand.nextInt(4) + 1;
	}
}