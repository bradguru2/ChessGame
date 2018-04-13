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
import com.chess.rules.KingProcessor;
import com.chess.rules.RuleProcessor;
import com.chess.rules.RuleResult;


public class KingProcessorTest {
	
	private Piece createPiece(PlayerColor color) {
		Player player = new Player(color, PlayerType.Auto, "player");
		return new Piece(player, Ability.King);
	}
	
	private List<Cell> createBoard(){
		List<Cell> cells = new ArrayList<>();
		
		for(int i=0; i < 64; i++) {
			Cell cell = new Cell(CellColor.Black, new Location((i % 8) + 1, (i/8) + 1));
			cells.add(cell);
		}
		
		return cells;
	}
	
	private Cell assignPiece(List<Cell> cells, Piece piece, int xIndex, int yIndex) {
		Cell cell = getCell(cells, xIndex, yIndex);
		cell.setPiece(piece);
		return cell;
	}
	
	private Cell getCell(List<Cell> cells, int xIndex, int yIndex) {
		return cells.get((yIndex-1)*8 + (xIndex - 1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testKingWhenCellsNull() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = null;
		Move theMove = new Move(new Cell(CellColor.Black, new Location(0, 0)), 
				new Cell(CellColor.White, new Location(0, 0)));
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testKingWhenMoveNull() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Move theMove = null;
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test
	public void testKingWhenClearOnOne() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 7, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKingWhenEnemyOnOne() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 7, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKingWhenClearOnLeftCastle() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece rook = createPiece(PlayerColor.Lower);
		Cell right = assignPiece(cells, player, 5, 8);
		Cell left = assignPiece(cells, null, 3, 8); 
		
		assignPiece(cells, rook, 1, 8);
		
		Move theMove = new Move(right, left);
				
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(CASTLED, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKingWhenClearOnRightCastle() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Upper);
		Piece rook = createPiece(PlayerColor.Upper);
		Cell right = assignPiece(cells, player, 5, 1);
		Cell left = assignPiece(cells, null, 7, 1);
		
		assignPiece(cells, rook, 8, 1);
		
		Move theMove = new Move(right, left);
				
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(CASTLED, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKingWhenBlockedOnCastle() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Piece rook = createPiece(PlayerColor.Lower);
		Cell left = assignPiece(cells, player, 5, 8);
		Cell right = assignPiece(cells, enemy, 7, 8);
		
		assignPiece(cells, rook, 8, 1);
		
		Move theMove = new Move(left, right);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testKingWhenHistoryOnCastle() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 7, 5);
		Move theMove = new Move(bottom, top);
		
		player.getHistory().add(theMove);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testKingWhenEnemyOnDiagonal() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 6, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNotNull(result.getCapturedPiece());
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKingWhenClearOnDiagonal() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 6, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNull(result.getCapturedPiece());
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
		
	@Test
	public void testKingWhenNoMove() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 4);
		Move theMove = new Move(bottom, bottom);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testPawnWhenNoMatch() {
		RuleProcessor processor = new KingProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, null, 7, 4);
		Move theMove = new Move(bottom, top);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
}
