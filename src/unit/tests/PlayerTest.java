package unit.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.pieces.PlayerType;

public class PlayerTest {

	@Test(expected = IllegalArgumentException.class)
	public void testPlayerColorWhenNull() {
		String name = "foo";
		new Player(null, PlayerType.Manual , name);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerTypeWhenNull() {
		String name = "foo";
		new Player(PlayerColor.Lower, null , name);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerNameWhenNull() {
		String name = null;
		new Player(PlayerColor.Lower, PlayerType.Auto , name);
	}
	
	@Test
	public void testGetName() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Upper;
		PlayerType type = PlayerType.Manual;
		
		Player player = new Player(color, type, name);
		
		assertEquals(name, player.getName());
	}

	@Test
	public void testGetColor() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Lower;
		PlayerType type = PlayerType.Manual;
		
		Player player = new Player(color, type, name);
		
		assertEquals(color, player.getColor());
	}

	@Test
	public void testGetPlayerType() {
		String name = "testMe";
		PlayerColor color = PlayerColor.Upper;
		PlayerType type = PlayerType.Auto;
		
		Player player = new Player(color, type, name);
		
		assertEquals(type, player.getPlayerType());
	}

}
