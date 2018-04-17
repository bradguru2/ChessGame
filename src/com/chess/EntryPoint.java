package com.chess;

import com.chess.console.LinuxConsoleController;

/**
 * This is where the Chess application gets kicked off
 * @author bradley
 *
 */
public class EntryPoint {

	public static void main(String[] args) {
		Application app = new LinuxConsoleController();
		app.initialize();
		app.execute();
	}

}
