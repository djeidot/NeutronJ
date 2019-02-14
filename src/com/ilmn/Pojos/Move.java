package com.ilmn.Pojos;

import javax.json.JsonObject;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Position;

public class Move {
    private Direction neutronDirn;
    private Position piece;
    private Direction pieceDirn;

    public Move() {
    }

    public Direction getNeutronDirn() {
        return neutronDirn;
    }

    public void setNeutronDirn(Direction neutronDirn) {
        this.neutronDirn = neutronDirn;
    }

    public Position getPiece() {
        return piece;
    }

    public void setPiece(Position piece) {
        this.piece = piece;
    }

    public Direction getPieceDirn() {
        return pieceDirn;
    }

    public void setPieceDirn(Direction pieceDirn) {
        this.pieceDirn = pieceDirn;
    }

    public static Move deserialize(JsonObject jsonMove) {
        Move move = new Move();
        move.setNeutronDirn(Direction.valueOf(jsonMove.getString("neutrondirn")));
        move.setPiece(new Position(jsonMove.getString("piece")));
        move.setPieceDirn(Direction.valueOf(jsonMove.getString("piecedirn")));
        return move;
    }
}
