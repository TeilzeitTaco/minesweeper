package minesweeper;


/**
 * Config to be passed into the GameState constructor.
 * 
 * @author SeiJ
 */
public final class Config {
	private final int width, height, seed;
	private final float difficulty;
	private final boolean useBot;
	
	public Config(final int width, final int heigth, final int seed, 
			final float difficulty, final boolean useBot) {
		
		this.difficulty = difficulty;
		this.useBot = useBot;
		this.height = heigth;
		this.width = width;
		this.seed = seed;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public boolean getUseBot() {
		return useBot;
	}
}
