package swingIO;

import minesweeper.Config;
import minesweeper.IGameConfigurator;
import minesweeper.IGameController;
import minesweeper.IGameFrontend;
import minesweeper.IGameVisualiser;

import roboIO.RoboGameController;


public class SwingGameFrontend implements IGameFrontend {
	private final SwingGameVisualiser sgv = new SwingGameVisualiser();

	@Override
	public IGameConfigurator getGameConfigurator() {
		return new SwingGameConfigurator();
	}

	@Override
	public IGameController getGameController(final Config config) {
		if (config.getUseBot())
			return new RoboGameController();
		
		return sgv;
	}

	@Override
	public IGameVisualiser getGameVisualiser(final Config config) {
		return sgv;
	}
}
