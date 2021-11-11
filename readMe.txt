Welcome to Dungeons of Doom
~~~~~~~~~~~~~~~~~~~~~~~~~~

How to install and run
~~~~~~~~~~~~~~~~~~~~~~
Extract the .zip and save it in a know location.
Open a compiler, such as linux.bath, and change the directory to match the Project directory location.
eg. cd ~/Users/YOURNAME/Games/CW2.rl836/Project/
Compile the code by typing javac*.java
To run the game type ./GameLogic
From here you should input one of the commands found in the "How to play" section.

To change the map you must go into the GameLogic.java file and on line 17, within the brackets, type the name of the map you wish to play surrounded by quotation marks.
Eg. map = new Map("exampleMap.txt");
The map text file must be contained in the Project folder and must comply with the requiremens below.

How to play
~~~~~~~~~~~
The aim of the game is to avoid the bot and collect enough gold to exit the dungeon.
Once the game is running you will have 8 available commands: LOOK, HELLO, PICKUP, QUIT, MOVE N, MOVE E, MOVE S, MOVE W
You and a bot will take turns to input a command. The bot can only move or look.
The commands are not case sensitive but they do have to have spelled correctly and can not have additional or too few spaces.
The commands can only be entered one at a time.

Commands:
LOOK: prints a 5x5 map of the area surrounding your player
HELLO: prints the remaining gold needed to be able to exit
PICKUP: picks up gold if your player is on a gold tile ('G')
QUIT: exits the game
MOVE N: moves the player north one tile
MOVE E: moves the player east one tile
MOVE S: moves the player south one tile
MOVE W: moves the player west one tile
Any other command will return "Invalid" to the screen and you will have to type another command.
An invalid command does not take up a turn.

Tiles:
'.' represents a blank tile which your player is able to freely move on.
'#' represents a wall which players will not be able to move onto.
'G' represents 1 gold. This can be pickup using the pickup command.
'P' represents your player character
'B' represents a bot. It is able to move around the map
'E' represents the exit. You can exit the dungeon was you have enough gold by moving on to the exit tile

The bot will chase you around the map if it sees you so keep your distance. If the bot catches you, the game will end.


Map requirements
~~~~~~~~~~~~~~~~
*The first line of the map file must be of the format: name "name of map"
*The second line of the map file must be of the format: win "value of gold required to win"
*The map must only be made up of the following characters: '#', '.', 'G', 'E', otherwise they will be replaced by '#'
*The map must contain enough gold to win, otherwise the game will close.
*The map must contain at least one exit tile, otherwise the game will close.
*The map must be surrounded by walls.
*The player must be able to move from any non-wall tile to any other non-wall tile.
Eg. 
#########
#.......#
#########
#.......#
#...E...#
#.......#
#########
Would not be allowed since it contains a closed off section.