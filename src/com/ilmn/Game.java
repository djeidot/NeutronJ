package com.ilmn;

import com.ilmn.Enums.Piece;
import com.ilmn.Players.Cpu5;
import com.ilmn.Players.Player;
import com.ilmn.Pojos.GameStartPojo;

public class Game {

    private Board board;
    private Player playerO;
    private Player playerX;
    private Api api;
    private String gameId;

    public Game(Api api) {
        this.board = new Board();
        this.api = api;
        this.playerO = new Cpu5("Cpu5O", Piece.PlayerO, board);
        this.playerX = new Cpu5("Cpu5X", Piece.PlayerX, board);
        board.setPlayers(this.playerO, this.playerX);
        board.show();
        setupRemoteGame();
        loop();
    }

    private void loop() {

        boolean gameEnd = false;
        int round = 1;
        while (!gameEnd) {

            System.out.println("Round " + round);
            gameEnd = playerTurn(playerO)
                    || playerTurn(playerX);
            round++;
        }
        System.out.println("Game Over");
    }

    private boolean playerTurn(Player player) {

        player.MoveNeutron();
        if (checkNeutronInBackLine()) {
            return true;
        }
        player.MovePlayerPiece();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CheckNeutronBlocked(player.getPlayerPiece());
    }

    // Checks if the neutron is in one of the back lines.
    // The player whose back line the neutron is on loses
    private boolean checkNeutronInBackLine() {
        Piece loser = board.getNeutronBackLine();
        if (loser == Piece.PlayerX || loser == Piece.PlayerO) {
            Piece winner = loser.opponent();
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
            Piece loser = player.opponent();
            System.out.println("Player " + loser.getMark() + " cannot move the neutron.");
            System.out.println("Player " + player.getMark() + " wins!!!");
            return true;
        }
        return false;
    }
    
    private void setupRemoteGame() {
        GameStartPojo gameStartPojo = new GameStartPojo(playerO.getName(), playerX.getName(), Piece.PlayerO.getMark());
        gameId = api.startGame(gameStartPojo);
        board.setApiGame(api, gameId);
    }
}
