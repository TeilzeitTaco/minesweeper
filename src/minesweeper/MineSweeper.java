package minesweeper;

import swingIO.SwingGameConfigurator;
import swingIO.SwingGameVisualiser;

import termIO.TerminalGameConfigurator;
import termIO.TerminalGameController;
import termIO.TerminalGameVisualiser;


/**
 * The main game driver class.
 * 
 * @author SeiJ
 */
public class MineSweeper {
	private static IGameConfigurator gameConfigurator;
	private static IGameVisualiser gameVisualiser;
	private static IGameController gameController;
	
	private static GameState gameState;
	
	public static void main(final String[] args) {
		// All the dirty non-pure game code has been
		// pushed away into these interfaces.
		gameConfigurator = new TerminalGameConfigurator();
		
		SwingGameVisualiser x = new SwingGameVisualiser();
		gameVisualiser = x;
		gameController = x;
		
		gameState = new GameState(gameConfigurator.getConfig());
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
