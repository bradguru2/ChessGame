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
import com.chess.rules.KnightProcessor;
import com.chess.rules.RuleProcessor;
import com.chess.rules.RuleResult;


public class KnightProcessorTest {
	private Piece createPiece(PlayerColor color) {
		Player player = new Player(color, PlayerType.Auto, "player");
		return new Piece(player, Ability.Knight);
	}
	
	private Cell createCell(Piece piece, int xIndex, int yIndex) {
		Cell newCell = new Cell(CellColor.Black, new Location(xIndex, yIndex));
		newCell.setPiece(piece);
		return newCell;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKnightWhenCellsNull() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = null;
		Move theMove = new Move(new Cell(CellColor.Black, new Location(0, 0)), 
				new Cell(CellColor.White, new Location(0, 0)));
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testKnightWhenMoveNull() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Move theMove = null;
		processor.GetMoveResult(cells, theMove);
	}
	
	@Test
	public void testKnightWhenClearOnFour() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 6, 4);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKnightWhenBlockedOnFour() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 4, 6);
		Move theMove = new Move(bottom, top);
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testKnightWhenEnemyOnFour() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Upper);
		Piece enemy = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(enemy, 8, 4);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertNotNull(result.getCapturedPiece());
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKnightWhenBackOnFour() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Piece enemy = null;
		Cell bottom = createCell(player, 7, 4);
		Cell top = createCell(enemy, 6, 7);
		Move theMove = new Move(bottom, top);
		
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNotNull(result);
		assertSame(VALID_MOVE, result.getMatchedRule().getId());
	}
	
	@Test
	public void testKnightWhenNoMove() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 4);
		Move theMove = new Move(bottom, bottom);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
	
	@Test
	public void testKnightWhenNoMatch() {
		RuleProcessor processor = new KnightProcessor();
		List<Cell> cells = new ArrayList<>();
		Piece player = createPiece(PlayerColor.Lower);
		Cell bottom = createCell(player, 7, 7);
		Cell top = createCell(null, 7, 4);
		Move theMove = new Move(bottom, top);
			
		RuleResult result = processor.GetMoveResult(cells, theMove);
		
		assertNull(result);
	}
}
