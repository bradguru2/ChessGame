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
 * Kings have their own set of rules to enforce <br>
 * KingProcessor not responsible for returning:
 * 1) King is in check, 
 * 2) King is in checkmate
 * @author bradley
 * 
 */
public class KingProcessor extends RuleProcessor {
	private List<Rule> rules;
	
	private List<Rule> createRules() {
		List<Rule> theRules = new ArrayList<>();
		
		//Add Valid Directional Moves
		theRules.add(new Rule(VALID_MOVE, Direction.Back, 1));
		theRules.add(new Rule(VALID_MOVE, Direction.Forward, 1));
		theRules.add(new Rule(VALID_MOVE, Direction.Right, 1));
		theRules.add(new Rule(VALID_MOVE, Direction.Left, 1));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalDownLeft, 2));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalDownRight, 2));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpLeft, 2));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpRight, 2));
		
		//Castle Moves
		theRules.add(new Rule(CASTLED, Direction.Right, 2));
		theRules.add(new Rule(CASTLED, Direction.Left, 2));
		
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
			Cell fromCell = theMove.getFromCell();
			Piece playerPiece = fromCell.getPiece();
			PlayerColor playerColor = playerPiece.getPlayer().getColor();
			Cell toCell = theMove.getToCell();
			Piece enemy = toCell.getPiece();
			
			if(matched != null) {
				
				if(enemy!=null && enemy.getPlayer().getColor() == playerColor) {
					matched = null;//can not take piece of same of same color
				}

			}
			else {
				matched = matchRule(CASTLED, direction, numberOf);
				
				if(matched != null) {
					boolean isValid = playerPiece.getHistory().size() == 0;
															
					if(isValid) {
						Location fromLocation = fromCell.getLocation();
						Location toLocation = toCell.getLocation();
						Piece rook = null;
						int y = fromLocation.getYIndex() - 1, x1, x2;
						
						if(toLocation.getXIndex() < fromLocation.getXIndex()) {
							rook = cells.get(y*8).getPiece();
							x1 = fromLocation.getXIndex() - 2;
							x2 = 1;
						}
						else {
							rook = cells.get(y*8 + 7).getPiece();
							x1 = fromLocation.getXIndex();
							x2 = 6;
						}
						
						isValid = rook != null && rook.getHistory().size() == 0 &&
								!this.isHorizontalBlocked(cells, x1, x2, y);
					}
					
					if(!isValid) {
						matched = null;
					}
				}
			}
		}
		
		return matched;
	}
	
	public KingProcessor() {
		rules = this.createRules();
	}
	
}
