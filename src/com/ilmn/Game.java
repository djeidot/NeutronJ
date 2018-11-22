package com.ilmn;

import java.io.IOException;

public class Game {

    private Board board;

    public Game(Board board) {
        this.board = board;
        board.tryMove(new Position(2,2), Piece.Neutron, Direction.S);
        board.show();
        board.tryMove(new Position(2, 3), Piece.Neutron, Direction.S);
        //loop();
    }

    private void loop() {

        boolean gameEnd = false;
        while (!gameEnd) {


        }
    }
}
