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
import com.chess.rules.QueenProcessor;
import com.chess.rules.RuleProcessor;
import com.chess.rules.RuleResult;


public class QueenProcessorTest {
	
	private Piece createPiece(PlayerColor color) {
		Player player = new Player(color, PlayerType.Manual, "player");
		return new Piece(player, Ability.Queen);
	}
	
	private List<Cell> createBoard(){
		List<Cell> cells = new ArrayList<>();
		
		for(int i=0; i < 64; i++) {
			Cell cell = new Cell(CellColor.White, new Location((i % 8) + 1, (i/8) + 1));
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
	public void testQueenWhenCellsNull() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = null;
		Move theMove = new Move(new Cell(CellColor.Black, new Location(0, 0)), 
				new Cell(CellColor.White, new Location(0, 0)));
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testQueenWhenMoveNull() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Move theMove = null;
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test
	public void testQueenWhenDiagonalClearOnOne() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 6, 6);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testQueenWhenHorizontalClearOnOne() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Upper);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 8, 7);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testQueenWhenVerticalClearOnOne() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = assignPiece(cells, player, 7, 6);
		Cell top = assignPiece(cells, enemy, 7, 7);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testQueenWhenEnemyOnDiagonalOne() {
		RuleProcessor processor = new QueenProcessor();
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
	public void testQueenWhenEnemyOnVerticalFour() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Cell bottom = assignPiece(cells, player, 7, 3);
		Cell top = assignPiece(cells, enemy, 7, 7);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNotNull(result.getCapturedPiece());
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testQueenWhenBlockedOnDiagonalFour() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Piece friend = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 3, 3);
		Move theMove = new Move(bottom, top);
		
		assignPiece(cells, friend, 5, 5);//Block the path
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testQueenWhenBlockedOnHorizontalFour() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Piece friend = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 3, 7);
		Move theMove = new Move(bottom, top);
		
		assignPiece(cells, friend, 5, 7);//Block the path
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testQueenWhenBlockedOnVerticalFour() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Upper);
		Piece friend = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, enemy, 7, 3);
		Move theMove = new Move(bottom, top);
		
		assignPiece(cells, friend, 7, 5);//Block the path
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testQueenWhenNoMove() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = assignPiece(cells, player, 7, 4);
		Move theMove = new Move(bottom, bottom);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testQueenWhenNoMatch() {
		RuleProcessor processor = new QueenProcessor();
		List<Cell> cells = createBoard();
		Piece player = createPiece(PlayerColor.Upper);
		Cell bottom = assignPiece(cells, player, 7, 7);
		Cell top = assignPiece(cells, null, 2, 3);
		Move theMove = new Move(bottom, top);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
}
