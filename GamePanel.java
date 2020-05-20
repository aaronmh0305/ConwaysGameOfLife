import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private static final int CELL_SIZE = 10;

	private static final Color backgroundColor = Color.BLACK;
	private static final Color foregroundColor = Color.GREEN;
	private static final Color gridColor = Color.GRAY;

	private int topBottomMargin;
	private int leftRightMargin;

	private World world;

	public GamePanel() {
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int mouseX = e.getX();
				int mouseY = e.getY();

				int row = (mouseY - topBottomMargin) / CELL_SIZE;
				int column = (mouseX - leftRightMargin) / CELL_SIZE;

				if (mouseX < leftRightMargin || row >= world.getNumRows() || mouseY < topBottomMargin
						|| column >= world.getNumCols()) {
					return;
				}

				world.setCell(row, column, !world.getCell(row, column));
				repaint();

			}
		});
	}

	// This lets you draw graphics on the panel
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();

		leftRightMargin = ((width % CELL_SIZE) + CELL_SIZE) / 2;
		topBottomMargin = ((height % CELL_SIZE) + CELL_SIZE) / 2;

		int rows = (height - (2 * topBottomMargin)) / CELL_SIZE;
		int columns = (width - (2 * leftRightMargin)) / CELL_SIZE;

		if (world == null || world.getNumRows() != rows || world.getNumCols() != columns) {
			world = new World(rows, columns);
		} 

		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, width, height);

		// update world cells
		for (int yRow = 0; yRow < rows; yRow++) {
			for (int xCol = 0; xCol < columns; xCol++) {
				fillCell(g2d, yRow, xCol, world.getCell(yRow, xCol));
			}
		}

		drawGrid(g2d, width, height);
	}

	/**
	 * Draws the corresponding gray outlined grid with cells of size CELL_SIZE width
	 * and CELL_SIZE height.
	 * 
	 * @param g2d
	 * @param width
	 * @param height
	 */
	private void drawGrid(Graphics2D g2d, int width, int height) {
		g2d.setColor(gridColor);

		for (int x = leftRightMargin; x <= width - leftRightMargin; x += CELL_SIZE) {
			g2d.drawLine(x, topBottomMargin, x, height - topBottomMargin - 1);
		}

		for (int y = topBottomMargin; y <= height - topBottomMargin; y += CELL_SIZE) {
			g2d.drawLine(leftRightMargin, y, width - leftRightMargin - 1, y);
		}
	}

	/**
	 * Fills a cell on our grid with a specific color at the row and column number
	 * specified.
	 * 
	 * @formatter:off
	 * The color chosen is: 
	 * background color if status == false  (black) 
	 * foreground color if status == true   (green)
	 * @formatter:on
	 * 
	 * @param g2d
	 * @param row
	 * @param col
	 * @param status
	 */
	private void fillCell(Graphics2D g2d, int row, int col, boolean status) {

		g2d.setColor(status ? foregroundColor : backgroundColor);
		g2d.fillRect(leftRightMargin + (col * CELL_SIZE) + 1, topBottomMargin + (row * CELL_SIZE) + 1, CELL_SIZE - 1,
				CELL_SIZE - 1);

	}
	
	public void stepToNextState() {
		world.nextState();
		repaint();
	}
	
	public void clear() {
		world.clearGrid();
		repaint();
	}
	
	public void randomize() {
		world.randomize();
		repaint();
	}
	

}
