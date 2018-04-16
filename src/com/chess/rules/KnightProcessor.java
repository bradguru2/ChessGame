package com.chess.rules;

import java.util.ArrayList;
import java.util.List;
import com.chess.board.Cell;
import com.chess.board.Move;
import com.chess.pieces.Piece;
import com.chess.pieces.PlayerColor;

import static com.chess.rules.RuleConstants.*;

/**
 * Knights have their own set of rules to enforce
 * @author bradley
 * 
 */
public class KnightProcessor extends RuleProcessor {
	private List<Rule> rules;
	
	private List<Rule> createRules() {
		List<Rule> theRules = new ArrayList<>();
		
		//Add Valid Directional Moves
		theRules.add(new Rule(VALID_MOVE, Direction.LDownLeft, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LDownRight, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LRightDown, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LLeftDown, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LLeftUp, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LRightUp, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LUpRight, 3));
		theRules.add(new Rule(VALID_MOVE, Direction.LUpLeft, 3));
		
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
			matched = matchRule(VALID_MOVE, direction, numberOf);
			
			if(matched != null) {
				Cell fromCell = theMove.getFromCell();
				Piece playerPiece = fromCell.getPiece();
				PlayerColor playerColor = playerPiece.getPlayer().getColor();
				Cell toCell = theMove.getToCell();
				Piece enemy = toCell.getPiece();
				
				if(enemy!=null && enemy.getPlayer().getColor() == playerColor) {
					matched = null;//can not take piece of same of same color
				}

			}
		}
		
		return matched;
	}
	
	public KnightProcessor() {
		rules = this.createRules();
	}
	
}
