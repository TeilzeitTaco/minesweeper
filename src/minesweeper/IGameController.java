package minesweeper;


public interface IGameController {
	/**
	 * Interface implemented to control the game.
	 * 
	 * @param gameState The current game state.
	 * @return The coordinates of the next field to be uncovered.
	 */
	Coords getNextFieldToUncover(final GameState gameState);
}
