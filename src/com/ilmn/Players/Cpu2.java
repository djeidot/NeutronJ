package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import javafx.util.Pair;

// Cpu2 will choose a direct winning move if there is one available and avoids a losing move unless it's the only choice. 
// However it treats neutron and piece moves separately.
public class Cpu2 extends Cpu1 {

    public Cpu2(String name, Piece playerPiece, Board board) {
        super(name, playerPiece, board);
    }

    @Override
    protected Direction chooseNeutronDirection() {
        List<Direction> moves = getPossibleMoves(board.getNeutron(), board);
        List<Direction> winningMoves = new ArrayList<>();
        List<Direction> losingMoves = new ArrayList<>();
        List<Direction> otherMoves = new ArrayList<>();
        for (Direction move : moves) {
            if (canMoveNeutronToPlayersBackline(board, playerPiece.opponent(), move)) {
                winningMoves.add(move);
            } else if (canMoveNeutronToPlayersBackline(board, playerPiece, move)) {
                losingMoves.add(move);
            } else {
                otherMoves.add(move);
            }
        }
        if (!winningMoves.isEmpty()) {
            System.out.println("Player " + playerPiece.getMark() + " has a winning move");
            return choice(winningMoves);
        } else if (!otherMoves.isEmpty()) {
            return choice(otherMoves);
        } else if (!losingMoves.isEmpty()) {
            System.out.println("Player " + playerPiece.getMark() + " forced to make a losing move");
            return choice(losingMoves);
        } else {
            throw new RuntimeException("No possible neutron moves - game should have been finished by now.");
        }
    }

    protected boolean canMoveNeutronToPlayersBackline(Board board, Piece playerPiece, Direction move) {
        Board vBoard2 = new Board(board);
        try {
            vBoard2.move(this, vBoard2.getNeutron(), Piece.Neutron, move);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu2 made a wrong move - " + e.getMessage());
        }
        return vBoard2.getNeutronBackLine() == playerPiece;
    }

    @Override
    protected Pair<Position, Direction> choosePlayerPositionAndDirection() {
        List<Position> positions = getPlayerPositions(this.board, this.playerPiece);
        List<Pair<Position, Direction>> winningMoves = new ArrayList<>();
        List<Pair<Position, Direction>> otherMoves = new ArrayList<>();

        for (Position pos : positions) {
            List<Direction> moves = getPossibleMoves(pos, board);
            for (Direction move : moves) {
                if (canTrapNeutron(pos, move, this.playerPiece, board)) {
                    winningMoves.add(new Pair<>(pos, move));
                } else {
                    otherMoves.add(new Pair<>(pos, move));
                }
            }
        }

        if (!winningMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " has a winning move");
            return choice(winningMoves);
        } else {
            return choice(otherMoves);
        }
    }

    protected boolean canTrapNeutron(Position pos, Direction move, Piece playerPiece, Board board) {
        Board vBoard2 = new Board(board);
        try {
            vBoard2.move(this, pos, playerPiece, move);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu2 made a wrong move - " + e.getMessage());
        }

        return vBoard2.isNeutronBlocked();
    }

}
