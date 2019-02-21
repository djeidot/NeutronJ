package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Piece;
import com.ilmn.Exceptions.InvalidMoveException;

public class Cpu5 extends Cpu4 {

    // In addition to the other cpus, Cpu5 will choose the normal move that traps the
    // neutron the most
    public Cpu5(Piece playerPiece, Board board) {
        super(playerPiece, board);
    }

    @Override
    protected List<PlayerMove> getPlayerMoves(Piece playerPiece, Board board) {
        // Returns a list of possible moves
        // If there is a list of winning moves only that list is returned
        // Otherwise it returns a list of normal moves
        // Losing moves are avoided unless there is no other choice

        // Winning and other moves are gotten from the Cpu4 version of this method so we can call it now
        List<PlayerMove> startingMoves = super.getPlayerMoves(playerPiece, board);

        if (startingMoves.get(0).getMoveType() == MoveType.winning || startingMoves.get(0).getMoveType() == MoveType.losing) {
            return startingMoves;
        }

        int minimumMoves = 9;

        for (PlayerMove playerMove : startingMoves) {
            Board vBoard5 = new Board(board);
            try {
                vBoard5.move(vBoard5.getNeutron(), Piece.Neutron, playerMove.getNeutronMove());
                vBoard5.move(playerMove.getPieceMove().getKey(), playerPiece, playerMove.getPieceMove().getValue());
            } catch (InvalidMoveException e) {
                System.out.println("Cpu5 made a wrong move - " + e.getMessage());
            }

            int neutronMoves = getPossibleMoves(vBoard5.getNeutron(), vBoard5).size();
            playerMove.setNeutronMovesAfter(neutronMoves);
            if (neutronMoves < minimumMoves) {
                minimumMoves = neutronMoves;
            }
        }

        List<PlayerMove> bestMoves = new ArrayList<>();
        for (PlayerMove playerMove : startingMoves) {
            if (playerMove.getNeutronMovesAfter() == minimumMoves) {
                bestMoves.add(playerMove);
            }
        }

        if (bestMoves.isEmpty()) {
            throw new RuntimeException("Cpu5 miscalculated its moves");
        }

        board.println("Player " + playerPiece.getMark() + " choosing between " + bestMoves.size() + " out of " + startingMoves.size() + " regular moves");
        return bestMoves;
    }
}
