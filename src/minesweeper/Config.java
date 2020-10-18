package minesweeper;


public final class Config {
	private final int width, height;
	private final float difficulty;
	
	public Config(final int width, final int heigth, final float difficulty) {
		this.difficulty = difficulty;
		this.height = heigth;
		this.width = width;
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
}
