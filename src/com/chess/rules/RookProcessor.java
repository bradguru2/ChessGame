package com.chess.rules;

import java.util.ArrayList;
import java.util.List;
import com.chess.board.Cell;
import com.chess.board.Location;
import com.chess.board.Move;
import com.chess.pieces.Piece;
import com.chess.pieces.PlayerColor;

import static com.chess.rules.RuleConstants.*;

/**
 * Rooks have their own set of rules to enforce <br>
 * 
 * @author bradley
 * 
 */
public class RookProcessor extends RuleProcessor {
	private List<Rule> rules;
	
	private List<Rule> createRules() {
		List<Rule> theRules = new ArrayList<>();
		
		//Add Valid Directional Moves
		theRules.add(new Rule(VALID_MOVE, Direction.Back, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Forward, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Right, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Left, -1));
		
		return theRules;
	}
	
	private Rule matchRule(int id, Direction direction, int numberOf) {
		Rule matched = null;
		
		for (Rule rule:rules) {
			if(id == rule.getId() && direction == rule.getDirection() &&
				numberOf == rule.getNumberOf()){
					matched = rule;
					break;
				}
		}
		
		return matched;
	}
	
	@Override
	protected Rule matchRule(List<Cell> cells, Move theMove, Direction direction, int numberOf) {
		Rule matched = null;
		
		if(direction!=null) {
			matched = matchRule(VALID_MOVE, direction, -1);
						
			if(matched != null) {
				Cell fromCell = theMove.getFromCell();
				Piece playerPiece = fromCell.getPiece();
				PlayerColor playerColor = playerPiece.getPlayer().getColor();
				Cell toCell = theMove.getToCell();
				Piece enemy = toCell.getPiece();
				Location toLocation = toCell.getLocation();
				Location fromLocation = fromCell.getLocation();
				int a1, b1, c;
								
				if(enemy!=null && enemy.getPlayer().getColor() == playerColor) {
					matched = null;//can not take piece of same of same color
				}
				
				if(numberOf > 1) {
					if(direction == Direction.Left || direction == Direction.Right) {
						c = fromLocation.getYIndex() - 1;
					
						if(toLocation.getXIndex() < fromLocation.getXIndex()) {
							a1 = fromLocation.getXIndex() - 2;
							b1 = toLocation.getXIndex();
						}
						else {
							a1 = fromLocation.getXIndex();
							b1 = toLocation.getXIndex() -2;
						}
						
						if(this.isHorizontalBlocked(cells, a1, b1, c)) {
							matched = null;
						}
					}
					else {
						c = fromLocation.getXIndex() - 1;
						
						if(toLocation.getYIndex() < fromLocation.getYIndex()) {
							a1 = fromLocation.getYIndex() - 2;
							b1 = toLocation.getYIndex();
						}
						else {
							a1 = fromLocation.getYIndex();
							b1 = toLocation.getYIndex() -2;
						}
						
						if(this.isVerticalBlocked(cells, a1, b1, c)) {
							matched = null;
						}
					}
				}
			}
		}
		
		return matched;
	}
	
	public RookProcessor() {
		rules = this.createRules();
	}
	
}
