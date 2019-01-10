package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import javafx.util.Pair;

// Cpu1 is the most basic AI. It simply selects a random move from all possible ones
public class Cpu1 extends Player {

    private Random random;

    public Cpu1(Piece playerPiece, Board board) {
        super(playerPiece, board);
        random = new Random();
    }

    @Override
    public void MoveNeutron() {
        Position posNeutron = board.getNeutron();

        Direction dir = chooseNeutronDirection(posNeutron);

        System.out.println("Player " + playerPiece.getMark() + " moving neutron to " + dir);
        try {
            board.move(posNeutron, Piece.Neutron, dir);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu1 made a wrong move - " + e.getMessage());
        }
    }

    @Override
    public void MovePlayerPiece() {

        Pair<Position, Direction> move = choosePlayerPositionAndDirection();
        Position pos = move.getKey();
        Direction dir = move.getValue();

        System.out.println("Player " + playerPiece.getMark() + " moving piece " + pos + " to " + dir);

        try {
            board.move(pos, playerPiece, dir);
        } catch (InvalidMoveException e) {
            System.out.println("Cpu made a wrong move - " + e.getMessage());
        }
    }

    protected Direction chooseNeutronDirection(Position posNeutron) {
        List<Direction> moves = getPossibleMoves(posNeutron, board);
        return choice(moves);
    }

    protected Pair<Position, Direction> choosePlayerPositionAndDirection() {
        List<Position> positions = getPlayerPositions();
        List<Direction> moves = new ArrayList<>();
        Position pos = new Position(1,1);

        while (moves.isEmpty()) {
            pos = choice(positions);
            moves = getPossibleMoves(pos, board);
        }
        Direction dir = choice(moves);
        return new Pair<>(pos, dir);
    }

    protected List<Direction> getPossibleMoves(Position pos, Board board) {
        List<Direction> moves = new ArrayList<Direction>();

        for (Direction dir : Direction.values()) {
            if (board.canMove(pos, dir)) {
                moves.add(dir);
            }
        }
        return moves;
    }

    List<Position> getPlayerPositions() {
        List<Position> positions = new ArrayList<>();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Position pos = new Position(x, y);
                if (board.hasPiece(pos, playerPiece)) {
                    positions.add(pos);
                }
            }
        }
        return positions;
    }

    protected <T> T choice(List<T> possibleChoices) {
        return possibleChoices.get(random.nextInt(possibleChoices.size()));
    }
}
