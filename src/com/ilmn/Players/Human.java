package com.ilmn.Players;

import java.util.Scanner;

import com.ilmn.Board;
import com.ilmn.Enums.Direction;
import com.ilmn.Exceptions.InvalidMoveException;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;

public class Human extends Player {

    private Scanner scanner;

    public Human(Piece player, Board board) {
        super(player, board);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void MoveNeutron() {
        System.out.print("Player " + playerPiece.getMark() + " move neutron (direction only, e.g. 'NE'): ");
        String input = scanner.nextLine();

        try {
            Direction dir = Direction.valueOf(input);
            board.move(board.getNeutron(), Piece.Neutron, dir);


        } catch (IllegalArgumentException ex) {
            System.out.println(input + " is not a valid direction");
            MoveNeutron();
        } catch (InvalidMoveException ex) {
            System.out.println(ex.getMessage());
            MoveNeutron();
        }
    }

    @Override
    public void MovePlayerPiece() {
        System.out.print("Player " + playerPiece.getMark() + " move piece (position + direction, e.g. 'A1 NE'): ");
        String input = scanner.nextLine();

        try {
            Position pos = new Position(input.substring(0, 2));
            Direction dir = Direction.valueOf(input.substring(2).trim());
            board.move(pos, playerPiece, dir);


        } catch (EnumConstantNotPresentException ex) {
            System.out.println(input + " is not a valid direction");
            MovePlayerPiece();
        } catch (InvalidMoveException | IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            MovePlayerPiece();
        }
    }
}
