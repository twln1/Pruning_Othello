/*
#########################################################
#														#
#	Theo Newton twln1 117 5457							#
#														#
#	COMP316-15A Assignment 2 - Adversarial search 		#
#														#
#	"Implement a program to compute the next best move	#
#	for any given Reversi (aka Othello) position based	#
#	on (alpha-beta pruning) minimax search using 		#
#	iterative deepening search."						#
#														#
#########################################################
*/

import java.util.Scanner;

// USAGE: java AB_Pruning <input.txt

public class AB_Pruning{

	public static int maximumDepth = 1, minimaxValue, numNodes;
	public static Action currentPos;
	// Set up the X, Y co-ordinates of the board
	final static String X = "abcdefgh";
	final static String Y = "12345678";

	public static void main(String[] args){
		/*
		Reference input:

			- abcdefgh
			1 ........
			2 ........
			3 ........
			4 ...OB...
			5 ...BO...
			6 ........
			7 ........
			8 ........
			B 60

		*/
			// Board is 8x8
			int[][] startState = new int[8][8];
			Scanner sc = new Scanner(System.in);
			sc.nextLine(); // First line irrelevant

			// Set cases to find out which player is occupying a space
			for(int i = 0; i < 8; i ++){
				String in = sc.nextLine();
				for(int j = 2; j<10; j++){
					switch (in.charAt(j)){
						case '.':
						// Already "0"
							break;
						case 'B':
						// MAXPLAYER, 'B' is +1
						startState[j-2][i] = 1;
							break;
						case 'O':
						// MINPLAYER, 'O' is -1
						startState[j-2][i] = -1;
					}
				}
			}
			String[] array = sc.nextLine().split(" ");
			String getPlayer = array[0];
			// Get the time that the user specifies for the AI to 'think'
			final int getTime = Integer.parseInt(array[1]);
			// If O, switch MAXPLAYER to O
			if(getPlayer.equals("O")){
				for(int i = 0; i < 8; i ++){
					for(int j = 0; j<8; j++){
						startState[i][j] *= -1;
					}
				}
			}
		// Start a thread to run for the amount of time the user specifies
		State search = new State(startState, 0, 1, 0, 0);
		// Create a thread to run for a user defined length of time
		new Thread( new Runnable() {
	  		public void run() {
			    try {
			      Thread.sleep(getTime*1000);
		            } catch (InterruptedException ie) {
			      System.out.println("Child thread interrupted! " + ie);
		            }
		            // Print the best next move
		               System.out.println("move " + X.charAt(currentPos.x) + " " + Y.charAt(currentPos.y) + " nodes " + numNodes + " depth " + (maximumDepth - 1) + " minimax " + minimaxValue);

			    System.exit(0);
	  		}
	  	}).start();
	  	// Display to the user how long the AI will think for
		System.out.println("Thinking for " + getTime + " seconds...");
		// Perform the alpha beta search
		while(true){
		currentPos = alphabetaSearch(search);
		maximumDepth++;

		}
	}

	// Method to perform the alpha beta search, checking the successors of boards.
	// Returns the best next move as an Action object
	public static Action alphabetaSearch(State state){
		minimaxValue = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
		// Successors is null which means there is a passing move due to no possible next move
		if(state.successors == null){
			System.out.println("move a -1 nodes 0 depth 0 minimax");
			System.exit(0);

		}
		// Check through the successor boards to find the best move
		for(State child : state.successors){
			if(child.value == minimaxValue)
				return new Action(child.x,child.y);
		}
		return null;
	}

	// Calculate the max value for the alpha beta search, returning a value which will be passed up to the alphabetasearch() to get the minimax value
	public static int maxValue(State state, int alpha, int beta){
		numNodes += 1;
		// System.out.print(state.depth + "\n");
		if(terminalTest(state)){
			return (int)Math.signum(state.heuristic())*100; // positive for win, negative for loss, and 0 for draw
		}
		if(state.depth == maximumDepth){
			return state.heuristic();
		}
		int value = Integer.MIN_VALUE;  // set -infiniti
		if(state.depth == 0){
			state.successors = Move.expand(state);
			// If there are no successors, there are no possible moves, so exit.
			if(state.successors.size() == 0){
	      		System.out.println("move a -1 nodes 0 depth 0 minimax");
	      		System.exit(0);
	      	}
			// Loops through the successors that we've set in line above
			for(State child : state.successors){
				value = Math.max(value, minValue(child, alpha, beta));
				child.value = value;
				alpha = Math.max(alpha, value);
				if(value >= beta)
				return value;
			}
		}
		else{
			for(State child : Move.expand(state)){
				value = Math.max(value, minValue(child, alpha, beta));
				alpha = Math.max(alpha, value);
				if(value >= beta)
					return value;
			}
		}
		return value;
	}

	// Calcualtes the min value for the alpha beta search, returning a value that gets passed up to the maxValue()
	public static int minValue(State state, int alpha, int beta){
		numNodes += 1;
		if(terminalTest(state)){
			return (int)Math.signum(state.heuristic())*100; // positive for win, negative for loss, and 0 for draw
		}
		if(state.depth == maximumDepth){
			return state.heuristic();
		}
		int value = Integer.MAX_VALUE; // set +infiniti
		for(State child : Move.expand(state)){
			value = Math.min(value, maxValue(child, alpha, beta));
			beta = Math.min(beta, value);
			if(value <= alpha)
				return value;
		}
		return value;
	}

	// Method to check whether the next move is possible, and return true if it is
	public static boolean terminalTest(State state){
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				if(Move.isPossibleMove(x, y, state.board, state.player))
					return false;
			}
		}

		return true;
	}



}
