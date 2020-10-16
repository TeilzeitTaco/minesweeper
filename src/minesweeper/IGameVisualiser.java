package minesweeper;

public interface IGameVisualiser {
	/**
	 * Implementation should update the visual game display.
	 * 
	 * @param The current game state.
	 */
	void onFieldUpdate(final GameState gameState);
	
	/**
	 * Called when a mine gets triggered.
	 * 
	 * @param The coordinates of the mine.
	 */
	void onGameOver(final Coords c);
	
	/**
	 * Called when the last non-mine field is uncovered.
	 * 
	 * @param The coordinates of the last move.
	 */
	void onGameWon(final Coords c);
	
	/**
	 * Called when the user attempts a illegal move.
	 * 
	 * @param The coordinates of the move.
	 */
	void onIllegalMove(final Coords c);
}
