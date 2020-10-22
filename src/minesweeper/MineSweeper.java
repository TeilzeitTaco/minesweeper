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
		
		// The configurator is the earliest called interface
		final IGameConfigurator gameConfigurator = gameFrontend.getGameConfigurator();
		final Config config = gameConfigurator.getConfig();
		
		final IGameVisualiser gameVisualiser = gameFrontend.getGameVisualiser(config);
		final IGameController gameController = gameFrontend.getGameController(config);
		
		final GameState gameState = new GameState(config);
		gameVisualiser.onFieldUpdate(gameState);
		
		while(true) {			
			// Let the player pick a new field
			final Coords c = gameController.getNextFieldToUncover(gameState);
			final Field f = gameState.getField(c);
			
			// Process the field
			if (f.isUncovered()) {
				gameVisualiser.onIllegalMove(c);
				continue;
			}
			
			if (f.isMine()) {
				gameVisualiser.onGameOver(c);
				break;
				
			} else {
				f.uncover();
			}
			
			gameVisualiser.onFieldUpdate(gameState);
			if (gameState.isFinished()) {
				gameVisualiser.onGameWon(c);
				break;
			}
		}
	}
}
