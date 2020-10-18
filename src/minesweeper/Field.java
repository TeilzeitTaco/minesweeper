package minesweeper;

import java.util.HashMap;
import java.util.Map.Entry;


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
	private boolean uncovered;
	
	public Field(final boolean mine) {
		this.mine = mine;
	}
	
	public boolean isUncovered() {
		return uncovered;
	}
	
	public byte getNeighbouringMineCount() {		
		return (byte) neighbours
				.values().stream()
				.filter(Field::isMine)
				.count();
	}
	
	@Package boolean isMine() {
		return mine;
	}
	
	@Package HashMap<Direction, Field> getNeighbours() {
		return neighbours;
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
