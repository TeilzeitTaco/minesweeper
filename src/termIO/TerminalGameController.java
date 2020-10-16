package termIO;

import java.util.InputMismatchException;
import java.util.Scanner;

import minesweeper.Coords;
import minesweeper.GameState;
import minesweeper.IGameController;


public class TerminalGameController implements IGameController {
	private final Scanner scanner = new Scanner(System.in);
	
	@Override
	public Coords getNextFieldToUncover(final GameState gameState) {
		while(true) {
			try {
				System.out.print(" Enter field to uncover (X|Y): ");
				final Coords c = new Coords(scanner.nextInt(), scanner.nextInt());
				
				
				if (
						(c.getX() > 0) && (c.getY() > 0) &&
						(c.getX() < gameState.getWidth()) &&
						(c.getY() < gameState.getHeight()))
					
					return c;

			} catch(final InputMismatchException e) { }	
			
			System.out.println("\n Invalid input!\n");
			scanner.nextLine();
		}
	}
}
