package com.chess.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.chess.pieces.Ability;
import com.chess.pieces.Piece;
import com.chess.pieces.Player;
import com.chess.pieces.PlayerColor;
import com.chess.rules.BishopProcessor;
import com.chess.rules.KingProcessor;
import com.chess.rules.KnightProcessor;
import com.chess.rules.PawnProcessor;
import com.chess.rules.QueenProcessor;
import com.chess.rules.RookProcessor;
import com.chess.rules.Rule;
import com.chess.rules.RuleProcessor;
import com.chess.rules.RuleResult;
import static com.chess.rules.RuleConstants.*;

/**
 * Encapsulates the logical actions and properties of a Chess Board
 * @author bradley
 *
 */
public class Board implements BoardActions{
	private Player[] players;
	int playerTurn;
	private List<Cell> cells;
	private HashMap<Ability, RuleProcessor> processors;
	private HashMap<Character, Integer> alphaMap;
	
		
	private HashMap<Character, Integer> buildAlphaMap () {
		HashMap<Character, Integer> result = new HashMap<>();
		
		result.put('a', 1);
		result.put('A', 1);
		result.put('b', 2);
		result.put('B', 2);
		result.put('c', 3);
		result.put('C', 3);
		result.put('d', 4);
		result.put('D', 4);
		result.put('e', 5);
		result.put('E', 5);
		result.put('f', 6);
		result.put('F', 6);
		result.put('g', 7);
		result.put('G', 7);
		result.put('h', 8);
		result.put('H', 8);
		
		return result;
	}
	
	private void assignPiece(Piece thePiece, int xIndex, int yIndex) {
		getCellByIndex(xIndex, yIndex).setPiece(thePiece);
	}
	
	private Cell getCellByIndex(int xIndex, int yIndex) {
		return cells.get((yIndex - 1) * 8 + (xIndex - 1));
	}
	
	private void initPieces(int playerNumber) {
		int startRowIndex = players[playerNumber].getColor() == 
				PlayerColor.Upper ? 1 : 7;
		int endRowIndex = players[playerNumber].getColor() == 
				PlayerColor.Upper ? 2 : 8;
		Piece thePiece = null;
		
		for(int i = startRowIndex; i <= endRowIndex; i++) {
			for(int j = 1; j <= 8; j++) {
				if(i == 2 || i == 7) {
					thePiece = new Piece(players[playerNumber], Ability.Pawn);
				}
				else {
					switch(j) {
					case 1:
					case 8:
						thePiece = new Piece(players[playerNumber], Ability.Rook);
						break;
					case 2:
					case 7:
						thePiece = new Piece(players[playerNumber], Ability.Knight);
						break;
					case 3:
					case 6:
						thePiece = new Piece(players[playerNumber], Ability.Bishop);
						break;
					case 4:
						thePiece = new Piece(players[playerNumber], Ability.Queen);
						break;
					default:
						thePiece = new Piece(players[playerNumber], Ability.King);
					}
				}
				
				assignPiece(thePiece, j, i);
			}
		}
	}
	
	private List<Cell> createBoard(){
		List<Cell> newCells = new ArrayList<>();
		int toggle = 1;
		
		for(int i=0; i < 64; i++) {
			if(i%8 == 0) {
				toggle ^= 1;
			}
			
			CellColor color = (toggle == 0) ? CellColor.White : CellColor.Black;
			
			toggle ^= 1;
			
			Cell cell = new Cell(color, new Location((i % 8) + 1, i/8 + 1));
			
			newCells.add(cell);
		}
		
		return newCells;
	}
	
