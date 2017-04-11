package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGameJFrame extends JFrame {

	private Board board;

	public ClueGameJFrame() throws FileNotFoundException, BadConfigFormatException {
		init();
	}

	void init() throws FileNotFoundException, BadConfigFormatException {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		JMenu file = new JMenu("File");
		bar.add(file);
		file.add(fileExit());
		file.add(new JMenuItem("Detective Notes"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(840, 1005);
		
		board = Board.getInstance();
		board.initialize();
		//setContentPane(board);
		
	    //add(board, BorderLayout.NORTH);
		
		setVisible(true);
		getGui();
		
		
		
		//Notes Initialize
		Board.getInstance().initialize();
		JFrame notesFrame = new JFrame();
		notesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		notesFrame.setTitle("Clue");
		notesFrame.setSize(550, 500);

		NotesGUI notes = new NotesGUI();
		notesFrame.add(notes, BorderLayout.CENTER);

		notesFrame.setVisible(true);
	}

	public void getGui() {
		// this.setSize(1200, 1200);
		add(this.board, BorderLayout.CENTER);
		ControlGUI control = new ControlGUI();
		control.setSize(500, 500);
		add(control, BorderLayout.SOUTH);
	}

	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
		ClueGameJFrame frame = new ClueGameJFrame();
		frame.setVisible(true);

	}
	
	public JMenuItem fileExit(){
		JMenuItem exit = new JMenuItem("Exit");
		return exit;
	}
}
