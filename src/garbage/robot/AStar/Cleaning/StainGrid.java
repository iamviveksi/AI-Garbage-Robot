package garbage.robot.AStar.Cleaning;

import java.util.Random;

public class StainGrid {
	private char[][] grid;
	private int dirtyFields;
	private int obstacles;
	private final int SIZE_X = 7;
	private final int SIZE_Y = 7;

	public StainGrid() {
		grid = new char[SIZE_Y][SIZE_X];
		this.generate();
		this.displayGrid();
	}

	private void generate() {
		Random generator = new Random();
		dirtyFields = generator.nextInt(20) + 5; // generuje ilosc zabrudzonych
													// pol 5 - 25
		obstacles = generator.nextInt(5) + 3; // generuje ilosc przeszkod 3 - 8
		for (int i = 0; i < SIZE_Y; i++) {
			for (int j = 0; j < SIZE_X; j++) {
				grid[i][j] = '0';
			}
		}
		int remainingDirtyFields = dirtyFields;
		int randX;
		int randY;
		while (remainingDirtyFields > 0) {
			randX = generator.nextInt(SIZE_X - 1);
			randY = generator.nextInt(SIZE_Y - 1);
			if (grid[randY][randX] != '1') {
				grid[randY][randX] = '1';
				remainingDirtyFields--;
			}
		}
		int remainingObstacles = obstacles;
		while (remainingObstacles > 0) {
			randX = generator.nextInt(SIZE_X - 1);
			randY = generator.nextInt(SIZE_Y - 1);
			if (grid[randY][randX] != '1' && grid[randY][randX] != '2') {
				grid[randY][randX] = '2';
				remainingObstacles--;
			}
		}
	}

	private void displayGrid() {
		for (int i = 0; i < SIZE_Y; i++) {
			for (int j = 0; j < SIZE_X; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public char getField(int x, int y){
		return grid[y][x];
	}
	
	public void cleanField(int x, int y){
		grid[y][x] = '0';
	}

	public int getSIZE_X() {
		return SIZE_X;
	}

	public int getSIZE_Y() {
		return SIZE_Y;
	}

	public int getDirtyFields() {
		return dirtyFields;
	}

	public void setDirtyFields(int dirtyFields) {
		this.dirtyFields = dirtyFields;
	}
	
	
}
