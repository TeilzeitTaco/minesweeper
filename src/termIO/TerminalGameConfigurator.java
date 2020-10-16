package termIO;

import minesweeper.Config;
import minesweeper.IGameConfigurator;


public class TerminalGameConfigurator implements IGameConfigurator {
	@Override
	public Config getConfig() {
		return new Config(24, 16, 0.1f);
	}
}
