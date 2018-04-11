package unit.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.chess.board.Move;
import com.chess.board.Cell;
import com.chess.board.CellColor;
import com.chess.board.Location;


public class MoveTest {

	@Test(expected = IllegalArgumentException.class)
	public void testFromCellWhenNull() {
		CellColor color = CellColor.Black;
		Location location = new Location(0, 0);
		Cell to = new Cell(color, location);
		Cell from = null;
		
		new Move(from, to);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testToCellWhenNull() {
		CellColor color = CellColor.Black;
		Location location = new Location(0, 0);
		Cell to = null;
		Cell from = new Cell(color, location);
		
		new Move(from, to);
	}
	
	@Test
	public void testToAndFromCell() {
		CellColor color = CellColor.Black;
		CellColor color2 = CellColor.White;
		Location location = new Location(0, 0);
		Location location2 = new Location(1, 1);
		Cell to = new Cell(color2, location2);
		Cell from = new Cell(color, location);
		
		Move move = new Move(from, to);
		
		assertSame(to, move.getToCell());
		assertSame(from, move.getFromCell());
	}
}
