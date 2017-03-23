package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	public IntBoard(int boardSizeX, int boardSizeY) {
		super();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[boardSizeX][boardSizeY];
		for (int x = 0; x < boardSizeX; x++) {
			for (int y = 0; y < boardSizeY; y++) {
				grid[x][y] = new BoardCell(x, y);
			}
		}
		calcAdjacencies(); // setup adjMtx

	}

	// Calculate map of valid adjacent positions
	private void calcAdjacencies() {
		BoardCell boardKey;
		BoardCell boardValue;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				boardKey = grid[x][y];
				if (x - 1 >= 0) {
					boardValue = grid[x - 1][y];
					addToAdjMap(boardKey, boardValue);
				}
				if (y - 1 >= 0) {
					boardValue = grid[x][y - 1];
					addToAdjMap(boardKey, boardValue);
				}
				if (x + 1 < grid.length) {
					boardValue = grid[x + 1][y];
					addToAdjMap(boardKey, boardValue);
				}
				if (y + 1 < grid[0].length) {
					boardValue = grid[x][y + 1];
					addToAdjMap(boardKey, boardValue);
				}
			}
		}

	}

	// adds to adjacency map. Checks if key is already in the map
	private void addToAdjMap(BoardCell key, BoardCell value) {
		if (adjMtx.containsKey(key))
			adjMtx.get(key).add(value);
		else {
			Set<BoardCell> temp = new HashSet<BoardCell>();
			temp.add(value);
			adjMtx.put(key, temp);
		}

	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		// Reset variables and calculated target spots
		targets.clear();
		visited.clear();
		calcTargetsRecursion(startCell, pathLength);

	}

	// Find all the targets
	private void calcTargetsRecursion(BoardCell currentCell, int pathLength) {
		for (BoardCell option : adjMtx.get(currentCell)) {
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
				if (pathLength < 2) {
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
		return targets;
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}

	public BoardCell getCell(int x, int y) {
		return grid[x][y];
	}
}
