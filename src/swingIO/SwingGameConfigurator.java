package swingIO;

import javax.swing.*;

import minesweeper.Config;
import minesweeper.IGameConfigurator;


public class SwingGameConfigurator implements IGameConfigurator {
	private volatile boolean cont;
	
	@Override
	public Config getConfig() {
		// Width
		final JSlider jsw = new JSlider();
		jsw.setBounds(95, 2, 100, 20);
		jsw.setMinimum(8);
		jsw.setMaximum(40);
		
		final JLabel jlw = new JLabel("Minefield width");
		jlw.setBounds(5, 0, 100, 20);
		
		// Height
		final JSlider jsh = new JSlider();
		jsh.setBounds(95, 22, 100, 20);
		jsh.setMinimum(8);
		jsh.setMaximum(40);
		
		final JLabel jlh = new JLabel("Minefield height");
		jlh.setBounds(5, 20, 100, 20);
		
		// Difficulty
		final JSlider jsd = new JSlider();
		jsd.setBounds(95, 42, 100, 20);
		jsd.setMinimum(0);
		jsd.setMaximum(100);
		
		final JLabel jld = new JLabel("Difficulty");
		jld.setBounds(5, 40, 100, 20);
		
		// Continue button
		final JButton jb = new JButton("Continue");
		jb.setBounds(200, 5, 100, 50); // X, Y, width, height
		jb.addActionListener(e -> {
			cont = true;
		});
		
		// Setup frame
		final JFrame jf = new JFrame();
		jf.add(jb);
		
		jf.add(jsw);
		jf.add(jlw);
		
		jf.add(jsh);
		jf.add(jlh);
		
		jf.add(jsd);
		jf.add(jld);
		
		jf.setSize(320, 100); 
		jf.setLayout(null);
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
		return new Config(jsw.getValue(), jsh.getValue(), jsd.getValue() / 100f);
	}
}
