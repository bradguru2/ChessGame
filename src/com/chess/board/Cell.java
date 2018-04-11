package com.chess.board;

import com.chess.pieces.Piece;

/**
 * Cell encapsulates the logical properties of a chess board block
 * @author bradley
 *
 */
public class Cell {
	private CellColor color;
	private Location location;
	private Piece piece;
	
	/**
	 * 
	 * @param theColor the color of the Cell
	 * @param theLocation the location of the Cell
	 */
	public Cell(CellColor theColor, Location theLocation) throws IllegalArgumentException {
		if(theColor == null) {
			throw new IllegalArgumentException("theColor is null");
		}
		
		if(theLocation == null) {
			throw new IllegalArgumentException("theLocation is null");
		}
		
		color = theColor;
		location = theLocation;
		piece = null;
	}
	
	/**
	 * 
	 * @return cell color
	 */
	public CellColor getColor() {
		return color;
	}
	
	/**
	 * 
	 * @return cell location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * 
	 * @return piece occupying cell
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * 
	 * @param thePiece make piece occupy cell
	 */
	public void setPiece(Piece thePiece) {
		piece = thePiece;
	}
}
