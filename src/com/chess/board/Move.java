package com.chess.board;

/**
 * Move encapsulates the properties of a move from cell to cell
 * @author bradley
 *
 */
public class Move {
	private Cell fromCell;
	private Cell toCell;
	
	/**
	 * 
	 * @param origin the starting cell
	 * @param destination the end cell
	 * @throws IllegalArgumentException
	 */
	public Move(Cell origin, Cell destination) throws IllegalArgumentException {
		if(origin == null) {
			throw new IllegalArgumentException("origin is null");
		}
		
		if(destination == null) {
			throw new IllegalArgumentException("destination is null");
		}
		
		fromCell = origin;
		toCell = destination;
	}
	
	/**
	 * 
	 * @return the from cell
	 */
	public Cell getFromCell() {
		return fromCell;
	}
	
	/**
	 * 
	 * @return the to cell
	 */
	public Cell getToCell() {
		return toCell;
	}
}
