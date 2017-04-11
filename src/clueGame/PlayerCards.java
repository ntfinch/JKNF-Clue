package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PlayerCards extends JPanel {
	List<Card> cards;

	public PlayerCards(List<Card> crds) {
		cards = crds;
		setLayout(new GridLayout(6, 1));
		setBorder(getBorder("My Cards"));
		createSubBoxes("People", CardType.PERSON);
		createSubBoxes("Rooms", CardType.ROOM);
		createSubBoxes("Weapons", CardType.WEAPON);

	}

	public TitledBorder getBorder(String t) {
		return BorderFactory.createTitledBorder(t);
	}

	public void createSubBoxes(String t, CardType cardType){
		JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(0, 1));
	    panel.setBorder(getBorder(t));
	    for(Card c : cards){
	    	if(c.getType() == cardType){
	    		panel.add(new JTextField(c.getName()));
	    	}
	    }
	    add(panel);
	}
}
