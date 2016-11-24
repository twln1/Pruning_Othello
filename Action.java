/*
#########################################################
#														#
#	Theo Newton twln1 117 5457							#
#														#
#	COMP316-15A Assignment 2 - Adversarial search 		#
#														#
#	"Implement a program to compute the next best move	#
#	for any given Reversi (aka Othello) position based 	#
#	on (alpha-beta pruning) minimax search using 		#
#	iterative deepening search. "						#
#														#
#########################################################
*/

public class Action{

	public int x, y;
	public Action(int x, int y){
		this.x = x;
		this.y = y;
	}
}