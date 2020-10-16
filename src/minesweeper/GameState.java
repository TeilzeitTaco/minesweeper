package minesweeper;

import java.util.HashMap;
import java.util.Random;


public class GameState {
	private final int width, height;
	private final Field[][] mineField;
	
	private final Random random = new Random();
	
	public int getMineCount() {
		int mineCount = 0;
		for (final Field[] fr : mineField) {
			for (final Field f : fr) {
				if (f.isMine())
					mineCount++;
			}
		}
		
		return mineCount;
	}
	
	public int getUncoveredFieldCount() {
		int mineCount = 0;
		for (final Field[] fr : mineField) {
			for (final Field f : fr) {
				if (f.isUncovered())
					mineCount++;
			}
		}
		
		return mineCount;
	}
	
	public int getFieldCount() {
		return width * height;
	}
	
	public GameState(final int width, final int heigth, final float difficulty) {
		this.width = width;
		this.height = heigth;
		
		// Generate the field
		mineField = new Field[height][width];
		
		for (int y = 0; y < mineField.length; y++) {
			for (int x = 0; x < mineField[y].length; x++) {
				mineField[y][x] = new Field(random.nextFloat() < difficulty);
			}
		}
		
		// Link up the fields with each other, dirty.
		for (int y = 0; y < mineField.length; y++) {
			for (int x = 0; x < mineField[y].length; x++) {
				final HashMap<Direction, Field> neighbours = mineField[y][x].getNeighbours();
				
				final boolean 
					upExists = (y > 0),
					downExists = (y < mineField.length - 1),
					leftExists = (x > 0),
					rightExists = (x < mineField[y].length - 1);
				
				// Upper row
				if (upExists) {
					neighbours.put(Direction.up, mineField[y - 1][x]);
					
					if (leftExists) {
						neighbours.put(Direction.upLeft, mineField[y - 1][x - 1]);
					}
					
					if (rightExists) {
						neighbours.put(Direction.upRight, mineField[y - 1][x + 1]);
					}
				}
				
				// Lower row
				if (downExists) {
					neighbours.put(Direction.down, mineField[y + 1][x]);
					
					if (leftExists) {
						neighbours.put(Direction.downLeft, mineField[y + 1][x - 1]);
					}
					
					if (rightExists) {
						neighbours.put(Direction.downRight, mineField[y + 1][x + 1]);
					}
				}
				
				if (leftExists)
					neighbours.put(Direction.left, mineField[y][x - 1]);

				
				if (rightExists) 
					neighbours.put(Direction.right, mineField[y][x + 1]);
			}
		}

		// Uncover initial mine
		while(true) {
			final Field f = mineField[random.nextInt(heigth)][random.nextInt(width)];
			if (!f.isMine() && f.getNeighbouringMineCount() == 0) {
				f.uncover();
				break;
			}
		}
	}
	
	public Field getField(final Coords c) {
		return mineField[c.getY()][c.getX()];
	}
	
	public Field[][] getMineField() {
		return mineField;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
