package minesweeper;

import java.util.HashMap;


/**
 * Represents a singular field on the play area.
 * Might be a mine, and can be uncovered.
 * Is aware of its neighbor fields.
 * 
 * @author SeiJ
 */
public class Field {
	private final HashMap<Direction, Field> neighbours = new HashMap<>();
	private final boolean mine;
	
	private byte cachedNeighbouringMineCount = -1;
	private boolean uncovered, flagged;
	
	public Field(final boolean mine) {
		this.mine = mine;
	}
	
	public boolean isUncovered() {
		return uncovered;
	}
	
	/**
	 * The flagged member represents the player's
	 * suspicion that a certain field might be a mine.
	 * 
	 * @return The current flag state.
	 */
	public boolean isFlagged() {
		return flagged;
	}
	
	public void setFlagged(final boolean flagged) {
		if (uncovered)
			throw new IllegalStateException("Attempted to flag uncovered field!");
			
		this.flagged = flagged;
	}
	
	/**
	 * Get the count of neighboring mines, and check for permission.
	 * 
	 * @return The count of mines.
	 */
	public byte getNeighbouringMineCount() {
		if (!uncovered)
			throw new IllegalStateException("Attempted to read mine count of covered field!");
		
		return _getNeighbouringMineCount();
	}
	
	/**
	 * getNeighbouringMineCount() without permission checks.
	 * 
	 * @return The count of mines.
	 */
	@Package byte _getNeighbouringMineCount() {
		// This, in general, doesn't change at all.
		if (cachedNeighbouringMineCount == -1) {
			cachedNeighbouringMineCount = (byte) neighbours
					.values().stream()
					.filter(Field::isMine)
					.count();
		}
		
		return cachedNeighbouringMineCount;
	}
	
	public byte getNeighbouringFlagCount() {
		return (byte) neighbours
				.values().stream()
				.filter(Field::isFlagged)
				.count();
	}
	
	public byte getNeighbouringCoveredFieldCount() {
		return (byte) neighbours
				.values().stream()
				.filter(f -> !f.isUncovered())
				.count();
	}
	
	/** 
	 * @return true if this field touches the inner side of the border
	 */
	public boolean inInnerBorder() {
		return uncovered && neighbours
				.values().stream()
				.filter(f -> !f.isUncovered())
				.count() > 0;
	}
	
	/** 
	 * @return true if this field touches the outer side of the border
	 */
	public boolean inOuterBorder() {
		return !uncovered && neighbours
				.values().stream()
				.filter(Field::isUncovered)
				.count() > 0;
	}
	
	public HashMap<Direction, Field> getNeighbours() {
		return neighbours;
	}
	
	
	@Package boolean isMine() {
		return mine;
	}
	
	/**
	 * Uncover this field, and if it isn't a mine has no neighboring mines,
	 * uncover all neighboring mines.
	 * 
	 * @throws IllegalStateException If this field was already uncovered.
	 */
	@Package void uncover() throws IllegalStateException {
		if (uncovered)
			throw new IllegalStateException("Attempted to double-uncover field!");
		
		uncovered = true;
		if (getNeighbouringMineCount() == 0) {
			neighbours
				.values().stream()
				.filter(e -> !e.isUncovered())
				.forEach(Field::uncover);
		}
	}
}
