package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	private String layoutLocation;
	private String legendLocation;
	private String playerLocation;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] grid;
	private int numRows, numCols;
	private Map<Character, String> legendMap;
	private Map<BoardCell, Set<BoardCell>> adjMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private List<Player> players;
	private List<Card> deck;
	private Solution answer;
	private Random random = new Random();
	private Player currentPlayer;
	private HumanPlayer humanPlayer;
	private ControlGUI cGUI;
	private int whoseTurn,roll;

	public Map<Character, String> getLegendMap() {
		return legendMap;
	}

	// this method returns the only Board and serves as a constructor for the
	// Board class
	public static Board getInstance() {
		// if (theInstance == null) {
		// theInstance = new Board();
		// }
		return theInstance;
	}

	public void setConfigFiles(String layoutLoc, String legendLoc, String playerLoc) {
		this.layoutLocation = layoutLoc;
		this.legendLocation = legendLoc;
		this.playerLocation = playerLoc;
	}

	public void initialize() throws FileNotFoundException, BadConfigFormatException {
		reset();
		setConfigFiles("ICJK_ClueLayout.csv", "ICJK_Legend.txt", "TDNFTP_players.txt");
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

		loadBoardConfig();

		try {
			loadPlayerConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		loadDeck();

		dealDeck(new Random());

		calcAdjacencies();
		addMouseListener(this);

	}

	public void reset() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		adjMap = new HashMap<BoardCell, Set<BoardCell>>();
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();
	}

	//TODO: dealDeck does not work - does not actually deal cards correctly
	public void dealDeck(Random rand) {
		final int cardsPerPlayer = (deck.size() - 3) / players.size();

		Card randomCard;
		do {
			randomCard = deck.get(rand.nextInt(deck.size()));
		} while (randomCard.hasBeenDealt() || randomCard.getType() != CardType.WEAPON);
		Card weapon = randomCard;
		randomCard.isSolution();

		do {
			randomCard = deck.get(rand.nextInt(deck.size()));
		} while (randomCard.hasBeenDealt() || randomCard.getType() != CardType.ROOM);
		Card room = randomCard;
		randomCard.isSolution();

		do {
			randomCard = deck.get(rand.nextInt(deck.size()));
		} while (randomCard.hasBeenDealt() || randomCard.getType() != CardType.PERSON);
		Card person = randomCard;
		randomCard.isSolution();

		answer = new Solution(person, room, weapon);

		for (Player player : players) {
			for (int i = 0; i < cardsPerPlayer; i++) {
				do {
					randomCard = deck.get(rand.nextInt(deck.size()));
				} while (randomCard.hasBeenDealt());
				randomCard.dealToPlayer(player);
			}
		}
	}

	public void loadDeck() throws FileNotFoundException {
		// Create weapon cards
		File weapon = new File("weapon.txt");
		Scanner weaponReader = new Scanner(weapon);
		while (weaponReader.hasNextLine()) {
			deck.add(new Card(CardType.WEAPON, weaponReader.nextLine()));
		}
		weaponReader.close();

		// Create room cards
		for (String room : legendMap.values()) {
			if (!room.equals("Walkway") && !room.equals("Closet")) {
				deck.add(new Card(CardType.ROOM, room));
			}
		}

		// Create people cards
		File person = new File("Person.txt");
		Scanner personReader = new Scanner(person);
		while (personReader.hasNextLine()) {
			deck.add(new Card(CardType.PERSON, personReader.nextLine()));
		}
		personReader.close();
	}

	public void calcAdjacencies() {
		for (int x = 0; x < numRows; x++) {
			for (int y = 0; y < numCols; y++) {
				BoardCell boardKey;
				BoardCell boardValue;
				Set<BoardCell> adjacent = new HashSet<BoardCell>();
				boardKey = grid[y][x];
				if (boardKey != null) {
					if (boardKey.isDoorway()) {
						switch (boardKey.getDoorDirection()) {
						case RIGHT:
							boardValue = grid[y + 1][x];
							if (boardValue.isWalkway()) {
								adjacent.add(boardValue);
							}
							break;
						case LEFT:
							boardValue = grid[y - 1][x];
							if (boardValue.isWalkway()) {
								adjacent.add(boardValue);
							}
							break;
						case UP:
							boardValue = grid[y][x - 1];
							if (boardValue.isWalkway()) {
								adjacent.add(boardValue);
							}
							break;
						case DOWN:
							boardValue = grid[y][x + 1];
							if (boardValue.isWalkway()) {
								adjacent.add(boardValue);
							}
							break;
						case NONE:
							break;
						}
					} else if (!boardKey.isRoom()) {
						if (x - 1 >= 0) {
							boardValue = grid[y][x - 1];
							if (boardValue.isWalkway() || boardValue.getDoorDirection() == DoorDirection.DOWN) {
								adjacent.add(boardValue);
							}
						}
						if (y - 1 >= 0) {
							boardValue = grid[y - 1][x];
							if (boardValue.isWalkway() || boardValue.getDoorDirection() == DoorDirection.RIGHT) {
								adjacent.add(boardValue);
							}

						}
						if (x + 1 < numRows) {
							boardValue = grid[y][x + 1];
							if (boardValue.isWalkway() || boardValue.getDoorDirection() == DoorDirection.UP) {
								adjacent.add(boardValue);
							}

						}
						if (y + 1 < numCols) {
							boardValue = grid[y + 1][x];
							if (boardValue.isWalkway() || boardValue.getDoorDirection() == DoorDirection.LEFT) {
								adjacent.add(boardValue);
							}
						}
					}
				}
				adjMap.put(boardKey, adjacent);
			}
		}
	}

	public void loadRoomConfig() throws BadConfigFormatException {
		// Create Legend
		FileReader reader = null;
		try {
			reader = new FileReader(legendLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner in = new Scanner(reader);
		legendMap = new HashMap<Character, String>();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			int tracker = 3;
			while (line.charAt(tracker) != ',')
				tracker++;
			legendMap.put(line.charAt(0), line.substring(3, tracker));
			if (!line.substring(tracker + 2).equals("Card") && !line.substring(tracker + 2).equals("Other")) {
				in.close();
				throw new BadConfigFormatException();
			}
		}
		in.close();
	}

	public void loadBoardConfig() throws BadConfigFormatException {
		int numColsPast = -1;
		FileReader reader = null;
		try {
			reader = new FileReader(layoutLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		Scanner in = new Scanner(reader);
		numRows = 0;
		String line = "";
		while (in.hasNextLine()) {
			line = in.nextLine();
			numCols = 0;
			char last = ',';
			for (int i = 0; i < line.length(); i++) {
				if (last == ',') {
					if (!legendMap.containsKey(line.charAt(i))) {
						in.close();
						throw new BadConfigFormatException();
					}
					grid[numCols][numRows] = new BoardCell(numCols, numRows, line.charAt(i));
					numCols++;
				} else if (line.charAt(i) != ',') {
					if (grid[numCols - 1][numRows] != null) {
						switch (line.charAt(i)) {
						case 'D':
							grid[numCols - 1][numRows].setToDoorway(DoorDirection.DOWN);
							break;
						case 'U':
							grid[numCols - 1][numRows].setToDoorway(DoorDirection.UP);
							break;
						case 'R':
							grid[numCols - 1][numRows].setToDoorway(DoorDirection.RIGHT);
							break;
						case 'L':
							grid[numCols - 1][numRows].setToDoorway(DoorDirection.LEFT);
							break;
						default:
							grid[numCols - 1][numRows].setToDoorway(DoorDirection.NONE);
							break;
						}
					}

				}
				last = line.charAt(i);
			}
			if (numColsPast != -1 && numColsPast != numCols) {
				in.close();
				throw new BadConfigFormatException();
			}
			numColsPast = numCols;
			numRows++;

		}
		in.close();
	}

	public Map<Character, String> getLegend() {
		return legendMap;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	public BoardCell getCellAt(int row, int col) {
		return grid[col][row];
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMap.get(grid[col][row]);
	}

	public void calcTargets(int row, int col, int pathLen) {
		targets.clear();
		visited.clear();
		BoardCell startCell = grid[col][row];
		calcTargetsRecursion(startCell, pathLen);
	}

	private void calcTargetsRecursion(BoardCell currentCell, int pathLength) {
		for (BoardCell option : adjMap.get(currentCell)) {
			// Check if the option was a past position
			boolean didVisit = false;
			if (!visited.isEmpty()) {
				for (BoardCell past : visited) {
					if (past.equals(option))
						didVisit = true;
				}
			}
			// Do something with object if it was not a past position
			if (!didVisit) {
				if (pathLength < 2 || option.isDoorway()) {
					targets.add(option); // add if it number of steps has been
											// met.
				} else {
					// keep going along path if still have more steps
					visited.add(currentCell);
					calcTargetsRecursion(option, pathLength - 1);
					visited.remove(currentCell);
				}
			}

		}

	}

	public String getRoomName(char initial) {
		if (legendMap.containsKey(initial))
			return legendMap.get(initial);
		return "Room initials not stored";
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void loadPlayerConfig() throws FileNotFoundException {
		FileReader reader = new FileReader(playerLocation);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String[] split = in.nextLine().split(",");
			Color color = null;
			switch (split[2]) {
			case "Green":
				color = Color.GREEN;
				break;
			case "Red":
				color = Color.RED;
				break;
			case "Blue":
				color = Color.BLUE;
				break;
			}
			if (split[0].equals("Human")) {
				humanPlayer = new HumanPlayer(split[1], color, Integer.parseInt(split[3]), Integer.parseInt(split[4]));
				players.add(humanPlayer);
				currentPlayer = humanPlayer;
			} else {
				players.add(
						new ComputerPlayer(split[1], color, Integer.parseInt(split[3]), Integer.parseInt(split[4])));
			}
		}
		in.close();
	}

	public void mouseClicked(MouseEvent e) {
	    calcTargets(this.currentPlayer.getRow(), this.currentPlayer.getColumn(), roll);
		if(!this.humanPlayer.isTurnUnfinished() == true){
			return;
		}
		
		BoardCell cellClicked = findCell(e.getX(), e.getY());
		if (cellClicked == null)
			JOptionPane.showMessageDialog(null, "Position clicked is not a target from which you can move to");
		else{
			this.humanPlayer.moveMade(cellClicked);
			highlightCells(false);
			repaint();
		}

	}

	public List<Player> getPlayers() {
		return players;
	}

	public BoardCell findCell(int x, int y) {

		x = x / BoardCell.CELL_SIZE;
		y = y / BoardCell.CELL_SIZE;
		//System.out.println("X : " + x);
		//System.out.println("Y : " + y);
		BoardCell posClicked = new BoardCell(x, y, 'W');
		for(BoardCell t : targets)
		//System.out.println("ROW: " + t.getRow() + "COL: " + t.getColumn());
		for(BoardCell b : targets)
	      if(b.getRow() == x && b.getColumn() == y
	      ) 
	        return posClicked;
	      
	    return null;
	}

	public void highlightCells(boolean highlighted) {
		if (this.targets != null)
			for (BoardCell bc : targets)
				bc.setValidMove(highlighted);

	}

	public List<Card> getDeck() {
		return deck;
	}

	public boolean checkAccusation(Solution accusation) {
		return answer.equals(accusation);
	}

	public Card handleSuggestion(Solution suggestion, Player accuser) {
		int pos = players.indexOf(accuser) + 1;
		if (pos >= players.size()) {
			pos = 0;
		}
		while (pos != players.indexOf(accuser)) {
			Card c = players.get(pos).disproveSuggestion(suggestion);
			if (c != null) {
				return c;
			}

			pos++;
			if (pos >= players.size()) {
				pos = 0;
			}
		}
		return null;
	}

	public Solution getAnswer() {
		return answer;
	}

	// ************** NextPlayer clicked **********
	public void nextPlayer() {
		if(this.humanPlayer.isTurnUnfinished()){
			JOptionPane.showMessageDialog(null, "You need to finish your turn");
				return;
		}
	    roll = this.random.nextInt(5) + 1;
	    this.whoseTurn = ((this.whoseTurn + 1) % this.players.size());
	    this.currentPlayer = ((Player)this.players.get(this.whoseTurn));
	    
	    this.cGUI.turnDisplay(this.currentPlayer.getName(), roll);
	    calcTargets(this.currentPlayer.getRow(), this.currentPlayer.getColumn(), roll);
	    
	    this.currentPlayer.moveNeedsToBeMade(this);
	    
	    repaint();
	}

	public boolean isHumanPlayer() {
		if (currentPlayer == humanPlayer)
			return true;
		return false;
	}

	/**
	 * For testing purposes.
	 * 
	 * @param soln
	 */
	public void setAnswer(Solution soln) {
		answer = soln;
	}

	// ***********GUI*****************
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawCells(g);
		drawPlayers(g);
	}

	public void drawCells(Graphics g) {
		for (int c = 0; c < this.numCols; c++) {
			for (int r = 0; r < this.numRows; r++) {
				grid[c][r].drawBoardCells(g);
			}
		}
	}

	public void drawPlayers(Graphics g) {
		for (Player p : this.players) {
			p.drawPlayer(g, this);
		}
	}
	
	public String getCurrentPersonsName(){
		return currentPlayer.getName();
	}
	

	public List<Card> getPlayersCards() {
		return players.get(0).getCards();
	}
	  public void setControlGUI(ControlGUI cGui)
	  {
	    this.cGUI = cGui;
	  }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
