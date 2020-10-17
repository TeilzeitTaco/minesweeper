package minesweeper;


/**
 * Implementations of this class should allow the player
 * to control the game.
 * 
 * @author SeiJ
 */
public interface IGameController {
	/**
	 * Interface implemented to control the game.
	 * 
	 * @param gameState The current game state.
	 * @return The coordinates of the next field to be uncovered.
	 */
	Coords getNextFieldToUncover(final GameState gameState);
}
