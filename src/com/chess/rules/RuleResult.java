package com.chess.rules;

import com.chess.pieces.Piece;

public class RuleResult {
	private Rule matchedRule;
	private Piece capturedPiece;
	
	public RuleResult(Rule rule, Piece piece) {
		matchedRule = rule;
		capturedPiece = piece;
	}
	
	public Rule getMatchedRule() {
		return matchedRule;
	}
	
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
}
