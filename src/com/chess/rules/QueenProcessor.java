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
public class QueenProcessor extends RuleProcessor {
	private List<Rule> rules;
	
	private List<Rule> createRules() {
		List<Rule> theRules = new ArrayList<>();
		
		//Add Valid Directional Moves
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalDownLeft, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalDownRight, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpLeft, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.DiagonalUpRight, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Left, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Right, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Forward, -1));
		theRules.add(new Rule(VALID_MOVE, Direction.Back, -1));
		
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
				int x1, x2, y1, y2;
												
				if(enemy!=null && enemy.getPlayer().getColor() == playerColor) {
					matched = null;//can not take piece of same of same color
				}
				
				if(numberOf > 2 && (direction == Direction.DiagonalDownLeft ||
						direction == Direction.DiagonalDownRight ||
						direction == Direction.DiagonalUpLeft ||
						direction == Direction.DiagonalUpRight)) {//Diagonal = DeltaX + DeltaY
					
					
					if(toLocation.getYIndex() < fromLocation.getYIndex()) {
						y1 = fromLocation.getYIndex() - 2;
						y2 = toLocation.getYIndex();
					}
					else {
						y1 = fromLocation.getYIndex();
						y2 = toLocation.getYIndex() - 2;
					}
					
					if(toLocation.getXIndex() < fromLocation.getXIndex()) {
						x1 = fromLocation.getXIndex() - 2;
						x2 = toLocation.getXIndex();
					}
					else {
						x1 = fromLocation.getXIndex();
						x2 = toLocation.getXIndex() - 2;
					}
					
					if(this.isDiagonalBlocked(cells, y1, y2, x1, x2)) {
						matched = null;
					}
				}
				else if(numberOf > 1 && (direction == Direction.Left || 
						direction == Direction.Right)) {
					y1 = fromLocation.getYIndex() - 1;
					
					if(toLocation.getXIndex() < fromLocation.getXIndex()) {
						x1 = fromLocation.getXIndex() - 2;
						x2 = toLocation.getXIndex();
					}
					else {
						x1 = fromLocation.getXIndex();
						x2 = toLocation.getXIndex() - 2;
					}
					
					if(this.isHorizontalBlocked(cells, x1, x2, y1)) {
						matched = null;
					}
				}
				else if(numberOf > 1 && (direction == Direction.Forward || 
						direction == Direction.Back)) {
					x1 = fromLocation.getXIndex() - 1;
					
					if(toLocation.getYIndex() < fromLocation.getYIndex()) {
						y1 = fromLocation.getYIndex() - 2;
						y2 = toLocation.getYIndex();
					}
					else {
						y1 = fromLocation.getYIndex();
						y2 = toLocation.getYIndex() - 2;
					}
					
					if(this.isVerticalBlocked(cells, y1, y2, x1)) {
						matched = null;
					}
				}
			}
		}
		
		return matched;
	}
	
	public QueenProcessor() {
		rules = this.createRules();
	}
	
}
