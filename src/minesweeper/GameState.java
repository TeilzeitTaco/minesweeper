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
	private final Field[][] mineField;
	private final Config config;
	
	/**
	 * Create a new minefield that is verifiably solvable
	 * 
	 * @param config The config to be used.
	 */
	public GameState(final Config config) {
		final Random random = new Random(config.getSeed());
		mineField = new Field[config.getHeight()][config.getWidth()];
		this.config = config;
		
		do {
			for (int y = 0; y < mineField.length; y++) {
				for (int x = 0; x < mineField[y].length; x++) {
					mineField[y][x] = new Field(random.nextFloat() < config.getDifficulty());
				}
			}
			
			// Generate at least one non-mine field
			mineField[random.nextInt(config.getHeight())][random.nextInt(config.getWidth())] = new Field(false);
			
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
			
			uncoverStartingField();
		} while (!isSolvable());	
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
	
	public int getCoveredFieldCount() {
		return getFieldCount() - getUncoveredFieldCount();
	}
	
	public Field getField(final Coords c) {
		return mineField[c.getY()][c.getX()];
	}
	
	public boolean isFinished() {
		return getMineCount() == getCoveredFieldCount();
	}
	
	public Field[][] getMineField() {
		return mineField;
	}
	
	public Config getConfig() {
		return config;
	}
	
	/**
	 * Cover all fields and remove all flags, then uncover a new
	 * starting field.
	 */
	private void reset() {
		Stream.of(mineField).flatMap(mf -> Stream.of(mf)).forEach(f -> {
			if (f.isFlagged())
				f.setFlagged(false);
			
			if (f.isUncovered())
				f.cover();
		});
		
		uncoverStartingField();
	}
	
	/**
	 * Uncover a field with as few mine neighbours as possible.
	 */
	private void uncoverStartingField() {
		// Uncover the initial field, and prefer fields with less mine neighbors.
		Stream.of(mineField).flatMap(mf -> Stream.of(mf)).sorted((a, b) -> {
			final int nca = a._getNeighbouringMineCount(),
					ncb = b._getNeighbouringMineCount();
			
			return (nca > ncb) ? 1 : ((nca < ncb) ? -1 : 0);  // La Creatura...
		}).findFirst().ifPresent(Field::uncover);
	}
	
	/**
	 * Verify if the generated minefield is solvable.
	 * This mutates the game state, with a resulting .reset() call.
	 * 
	 * @return true if the field is solvable
	 */
	private boolean isSolvable() {
		// Loop labels are weird.
		main: while(true) {
			// Get all fields touching the border from the inside,
			// and flag all inferable mines...
			for (final Field[] fr : mineField) {
				for (final Field f : fr) {
					if (!f.inInnerBorder() || f.getNeighbouringCoveredFieldCount() != f.getNeighbouringMineCount())
						continue;
					
					for (final Field n : f.getNeighbours().values()) {
						if (!n.isUncovered())
							n.setFlagged(true);
					}
				}
			}

			// Get all the uncovered fields touching the border, and
			// uncover some...
			for (final Field[] fr : mineField) {
				for (final Field f : fr) {
		    		if (!f.inOuterBorder() || f.isFlagged())
		    			continue;
		    		
		    		for (final Field n : f.getNeighbours().values()) {
		    			if (!n.isUncovered())
		    				continue;
		    			
		    			if (n.getNeighbouringMineCount() == n.getNeighbouringFlagCount()) {
		    				f.uncover();
		    				if (f.isMine())   // Sanity check
		    					return false;
		    				
		    				if (isFinished()) {
		    					reset();
		    					return true;
		    				}
		    				
		    				continue main;
		    			}
		    		}
		    	}
		    }
		    
		    return false;
		}
	}
}
