package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.MoveType;
import com.ilmn.Enums.Piece;
import com.ilmn.Exceptions.InvalidMoveException;
import com.ilmn.PlayerMove;

public class Cpu4 extends Cpu3 {

    // Cpu4 will try to anticipate winning moves by the opponent player so that it can identify
    // and avoid losing moves by the current player
    public Cpu4(String name, Piece playerPiece, Board board) {
        super(name, playerPiece, board);
    }

    @Override
    protected List<PlayerMove> getPlayerMoves(Piece playerPiece, Board board) {
        // Returns a list of possible moves
        // If there is a list of winning moves only that list is returned
        // Otherwise it returns a list of normal moves
        // Losing moves are avoided unless there is no other choice

        // Winning and other moves are gotten from the Cpu3 version of this method so we can call it now
        List<PlayerMove> startingMoves = super.getPlayerMoves(playerPiece, board);

        if (startingMoves.get(0).getMoveType() == MoveType.winning || startingMoves.get(0).getMoveType() == MoveType.losing) {
            return startingMoves;
        }

        // Lets pick the other moves and determine if they are losing or winning moves
        List<PlayerMove> losingMoves = new ArrayList<>();
        List<PlayerMove> otherMoves = new ArrayList<>();
        List<PlayerMove> winningMoves = new ArrayList<>();

        for (PlayerMove playerMove : startingMoves) {
            Board vBoard4 = new Board(board);
            try {
                vBoard4.move(this, vBoard4.getNeutron(), Piece.Neutron, playerMove.getNeutronMove());
                vBoard4.move(this, playerMove.getPieceMove().getKey(), playerPiece, playerMove.getPieceMove().getValue());
            } catch (InvalidMoveException e) {
                System.out.println("Cpu4 made a wrong move - " + e.getMessage());
            }

            List<PlayerMove> opponentMoves = super.getPlayerMoves(playerPiece.opponent(), vBoard4);
            if (opponentMoves.get(0).getMoveType() == MoveType.winning) {
                playerMove.setMoveType(MoveType.losing);
                losingMoves.add(playerMove);
            } else if (opponentMoves.get(0).getMoveType() == MoveType.losing) {
                playerMove.setMoveType(MoveType.winning);
                winningMoves.add(playerMove);
            } else {
                playerMove.setMoveType(MoveType.other);
                otherMoves.add(playerMove);
            }
        }

        if (!winningMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " has a winning move");
            return winningMoves;
        }

        if (otherMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " forced to make a losing move");
            return losingMoves;
        }

        if (!losingMoves.isEmpty()) {
            board.println("Player " + playerPiece.getMark() + " is avoiding " + losingMoves.size() + " losing moves");
        }

        return otherMoves;
    }
}
