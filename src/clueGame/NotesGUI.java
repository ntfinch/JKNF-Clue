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

public class NotesGUI extends JPanel{

	
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
		Map<Character, String> legendMap = clueGame.Board.getLegendMap();
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
	 */
	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(400, 600);

		NotesGUI notes = new NotesGUI();
		frame.add(notes, BorderLayout.CENTER);

		frame.setVisible(true);

	}

}
