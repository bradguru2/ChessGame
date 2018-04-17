package com.chess.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import com.chess.Application;
import com.chess.board.Board;
import com.chess.board.BoardActions;
import com.chess.board.Cell;
import com.chess.board.CellColor;
import com.chess.board.IllegalMoveException;
import com.chess.pieces.Piece;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.pieces.PlayerType;
import com.chess.rules.RuleResult;
import static com.chess.console.ConsoleColors.*;

public class LinuxConsoleController implements Application {

	private BoardActions board;
	private int playerTurn;
	private Player player1;
	private Player player2;
	
	private String renderCell(Cell cell) {
		String cellColor = cell.getColor() == CellColor.White ? WHITE_BACKGROUND :
			BLACK_BACKGROUND;
		String result;
		Piece piece = cell.getPiece();
		
		if(piece != null) {
			PlayerColor color = piece.getPlayer().getColor();
			result = color == PlayerColor.Lower ? GREEN : RED;
			result += cellColor;
			System.out.print(result + piece.getAbility().toString().substring(0, 2));
		}
		else {
			result = cellColor + "  ";
		}
		return result + RESET + " ";
	}
	
	private void renderBoard() {
		System.out.println(RESET);
		System.out.print("  A  B  C  D  E  F  G  H  ");
		
		List<Cell> cells = board.getOrderedCells();
		int rowCount = 0;
		
		for(int i=0; i < cells.size(); i++) {
			if(i % 8 == 0) {
				System.out.println();
				System.out.println("                          ");
				System.out.print(++rowCount);
				System.out.print(" ");
			}
			System.out.print(renderCell(cells.get(i)));
		}
		System.out.println();
		System.out.println("  A  B  C  D  E  F  G  H");
		System.out.print(RESET);
	}
	
	private String getPlayerInput() throws IOException {
		Player current = playerTurn == 0 ? player1 : player2;
		
		System.out.print(current.getName() + " please make your move (a1h8) or type quit and press enter:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine().substring(0, 4);
	}
	
	public LinuxConsoleController() {
		playerTurn = 0;
		player1 = new Player(PlayerColor.Lower, PlayerType.Manual, "Player 1");
		player2 = new Player(PlayerColor.Upper, PlayerType.Manual, "Player 2");
		board = new Board(player1, player2);
	}
	
	@Override
	public void initialize() {
	    System.out.println("\nWelcome to Chess");
		System.out.println("*******************************************");
		System.out.println("Player 1 will be Green side and makes first move");
		System.out.println("Player 2 will be Red side");
	}

	@Override
	public void execute() {
		boolean isCheckMate = false;
		Player current = null;
		
		while(!isCheckMate) {
			renderBoard();
			
			System.out.println();
			
			String move;
			try {
				move = getPlayerInput();
			} catch (IOException e) {
				System.out.println(e);
				move = null;
			}
			catch (StringIndexOutOfBoundsException e) {
				System.out.println(e);
				move = null;
			}
			
			if(move.equals("quit")) {
				return;
			}
			else {
				try {
					RuleResult result = board.makeMove(move);
					Piece captured = result.getCapturedPiece();
					
					if(captured != null) {
						System.out.println(captured.getAbility().toString() + " was captured");
					}
					
					playerTurn ^= 1;
					
					current = playerTurn == 0 ? player1 : player2;
					
					if(board.kingIsInCheck(current)) {
						isCheckMate = board.isCheckMate(current);
						
						if(!isCheckMate) {
							System.out.println(current.getName() + " is in check");
						}
					}
					
				}
				catch(IllegalMoveException e) {
					System.out.println(e);
				}
			}
		}
		renderBoard();
		System.out.println(current.getName() + " was checkmated. game over");
	}

}
