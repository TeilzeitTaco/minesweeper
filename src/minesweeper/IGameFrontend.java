package minesweeper;


/**
 * Abstracts away the individual interfaces initialization,
 * so that they can for example reference each other with
 * custom constructors or similar.
 * 
 * @author SeiJ
 */
public interface IGameFrontend {
	IGameConfigurator getGameConfigurator();
	IGameController getGameController();
	IGameVisualiser getGameVisualiser();
}
