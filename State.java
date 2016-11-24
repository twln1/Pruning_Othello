import java.util.*;

public class State{
		public int[][] board;
		public int depth, x, y;
		public int player, value;
		public List<State> successors;

		// Constructor for the node class
		public State(int[][] board, int depth, int player, int x, int y){
			this.board = board;
			this.depth = depth;
			this.player = player;
			this.x = x;
			this.y = y;
		}

		public int heuristic(){
			int h = 0;
			for(int[] row : board){
				for(int i : row){
					h += i;
				}
			}
			return h;
		}

		
}
