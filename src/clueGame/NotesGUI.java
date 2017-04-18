package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import tests.FakeRandom;

public class NotesGUI extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public Board board;


	public NotesGUI() throws FileNotFoundException, BadConfigFormatException {
		this.board = Board.getInstance();
		setLayout(new GridLayout(3, 2));
		add(peopleIndicator());
		add(bestPerson());
		add(roomIndicator());
		add(bestRoom());
		add(weaponIndicator());
		add(bestWeapon());
	}

	
	private Component peopleIndicator() throws FileNotFoundException {
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3,2));
		people.setBorder(new TitledBorder("People"));
		
		File person = new File("Person.txt");
		Scanner personReader = new Scanner(person);
		while (personReader.hasNextLine()) {
			people.add(createCheckBox(personReader.nextLine()));
		}
		personReader.close();
		

		return people;
	}
	
	
	private Component roomIndicator() throws FileNotFoundException {
		Map<Character, String> legendMap = Board.getInstance().getLegendMap();
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(5,2));
		rooms.setBorder(new TitledBorder("Rooms"));
		
		for (String room : legendMap.values()) {
			if (!room.equals("Walkway") && !room.equals("Closet")) {
				rooms.add(createCheckBox(room));
			}
		}
		return rooms;
	}
	
	private Component weaponIndicator() throws FileNotFoundException {
		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(3,2));
		weapon.setBorder(new TitledBorder("Weapons"));
		
		File weapons = new File("weapon.txt");
		Scanner weaponReader = new Scanner(weapons);
		while (weaponReader.hasNextLine()) {
			weapon.add(createCheckBox(weaponReader.nextLine()));
		}
		weaponReader.close();
		

		return weapon;
	}
	//Creates a checkbox with given name
	private Component createCheckBox (String checkBoxName){
		JCheckBox checkbox = new JCheckBox();
		checkbox.setText(checkBoxName);
		return checkbox;
	}
	
	private Component bestPerson() throws FileNotFoundException {
		JPanel menu = new JPanel();
		//menu.setLayout(new GridLayout(3,1));
		JComboBox personList = new JComboBox();
		menu.add(personList);
		menu.setBorder(new TitledBorder("Person Guess"));
		
		File person = new File("Person.txt");
		Scanner personReader = new Scanner(person);
		while (personReader.hasNextLine()) {
			personList.addItem(personReader.nextLine());
		}
		personList.addItem("Unsure");
		personReader.close();
		
		return menu;
	}
	private Component bestRoom() throws FileNotFoundException {
		Map<Character, String> legendMap = Board.getInstance().getLegendMap();
		JPanel menu = new JPanel();
		//menu.setLayout(new GridLayout(3,1));
		menu.setBorder(new TitledBorder("Rooms Guess"));
		JComboBox roomList = new JComboBox();
		menu.add(roomList);
		
		for (String room : legendMap.values()) {
			if (!room.equals("Walkway") && !room.equals("Closet")) {
			roomList.addItem(room);
			}
		}
		roomList.addItem("Unsure");
		return menu;
	}
	
	private Component bestWeapon() throws FileNotFoundException {
		JPanel menu = new JPanel();
		//menu.setLayout(new GridLayout(3,1));
		menu.setBorder(new TitledBorder("Weapon Guess"));
		JComboBox weaponList = new JComboBox();
		menu.add(weaponList);
		
		File weapon = new File("weapon.txt");
		Scanner weaponReader = new Scanner(weapon);
		while (weaponReader.hasNextLine()) {
			weaponList.addItem(weaponReader.nextLine());
		}
		weaponReader.close();
		weaponList.addItem("Unsure");
		return menu;
	}
	//Creates a label with given name
	private Component createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		return label;
	}

	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public static void notesInit() throws FileNotFoundException, BadConfigFormatException {
		Board.getInstance().initialize();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(550, 500);

		NotesGUI notes = new NotesGUI();
		frame.add(notes, BorderLayout.CENTER);

		frame.setVisible(true);
		
		

	}

}
