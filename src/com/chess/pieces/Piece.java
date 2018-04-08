package com.chess.pieces;

import java.util.ArrayList;
import com.chess.board.Move;

/**
 * Piece encapsulates the properties of a chess piece
 * @author bradley
 *
 */
public class Piece {
	private ArrayList<Move> history;
	private Ability ability;
	private Player player;
	
	/**
	 * 
	 * @param thePlayer the player that owns this piece
	 * @param initialAbility the initial ability of this piece
	 * @throws IllegalArgumentException
	 */
	public Piece(Player thePlayer, Ability initialAbility) throws IllegalArgumentException {
		if(thePlayer == null) {
			throw new IllegalArgumentException("thePlayer is null");
		}
		
		if(initialAbility == null) {
			throw new IllegalArgumentException("initialAbility is null");
		}
		
		player = thePlayer;
		ability = initialAbility;
		history = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return a list of moves that were made
	 */
	public ArrayList<Move> getHistory(){
		return history;
	}
	
	/**
	 * 
	 * @return the player that owns this piece
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * 
	 * @return the current ability of this piece
	 */
	public Ability getAbility() {
		return ability;
	}
	
	/**
	 * 
	 * @param theAbility changes the ability of the piece 
	 * @throws IllegalArgumentException
	 */
	public void setAbility(Ability theAbility) throws IllegalArgumentException {
		if(theAbility == null) {
			throw new IllegalArgumentException("theAbility is null");
		}
		
		ability = theAbility;
	}
}
