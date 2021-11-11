import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Runs the game with a human player and contains code needed to read inputs.
public class HumanPlayer extends Player{

	BufferedReader reader;
	
	public Player p;
	
	public HumanPlayer(char name, int x, int y) {
		super(name, x, y);
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	//Reads player's input from the console.
	//<p>
	//return : A string containing the input the player entered.
	protected String getInput() {
		
		try {
			//Keep on accepting input from the command-line
			while(true) {
				String command = reader.readLine();
				
				//Close on an End-of-file (EOF) (Ctrl-D on the terminal)
				if(command == null){
					//Exit code 0 for a graceful exit
					System.exit(0);
				}
				//Otherwise, (attempt to) process the character
				return getNextAction(command);          
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return null;
	}
	
	//prints the output of the command entered
	protected void analyse(String output) {
		System.out.println(output);
	}	
}