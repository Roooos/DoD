public abstract class Player{
	
	protected int xLocation;
	protected int yLocation;
	private int goldCount;
	String[] validCmd = {"hello", "look", "pickup", "quit", "move n", "move e", "move s", "move w"};
	char name;
	
	public Player(char name, int x, int y){
		this.name = name;
		setLocation(x, y);
		goldCount = 0;
	}
	
	//Processes the command. It should return a reply in form of a String, as the protocol dictates.
	//Otherwise it should return the string "Invalid".
	//@param command : Input entered by the user.
	//@return : Processed output or Invalid if the @param command is wrong.
	protected String getNextAction(String command) {
		command = command.toLowerCase();
		
		for(int i = 0; i < validCmd.length; i++) {
			if(validCmd[i].equals(command)){
					return command;
			}
		}
		System.out.println("Invalid");
		return "invalid";
	}
	
	protected abstract String getInput();
	
	protected abstract void analyse(String output);
	
	//sets the players location
	public void setLocation(int x, int y){
		xLocation = x;
		yLocation = y;			
	}
	
	public int getX() {
		return xLocation;
	}
	
	public int getY() {
		return yLocation;
	}
	
	public int increaseX() {
		xLocation++;
		return xLocation;
	}
	
	public int decreaseX() {
		xLocation--;
		return xLocation;
	}
	
	public int increaseY() {
		yLocation++;
		return yLocation;
	}
	
	public int decreaseY() {
		yLocation--;
		return yLocation;
	}
	
	public int getGoldCount() {
		return goldCount;
	}

	public void increaseGoldCount() {
		goldCount++;
	}
}