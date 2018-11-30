package com.ilmn;

import java.util.Scanner;

public class Game {

    private Board board;
    private Scanner scanner;

    public Game(Board board) {
        this.scanner = new Scanner(System.in);
        this.board = board;
        loop();
    }

    private void loop() {

        boolean gameEnd = false;
        while (!gameEnd) {

            gameEnd = CheckEndPlayerO();
            PlayerOMove();

            if (!gameEnd) {
                gameEnd = CheckEndPlayerX();
                PlayerXMove();
            }

        }
    }

    private void PlayerOMove() {

        MoveNeutron(Piece.PlayerO);
        MovePlayerPiece(Piece.PlayerO);
    }

    private void PlayerXMove() {

        MoveNeutron(Piece.PlayerX);
        MovePlayerPiece(Piece.PlayerX);
    }

    private void MoveNeutron(Piece player) {
        System.out.print("Player " + player.getMark() + " move neutron (direction only, e.g. 'NE'): ");
        String input = scanner.nextLine();

        try {
            Direction dir = Direction.valueOf(input);
            board.move(board.getNeutron(), Piece.Neutron, dir);


        } catch (EnumConstantNotPresentException ex) {
            System.out.print(input + " is not a valid direction\n");
            MoveNeutron(player);
        } catch (InvalidMoveException ex) {
            System.out.print(ex.getMessage());
            MoveNeutron(player);
        }
    }

    private void MovePlayerPiece(Piece player) {
        System.out.print("Player " + player.getMark() + " move piece (position + direction, e.g. 'A1 NE'): ");
        String input = scanner.nextLine();

        try {
            Position pos = new Position(input.substring(0, 2));
            Direction dir = Direction.valueOf(input.substring(2).trim());
            board.move(pos, player, dir);


        } catch (EnumConstantNotPresentException ex) {
            System.out.println(input + " is not a valid direction");
            MovePlayerPiece(player);
        } catch (InvalidMoveException | IndexOutOfBoundsException ex) {
            System.out.print(ex.getMessage());
            MovePlayerPiece(player);
        }
    }

    private boolean CheckEndPlayerX() {
        return false;
    }

    private boolean CheckEndPlayerO() {
        return false;
    }


}
