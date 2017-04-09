package clueGame;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class ClueGameJFrame {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(800, 1200);

		ControlGUI control = new ControlGUI();
		frame.add(control, BorderLayout.CENTER);

		frame.setVisible(true);
		
		Board.getInstance().initialize();
		JFrame notesFrame = new JFrame();
		notesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		notesFrame.setTitle("Clue");
		notesFrame.setSize(550, 500);

		NotesGUI notes = new NotesGUI();
		notesFrame.add(notes, BorderLayout.CENTER);

		notesFrame.setVisible(true);
		
		
	}
}
