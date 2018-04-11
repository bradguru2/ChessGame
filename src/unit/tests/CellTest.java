package unit.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.chess.board.Cell;
import com.chess.board.CellColor;
import com.chess.board.Location;
import com.chess.pieces.Ability;
import com.chess.pieces.Piece;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.pieces.PlayerType;


public class CellTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCellColorWhenNull() {
		CellColor color = null;
		Location location = new Location(0, 0);
		new Cell(color, location);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCellLocationWhenNull() {
		CellColor color = CellColor.Black;
		Location location = null;
		new Cell(color, location);
	}
	
	@Test
	public void testCellColorAndLocation() {
		CellColor color = CellColor.Black;
		Location location = new Location(0, 0);
		Cell from = new Cell(color, location);
		
		assertSame(color, from.getColor());
		assertSame(location, from.getLocation());
	}
	
	@Test
	public void testCellNoPiece() {
		CellColor color = CellColor.Black;
		Location location = new Location(0, 0);
		Cell from = new Cell(color, location);
		Piece piece = null;
        
		assertSame(piece, from.getPiece());
	}
	
	@Test
	public void testCellSetPiece() {
		CellColor color = CellColor.Black;
		Location location = new Location(0, 0);
		Cell from = new Cell(color, location);
		String playerName = "player 1";
		Ability ability = Ability.Bishop;
		Player player = new Player(PlayerColor.Lower, PlayerType.Auto, playerName);
		Piece piece = new Piece(player, ability);
		from.setPiece(piece);
        
		assertSame(piece, from.getPiece());
	}
}
