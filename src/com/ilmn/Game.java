package com.ilmn;

import com.ilmn.Enums.Piece;
import com.ilmn.Players.Human;
import com.ilmn.Players.Player;

public class Game {

    private Board board;
    private Player player0;
    private Player playerX;

    public Game(Board board) {
        this.board = board;
        this.player0 = new Human(Piece.PlayerO, board);
        this.playerX = new Human(Piece.PlayerX, board);
        loop();
    }

    private void loop() {

        boolean gameEnd = false;
        while (!gameEnd) {

            gameEnd = PlayerMove(player0)
                    || PlayerMove(playerX);
        }
        System.out.println("Game Over");
    }

    private boolean PlayerMove(Player player) {

        player.MoveNeutron();
        if (checkNeutronInBackLine()) {
            return true;
        }
        player.MovePlayerPiece();
        return CheckNeutronBlocked(player.getPlayerPiece());
    }

    // Checks if the neutron is in one of the back lines.
    // The player whose back line the neutron is on loses
    private boolean checkNeutronInBackLine() {
        Piece loser = board.getNeutronBackLine();
        if (loser == Piece.PlayerX || loser == Piece.PlayerO) {
            Piece winner = (loser == Piece.PlayerO ? Piece.PlayerX : Piece.PlayerO);
            System.out.println("Neutron is on player " + loser.getMark() + "'s back line.");
            System.out.println("Player " + winner.getMark() + " wins!!!");
            return true;
        }
        return false;
    }

    // Checks if the neutron is blocked
    // The player to have last played wins
    private boolean CheckNeutronBlocked(Piece player) {
        if (board.isNeutronBlocked()) {
            Piece loser = (player == Piece.PlayerO ? Piece.PlayerX : Piece.PlayerO);
            System.out.println("Player " + loser.getMark() + " cannot move the neutron.");
            System.out.println("Player " + player.getMark() + " wins!!!");
            return true;
        }
        return false;
    }
}
