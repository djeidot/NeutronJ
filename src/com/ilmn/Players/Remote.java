package com.ilmn.Players;

import com.ilmn.Api;
import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;
import com.ilmn.Pojos.GamePojo;
import com.ilmn.Pojos.MovePojo;
import javafx.util.Pair;

public class Remote extends Player {
    
    private Api api;
    private String gameId;
    
    private MovePojo lastMove;

    public Remote(String name, Piece playerPiece, Board board) {
        super(name, playerPiece, board);
    }


    @Override
    public void MoveNeutron() {
        waitForMove();
        try {
            board.move(this, board.getNeutron(), Piece.Neutron, lastMove.getNeutronDirn());
        } catch (InvalidMoveException e) {
            System.out.println("Remote player made a wrong move - " + e.getMessage());
        }        
    }

    @Override
    public void MovePlayerPiece() {
        try {
            board.move(this, lastMove.getPiece(), playerPiece, lastMove.getPieceDirn());
        } catch (InvalidMoveException e) {
            System.out.println("Remote player made a wrong move - " + e.getMessage());
        }
    }

    @Override
    public void setApiGame(Api api, String gameId) {
        super.setApiGame(api, gameId);
        this.api = api;
        this.gameId = gameId;
    }

    protected void waitForMove(){
        System.out.println("Waiting for remote player " + name + "'s move...");
        while (true) {
            GamePojo gamePojo = api.getGame(gameId);
            if (gamePojo.getMove() == playerPiece.opponent()) {
                lastMove = gamePojo.getHistory().get(gamePojo.getHistory().size() - 1);
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupting. Aborting.");
                return;
            }
        }
    }
}
