package unit.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.chess.pieces.*;

public class PieceTest {

	@Test(expected = IllegalArgumentException.class)
	public void testPiecePlayerWhenNull() {
		new Piece(null, Ability.Bishop);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPieceAbilityWhenNull() {
		new Piece(new Player(PlayerColor.Green, PlayerType.Auto, "foo"), null);
	}
	
	@Test
	public void testGetHistory() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Red;
		PlayerType type = PlayerType.Manual;
		Ability ability = Ability.Bishop;
		
		Player player = new Player(color, type, name);
				
		Piece piece = new Piece(player, ability);
		
		assertNotSame(null, piece.getHistory());
		assertEquals(0, piece.getHistory().size());
	}

	@Test
	public void testGetPlayer() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Red;
		PlayerType type = PlayerType.Manual;
		Ability ability = Ability.Bishop;
		
		Player player = new Player(color, type, name);
				
		Piece piece = new Piece(player, ability);
		
		assertSame(player, piece.getPlayer());
	}

	@Test
	public void testGetAbility() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Red;
		PlayerType type = PlayerType.Manual;
		Ability ability = Ability.Bishop;
		
		Player player = new Player(color, type, name);
				
		Piece piece = new Piece(player, ability);
		
		assertSame(ability, piece.getAbility());
	}

	@Test
	public void testSetAbility() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Red;
		PlayerType type = PlayerType.Manual;
		Ability ability = Ability.Pawn;
		Ability newAbility = Ability.Queen;
		
		Player player = new Player(color, type, name);
				
		Piece piece = new Piece(player, ability);
		piece.setAbility(newAbility);
		
		assertSame(newAbility, piece.getAbility());
	}

}
