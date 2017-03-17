package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Board {
    
    // variable used for singleton pattern
    private static Board theInstance;
    // ctor is private to ensure only one can be created
    private String layoutLocation;
    private String legendLocation;
    private String playerLocation;
    
    private Board() {
        visited = new HashSet<BoardCell>();
        targets = new HashSet<BoardCell>();
        adjMap = new HashMap<BoardCell, Set<BoardCell>>();
    }
    
    private final int MAX_BOAR_SIZE = 50;
    private BoardCell[][] grid;
    private int numRows, numCols;
    private Map<Character, String> legendMap;
    private Map<BoardCell, Set<BoardCell>> adjMap;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private List<Player> players;
    private Set<Card> deck;
    
    // this method returns the only Board
    public static Board getInstance() {
        if (theInstance == null) {
            theInstance = new Board();
        }
        return theInstance;
    }
    
    public void setConfigFiles(String layoutLoc, String legendLoc, String playerLoc) {
        this.layoutLocation = layoutLoc;
        this.legendLocation = legendLoc;
        this.playerLocation = playerLoc;
    }
    
    public void initialize() {
        try {
            loadRoomConfig();
        } catch (BadConfigFormatException e) {
            e.printStackTrace();
        }
        
        try {
            loadBoardConfig();
        } catch (BadConfigFormatException e) {
            e.printStackTrace();
        }
        
        try {
            loadPlayerConfig();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        _loadDeck();
        _calcAdjacencies();
        
    }
    
    private void _loadDeck() {
//    	// Create weapon cards
//		deck.add(new Card(CardType.WEAPON, "Bat"));
//		deck.add(new Card(CardType.WEAPON, "Gun"));
//		deck.add(new Card(CardType.WEAPON, "Knife"));
//		deck.add(new Card(CardType.WEAPON, "Screwdriver"));
//		deck.add(new Card(CardType.WEAPON, "Hammer"));
//		deck.add(new Card(CardType.WEAPON, "Katana"));
//		
//		// Create room cards
//		for (String room : legendMap.values()) {
//			// TODO: this could be done better considering that the legend file has a way of knowing if a room is a card or not.
//			if (room != "Walkway" || room != "Closet") {
//				deck.add(new Card(CardType.ROOM, room));
//			}
//		}
//		
//		// Create people cards
//		deck.add(new Card(CardType.PERSON, "Mr. Bob"));
//		deck.add(new Card(CardType.PERSON, "Mrs. Kellie"));
//		deck.add(new Card(CardType.PERSON, "Mr. Ryan"));
//		deck.add(new Card(CardType.PERSON, "Mrs. Coolio"));
//		deck.add(new Card(CardType.PERSON, "Mr. Platoon"));
//		deck.add(new Card(CardType.PERSON, "Mrs. Kitten"));
    }
    
    private void _calcAdjacencies() {
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
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        grid = new BoardCell[MAX_BOAR_SIZE][MAX_BOAR_SIZE];
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
    
    public BoardCell getCellAt(int x, int y) {
        return grid[y][x];
    }
    
    public Set<BoardCell> getAdjList(int x, int y) {
        return adjMap.get(grid[y][x]);
    }
    
    public void calcTargets(int i, int j, int k) {
        // TODO Auto-generated method stub
        targets.clear();
        visited.clear();
        BoardCell startCell = grid[j][i];
        calcTargetsRecursion(startCell, k);
    }
    
    private void calcTargetsRecursion(BoardCell currentCell, int pathLength) {
        for (BoardCell option : adjMap.get(currentCell)) {
            // Check if the option was a past position
            boolean didVisit = false;
            if (!visited.isEmpty()) {
                for (BoardCell past : visited) {
                    if (past.equals(option)) didVisit = true;
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
    
    public Set<BoardCell> getTargets() {
        // TODO Auto-generated method stub
        return targets;
    }
    
    public List<Player> loadPlayerConfig() throws FileNotFoundException {
        List<Player> players = new ArrayList<Player>();
        
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
                players.add(new HumanPlayer(split[1], color, Integer.parseInt(split[3]), Integer.parseInt(split[4])));
            } else {
                players.add(
                        new ComputerPlayer(split[1], color, Integer.parseInt(split[3]), Integer.parseInt(split[4])));
            }
        }
        in.close();
        
        return players;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public Set<Card> getDeck() {
    	return deck;
    }
}
