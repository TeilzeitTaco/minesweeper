package roboIO;

import javax.swing.JOptionPane;

import minesweeper.Coords;
import minesweeper.Field;
import minesweeper.GameState;
import minesweeper.IGameController;


public class RoboGameController implements IGameController {
	@Override
	public Coords getNextFieldToUncover(final GameState gameState) {
		final Field[][] mineField = gameState.getMineField();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Get all fields touching the border from the inside,
		// and flag known mines
		for (Field[] fr : mineField) {
			for (Field f : fr) {
				if (!f.inInnerBorder() || f.getNeighbouringCoveredFieldCount() != f.getNeighbouringMineCount())
					continue;
				
				for (Field n : f.getNeighbours().values()) {
					if (!n.isUncovered())
						n.setFlagged(true);
				}
			}
		}

		// Get all the uncovered fields touching the border, and
		// uncover some...
	    for (int y = 0; y < mineField.length; y++) {
	    	for (int x = 0; x < mineField[y].length; x++) {
	    		final Field f = mineField[y][x];
	   
	    		// Process all outer border fields that are not flagged
	    		if (!f.inOuterBorder() || f.isFlagged())
	    			continue;
	    		
	    		for (Field n : f.getNeighbours().values()) {
	    			if (!n.isUncovered())
	    				continue;
	    			
	    			if (n.getNeighbouringMineCount() == n.getNeighbouringFlagCount())
	    				return new Coords(x, y);
	    		}
	    	}
	    }
	    
	    JOptionPane.showMessageDialog(null, "Impossible without guessing!");
		System.exit(0);
		return null;
	}
}
