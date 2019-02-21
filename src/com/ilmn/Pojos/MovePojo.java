package com.ilmn.Pojos;

import javax.json.JsonObject;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Position;

public class MovePojo {
    private Direction neutronDirn;
    private Position piece;
    private Direction pieceDirn;

    public MovePojo() {
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

    public static MovePojo deserialize(JsonObject jsonMove) {
        MovePojo movePojo = new MovePojo();
        movePojo.setNeutronDirn(Direction.valueOf(jsonMove.getString("neutrondirn")));
        movePojo.setPiece(new Position(jsonMove.getString("piece")));
        movePojo.setPieceDirn(Direction.valueOf(jsonMove.getString("piecedirn")));
        return movePojo;
    }

    @Override
    public String toString() {
        return "\nMovePojo{" +
                "neutronDirn=" + neutronDirn +
                ", piece=" + piece +
                ", pieceDirn=" + pieceDirn +
                "}";
    }
}
