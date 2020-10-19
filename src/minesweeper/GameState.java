package minesweeper;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;


/**
 * Represents the current state of the game.
 * This holds the array of Fields, the game configuration,
 * and contains methods to get more insight into the mine field.
 * 
 * @author SeiJ
 */
public class GameState {
	private final Random random = new Random();
	private final Field[][] mineField;
	private final Config config;
	
	public GameState(final Config config) {
		this.config = config;
		
		// Generate the field
		mineField = new Field[config.getHeight()][config.getWidth()];
		for (int y = 0; y < mineField.length; y++) {
			for (int x = 0; x < mineField[y].length; x++) {
				mineField[y][x] = new Field(random.nextFloat() < config.getDifficulty());
			}
		}
		
		// Generate at least one non-mine field
		final int initialNonMineX = random.nextInt(config.getWidth()), initialNonMineY = random.nextInt(config.getHeight());
		mineField[initialNonMineY][initialNonMineX] = new Field(false);
		
		// Link up the fields with each other, dirty.
		for (int y = 0; y < config.getHeight(); y++) {
			for (int x = 0; x < config.getWidth(); x++) {
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
		mineField[initialNonMineY][initialNonMineX].uncover();
	}
	
	public int getMineCount() {
		return (int) Stream.of(mineField)
				.flatMap(mf -> Stream.of(mf))
				.filter(Field::isMine)
				.count();
	}
	
	public int getUncoveredFieldCount() {
		return (int) Stream.of(mineField)
				.flatMap(mf -> Stream.of(mf))
				.filter(Field::isUncovered)
				.count();
	}
	
	public int getFieldCount() {
		return config.getWidth() * config.getHeight();
	}
	
	public Field getField(final Coords c) {
		return mineField[c.getY()][c.getX()];
	}
	
	public Field[][] getMineField() {
		return mineField;
	}
	
	public Config getConfig() {
		return config;
	}
}
