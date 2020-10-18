package termIO;

import minesweeper.IGameConfigurator;
import minesweeper.IGameController;
import minesweeper.IGameFrontend;
import minesweeper.IGameVisualiser;


public class TerminalGameFrontend implements IGameFrontend {
	@Override
	public IGameConfigurator getGameConfigurator() {
		return new TerminalGameConfigurator();
	}

	@Override
	public IGameController getGameController() {
		return new TerminalGameController();
	}

	@Override
	public IGameVisualiser getGameVisualiser() {
		return new TerminalGameVisualiser();
	}
}
