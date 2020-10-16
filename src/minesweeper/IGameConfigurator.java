package minesweeper;


public interface IGameConfigurator {
	/**
	 * Called on game start. Might allow the player to choose
	 * difficulty and other values.
	 * 
	 * @return The newly constructed configuration object.
	 */
	Config getConfig();
}
