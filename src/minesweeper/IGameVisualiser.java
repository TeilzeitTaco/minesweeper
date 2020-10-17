package minesweeper;


/**
 * Implementations of this interface should represent
 * the received events to the user.
 * 
 * @author SeiJ
 */
public interface IGameVisualiser {
	/**
	 * Implementation should update the visual game display.
	 * 
	 * @param gameState The current game state.
	 */
	void onFieldUpdate(final GameState gameState);
	
	/**
	 * Called when a mine gets triggered.
	 * 
	 * @param c The coordinates of the mine.
	 */
	void onGameOver(final Coords c);
	
	/**
	 * Called when the last non-mine field is uncovered.
	 * 
	 * @param c The coordinates of the last move.
	 */
	void onGameWon(final Coords c);
	
	/**
	 * Called when the user attempts a illegal move.
	 * 
	 * @param c The coordinates of the move.
	 */
	void onIllegalMove(final Coords c);
}
