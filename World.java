import java.util.Arrays;
import java.util.Random;

public class World {

	private int rows;
	private int columns;

	private boolean[][] grid; // determines which rows and columns are colored
	private boolean[][] buffer;
	
	private Random random = new Random();

	public World(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		this.grid = new boolean[rows][columns];
		this.buffer = new boolean[rows][columns];
	}

	public int getNumRows() {
		return this.rows;
	}

	public int getNumCols() {
		return this.columns;
	}

	public boolean getCell(int row, int column) {
		return grid[row][column];
	}

	public void setCell(int row, int column, boolean status) {
		grid[row][column] = status;
	}

	private int countNeighbors(int row, int col) {

		int neighborCount = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			for (int colOffset = -1; colOffset <= 1; colOffset++) {

				int gridRow = row + rowOffset;
				int gridCol = col + colOffset;

				if (gridRow < 0 || gridRow >= rows) {
					// if the row is out of bounds, don't bother iterating over remaining columns
					break;
				} else if ((rowOffset == 0 && colOffset == 0) || (gridCol < 0) || (gridCol >= columns)) {
					// if the current cell is chosen or the column goes out of bounds, skip this
					// column
					continue;
				}

				// if neighbor cell is marked active, count it
				if (grid[gridRow][gridCol])
					neighborCount++;

			}
		}

		return neighborCount;
	}

	public void nextState() {

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
                int neighbors = countNeighbors(row, col);
                
                // GAME OF LIFE ALGORITHM CODE
                // if neighbors > 3 || neighbors < 2 then false already
                boolean status = false;
                if (neighbors == 3) {
                	status = true;
                } else if (neighbors == 2){
                	status = grid[row][col];
                }
                
                buffer[row][col] = status;
                
			}
		}
		
		// copy over the buffer into the actual grid
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
		        grid[row][col] = buffer[row][col];
			}
		}

	}

	public void clearGrid() {

		for (int row = 0; row < rows; row++) {
			Arrays.fill(grid[row], false);
		}

	}

	public void randomize() {

		// set roughly 1/10 cells to randomly be active
		for (int i = 0; i < (rows * columns) / 10; i++) {
			grid[random.nextInt(rows)][random.nextInt(columns)] = true;
		}

	}

}
