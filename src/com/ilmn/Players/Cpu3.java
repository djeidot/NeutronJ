package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.MoveType;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import com.ilmn.PlayerMove;
import javafx.util.Pair;

public class Cpu3 extends Cpu2 {

    PlayerMove playerMove;

    // Cpu3 considers both neutron and player piece moves to determine if there is a winning outcome
    public Cpu3(String name, Piece playerPiece, Board board) {
        super(name, playerPiece, board);
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
        // Losing moves are avoided unless there is no other choice

        List<Direction> neutronMoves = getPossibleMoves(board.getNeutron(), board);
        List<PlayerMove> winningNeutronMoves = new ArrayList<>();
        List<PlayerMove> losingNeutronMoves = new ArrayList<>();
        List<PlayerMove> otherNeutronMoves = new ArrayList<>();
        for (Direction move : neutronMoves) {
            if (canMoveNeutronToPlayersBackline(board, playerPiece.opponent(), move)) {
                winningNeutronMoves.add(new PlayerMove(this, move, null, MoveType.winning));
            } else if (canMoveNeutronToPlayersBackline(board, playerPiece, move)) {
                losingNeutronMoves.add(new PlayerMove(this, move, null, MoveType.losing));
            } else {
                otherNeutronMoves.add(new PlayerMove(this, move, null, MoveType.other));
            }
        }
        if (!winningNeutronMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " has a winning move");
            return winningNeutronMoves;
        } else if (otherNeutronMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " forced to make a losing move");
            return losingNeutronMoves;
        }

        List<PlayerMove> winningPieceMoves = new ArrayList<>();
        List<PlayerMove> otherPieceMoves = new ArrayList<>();
        for (PlayerMove neutronMove : otherNeutronMoves) {
            Board vBoard3 = new Board(board);
            try {
                vBoard3.move(this, vBoard3.getNeutron(), Piece.Neutron, neutronMove.getNeutronMove());
            } catch (InvalidMoveException e) {
                System.out.println("Cpu3 made a wrong move - " + e.getMessage());
            }
            List<Position> positions = getPlayerPositions(vBoard3, playerPiece);

            for (Position pos : positions) {
                List<Direction> moves = getPossibleMoves(pos, vBoard3);
                for (Direction move : moves) {
                    if (canTrapNeutron(pos, move, playerPiece, vBoard3)) {
                        winningPieceMoves.add(new PlayerMove(this, neutronMove.getNeutronMove(), pos, move, MoveType.winning));
                    } else {
                        otherPieceMoves.add(new PlayerMove(this, neutronMove.getNeutronMove(), pos, move, MoveType.other));
                    }
                }
            }
        }
        if (!winningPieceMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " has a winning move");
            return winningPieceMoves;
        } else {
            return otherPieceMoves;
        }
    }
}
