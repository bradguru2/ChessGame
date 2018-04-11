package com.chess.rules;

import java.util.List;

import com.chess.board.Cell;
import com.chess.board.Move;
import com.chess.pieces.Piece;

/**
 * The base class for all Rule Processors
 * @author bradley
 *
 */
public abstract class RuleProcessor {
	private Move previousMove;
	
	/**
	 * All inheritors MUST implement this function
	 * @param cells the cells of the board
	 * @param direction the direction relative to the player
	 * @param theMove the move being matched against
	 * @return the matched rule, if any
	 */
	abstract protected Rule matchRule(List<Cell> cells, Direction direction, Move theMove);
	
	protected Direction determineDirection(Move theMove) {
		Cell fromCell = theMove.getFromCell();
		//TODO:More work needed here
		return null;
	}
	
	protected Move getPreviousMove() {
		return previousMove;
	}
	
	/**
	 * Default constructor for all rule processors
	 */
	public RuleProcessor() {
		previousMove = null;
	}
	
	/**
	 * Simulates a move but does not alter cells
	 * @param cells an ordered list of cells
	 * @param theMove the move being simulated
	 * @return the result of the simulation
	 * @throws IllegalArgumentException
	 */
	public RuleResult GetMoveResult(List<Cell> cells, Move theMove) throws IllegalArgumentException {
		if(cells == null) {
			throw new IllegalArgumentException("cells is null");
		}
		
		if(theMove == null) {
			throw new IllegalArgumentException("theMove is null");
		}
		
		Direction direction = determineDirection(theMove);
		Rule matched = matchRule(cells, direction, theMove);
		RuleResult result = null;//assume null unless there's a match
		
		if(matched!=null) {
			Piece captured = theMove.getToCell().getPiece();
			result = new RuleResult(matched, captured);
		}
		
		return result;
	}
	
	/**
	 * Some rule(s) require knowledge of the previous move
	 * @param theMove
	 */
	public void setPreviousMove(Move theMove) {
		previousMove = theMove;
	}
}
