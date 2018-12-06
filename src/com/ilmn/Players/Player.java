package com.ilmn.Players;

import com.ilmn.Board;
import com.ilmn.Enums.Piece;

public abstract class Player {
    Piece playerPiece;
    Board board;

    public Player(Piece playerPiece, Board board) {
        this.playerPiece = playerPiece;
        this.board = board;
    }

    public Piece getPlayerPiece() {
        return playerPiece;
    }

    public abstract void MoveNeutron();
    public abstract void MovePlayerPiece();
}
