package com.ilmn;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Exceptions.InvalidMoveException;

public class Board {

    private Piece[][] board = new Piece[5][5];

    private boolean invisible = false;

    public Board() {
        initBoard();
    }

    public Board(Board other) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                this.board[x][y] = other.board[x][y];
            }
        }
        invisible = true;
    }

    public boolean isInvisible() {
        return invisible;
    }

    private Piece pieceAt(Position position) {
        return board[position.getX()][position.getY()];
    }

    private void movePieceTo(Position from, Position to) {
        Piece piece = pieceAt(from);
        board[from.getX()][from.getY()] = Piece.Empty;
        board[to.getX()][to.getY()] = piece;
    }

    private void initBoard() {
        for (int x = 0; x < 5; x++) {
            board[x][0] = Piece.PlayerX;
            board[x][4] = Piece.PlayerO;
        }
        for (int y = 1; y < 4; y++) {
            for (int x = 0; x < 5; x++) {
                board[x][y] = Piece.Empty;
            }
        }
        board[2][2] = Piece.Neutron;
    }

    public void show() {

        if (invisible) return;

        System.out.println("       1 2 3 4 5");
        System.out.println("      +---------+");
        for (int y = 0; y < 5; y++) {
            System.out.print("    " + (char) ('A' + y) + " |");
            for (int x = 0; x < 5; x++) {
                System.out.print(board[x][y].getMark());
                if (x < 4) {
                    System.out.print(" ");
                }
            }
            System.out.print("|\n");
        }
        System.out.print("      +---------+\n\n");
    }

    public boolean hasPiece(Position pos, Piece pieceType) {
        return pieceAt(pos) == pieceType;
    }

    public boolean canMove(Position pos, Direction dir) {
        Position pos2 = new Position(pos);
        pos2.move(dir);
        return !pos2.isOffScreen() && pieceAt(pos2) == Piece.Empty;
    }

    private Position moveInternal(Position pos, Direction dir) {
        Position posEnd = new Position(pos);
        while (canMove(posEnd, dir)) {
            posEnd.move(dir);
        }
        movePieceTo(pos, posEnd);
        return posEnd;
    }

    public void move(Position pos, Piece piece, Direction dir) throws InvalidMoveException {
        if (!hasPiece(pos, piece)) {
            if (pieceAt(pos) == Piece.Empty) {
                throw new InvalidMoveException("No piece in " + pos.toString() + " to move.");
            } else {
                throw new InvalidMoveException("Piece in " + pos.toString() + " is not a " + piece + " piece.");
            }
        } else if (!canMove(pos, dir)) {
            throw new InvalidMoveException("Can't move piece " + pos.toString() + " in direction " + dir.toString());
        } else {
            moveInternal(pos, dir);
        }

        show();
    }

    public Position getNeutron() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Position pos = new Position(x, y);
                if (hasPiece(pos, Piece.Neutron)) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("Error - Neutron not found in board.");
    }

    public Piece getNeutronBackLine() {
        Position posNeutron = getNeutron();
        if (posNeutron.getY() == 0) {
            return Piece.PlayerX;
        } else if (posNeutron.getY() == 4) {
            return Piece.PlayerO;
        } else {
            return Piece.Empty;
        }
    }

    public boolean isNeutronBlocked() {
        Position posNeutron = getNeutron();
        for (Direction dir : Direction.values()) {
            if (canMove(posNeutron, dir)) {
                return false;
            }
        }
        return true;
    }

}
