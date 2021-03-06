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
		final int width = gameState.getConfig().getWidth();
		
		clearScreen();
		
		// The horizontal labels
		System.out.print("      ");
		for (int x = 0; x < width; x++) {
			if (x % 2 == 0) continue;
			System.out.print(String.format("  %02d", x));
		}
		System.out.println();
		
		System.out.print("     " + "   |".repeat(mineField[0].length / 2) + "\n      ");
		for (int x = 0; x < width; x++) {
			if (x % 2 == 1) continue;
			System.out.print(String.format("%02d", x));
			if (x < width - 1)
				System.out.print("| ");
		}
		System.out.println("\n      " + "| ".repeat(width));
		
		// The board
		for (int y = 0; y < gameState.getConfig().getHeight(); y++) {
			System.out.print(String.format(" %02d - ", y));
			
			for (int x = 0; x < width; x++) {
				final Field f = mineField[y][x];
				if (f.isUncovered()) {
					final int neighbouringCount = f.getNeighbouringMineCount();
					if (neighbouringCount == 0) {
						System.out.print("  ");
					} else {
						System.out.print(neighbouringCount + " ");
					}
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
		System.out.println("\n " + (gameState.getFieldCount() - gameState.getUncoveredFieldCount()) + 
				" uncertain fields remaining, thereof mines: " + gameState.getMineCount());
	}

	@Override
	public void onGameOver(final Coords c) {
		System.out.println("\n Game Over!");
	}
	
	@Override
	public void onGameWon(final Coords c) {
		System.out.println("\n You won!");
	}

	@Override
	public void onIllegalMove(final Coords c) {
		System.out.println("\n Illegal Move!");
		waitForEnter();
	}
}
