package swingIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import minesweeper.Coords;
import minesweeper.Config;
import minesweeper.Field;
import minesweeper.GameState;
import minesweeper.IGameVisualiser;
import minesweeper.IGameController;


public class SwingGameVisualiser implements IGameVisualiser, IGameController {
	private static final Color uncoveredFieldColor = new Color(153, 139, 138);
	private static final Color coveredFieldColor = new Color(255, 112, 102);
	private static final Color flaggedFieldColor = new Color(227, 166, 227);
	
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
		            jb.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(final MouseEvent e) {
							// Right click to flag fields
							if (SwingUtilities.isRightMouseButton(e)) {
								mineField[_y][_x].setFlagged(!mineField[_y][_x].isFlagged());
								updateJButtons();
								return;
							}
							
							lastX = _x;
			            	lastY = _y;
			            	cont = true;
						}

						@Override
						public void mousePressed(final MouseEvent e) {
						}

						@Override
						public void mouseReleased(final MouseEvent e) {
						}

						@Override
						public void mouseEntered(final MouseEvent e) {
						}

						@Override
						public void mouseExited(final MouseEvent e) {
						}
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
		
		updateTitle(gameState);
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
	            	jb.setBackground(uncoveredFieldColor);
	            	
	            	final int mineCount = f.getNeighbouringMineCount();
	            	if (mineCount > 0) {
	            		jb.setText(String.valueOf(mineCount));
		            	jb.setMargin(new Insets(0, 0, 0, 0));
	            	}
	            	
	            } else if (f.isFlagged()) {
	            	jb.setBackground(flaggedFieldColor);
	            } else {
	            	jb.setBackground(coveredFieldColor);
	            }
	        }
	    }
	}
	
	private void updateTitle(final GameState gameState) {
		final int remainingFieldsCount = gameState.getFieldCount() - gameState.getUncoveredFieldCount();
		jf.setTitle(String.format("Swing Minesweeper [%d fields remaining, %d mines]", remainingFieldsCount, gameState.getMineCount()));
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
