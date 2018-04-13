package com.chess.rules;

import java.util.List;
import com.chess.board.Cell;
import com.chess.board.Location;
import com.chess.board.Move;
import com.chess.pieces.Piece;
import com.chess.pieces.PlayerColor;
import static com.chess.rules.RuleConstants.*;

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
	 * @param theMove the move being matched against 
	 * @param direction the direction relative to the player (could be null)
	 * @param numberOf the total number of blocks passed through (remember 
	 *  always |deltaX| + |deltaY|)
	 * @return the matched rule, if any
	 */
	abstract protected Rule matchRule(List<Cell> cells, Move theMove, Direction direction, int numberOf);
	
	/**
	 * Detects if a pieces is blocking the path horizontally
	 * @param cells the cells of the board
	 * @param x1 (zero-based)
	 * @param x2 (zero-based)
	 * @param y  the row value (zero-based)
	 * @return
	 */
	protected boolean isHorizontalBlocked(List<Cell> cells, int x1, int x2, int y) {
		boolean isBlocked = false;
		
		int a,b;
		
		if(x2 < x1) {
			b = x1;
			a = x2;
		}
		else {
			a = x1;
			b = x2;
		}
		
		for(int i=a; i <= b; i++) {
			if(cells.get(i + 8*y).getPiece()!=null) {
				isBlocked = true;
				break;
			}
		}
		
		return isBlocked;
	}
	
	/**
	 * Detects if a pieces is blocking the path vertically
	 * @param cells the cells of the board
	 * @param y1 (zero-based)
	 * @param y2 (zero-based)
	 * @param x  the column value (zero-based)
	 * @return
	 */
	protected boolean isVerticalBlocked(List<Cell> cells, int y1, int y2, int x) {
		boolean isBlocked = false;
		int a,b;
		
		if(y2 < y1) {
			b = y1;
			a = y2;
		}
		else {
			a = y1;
			b = y2;
		}
		
		for(int i=a; i <= b; i++) {
			if(cells.get(i*8 + x).getPiece()!=null) {
				isBlocked = true;
				break;
			}
		}
		
		return isBlocked;
	}
	
	/**
	 * Detects if a pieces is blocking the path vertically
	 * @param cells the cells of the board
	 * @param y1 (zero-based)
	 * @param y2 (zero-based)
	 * @param x1 (zero-based)
	 * @param x2 (zero-based) 
	 * @return
	 */
	protected boolean isDiagonalBlocked(List<Cell> cells, int y1, int y2, int x1, int x2) {
		boolean isBlocked = false;
		int a,b,x,increment;
		
		if(y2 < y1) {
			x = x2;
			a = y2;
			b = y1;
			increment = -1;
		}
		else {
			x = x1;
			a = y1;
			b = y2;
			increment = 1;
		}
		
		if(x2 < x1) {
			increment *= -1;
		}
		
		for(int i=a; i <= b; i++, x+=increment) {
			if(cells.get(i*8 + x).getPiece()!=null) {
				isBlocked = true;
				break;
			}
		}
		
		return isBlocked;
	}
	
	protected Direction determineDirection(PlayerColor myColor, int deltaX, int deltaY) {
		Direction direction = null;
				
		if(deltaX != 0 || deltaY !=0) {
			if(myColor == PlayerColor.Lower) {
				//Left is positive and Forward is positive
				deltaX *= -1; deltaY *= -1;
			}
			
			if(deltaY == 0) {
				if(deltaX > 0) {
					direction = Direction.Left;
				}
				else {
					direction = Direction.Right;
				}
			}
			else if (deltaX == 0){
				if(deltaY > 0) {
					direction = Direction.Forward;
				}
				else {
					direction = Direction.Back;
				}
			}
			else if(Math.abs(deltaY) == Math.abs(deltaX)) {
				if(deltaX > 0 && deltaY > 0) {
					direction = Direction.DiagonalUpLeft;
				}
				else if(deltaX > 0) {
					direction = Direction.DiagonalDownLeft;
				}
				else if(deltaY > 0) {
					direction = Direction.DiagonalUpRight;
				}
				else {
					direction = Direction.DiagonalDownRight;
				}
			}
			else if(Math.abs(deltaX) == 1 && Math.abs(deltaY) == 3) {
				if(deltaX > 0 && deltaY > 0) {
					direction = Direction.LLeftUp;
				}
				else if(deltaX > 0) {
					direction = Direction.LLeftDown;
				}
				else if(deltaY > 0) {
					direction = Direction.LRightUp;
				}
				else {
					direction = Direction.LRightDown;
				}
			}
			else if(Math.abs(deltaX) == 3 && Math.abs(deltaY) == 1) {
				if(deltaX > 0 && deltaY > 0) {
					direction = Direction.LUpLeft;
				}
				else if(deltaX > 0) {
					direction = Direction.LDownLeft;
				}
				else if(deltaY > 0) {
					direction = Direction.LUpRight;
				}
				else {
					direction = Direction.LDownRight;
				}
			}
		}
		
		return direction;
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
		
		Cell fromCell = theMove.getFromCell();
		Cell toCell = theMove.getToCell();
		Location toLocation = toCell.getLocation();
		Location fromLocation = fromCell.getLocation();
		int deltaX = toLocation.getXIndex() - fromLocation.getXIndex();
		int deltaY = toLocation.getYIndex() - fromLocation.getYIndex();
		PlayerColor playerColor = fromCell.getPiece().getPlayer().getColor();
		
		Direction direction = determineDirection(playerColor, deltaX, deltaY);
		
		Rule matched = matchRule(cells, theMove, direction, Math.abs(deltaX) + Math.abs(deltaY));
		
		RuleResult result = null;//assume null unless there's a match
		
		if(matched!=null) {
			Piece captured = theMove.getToCell().getPiece();
			
			if(matched.getId() == EN_PASSANT) {
				captured = previousMove.getToCell().getPiece();
			}
			
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