	/**
	 * Keep very basic because rule processors will be evaluating these moves
	 * @param fromCell the origin cell to make moves from
	 * @return a list of moves to evaluate for the piece occupying the cell
	 */
	private List<Move> getInCheckMoves(Cell fromCell){
		List<Move> moves = new ArrayList<Move>();
		Piece fromPiece = fromCell.getPiece();
		Ability fromAbility = fromPiece.getAbility();
		Location fromLocation = fromCell.getLocation();
		int x1 = fromLocation.getXIndex(), y1 = fromLocation.getYIndex();
				
		for(Cell toCell:cells) {
			if(toCell != fromCell) {
				Location toLocation = toCell.getLocation();
				int x2 = toLocation.getXIndex(), y2 = toLocation.getYIndex(),
						absDeltaX = Math.abs(x2 - x1),
						absDeltaY = Math.abs(y2 - y1);
				
				switch(fromAbility) {
				case Pawn:
					if(absDeltaX == 1 || absDeltaY == 1 || absDeltaY == 2) {
						moves.add(new Move(fromCell, toCell));
					}
					break;
				case Knight:
					if((absDeltaX == 1 && absDeltaY == 2) || 
					   (absDeltaX == 2 && absDeltaY == 1)){
						moves.add(new Move(fromCell, toCell));
						}
					break;
				case Bishop:
					if(absDeltaX == absDeltaY) {
						moves.add(new Move(fromCell, toCell));
					}
					break;
				case Rook:
					if(absDeltaX == 0 || absDeltaY == 0) {
						moves.add(new Move(fromCell, toCell));
					}
					break;
				case Queen:
					if(absDeltaX == 0 || absDeltaY == 0 || absDeltaX == absDeltaY) {
						moves.add(new Move(fromCell, toCell));
					}
					break;
				case King:
					if(absDeltaX == 1 || absDeltaY == 1) {
						moves.add(new Move(fromCell, toCell));
					}
					break;
				}
			}
		}
		
		return moves;
	}
	
	public Board(Player player1, Player player2) throws IllegalArgumentException {
		if(player1 == null) {
			throw new IllegalArgumentException("player1 is null");
		}
		
		if(player2 == null) {
			throw new IllegalArgumentException("player2 is null");
		}
		
		players = new Player[] { player1, player2 };
		playerTurn = 0;
		
		cells = createBoard();
		
		alphaMap = buildAlphaMap();//Used for conversion on input
		
		processors = new HashMap<>();
		processors.put(Ability.Pawn, new PawnProcessor());
		processors.put(Ability.Knight, new KnightProcessor());
		processors.put(Ability.Bishop, new BishopProcessor());
		processors.put(Ability.Rook, new RookProcessor());
		processors.put(Ability.Queen, new QueenProcessor());
		processors.put(Ability.King, new KingProcessor());
		
		initPieces(0);//player 1's pieces
		initPieces(1);//player 2's pieces
	}
	
	@Override
	public List<Cell> getOrderedCells() {
		return cells;
	}

	@Override
	public boolean kingIsInCheck(Player checkPlayer) throws IllegalArgumentException {
		if(checkPlayer == null) {
			throw new IllegalArgumentException("checkPlayer is null");
		}
		
		boolean isCheck = false;
		List<Cell> enemyCells = new ArrayList<>();
		Cell kingCell = null;
		
		for (Cell cell:cells) {
			Piece piece = cell.getPiece();
			
			if(piece != null) {
				if(piece.getPlayer() != checkPlayer) {
					enemyCells.add(cell);
				}
				else {
					if(piece.getAbility() == Ability.King) {
						kingCell = cell;
					}
				}
			}
		}
		
		for (Cell cell:enemyCells) {
			Move theMove = new Move(cell, kingCell);
			RuleResult result = processors.get(cell.getPiece().getAbility())
					.GetMoveResult(cells , theMove);
			
			if(result != null && result.getCapturedPiece().getAbility() == Ability.King) {
				isCheck=true;
				break;
			}
		}
		
		return isCheck;
	}

	@Override
	public boolean isCheckMate(Player checkPlayer) throws IllegalArgumentException {
		if(checkPlayer == null) {
			throw new IllegalArgumentException("checkPlayer is null");
		}
		
		boolean isCheck = kingIsInCheck(checkPlayer);//Is King in check?
		
		if(isCheck) {//Can any piece stop check?
			for(Cell friendCell:cells) {
				Piece friendPiece = friendCell.getPiece();
								
				if(friendPiece != null && friendPiece.getPlayer() == checkPlayer) {
					Ability friendAbility = friendPiece.getAbility();
					List<Move> moves = getInCheckMoves(friendCell);
					
					for(Move theMove:moves) {
						RuleResult result = processors.get(friendAbility)
								.GetMoveResult(cells, theMove);
						
						if(result != null) {
							Cell toCell = theMove.getToCell();
							Piece toPiece = toCell.getPiece();
							
							friendCell.setPiece(null);//Make the move
							toCell.setPiece(friendPiece);
							
							isCheck = kingIsInCheck(checkPlayer);
							
							toCell.setPiece(toPiece);//Restore previous state
							friendCell.setPiece(friendPiece);
						}
						
						if(!isCheck) {
							break;
						}
					}
					
					if(!isCheck) {
						break;
					}
				}
			}
		}
		
		return isCheck;//Is Check no matter what action was taken
	}

