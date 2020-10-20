package termIO;

import minesweeper.Config;
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
	public IGameController getGameController(final Config config) {
		return new TerminalGameController();
	}

	@Override
	public IGameVisualiser getGameVisualiser(final Config config) {
		return new TerminalGameVisualiser();
	}
}
