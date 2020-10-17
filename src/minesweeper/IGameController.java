package minesweeper;


/**
 * Implementations of this class should allow the player
 * to control the game.
 * 
 * @author SeiJ
 */
public interface IGameController {
	/**
	 * Generate the coordinates of the next field that should be uncovered.
	 * 
	 * @param gameState The current game state.
	 * @return The coordinates of the next field to be uncovered.
	 */
	Coords getNextFieldToUncover(final GameState gameState);
}