	@Override
	public RuleResult makeMove(String move) throws IllegalMoveException {
		if(move == null) {
			throw new IllegalMoveException("move is null");
		}
		
		boolean isCheck = false;
		RuleResult result = null;
		
		int x1 = alphaMap.get(move.charAt(0)), y1 = move.charAt(1) - '0',
			x2 = alphaMap.get(move.charAt(2)), y2 = move.charAt(3) - '0';
		
		Cell fromCell = getCellByIndex(x1, y1), toCell = getCellByIndex(x2, y2);
		
		Piece playerPiece = fromCell.getPiece();
		
		if(playerPiece == null || playerPiece.getPlayer() != players[playerTurn]) {
			throw new IllegalMoveException("fromCell did not contain the player's piece");
		}
		
		Move theMove = new Move(fromCell, toCell);
		
		result = processors.get(playerPiece.getAbility()).GetMoveResult(cells, theMove);
		
		if(result != null) {
			Rule matched = result.getMatchedRule();
			int id = matched.getId();
			Piece enemyPiece = null;
			Move lastMove = null;
			Cell intermediate = null, rookCell = null;
			
			if(id == VALID_MOVE) {
				enemyPiece = toCell.getPiece();
				toCell.setPiece(playerPiece);	
				fromCell.setPiece(null);
			}
			else if(id == PROMOTION_REQUIRED) {
				enemyPiece = toCell.getPiece();
				toCell.setPiece(playerPiece);	
				fromCell.setPiece(null);
				playerPiece.setAbility(Ability.Queen);
			}
			else if(id == EN_PASSANT) {
				toCell.setPiece(playerPiece);	
				fromCell.setPiece(null);
				lastMove = result.getCapturedPiece().getHistory().get(0);
				lastMove.getToCell().setPiece(null);
			}
			else if(id == CASTLED) {
				isCheck = kingIsInCheck(players[playerTurn]);//Not allowed, if already in check
				
				if(!isCheck) {
					if(x1 < x2) {
						intermediate = getCellByIndex(x2 - 1, y2);
						rookCell = getCellByIndex(1, y2);
					}
					else {
						intermediate = getCellByIndex(x2 + 1, y2);
						rookCell = getCellByIndex(8, y2);
					}
					
					fromCell.setPiece(null);
					intermediate.setPiece(playerPiece);
					
					isCheck = kingIsInCheck(players[playerTurn]);//Not allowed to move through check
					
					toCell.setPiece(playerPiece);
					intermediate.setPiece(rookCell.getPiece());
					rookCell.setPiece(null);
				}
			}
			
			if(!isCheck) {
				isCheck = kingIsInCheck(players[playerTurn]);//Moving into check?
			}
			
			if(isCheck) {
				fromCell.setPiece(playerPiece);
				toCell.setPiece(enemyPiece);
				
				if(id == EN_PASSANT) {
					lastMove.getToCell().setPiece(result.getCapturedPiece());
				}
				else if(id == CASTLED) {
					if(intermediate != null) {
						rookCell.setPiece(intermediate.getPiece());
						intermediate.setPiece(null);
					}
				}
				else if(id == PROMOTION_REQUIRED) {
					playerPiece.setAbility(Ability.Pawn);
				}
				
				result = null;
			}
			else {
				List<Move> history = playerPiece.getHistory();
				
				if(history.size() == 0) {
					processors.get(playerPiece.getAbility()).setPreviousMove(theMove);//only set for first move
				}
				else {
					processors.get(playerPiece.getAbility()).setPreviousMove(null);
				}
				
				history.add(theMove);//Add to piece's history
			}
		}
		
		if(result == null) {
			throw new IllegalMoveException("an illegal move was attempted");
		}
		
		playerTurn ^= 1;//Toggle between 0 and 1
		
		return result;
	}
}
