package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import javafx.util.Pair;

public class Cpu3 extends Cpu2 {

    protected class PlayerMove {
        private Direction neutronMove;
        private Pair<Position, Direction> pieceMove;
        private boolean winningMove;

        public PlayerMove(Direction neutronMove, Pair<Position, Direction> pieceMove, boolean isWinningMove) {
            this.neutronMove = neutronMove;
            this.pieceMove = pieceMove;
            this.winningMove = isWinningMove;
        }

        public PlayerMove(Direction neutronMove, Position piecePosition, Direction pieceDirection, boolean isWinningMove) {
            this.neutronMove = neutronMove;
            this.pieceMove = new Pair<>(piecePosition, pieceDirection);
            this.winningMove = isWinningMove;
        }

        public Direction getNeutronMove() {
            return neutronMove;
        }

        public Pair<Position, Direction> getPieceMove() {
            return pieceMove;
        }

        public boolean isWinningMove() {
            return winningMove;
        }
    }

    PlayerMove playerMove;

    // Cpu3 considers both neutron and player piece moves to determine if there is a winning outcome
    public Cpu3(Piece playerPiece, Board board) {
        super(playerPiece, board);
    }

    @Override
    protected Direction chooseNeutronDirection() {
        this.playerMove = choice(getPlayerMoves(this.playerPiece, this.board));
        return this.playerMove.getNeutronMove();
    }

    @Override
    protected Pair<Position, Direction> choosePlayerPositionAndDirection() {
        return this.playerMove.getPieceMove();
    }

    protected List<PlayerMove> getPlayerMoves(Piece playerPiece, Board board) {
        // Returns a list of possible moves
        // If there is a list of winning moves only that list is returned
        // Otherwise it returns a list of normal moves

        List<Direction> neutronMoves = getPossibleMoves(board.getNeutron(), board);
        List<PlayerMove> winningNeutronMoves = new ArrayList<>();
        for (Direction move : neutronMoves) {
            if (canMoveNeutronToOpponentsBackline(move)) {
                winningNeutronMoves.add(new PlayerMove(move, null, true));
            }
        }
        if (!winningNeutronMoves.isEmpty()) {
            if (!board.isInvisible()) {
                System.out.println("Player " + playerPiece.getMark() + " has a winning move");
            }
            return winningNeutronMoves;
        }

        List<PlayerMove> winningPieceMoves = new ArrayList<>();
        List<PlayerMove> otherPieceMoves = new ArrayList<>();
        for (Direction neutronMove : neutronMoves) {
            Board vBoard3 = new Board(board);
            try {
                vBoard3.move(vBoard3.getNeutron(), Piece.Neutron, neutronMove);
            } catch (InvalidMoveException e) {
                System.out.println("Cpu3 made a wrong move - " + e.getMessage());
            }
            List<Position> positions = getPlayerPositions(vBoard3, playerPiece);

            for (Position pos : positions) {
                List<Direction> moves = getPossibleMoves(pos, vBoard3);
                for (Direction move : moves) {
                    if (canTrapNeutron(pos, move, playerPiece, vBoard3)) {
                        winningPieceMoves.add(new PlayerMove(neutronMove, pos, move, true));
                    } else {
                        otherPieceMoves.add(new PlayerMove(neutronMove, pos, move, false));
                    }
                }
            }
        }
        if (!winningPieceMoves.isEmpty()) {
            if (!board.isInvisible()) {
                System.out.println("Player " + playerPiece.getMark() + " has a winning move");
            }
            return winningPieceMoves;
        } else {
            return otherPieceMoves;
        }
    }
}
