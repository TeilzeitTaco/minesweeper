package minesweeper;

import swingIO.SwingGameFrontend;


/**
 * The main game driver class.
 * 
 * @author SeiJ
 */
public class MineSweeper {
	public static void main(final String[] args) {
		// All the dirty non-pure game code has been
		// pushed away into these interfaces.		
		final IGameFrontend gameFrontend = new SwingGameFrontend();
		
		final IGameConfigurator gameConfigurator = gameFrontend.getGameConfigurator();
		final IGameVisualiser gameVisualiser = gameFrontend.getGameVisualiser();
		final IGameController gameController = gameFrontend.getGameController();
		
		final GameState gameState = new GameState(gameConfigurator.getConfig());
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
