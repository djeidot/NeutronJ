package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import javafx.util.Pair;

// Cpu2 will choose a direct winning move if there is one available. However it treats neutron and piece moves separately.
public class Cpu2 extends Cpu1 {

    public Cpu2(Piece playerPiece, Board board) {
        super(playerPiece, board);
    }

    @Override
    protected Direction chooseNeutronDirection(Position posNeutron) {
        List<Direction> moves = getPossibleMoves(posNeutron, board);
        List<Direction> winningMoves = new ArrayList<>();
        for (Direction move : moves) {
            if (canMoveNeutronToOpponentsBackline(move)) {
                winningMoves.add(move);
            }
        }
        if (!winningMoves.isEmpty()) {
            System.out.println("Player " + playerPiece.getMark() + " has a winning move");
            return choice(winningMoves);
        } else {
            return choice(moves);
        }
    }

    protected boolean canMoveNeutronToOpponentsBackline(Direction move) {
        Board vBoard1 = new Board(board);
        try {
            vBoard1.move(vBoard1.getNeutron(), Piece.Neutron, move);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu2 made a wrong move - " + e.getMessage());
        }
        return vBoard1.getNeutronBackLine() == playerPiece.opponent();
    }

    @Override
    protected Pair<Position, Direction> choosePlayerPositionAndDirection() {
        List<Position> positions = getPlayerPositions();
        List<Pair<Position, Direction>> winningMoves = new ArrayList<>();
        List<Pair<Position, Direction>> otherMoves = new ArrayList<>();

        for (Position pos : positions) {
            List<Direction> moves = getPossibleMoves(pos, board);
            for (Direction move : moves) {
                if (canTrapNeutron(pos, move, board)) {
                    winningMoves.add(new Pair<>(pos, move));
                } else {
                    otherMoves.add(new Pair<>(pos, move));
                }
            }
        }

        if (!winningMoves.isEmpty()) {
            System.out.println("Player " + playerPiece.getMark() + " has a winning move");
            return choice(winningMoves);
        } else {
            return choice(otherMoves);
        }
    }

    protected boolean canTrapNeutron(Position pos, Direction move, Board board) {
        Board vBoard1 = new Board(board);
        try {
            vBoard1.move(pos, playerPiece, move);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu2 made a wrong move - " + e.getMessage());
        }

        return vBoard1.isNeutronBlocked();
    }

}
