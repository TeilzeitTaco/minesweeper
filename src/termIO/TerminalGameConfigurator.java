package termIO;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.FilterInputStream;
import java.io.IOException;

import minesweeper.Config;
import minesweeper.IGameConfigurator;


public class TerminalGameConfigurator implements IGameConfigurator {
	@Override
	public Config getConfig() {
		try {
			System.out.println(" [Terminal Minesweeper Frontend V0.1]\n");
			
			final Scanner scanner = new Scanner(new FilterInputStream(System.in) {
			    @Override
			    public void close() throws IOException {
			        // Don't close System.in! 
			    }
			});
			
			System.out.print(" Enter field size (X|Y): ");
			final int width = scanner.nextInt(), height = scanner.nextInt();
			
			System.out.print(" Enter difficulty (0.0 - 1.0): ");
			final float difficulty = scanner.nextFloat();
			
			scanner.close();
			return new Config(width, height, difficulty, false);
			
		} catch(final InputMismatchException e) {
			System.out.println(" \nInvalid input!");
			System.exit(-1);
		}
		
		return null;  // Doesn't happen...
	}
}
