package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGameJFrame {
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(600, 200);

		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);

		frame.setVisible(true);
	}
}
