import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private GamePanel gamePanel = new GamePanel();

	public MainFrame() {
		super("Game of Life");

		setLayout(new BorderLayout());
		add(gamePanel, BorderLayout.CENTER);

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					gamePanel.stepToNextState();
					break;
				case KeyEvent.VK_BACK_SPACE:
					gamePanel.clear();
					break;
				case KeyEvent.VK_ENTER:
					gamePanel.randomize();
					break;
				}
			}
		});

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
