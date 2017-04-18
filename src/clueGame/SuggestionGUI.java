package clueGame;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class SuggestionGUI extends JPanel {
public Board board;


public SuggestionGUI() {
	this.board = Board.getInstance();
	setLayout (new GridLayout(4,2));
	
}

private Component currentRoom() {
	return board;
	
}

private Component personGuess() throws FileNotFoundException {
	JPanel menu = new JPanel();
	JComboBox personList = new JComboBox();
	menu.add(personList);
	menu.setBorder(new TitledBorder("Person Guess"));
	
	File person = new File("Person.txt");
	Scanner personReader = new Scanner(person);
	while (personReader.hasNextLine()) {
		personList.addItem(personReader.nextLine());
	}
	
	personReader.close();
	return menu;
}

private Component bestWeapon() throws FileNotFoundException {
	JPanel menu = new JPanel();
	menu.setBorder(new TitledBorder("Weapon Guess"));
	JComboBox weaponList = new JComboBox();
	menu.add(weaponList);
	
	File weapon = new File("weapon.txt");
	Scanner weaponReader = new Scanner(weapon);
	while (weaponReader.hasNextLine()) {
		weaponList.addItem(weaponReader.nextLine());
	}
	weaponReader.close();
	return menu;
}



}
