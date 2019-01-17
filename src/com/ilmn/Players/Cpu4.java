package com.ilmn.Players;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Board;
import com.ilmn.Enums.Piece;
import com.ilmn.Exceptions.InvalidMoveException;

public class Cpu4 extends Cpu3 {
    
    // Cpu4 will try to anticipate winning moves by the opponent player so that it can identify
    // and avoid losing moves by the current player
    public Cpu4(Piece playerPiece, Board board) {
        super(playerPiece, board);
    }

    @Override
    protected List<PlayerMove> getPlayerMoves(Piece playerPiece, Board board) {
        // Returns a list of possible moves
        // If there is a list of winning moves only that list is returned
        // Otherwise it returns a list of normal moves
        // Normal moves are differentiated from losing moves so that losing moves are avoided

        // Winning and other moves are gotten from the Cpu3 version of this method so we can call it now
        List<PlayerMove> startingMoves = super.getPlayerMoves(playerPiece, board);

        if (startingMoves.get(0).isWinningMove()) {
            return startingMoves;
        }
        
        // Lets pick the other moves and determine if they are losing moves
        List<PlayerMove> losingMoves = new ArrayList<>();
        List<PlayerMove> otherMoves = new ArrayList<>();
        
        for (PlayerMove playerMove : startingMoves) {
            Board vBoard4 = new Board(board);
            try {
                vBoard4.move(vBoard4.getNeutron(), Piece.Neutron, playerMove.getNeutronMove());
                vBoard4.move(playerMove.getPieceMove().getKey(), playerPiece, playerMove.getPieceMove().getValue());
            } catch (InvalidMoveException e) {
                System.out.println("Cpu4 made a wrong move - " + e.getMessage());
            }

            List<PlayerMove> opponentMoves = super.getPlayerMoves(playerPiece.opponent(), vBoard4);
            if (opponentMoves.get(0).isWinningMove()) {
                losingMoves.add(playerMove);
            } else {
                otherMoves.add(playerMove);
            }
        }

        if (otherMoves.isEmpty()) {
            if (!board.isInvisible()) {
                System.out.println("Player " + playerPiece.getMark() + " forced to make a losing move");
            }
            return losingMoves;
        }
        
        if (!losingMoves.isEmpty()) {
            if (!board.isInvisible()) {
                System.out.println("Player " + playerPiece.getMark() + " is avoiding " + losingMoves.size() + " losing moves");
            }
        }
        
        return otherMoves;
    }
}