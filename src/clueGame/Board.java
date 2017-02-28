package clueGame;

import java.util.Map;

public class Board {
		// variable used for singleton pattern
		private static Board theInstance = new Board();
		// ctor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
		public void setConfigFiles(String x, String y){
			
		}
		
		public void initialize(){
			
		}
		public Map<Character, String> getLegend(){
			return null;
		}
		public int getNumRows(){
			return 0;
		}
		public int getNumColumns(){
			return 0;
		}
		public BoardCell getCellAt(int x, int y){
			return null;
		}
}
