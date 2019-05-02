package com.ilmn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ilmn.Enums.Piece;
import com.ilmn.Players.Cpu5;
import com.ilmn.Players.Player;
import com.ilmn.Players.Remote;
import com.ilmn.Pojos.GamePojo;
import com.ilmn.Pojos.GameStartPojo;
import com.ilmn.Pojos.GamesPojo;

import static com.ilmn.Format.center;

public class Game {

    private Board board;
    private Player playerO;
    private Player playerX;
    private Api api;
    private String gameId;
    private Scanner scanner;

    public Game(Api api) {

        this.api = api;
        this.scanner = new Scanner(System.in);
        System.out.print("Type S to start a new game or J to join a game: ");
        String input = scanner.nextLine();
        input = input.trim();
        if (input.equals("S") || input.equals("s")) {
            startNewGame();
        } else if (input.equals("J") || input.equals("j")) {
            joinGame();
        }
        loop();
    }
    
    private void startNewGame() {
        this.board = new Board();
        playerO = new Cpu5("Cpu5", Piece.PlayerO, board);
        playerX = new Remote("Cpu5X", Piece.PlayerX, board);
        board.setPlayers(this.playerO, this.playerX);
        board.show();
        setupRemoteGame();
    }
    
    private void startExistingGame(GamePojo gamePojo) {
        this.board = new Board(gamePojo);
    //    playerO = 
    }
    
    private void joinGame() {
        GamesPojo gamesPojo = api.getGames();
        List<GamePojo> validGames = new ArrayList<>();
        System.out.println("Here's a list of games to join:");
        System.out.println("Game ID         | Player O name | Player X name | Player turn");
        for (GamePojo gamePojo : gamesPojo.getGames().values()) {
            if (gamePojo.getWinner() != "null" && gamePojo.getHistory().size() <= 1) {
                validGames.add(gamePojo);
                System.out.println(gamePojo.getId() + " | "
                    + center(gamePojo.getPlayerO(), 13, false) + " | "
                    + center(gamePojo.getPlayerX(), 13, false) + " |      "
                    + gamePojo.getMove().getMark());
            }
        }

        while (true) {
            System.out.println("\nInput the last " + getMinimumGameIdDigits(validGames) + " digits of the game ID");
            String input = scanner.nextLine().trim();
            for (GamePojo gamePojo : validGames) {
                if (gamePojo.getId().endsWith(input)) {

                    Player playerO, playerX;
                    
                    System.out.println("\nInput the type of player for both players (H - Human, C - CPU, R - Remote)");
                    //setPlayer(playerO, "Player O", gamePojo.getPlayerO());
                    //setPlayer(playerX, "Player X", gamePojo.getPlayerX());
                    //startExistingGame(gamePojo);
                    return;
                }
            }

            System.out.println("Game with ID ending with " + input + " not identified.");
        }
    }

    private void setPlayer(Player player, String playerMark, String playerName) {
        while (true) {
            
        }
    }

    private String getMinimumGameIdDigits(List<GamePojo> games) {
        
        Integer digits = 3;
        Set<String> ids;
        
        while (true) {
            ids = new HashSet<>();
            boolean hasDuplicates = false;

            for (GamePojo gamePojo : games) {
                String id = gamePojo.getId().substring(gamePojo.getId().length() - digits);
                if (ids.contains(id)) {
                    hasDuplicates = true;
                    digits++;
                    break;
                }
                ids.add(id);
            }

            if (!hasDuplicates) {
                return digits.toString();
            }
        }
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
        playerO.setApiGame(api, gameId);
        playerX.setApiGame(api, gameId);
    }
}
