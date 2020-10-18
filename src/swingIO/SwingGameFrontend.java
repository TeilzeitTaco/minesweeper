package swingIO;

import minesweeper.IGameConfigurator;
import minesweeper.IGameController;
import minesweeper.IGameFrontend;
import minesweeper.IGameVisualiser;


public class SwingGameFrontend implements IGameFrontend {
	private final SwingGameVisualiser sgv = new SwingGameVisualiser();

	@Override
	public IGameConfigurator getGameConfigurator() {
		return new SwingGameConfigurator();
	}

	@Override
	public IGameController getGameController() {
		return sgv;
	}

	@Override
	public IGameVisualiser getGameVisualiser() {
		return sgv;
	}
}
