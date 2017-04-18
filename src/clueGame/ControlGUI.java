package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	JButton nextPlayer, accusation;
	private JTextField roll, guessResult, guess, whosTurnIsIt;

	public ControlGUI() {
		setLayout(new GridLayout(2, 0));
		add(createTurnIndicator());
		add(createNextPlayer());
		add(createMakeAccusation());
		add(createDiceRoll());
		add(createCurrentGuess());
		add(createGuessResult());
	}

	private Component createTurnIndicator() {
		JPanel turn = new JPanel();
		turn.setLayout(new BorderLayout());
		turn.add(createLabel("Current Player"), BorderLayout.NORTH);
		whosTurnIsIt = new JTextField();
		whosTurnIsIt.setEditable(false);
		turn.add(whosTurnIsIt, BorderLayout.CENTER);
		return turn;
	}

	private Component createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		return label;
	}

	private Component createGuessResult() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(createLabel("Guess Result"), BorderLayout.NORTH);
		result.add(createLabel("Response: "), BorderLayout.WEST);
		guessResult = new JTextField();
		guessResult.setEditable(false);
		result.add(guessResult, BorderLayout.CENTER);
		return result;
	}

	private Component createCurrentGuess() {
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new BorderLayout());
		guessPanel.add(createLabel("Guess"), BorderLayout.NORTH);
		guessPanel.add(createLabel("Guess: "), BorderLayout.WEST);
		guess = new JTextField();
		guessPanel.add(guess, BorderLayout.CENTER);
		return guessPanel;
	}

	private Component createDiceRoll() {
		JPanel diceDisplay = new JPanel();
		diceDisplay.setLayout(new BorderLayout());
		diceDisplay.add(createLabel("Die"), BorderLayout.NORTH);
		diceDisplay.add(createLabel("Roll: "), BorderLayout.WEST);
		roll = new JTextField();
		roll.setEditable(false);
		diceDisplay.add(roll, BorderLayout.CENTER);
		return diceDisplay;
	}

	ButtonActions listener = new ButtonActions();
	private JButton createMakeAccusation() {
		accusation = new JButton("Make an accusation");
		this.accusation.addActionListener(listener);
		return accusation;
	}

	private JButton createNextPlayer() {
		nextPlayer = new JButton("Next Player");
	    this.nextPlayer.addActionListener(listener);
		return nextPlayer;
	}

	private class ButtonActions implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ControlGUI.this.accusation)
				ControlGUI.this.makeAccusationHit();
			if(e.getSource() == ControlGUI.this.nextPlayer)
				ControlGUI.this.nextPlayerHit();
		}
		
	}
	
	public void turnDisplay(String n, int r){
		this.whosTurnIsIt.setText(n);
	    this.roll.setText(String.valueOf(r));
	}
	
	

		//BUtton pressed methods
		public void nextPlayerHit(){
			this.guess.setText("");
		    this.guessResult.setText("");
		    Board.getInstance().nextPlayer();
		}
		
		public void makeAccusationHit(){
			
		}

		
		
		//****Setters


		public void setGuessResult(String guessResult) {
			this.guessResult.setText(guessResult);
		}

		public void setGuess(String guess) {
			this.guess.setText(guess);
		}


	
}
