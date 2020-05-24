import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	// default save file name
	private static final String defaultFileName = "gameoflife.gol";
	
	private GamePanel gamePanel = new GamePanel();

	public MainFrame() {
		super("Game of Life");

		setLayout(new BorderLayout());
		add(gamePanel, BorderLayout.CENTER);
		
	    this.addMenu();

		// allow keyboard input from user
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
	
	private void addMenu() {
		
		
		// create load and save file menus
		JMenuItem loadItem = new JMenuItem("Load");
		JMenuItem saveItem = new JMenuItem("Save");
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		
		JMenuBar bar = new JMenuBar();
		bar.setBackground(new Color(0.75f, 0.75f, 0.75f));
		bar.add(fileMenu);
		
		setJMenuBar(bar);
		
		// file choosing code - only allow .gol files
		JFileChooser fileChooser = new JFileChooser();
		var fileFilter = new FileNameExtensionFilter("Game of Life Files", "gol");
		fileChooser.setAcceptAllFileFilterUsed(false); // disallow choosing all files
		fileChooser.addChoosableFileFilter(fileFilter); 
		
		loadItem.addActionListener(x -> {
			
			int userOption = fileChooser.showOpenDialog(MainFrame.this);
			if (userOption == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				gamePanel.load(selectedFile);
			}
		});
		
		saveItem.addActionListener(x -> {
			fileChooser.setSelectedFile(new File(defaultFileName));
			int userOption = fileChooser.showSaveDialog(MainFrame.this);
			if (userOption == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				gamePanel.save(selectedFile);
			}
		});
		
	}

}
