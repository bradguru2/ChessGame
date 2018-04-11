package com.chess.rules;

import com.chess.pieces.Ability;

/**
 * Rule encapsulates the properties of a Rule
 * @author bradley
 *
 */
public class Rule {
	private int id;
	private Ability ability;
	private Direction direction;
	private int numberOf;
	
	/**
	 * 
	 * @param theId is the id assigned to this rule
	 * @param theAbility is the ability assigned to this rule
	 * @param theDirection is the direction assigned to this rule
	 * @param theNumberOf the valid number of total squares assigned to this rule
	 */
	public Rule(int theId, Ability theAbility, Direction theDirection, int theNumberOf) {
		id = theId;
		ability = theAbility;
		direction = theDirection;
		numberOf = theNumberOf;
	}
	
	/**
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return the ability
	 */
	public Ability getAbility() {
		return ability;
	}
	
	/**
	 * 
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @return the number of valid squares
	 */
	public int getNumberOf() {
		return numberOf;
	}
}
