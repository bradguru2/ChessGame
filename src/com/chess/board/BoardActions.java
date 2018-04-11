package com.chess.board;

import java.util.List;

public interface BoardActions {
	List<Cell> getOrderedCells();
	boolean kingIsInCheck(String checkPlayer) throws IllegalArgumentException;
	boolean isCheckMate(String checkPlayer) throws IllegalArgumentException;
	boolean makeMove(Move theMove);
}
