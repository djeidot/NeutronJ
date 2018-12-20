package com.ilmn.Players;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import javafx.util.Pair;

public class Cpu2 extends Cpu1 {

    public Cpu2(Piece playerPiece, Board board) {
        super(playerPiece, board);
    }

    @Override
    protected Pair<Position, Direction> choosePlayerPositionAndDirection() {
        return super.choosePlayerPositionAndDirection();
    }

    @Override
    protected Direction chooseNeutronDirection(Position posNeutron) {
        return super.chooseNeutronDirection(posNeutron);
    }
}
