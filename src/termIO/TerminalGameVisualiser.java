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
		for (int y = 0; y < mineField.length; y++) {
			System.out.print(String.format(" [%02d] ", y));
			
			for (int x = 0; x < mineField[y].length; x++) {
				final Field f = mineField[y][x];
				if (f.isUncovered()) {
					System.out.print(f.getNeighbouringMineCount() + " ");
				} else {
					System.out.print("+ ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public void onGameOver(final Coords c) {
		System.out.println("\n Game Over!");
	}

	@Override
	public void onIllegalMove(final Coords c) {
		System.out.println("\n Illegal Move!");
		waitForEnter();
	}
}
