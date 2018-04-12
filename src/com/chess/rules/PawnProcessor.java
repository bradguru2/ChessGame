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
 * Pawns have their own set of rules to enforce
 * @author bradley
 *
 */
public class PawnProcessor extends RuleProcessor {
	private List<Rule> rules;
	
	private List<Rule> createRules() {
		List<Rule> theRules = new ArrayList<>();
		
		//Add Valid Directional Moves
		theRules.add(new Rule(VALID_MOVE, Direction.Forward, 1));
		theRules.add(new Rule(VALID_MOVE, Direction.Forward, 2));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpLeft, 2));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpRight, 2));
		
		//EN_PASSANT
		theRules.add(new Rule(EN_PASSANT, null, -1));
		
		//PROMOTION_REQUIRED
		theRules.add(new Rule(PROMOTION_REQUIRED, null, -1));
		
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
				Location toLocation = toCell.getLocation();
				
				//Check for Promotion
				if(playerColor == PlayerColor.Lower && toLocation.getYIndex() == 1 ||
				   playerColor == PlayerColor.Upper && toLocation.getYIndex() == 8) {
					matched = matchRule(PROMOTION_REQUIRED, null, -1);
				}
				
				//Check when Diagonal Move
				if(direction == Direction.DiagonalUpLeft || direction == Direction.DiagonalUpRight) {
					Move lastMove = this.getPreviousMove();

					if(lastMove != null) { //Check for EN_PASSANT
						if(playerColor == PlayerColor.Lower && toLocation.getYIndex() == 3 ||
						   playerColor == PlayerColor.Upper && toLocation.getYIndex() == 6) {
							if(toLocation.getXIndex() == lastMove.getToCell().getLocation().getXIndex()) {
								matched = matchRule(EN_PASSANT, null, -1);
							}
						}
					}
					else if(enemy == null || enemy.getPlayer().getColor() == playerColor) {
						matched = null; //Can not take own pieces
					}
				}
				else if(numberOf == 2) {
					if(playerPiece.getHistory().size() > 0) {
						matched = null; //Can not move 2 after first move
					}
					else if(enemy != null){
						matched = null; //Can not move 2 if blocked
					}
				}
				else {
					if(enemy !=null) {
						matched = null; //Can not move 1 if blocked
					}
				}
			}
		}
		
		return matched;
	}
	
	public PawnProcessor() {
		rules = this.createRules();
	}
	
}
