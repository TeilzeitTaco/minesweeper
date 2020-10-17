package minesweeper;


/**
 * Implementations of this interface should allow the
 * user to configure the game before starting.
 * 
 * @author SeiJ
 */
public interface IGameConfigurator {
	/**
	 * Called on game start. Might allow the player to choose
	 * difficulty and other values.
	 * 
	 * @return The newly constructed configuration object.
	 */
	Config getConfig();
}
