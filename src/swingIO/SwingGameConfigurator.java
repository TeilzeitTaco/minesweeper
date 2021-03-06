package swingIO;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.*;

import minesweeper.Config;
import minesweeper.IGameConfigurator;


public class SwingGameConfigurator implements IGameConfigurator {
	private final Random random = new Random();
	private volatile boolean cont;
	
	@Override
	public Config getConfig() {
		final JPanel jp = new JPanel();
	    jp.setLayout(new GridBagLayout());
	    
		final GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(1, 1, 1, 1);
	    c.fill = GridBagConstraints.BOTH;
		
	    // Slider
		final JLabel jlw = new JLabel("Minefield width");
		final JSlider jsw = new JSlider();
		jsw.setMinimum(8);
		jsw.setMaximum(32);
		
		c.gridx = 0;
		c.gridy = 0;
		jp.add(jlw, c);
		c.gridx = 1;
		c.gridy = 0;
		jp.add(jsw, c);
		
		// Slider
		final JLabel jlh = new JLabel("Minefield height");
		final JSlider jsh = new JSlider();
		jsh.setMinimum(8);
		jsh.setMaximum(32);
		
		c.gridx = 0;
		c.gridy = 1;
		jp.add(jlh, c);
		c.gridx = 1;
		c.gridy = 1;
		jp.add(jsh, c);
		
		// Slider
		final JLabel jld = new JLabel("Difficulty");
		final JSlider jsd = new JSlider();
		jsd.setMinimum(0);
		jsd.setMaximum(20);
		
		c.gridx = 0;
		c.gridy = 2;
		jp.add(jld, c);
		c.gridx = 1;
		c.gridy = 2;
		jp.add(jsd, c);
		
		// Seed
		final JLabel jls = new JLabel("Seed");
		final JTextField jts = new JTextField();
		
		jts.setText(String.valueOf(random.nextInt(2147483647)));

		c.gridx = 0;
		c.gridy = 3;
		jp.add(jls, c);
		c.gridx = 1;
		c.gridy = 3;
		jp.add(jts, c);

		// Check box
	    final JLabel jlb = new JLabel("Enable bot");
		final JCheckBox jcb = new JCheckBox();

		c.gridx = 0;
		c.gridy = 4;
		jp.add(jlb, c);
		c.gridx = 1;
		c.gridy = 4;
		jp.add(jcb, c);
		
		// Button
		final JButton jb = new JButton("Continue");
		jb.addActionListener(e -> {
			cont = true;
		});
		
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 5;
	    jp.add(jb, c);
		
		// Setup frame
		final JFrame jf = new JFrame();
		jf.add(jp);
		jf.pack();
		jf.setTitle("Minesweeper Config");
		jf.setVisible(true);
		
		// Wait for button press
		while(!cont) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		jf.setVisible(false);
		cont = false;  // Be reusable
		return new Config(jsw.getValue(), jsh.getValue(), Integer.valueOf(jts.getText()), jsd.getValue() / 100f, jcb.isSelected());
	}
}
