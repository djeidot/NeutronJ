package com.ilmn.Players;

import com.ilmn.Api;
import com.ilmn.Board;
import com.ilmn.Enums.Piece;

public abstract class Player {
    String name;
    Piece playerPiece;
    Board board;

    public Player(String name, Piece playerPiece, Board board) {
        this.name = name;
        this.playerPiece = playerPiece;
        this.board = board;
    }

    public String getName() {
        return name;
    }
    
    public Piece getPlayerPiece() {
        return playerPiece;
    }

    public abstract void MoveNeutron();
    public abstract void MovePlayerPiece();

    public void setApiGame(Api api, String gameId) {}
}
