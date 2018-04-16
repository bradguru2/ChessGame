package com.chess.board;

import java.util.List;
import com.chess.pieces.Player;
import com.chess.rules.RuleResult;

/**
 * BoardActions represents the actions that can be taken on a board
 * @author bradley
 *
 */
public interface BoardActions {
	/**
	 * 
	 * @return the cells of the board in order
	 */
	List<Cell> getOrderedCells();
	
	/**
	 * 
	 * @param checkPlayer the player to check against
	 * @return if the King is in check
	 * @throws IllegalArgumentException
	 */
	boolean kingIsInCheck(Player checkPlayer) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param checkPlayer the player to check against
	 * @return if the King is check mate
	 * @throws IllegalArgumentException
	 */
	boolean isCheckMate(Player checkPlayer) throws IllegalArgumentException;
	
	/**
	 * Attempts to make a move on the board
	 * @param move the move being attempted
	 * @return the result of the move
	 * @throws IllegalMoveException
	 */
	RuleResult makeMove(String move) throws IllegalMoveException;
}
