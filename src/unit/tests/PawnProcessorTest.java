package unit.tests;

import static org.junit.Assert.*;
import static com.chess.rules.RuleConstants.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.chess.board.Cell;
import com.chess.board.CellColor;
import com.chess.board.Location;
import com.chess.board.Move;
import com.chess.pieces.Ability;
import com.chess.pieces.Piece;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.pieces.PlayerType;
import com.chess.rules.PawnProcessor;
import com.chess.rules.RuleProcessor;
import com.chess.rules.RuleResult;


public class PawnProcessorTest {
	private Piece createPiece(PlayerColor color) {
		Player player = new Player(color, PlayerType.Auto, "player");
		return new Piece(player, Ability.Pawn);
	}
	
	private Cell createCell(Piece piece, int xIndex, int yIndex) {
		Cell newCell = new Cell(CellColor.Black, new Location(xIndex, yIndex));
		newCell.setPiece(piece);
		return newCell;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPawnWhenCellsNull() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = null;
		Move theMove = new Move(new Cell(CellColor.Black, new Location(0, 0)), 
				new Cell(CellColor.White, new Location(0, 0)));
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPawnWhenMoveNull() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Move theMove = null;
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test
	public void testPawnWhenClearOnOne() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenBlockedOnOne() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 6);
		Move theMove = new Move(bottom, top);
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenClearOnTwo() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 5);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenBlockedOnTwo() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 5);
		Move theMove = new Move(bottom, top);
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenHistoryOnTwo() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 5);
		Move theMove = new Move(bottom, top);
		
		player.getHistory().add(theMove);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenEnemyOnDiagonal() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 6, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNotNull(result.getCapturedPiece());
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenClearOnDiagonal() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 6, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenPromotionForLower() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 2);
		Cell top = createCell(enemy, 7, 1);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(PROMOTION_REQUIRED, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenPromotionForUpper() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Upper);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 7, 8);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(PROMOTION_REQUIRED, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenEnPassant() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = createCell(player, 7, 4);
		Cell top = createCell(null, 8, 3);
		Move theMove = new Move(bottom, top);
		Cell prevBottom = createCell(enemy, 8, 2);
		Cell prevTop = createCell(enemy, 8, 4);
		Move prevMove = new Move(prevBottom, prevTop);
		
		processor.setPreviousMove(prevMove);//Have to be careful when setting previous move for pawn
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNotNull(result.getCapturedPiece());
		assertSame(EN_PASSANT, result.getMatchedRule().getId());
	}
	
	@Test
	public void testPawnWhenNoMove() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 4);
		Move theMove = new Move(bottom, bottom);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenNoMatch() {
		RuleProcessor processor = new PawnProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(null, 7, 4);
		Move theMove = new Move(bottom, top);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
}
