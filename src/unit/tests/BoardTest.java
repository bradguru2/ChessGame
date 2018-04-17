package unit.tests;

import static org.junit.Assert.*;
import static com.chess.rules.RuleConstants.*;
import java.util.List;
import org.junit.Test;
import com.chess.board.Board;
import com.chess.board.BoardActions;
import com.chess.board.Cell;
import com.chess.board.CellColor;
import com.chess.board.IllegalMoveException;
import com.chess.pieces.Ability;
import com.chess.pieces.Piece;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.pieces.PlayerType;
import com.chess.rules.RuleResult;


public class BoardTest {
	
	private void clearBoard(List<Cell> cells){
		for(Cell cell:cells) {
			cell.setPiece(null);
		}
	}
	
	private Cell assignPiece(List<Cell> cells, Piece piece, int xIndex, int yIndex) {
		Cell cell = getCellByIndex(cells, xIndex, yIndex);
		cell.setPiece(piece);
		return cell;
	}
	
	private Cell getCellByIndex(List<Cell> cells, int xIndex, int yIndex) {
		return cells.get((yIndex-1)*8 + (xIndex - 1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardWhenPlayer1Null() {
		Player player1 = null;
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		new Board(player1, player2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardWhenPlayer2Null() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = null;
		new Board(player1, player2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardWhenDefaultKingInCheckNull() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.kingIsInCheck(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardWhenDefaultKingIsCheckMateNull() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.isCheckMate(null);
	}
	
	@Test(expected = IllegalMoveException.class)
	public void testBoardWhenMoveNull() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.makeMove(null);
	}
	
	@Test
	public void testBoardDefaults() {
		final int totalCells = 64;
		final int totalPieces = 32;
		final int totalUpper = 16;
		final int totalLower = 16;
		int numberOfPieces = 0, upperPieces = 0, lowerPieces = 0, numberOfCells = 0;
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		assertSame(totalCells, cells.size());
		assertSame(CellColor.White, cells.get(0).getColor());
		assertSame(CellColor.Black, cells.get(7).getColor());
		assertSame(CellColor.Black, cells.get(8).getColor());
		assertSame(CellColor.White, cells.get(16).getColor());
		assertSame(CellColor.Black, cells.get(24).getColor());
		assertSame(CellColor.White, cells.get(32).getColor());
		assertSame(CellColor.Black, cells.get(40).getColor());
		assertSame(CellColor.White, cells.get(48).getColor());
		assertSame(CellColor.Black, cells.get(56).getColor());
		assertSame(CellColor.White, cells.get(63).getColor());
				
		for(Cell cell:cells) {
			Piece piece = cell.getPiece();
			
			if(piece!=null) {
				++numberOfPieces;
				
				switch(piece.getPlayer().getColor()) {
				case Upper:
					++upperPieces;
					break;
				case Lower:
					++lowerPieces;
					break;
				}
			}
			
			++numberOfCells;
		}
		
		assertSame(totalCells, numberOfCells);
		assertSame(totalPieces, numberOfPieces);
		assertSame(totalUpper, upperPieces);
		assertSame(totalLower, lowerPieces);
	}
	
	@Test
	public void testBoardDefaultKingIsCheck() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		assertFalse(board.kingIsInCheck(player1));
		assertFalse(board.kingIsInCheck(player2));
	}
	
	@Test
	public void testBoardDefaultKingIsCheckMate() {
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		assertFalse(board.isCheckMate(player1));
		assertFalse(board.isCheckMate(player2));
	}
	
	@Test(expected = IllegalMoveException.class)
	public void testBoardWhenMoveFromEmpty() {
		final String move = "a3a4";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.makeMove(move);
	}
	
	@Test(expected = IllegalMoveException.class)
	public void testBoardWhenMovePieceIllegal() {
		final String move = "a1d4";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.makeMove(move);
	}
	
	@Test(expected = IllegalMoveException.class)
	public void testBoardWhenMoveOutOfOrder() {
		final String move = "g7g6";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		
		board.makeMove(move);
	}
	
	@Test
	public void testBoardWhenMoveUpperValid() {
		final String move = "b2b3";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		RuleResult result = board.makeMove(move);
		
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
		assertNull(result.getCapturedPiece());
		assertSame(Ability.Pawn, getCellByIndex(cells, 2, 3).getPiece().getAbility());
	}
	
	@Test
	public void testBoardWhenMoveLowerValid() {
		final String move1 = "b2b3";
		final String move2 = "g7g6";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		board.makeMove(move1);//Make first move
		RuleResult result = board.makeMove(move2);
		
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
		assertNull(result.getCapturedPiece());
		assertSame(Ability.Pawn, getCellByIndex(cells, 7, 6).getPiece().getAbility());
	}
	
	@Test
	public void testBoardWhenEnPassant() {
		final String move1 = "b2b4";
		final String move2 = "g8h6";
		final String move3 = "b4b5";
		final String move4 = "c7c5";
		final String move5 = "b5c6";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		RuleResult result = board.makeMove(move5);
		
		assertSame(EN_PASSANT, result.getMatchedRule().getId());
		assertNotNull(result.getCapturedPiece());
		assertSame(Ability.Pawn, getCellByIndex(cells, 3, 6).getPiece().getAbility());
		assertNull(getCellByIndex(cells, 3, 5).getPiece());
	}
	
	@Test
	public void testBoardWhenPromotionRequired() {
		final String move1 = "b2b4";
		final String move2 = "c7c5";
		final String move3 = "h2h4";
		final String move4 = "c5b4";
		final String move5 = "b1a3";
		final String move6 = "b4b3";
		final String move7 = "h4h5";
		final String move8 = "b3b2";
		final String move9 = "h5h6";
		final String move10 = "b2b1";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		board.makeMove(move5);
		board.makeMove(move6);
		board.makeMove(move7);
		board.makeMove(move8);
		board.makeMove(move9);
		RuleResult result = board.makeMove(move10);
		
		assertSame(PROMOTION_REQUIRED, result.getMatchedRule().getId());
		assertNull(result.getCapturedPiece());
		assertSame(Ability.Queen, getCellByIndex(cells, 2, 1).getPiece().getAbility());
	}
	
	@Test
	public void testBoardWhenCastle() {
		final String move1 = "g1h3";
		final String move2 = "c7c6";
		final String move3 = "g2g3";
		final String move4 = "c6c5";
		final String move5 = "f1g2";
		final String move6 = "c5c4";
		final String move7 = "e1g1";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		board.makeMove(move5);
		board.makeMove(move6);
		RuleResult result = board.makeMove(move7);
		
		assertSame(CASTLED, result.getMatchedRule().getId());
		assertNull(result.getCapturedPiece());
		assertSame(Ability.Rook, getCellByIndex(cells, 6, 1).getPiece().getAbility());
		assertSame(Ability.King, getCellByIndex(cells, 7, 1).getPiece().getAbility());
	}
	
	@Test
	public void testBoardWhenUpperCheckMate() {
		final String move1 = "e2e4";
		final String move2 = "e7e5";
		final String move3 = "h2h3";
		final String move4 = "f8c5";
		final String move5 = "b1a3";
		final String move6 = "d8f6";
		final String move7 = "g2g3";
		final String move8 = "f6f2";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
				
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		board.makeMove(move5);
		board.makeMove(move6);
		board.makeMove(move7);
		board.makeMove(move8);
		
		assertTrue(board.isCheckMate(player1));
	}
	
	@Test
	public void testBoardWhenUpperCheck() {
		final String move1 = "e2e4";
		final String move2 = "e7e5";
		final String move3 = "g1h3";
		final String move4 = "f8c5";
		final String move5 = "b1a3";
		final String move6 = "d8f6";
		final String move7 = "d1e2";
		final String move8 = "f6f2";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
				
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		board.makeMove(move5);
		board.makeMove(move6);
		board.makeMove(move7);
		board.makeMove(move8);
		
		assertTrue(board.kingIsInCheck(player1));
		assertFalse(board.isCheckMate(player1));
	}
	
	@Test
	public void testBoardWhenUpperInvalidCheckMove() {
		final String move1 = "e2e4";
		final String move2 = "e7e5";
		final String move3 = "g1h3";
		final String move4 = "f8c5";
		final String move5 = "b1a3";
		final String move6 = "d8f6";
		final String move7 = "d1e2";
		final String move8 = "f6f2";
		final String move9 = "e2e3";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
				
		board.makeMove(move1);
		board.makeMove(move2);
		board.makeMove(move3);
		board.makeMove(move4);
		board.makeMove(move5);
		board.makeMove(move6);
		board.makeMove(move7);
		board.makeMove(move8);
	    
		try {
			board.makeMove(move9);
		}
		catch(IllegalMoveException e) {
			List<Cell> cells = board.getOrderedCells();
			assertNull(getCellByIndex(cells, 5, 3).getPiece());
			assertSame(Ability.Queen, getCellByIndex(cells, 5, 2).getPiece().getAbility());
		}
	}
	
	@Test
	public void testBoardWhenLowerCastledInCheck() {
		final String move1 = "e2e7";
		final String move2 = "e8g8";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		clearBoard(cells);
		
		Piece upperKing = new Piece(player1, Ability.King);
		Piece upperQueen = new Piece(player1, Ability.Queen);
		Piece lowerKing = new Piece(player2, Ability.King);
		Piece lowerPawn = new Piece(player2, Ability.Pawn);
		Piece lowerRook = new Piece(player2, Ability.Rook);
		
		assignPiece(cells, upperKing, 5, 1);
		assignPiece(cells, upperQueen, 5, 2);
		assignPiece(cells, lowerKing, 5, 8);
		assignPiece(cells, lowerPawn, 5, 7);
		assignPiece(cells, lowerRook, 8, 8);
				
		board.makeMove(move1);
	    
		try {
			board.makeMove(move2);
		}
		catch(IllegalMoveException e) {
			
			assertNull(getCellByIndex(cells, 7, 8).getPiece());
			assertNull(getCellByIndex(cells, 6, 8).getPiece());
			assertSame(Ability.King, getCellByIndex(cells, 5, 8).getPiece().getAbility());
			assertSame(Ability.Rook, getCellByIndex(cells, 8, 8).getPiece().getAbility());
		}
	}
	
	@Test
	public void testBoardWhenLowerCastledThroughCheck() {
		final String move1 = "e2f2";
		final String move2 = "e8g8";
		Player player1 = new Player(PlayerColor.Upper, PlayerType.Manual, "player 1");
		Player player2 = new Player(PlayerColor.Lower, PlayerType.Manual, "player 2");
		BoardActions board = new Board(player1, player2);
		List<Cell> cells = board.getOrderedCells();
		
		clearBoard(cells);
		
		Piece upperKing = new Piece(player1, Ability.King);
		Piece upperQueen = new Piece(player1, Ability.Queen);
		Piece lowerKing = new Piece(player2, Ability.King);
		Piece lowerPawn = new Piece(player2, Ability.Pawn);
		Piece lowerRook = new Piece(player2, Ability.Rook);
		
		assignPiece(cells, upperKing, 5, 1);
		assignPiece(cells, upperQueen, 5, 2);
		assignPiece(cells, lowerKing, 5, 8);
		assignPiece(cells, lowerPawn, 5, 7);
		assignPiece(cells, lowerRook, 8, 8);
				
		board.makeMove(move1);
	    
		try {
			board.makeMove(move2);
		}
		catch(IllegalMoveException e) {
			
			assertNull(getCellByIndex(cells, 7, 8).getPiece());
			assertNull(getCellByIndex(cells, 6, 8).getPiece());
			assertSame(Ability.King, getCellByIndex(cells, 5, 8).getPiece().getAbility());
			assertSame(Ability.Rook, getCellByIndex(cells, 8, 8).getPiece().getAbility());
		}
	}
}
