package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class playerCards extends JPanel {
	ArrayList<Card> cards;

	public playerCards(ArrayList<Card> crds) {
		cards = crds;
		setLayout(new GridLayout(6, 1));
		setBorder(getBorder("My Cards"));
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
