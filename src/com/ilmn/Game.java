package com.ilmn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ilmn.Enums.Piece;
import com.ilmn.Players.Cpu5;
import com.ilmn.Players.Human;
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
        boolean playerXStarts;
        while (true) {
            System.out.print("Type S to start a new game or J to join a game: ");
            String input = scanner.nextLine();
            input = input.trim();
            if (input.equals("S") || input.equals("s")) {
                playerXStarts = startNewGame();
                break;
            } else if (input.equals("J") || input.equals("j")) {
                playerXStarts = joinGame();
                break;
            } else {
                System.out.println("Game type not recognized. Please input S or J");
            }
        }
        loop(playerXStarts);
    }

    private boolean startNewGame() {
        this.board = new Board();
        
        System.out.print("\nInput player names\nPlayer O: ");
        String playerOName = scanner.nextLine().trim();
        System.out.print("\nPlayer X: ");
        String playerXName = scanner.nextLine().trim();
        
        System.out.println("\nInput the type of player for both players (H - Human, C - CPU, R - Remote)");
        String playerOClass = getPlayerClass("Player O", playerOName);
        String playerXClass = getPlayerClass("Player X", playerXName);

        Piece startingPlayer = Piece.Empty;
        while (startingPlayer == Piece.Empty) {
            System.out.print("\nInput starting player (O or X): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("O")) {
                startingPlayer = Piece.PlayerO;
            } else if (input.equals("X")) {
                startingPlayer = Piece.PlayerX;
            } else {
                System.out.println("\nStarting player not recognized");
            }
        }

        playerO = setNewPlayer(playerOName, playerOClass, Piece.PlayerO, board);
        playerX = setNewPlayer(playerXName, playerXClass, Piece.PlayerX, board); 
        board.setPlayers(this.playerO, this.playerX, startingPlayer);
        board.show();
        setupRemoteGame(null, startingPlayer);
        return startingPlayer == Piece.PlayerX;
    }

    private boolean startExistingGame(GamePojo gamePojo, String playerOClass, String playerXClass) {
        this.board = new Board(gamePojo);
        playerO = setNewPlayer(gamePojo.getPlayerO(), playerOClass, Piece.PlayerO, board);
        playerX = setNewPlayer(gamePojo.getPlayerX(), playerXClass, Piece.PlayerX, board);
        board.setPlayers(playerO, playerX, gamePojo.getStartingPlayer());
        board.setPreviousMoves(gamePojo);
        board.show();
        setupRemoteGame(gamePojo.getId(), gamePojo.getStartingPlayer());
        return gamePojo.getMove().equals(Piece.PlayerX);
    }

    private Player setNewPlayer(String playerName, String playerClass, Piece playerPiece, Board board) {
        switch (playerClass) {
            case "H":
                return new Human(playerName, playerPiece, board);
            case "C":
                return new Cpu5(playerName, playerPiece, board);
            case "R":
                return new Remote(playerName, playerPiece, board);
            default:
                throw new IndexOutOfBoundsException("Wrong player class");
        }
    }

    private boolean joinGame() {
        GamesPojo gamesPojo = api.getGames();
        List<GamePojo> validGames = new ArrayList<>();
        
        System.out.println("Here's a list of games to join:");
        System.out.println("Game ID         | Player O name | Player X name | Player turn");
        for (GamePojo gamePojo : gamesPojo.getGames().values()) {
            if (gamePojo.getWinner() == null) {
                validGames.add(gamePojo);
                System.out.println(gamePojo.getId() + " | "
                        + center(gamePojo.getPlayerO(), 13, false) + " | "
                        + center(gamePojo.getPlayerX(), 13, false) + " |      "
                        + gamePojo.getMove().getMark());
            }
        }

        while (true) {
            System.out.print("\nInput the last " + getMinimumGameIdDigits(validGames) + " digits of the game ID: ");
            String input = scanner.nextLine().trim();
            for (GamePojo gamePojo : validGames) {
                if (gamePojo.getId().endsWith(input)) {

                    System.out.println("\nInput the type of player for both players (H - Human, C - CPU, R - Remote)");
                    String playerOClass = getPlayerClass("Player O", gamePojo.getPlayerO());
                    String playerXClass = getPlayerClass("Player X", gamePojo.getPlayerX());
                    return startExistingGame(gamePojo, playerOClass, playerXClass);
                }
            }

            System.out.println("Game with ID ending with " + input + " not identified.");
        }
    }

    private String getPlayerClass(String playerMark, String playerName) {
        while (true) {
            System.out.print("Player " + playerMark + " (" + playerName + "): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("H") || input.equals("C") || input.equals("R")) {
                return input;
            }

            System.out.println("Type of player not recognized. Please input H, C or R");
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

    private void loop(boolean playerXStarts) {

        boolean gameEnd = false;
        int round = 1;
        while (!gameEnd) {

            System.out.println("Round " + round);
            if (playerXStarts) {
                gameEnd = playerTurn(playerX)
                        || playerTurn(playerO);
            } else {
                gameEnd = playerTurn(playerO)
                        || playerTurn(playerX);
            }
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

    private void setupRemoteGame(String gameId, Piece startingPlayer) {
        if (gameId == null) {
            GameStartPojo gameStartPojo = new GameStartPojo(playerO.getName(), playerX.getName(), startingPlayer.getMark());
            gameId = api.startGame(gameStartPojo);
        }
        board.setApiGame(api, gameId);
        playerO.setApiGame(api, gameId);
        playerX.setApiGame(api, gameId);
    }
}
