# Dungeons of Doom
This is to guide you on how to play the 'Dungeon of Doom' game.


Once the program is run, you are asked to either enter the file name of a custom map or press enter to continue and play the game on the default map.
NOTE: The file which contains the custom map MUST be in the following format:
First line: name [Name of the map] (not including the [])
Second line: win [Gold required to win] (not including the [])
Third line onward: Custom map
If the format of your file does not match the aforementioned format, the game does not start and the program ends.

If you enter a file name, the program then proceeds to ask you about the dimensions of the map.
You are first asked to enter the X dimension of the map (number of tiles going across / horizontal dimension).
You are then asked to enter the Y dimension of the map (number of tiles going down or up / vertical dimension).
If the correct details have been entered, the program runs on your custom map.


Regardless of the map, the game works the same way.

In order to play the game, you must enter a command in the console.
The aim of this game is for you to go around the map and pick gold up and once you have enough gold, head to an exit tile and enter the QUIT command to end the game.

There is a bot which is actively looking to catch you, so make sure to stay away from it. If you and the bot step on the same tile, you lose the game and the bot wins.
The bot is implemented in a way such that it can only look at the map by also executing the LOOK command.
It only gets to see a portion of the map the same way you would if you execute the LOOK command.

If the bot DOES NOT have view of you, it moves randomly and looks at the map every other turn.
If the bot DOES have view of you, there's a 50% chance it would move towards you.

The command you enter, regardless of whether it is successful or not is processed as a full turn and the bot gets to take its turn.
In order to win the game, you must have at least the number of gold required to win, must be on an exit tile and must execute the QUIT command.


This is the full list of available commands and what they do:
NOTE: The commands are NOT case-sensitive.


	HELLO:
		Outputs how many gold you need to have in order to be eligible to win the game.
	GOLD:
		Outputs how many gold you currently own.
	MOVE <DIRECTION>:
		The '<>' must not be included. The direction is either 'N', 'E', 'S' or 'W' for up, right, down and left respectively.
		Outputs whether the move was successful or not. You can not move into a wall.
	PICKUP:
		Outputs whether you successfully picked a gold up and the number of golds you own already.
		You need to be on a tile containing a gold for this command to be successful.
	LOOK:
		Shows you the map around you.
		Gold is displayed as 'G'.
		The bot player is displayed as 'B'.
		Exit tiles are displayed as 'E'.
		Walls are displayed as '#'.
	QUIT:
		Quits the game regardless of the circumstances; however, if you are not on an exit tile and/or do not have enough gold to win the game, you lose.
		Outputs the outcome of the game.