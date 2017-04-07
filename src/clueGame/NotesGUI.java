package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tests.FakeRandom;

public class NotesGUI extends JPanel{

	private static Board board;

	public static void initTheBoard() throws FileNotFoundException, BadConfigFormatException {
		// initialize board
		// Board is singleton, get the only instance and initialize it
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt", "TDNFTP_players.txt");

		board.initialize();
		board.loadRoomConfig();
	}
	
	private static final long serialVersionUID = 1L;
	

	public NotesGUI() throws FileNotFoundException {
		setLayout(new GridLayout(3, 2));
		add(peopleIndicator());
		add(roomIndicator());
		
		
	}

	
	//Creates a label with given name
	private Component createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		return label;
	}
	
	//Creates a checkbox with given name
	private Component createCheckBox (String checkBoxName){
		JCheckBox checkbox = new JCheckBox();
		checkbox.setText(checkBoxName);
		return checkbox;
	}
	
	private Component peopleIndicator() throws FileNotFoundException {
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3,1));
		people.add(createLabel("People"), BorderLayout.NORTH);
		
		File person = new File("Person.txt");
		Scanner personReader = new Scanner(person);
		while (personReader.hasNextLine()) {
			people.add(createCheckBox(personReader.nextLine()));
		}
		personReader.close();
		

		return people;
	}
	
	
	private Component roomIndicator() throws FileNotFoundException {
		Map<Character, String> legendMap = board.getLegendMap();
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(3,1));
		rooms.add(createLabel("People"), BorderLayout.CENTER);
		
		for (String room : legendMap.values()) {
			if (!room.equals("Walkway") && !room.equals("Closet")) {
				rooms.add(createCheckBox(room));
			}
		}
		

		return rooms;
	}

	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
		initTheBoard();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(400, 600);

		NotesGUI notes = new NotesGUI();
		frame.add(notes, BorderLayout.CENTER);

		frame.setVisible(true);
		
		

	}

}