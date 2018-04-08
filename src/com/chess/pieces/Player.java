package com.chess.pieces;

/**
 * Player encapsulates the properties of a player
 * @author bradley
 *
 */
public class Player {
	private String name;
	private PlayerColor color;
	private PlayerType type;
	
	/**
	 * 
	 * @param theColor represents the color of the player
	 * @param theType represents the type of player
	 * @param theName  represents the name of player
	 * @throws IllegalArgumentException
	 */
	public Player(PlayerColor theColor, PlayerType theType, String theName) throws IllegalArgumentException {
		if(theColor == null) {
			throw new IllegalArgumentException("theColor is null");
		}
		
		if(theType == null) {
			throw new IllegalArgumentException("theType is null");
		}
		
		if(theName == null) {
			throw new IllegalArgumentException("theName parameter was not set");
		}
		
		name = theName;
		color = theColor;
		type = theType;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the color
	 */
	public PlayerColor getColor() {
		return color;
	}
	
	/**
	 * @return the player type
	 */
	public PlayerType getPlayerType() {
		return type;
	}
}
