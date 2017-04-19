package clueGame;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class SuggestionGUI extends JPanel {
public Board board;
JButton submit, cancel;
private boolean hasBeenSubmitted = false;
private Solution solution;
private JComboBox<String> weapon, room, person;


public SuggestionGUI() throws FileNotFoundException {
	this.board = Board.getInstance();
	setLayout (new GridLayout(4,2));
	add(createLabel("Your Room"));
	add(roomGuess(board.getCellAt(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn())));
	add(createLabel("Person"));
	add(personGuess());
	add(createLabel("Weapon"));
	add(bestWeapon());
	add(submitSuggestionButton());
	add(cancelSuggestion());
}

private Component createLabel(String text) {
	JLabel label = new JLabel();
	label.setText(text);
	return label;
}



private Component roomGuess(BoardCell room) {
	String currentRoom = Board.getInstance().getRoomName(room.getInitial());
	JPanel menu = new JPanel();
	menu.add(createLabel(currentRoom));
	return board;
	
}
private Component personGuess() throws FileNotFoundException {
	JPanel menu = new JPanel();
	this.person = new JComboBox();
	menu.add(this.person);
	menu.setBorder(new TitledBorder("Person Guess"));
	
	File personFile = new File("Person.txt");
	Scanner personReader = new Scanner(personFile);
	while (personReader.hasNextLine()) {
		this.person.addItem(personReader.nextLine());
	}
	
	personReader.close();
	return menu;
}
private Component bestWeapon() throws FileNotFoundException {
	JPanel menu = new JPanel();
	menu.setBorder(new TitledBorder("Weapon Guess"));
	this.weapon = new JComboBox();
	menu.add(weapon);
	
	File weaponFile = new File("weapon.txt");
	Scanner weaponReader = new Scanner(weaponFile);
	while (weaponReader.hasNextLine()) {
		weapon.addItem(weaponReader.nextLine());
	}
	weaponReader.close();
	return menu;
}

ButtonActions listener = new ButtonActions();
private class ButtonActions implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == SuggestionGUI.this.submit){
			hasBeenSubmitted = true;
			SuggestionGUI.this.makeSuggestionHit();
		}
		if(e.getSource() == SuggestionGUI.this.cancel)
			SuggestionGUI.this.cancelHit();
	}
	
}

private JButton submitSuggestionButton() {
	submit = new JButton("Submit");
	this.submit.addActionListener(listener);
	return submit;
	
}
private JButton cancelSuggestion() {
	cancel = new JButton("Submit");
	this.cancel.addActionListener(listener);
	return cancel;
	
}

//TODO
public void makeSuggestionHit() {
	solution = new Solution();
	solution.person = this.person.getSelectedItem().toString();
}
public void cancelHit() {
	
}


}
