/*
#############################################################
#															#
#	Theo Newton twln1 117 5457								#
#															#
#	COMP316-15A Assignment 2 - Adversarial search 			#
#															#
#	"Implement a program to compute the next best move		#
#	for any given Reversi (aka Othello) position based		#
#	on (alpha-beta pruning) minimax search using 			#
#	iterative deepening search."							#
#	Code taken from:										#
#	http://www.cs.waikato.ac.nz/~bernhard/316/a2/Move.java	#
#															#
#############################################################
*/
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Move {
  public static State state;

  // Create a copy of the board for the expand() to add to the list
  public static int[][] copyBoard(int[][] board) {
    int[][] copy = new int[board.length][];
    for (int i = 0; i < copy.length; i++) {
      copy[i] = Arrays.copyOf( board[i], board[i].length);
    }
    return copy;
  }

  public static boolean isPossibleMove(int x, int y, int[][] board, int player) {
    if (board[x][y] != 0) return false;
    for (int deltaX = -1; deltaX < 2; deltaX++) {
      for (int deltaY = -1; deltaY < 2; deltaY++) {
		if (deltaX == 0 && deltaY == 0) continue;
		if (isPossibleMove(x,y,board,player,deltaX,deltaY)) {
		  return true;
		}
      }
    }
    return false;
  }


  public static boolean isPossibleMove(int x, int y, int[][] board, int player, int deltaX, int deltaY) {
    int other = -player;
    //if (board[x][y] != 0) return false;
    x += deltaX;
    y += deltaY;
    if (x < 0 || y < 0 || x >= board.length || y >= board.length)
      return false;
    
    if (board[x][y] != other)
      return false;
    
    while (true) {
      x += deltaX;
      y += deltaY;
      if (x < 0 || y < 0 || x >= board.length || y >= board.length)
        return false;
     
      if (board[x][y] == player)
        return true;
      
      if (board[x][y] == 0)
        return false;
      }
  }

  public static void capture(int x, int y, int[][] board, int player) {
    for (int deltaX = -1; deltaX < 2; deltaX++) {
      for (int deltaY = -1; deltaY < 2; deltaY++) {
	if (deltaX == 0 && deltaY == 0) continue;
	if (isPossibleMove(x,y,board,player,deltaX,deltaY)) {
	  capture(x,y,board,player,deltaX,deltaY);
	}
      }
    }
  }

  public static void capture(int x, int y, int[][] board, int player, int deltaX, int deltaY) {
    board[x][y] = player;
    while (true) {
      x += deltaX;
      y += deltaY;
      if (board[x][y] == player) return;
      board[x][y] = player;
    }
  }

  // Make a list of the successor boards
  public static List<State> expand(State state) {
    List<State> nextBoards = new ArrayList<>();
    int[][] board = state.board;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
      	if (isPossibleMove(i,j,board,state.player)) {
      	  int[][] nextBoard = copyBoard(board);
      	  capture(i,j,nextBoard,state.player);
      	  nextBoards.add(new State(nextBoard, state.depth + 1, state.player * -1, i, j));
      	}
      }
    }
    return nextBoards;
  }



}

	