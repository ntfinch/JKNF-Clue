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
		calcAdjacencies(); //setup adjMtx
		
	}
	
	private void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
	
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}
	
	public BoardCell getCell(int x, int y){
		return null;
	}
}
