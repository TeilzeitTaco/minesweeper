package minesweeper;


public final class Config {
	private final int width, height;
	private final float difficulty;
	private final boolean useBot;
	
	public Config(final int width, final int heigth, final float difficulty, final boolean useBot) {
		this.difficulty = difficulty;
		this.height = heigth;
		this.width = width;
		this.useBot = useBot;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public boolean getUseBot() {
		return useBot;
	}
}
