package swingIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import minesweeper.Coords;
import minesweeper.Config;
import minesweeper.Field;
import minesweeper.GameState;
import minesweeper.IGameVisualiser;
import minesweeper.IGameController;


public class SwingGameVisualiser implements IGameVisualiser, IGameController {
	private JFrame jf;
	private boolean initialized;
	
	private JButton[][] jbMatrix;
	private Field[][] mineField;
	
	private int lastX, lastY;
	private volatile boolean cont;
	
	@Override
	public void onFieldUpdate(final GameState gameState) {
		if (!initialized) {
			initialized = true;
			jf = new JFrame();
			jf.setTitle("Swing Minesweeper");
			mineField = gameState.getMineField();
			
			final Config config = gameState.getConfig();
			jbMatrix = new JButton[config.getHeight()][config.getWidth()];
			
			final JPanel jp = new JPanel();
		    jp.setLayout(new GridBagLayout());
		    
			final GridBagConstraints c = new GridBagConstraints();
		    
		    c.insets = new Insets(1, 1, 1, 1);
		    c.fill = GridBagConstraints.BOTH;

		    // Make the JButtons!
		    for (int y = 0; y < jbMatrix.length; y++) {
		    	for (int x = 0; x < jbMatrix[y].length; x++) {
		            final JButton jb = new JButton();
		            jb.setPreferredSize(new Dimension(20, 20));
		            
		            jbMatrix[y][x] = jb;
		            final int _x = x, _y = y;
		            jb.addActionListener(e -> {
		            	lastX = _x;
		            	lastY = _y;
		            	cont = true;
		            });
		         
		            c.gridx = x;
		            c.gridy = y;
		            jp.add(jb, c);
		        }
		    }
		    
		    jf.add(jp);
		    jf.pack();
			jf.setVisible(true);
		}
		
		updateJButtons();
	}
	
	/**
	 * Update the visuals of the JButton matrix.
	 */
	private void updateJButtons() {	    
		for (int y = 0; y < jbMatrix.length; y++) {
	    	for (int x = 0; x < jbMatrix[y].length; x++) {
	            final JButton jb = jbMatrix[y][x];
	            final Field f = mineField[y][x];
	            
	            if (f.isUncovered()) {
	            	jb.setBackground(Color.GRAY);
	            	
	            	final int mineCount = f.getNeighbouringMineCount();
	            	if (mineCount > 0) {
	            		jb.setText(String.valueOf(mineCount));
		            	jb.setMargin(new Insets(0, 0, 0, 0));
	            	}
	            } else {
	            	jb.setBackground(Color.DARK_GRAY);
	            }
	        }
	    }
	}

	@Override
	public void onGameOver(final Coords c) {
		JOptionPane.showMessageDialog(jf, "You lost!");
		jf.setVisible(false);
	}

	@Override
	public void onGameWon(final Coords c) {
		JOptionPane.showMessageDialog(jf, "You won!");
		jf.setVisible(false);
	}

	@Override
	public void onIllegalMove(final Coords c) {
		// No operation
	}

	@Override
	public Coords getNextFieldToUncover(final GameState gameState) {
		while(!cont) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		cont = false;
		return new Coords(lastX, lastY);
	}
}
