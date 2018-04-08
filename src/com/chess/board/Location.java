package com.chess.board;

/**
 * Location represents a logical index into board position
 * @author bradley
 *
 */
public class Location {
	private int x;
	private int y;
	
	/**
	 * 
	 * @param xIndex represents the Column
	 * @param yIndex represents the Row
	 */
	public Location(int xIndex, int yIndex) {
		x = xIndex;
		y = yIndex;
	}
	
	/**
	 * 
	 * @return the column value
	 */
	public int getXIndex() {
		return x;
	}
	
	/**
	 * 
	 * @return the row value
	 */
	public int getYIndex() {
		return y;
	}
}
