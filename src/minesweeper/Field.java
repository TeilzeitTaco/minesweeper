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
	
	public boolean isMine() {
		return mine;
	}
	
	public boolean isUncovered() {
		return uncovered;
	}
	
	public HashMap<Direction, Field> getNeighbours() {
		return neighbours;
	}
	
	public byte getNeighbouringMineCount() {
		byte mineCount = 0;
		for (final Entry<Direction, Field> e : neighbours.entrySet()) {
			if (e.getValue().isMine())
				mineCount++;
		}
		
		return mineCount;
	}
	
	public void uncover() throws IllegalStateException {
		if (uncovered)
			throw new IllegalStateException("Attempted to double-uncover field!");
		
		uncovered = true;
		if (getNeighbouringMineCount() == 0) {
			for (final Entry<Direction, Field> e : neighbours.entrySet()) {
				final Field f = e.getValue();
				if (!f.isUncovered()) {
					f.uncover();
				}	
			} 
		}
	}
}
