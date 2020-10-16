package minesweeper;

import termIO.TerminalGameController;
import termIO.TerminalGameVisualiser;


public class MineSweeper {
	private static IGameVisualiser gameVisualiser;
	private static IGameController gameController;
	private static GameState gameState;
	
	public static void main(final String[] args) {
		gameVisualiser = new TerminalGameVisualiser();
		gameController = new TerminalGameController();
		
		gameState = new GameState(24, 16, 0.1f);
		while(true) {
			gameVisualiser.onFieldUpdate(gameState);
			
			// Let the player pick a new field
			final Coords c = gameController.getNextFieldToUncover(gameState);
			final Field f = gameState.getField(c);
			
			// Process the field
			if (f.isUncovered()) {
				gameVisualiser.onIllegalMove(c);
				continue;
			} 
			
			f.uncover();
			
			if (f.isMine()) {
				gameVisualiser.onGameOver(c);
				break;
			}
			
			if (gameState.getFieldCount() - gameState.getUncoveredFieldCount() == gameState.getMineCount()) {
				gameVisualiser.onGameWon(c);
				break;
			}
		}
	}
}
