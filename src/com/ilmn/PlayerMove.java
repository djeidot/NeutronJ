package com.ilmn;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.MoveType;
import com.ilmn.Enums.Position;
import com.ilmn.Players.Player;
import javafx.util.Pair;

public class PlayerMove {

    private Player player;
    private Direction neutronMove;
    private Pair<Position, Direction> pieceMove;
    private MoveType moveType;
    private int neutronMovesAfter;

    public PlayerMove(Player player, Direction neutronMove, Pair<Position, Direction> pieceMove, MoveType moveType) {
        this.player = player;
        this.neutronMove = neutronMove;
        this.pieceMove = pieceMove;
        this.moveType = moveType;
    }

    public PlayerMove(Player player, Direction neutronMove, Position piecePosition, Direction pieceDirection, MoveType moveType) {
        this.player = player;
        this.neutronMove = neutronMove;
        this.pieceMove = new Pair<>(piecePosition, pieceDirection);
        this.moveType = moveType;
    }

    public PlayerMove(Player player, Direction neutronMove) {
        this.player = player;
        this.neutronMove = neutronMove;
        this.pieceMove = null;
        this.moveType = null;
    }

    public void setPieceMove(Player player, Position piecePosition, Direction pieceDirection) {
        assert (this.player == player);
        this.pieceMove = new Pair<>(piecePosition, pieceDirection);
    }

    public Direction getNeutronMove() {
        return neutronMove;
    }

    public Pair<Position, Direction> getPieceMove() {
        return pieceMove;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setNeutronMovesAfter(int neutronMovesAfter) {
        this.neutronMovesAfter = neutronMovesAfter;
    }

    public int getNeutronMovesAfter() {
        return this.neutronMovesAfter;
    }

    public Player getPlayer() {
        return player;
    }

}
