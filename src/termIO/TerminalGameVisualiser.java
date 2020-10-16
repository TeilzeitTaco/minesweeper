package termIO;

import java.io.IOException;

import minesweeper.Coords;
import minesweeper.Field;
import minesweeper.GameState;
import minesweeper.IGameVisualiser;


public class TerminalGameVisualiser implements IGameVisualiser {	
	private void clearScreen() {
		for(byte i = 0; i < 64; i++)
			System.out.println();
	}
	
	private void waitForEnter() {
		try {
			System.in.read();
		} catch (IOException e) { }
	}
	
	@Override
	public void onFieldUpdate(final GameState gameState) {
		final Field[][] mineField = gameState.getMineField();
		
		clearScreen();
		for (final Field[] r : mineField) {
			for (final Field f : r) {
				if (f.isUncovered()) {
					System.out.print(f.getNeighbouringMineCount());
				} else {
					System.out.print("~");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public void onGameOver(final Coords c) {
		System.out.println("\nGame Over!");
	}

	@Override
	public void onIllegalMove(final Coords c) {
		System.out.println("\nIllegal Move!");
		waitForEnter();
	}
}
