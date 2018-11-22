package com.ilmn;

public class Board {

    private Piece[][] board = new Piece[5][5];

    public Board() {
        initBoard();
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
        System.out.print("       1 2 3 4 5\n");
        System.out.print("      +---------+\n");
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

    public Position move(Position pos, Direction dir) {
        Position posEnd = new Position(pos);
        while (canMove(posEnd, dir)) {
            posEnd.move(dir);
        }
        movePieceTo(pos, posEnd);
        return posEnd;
    }

    public boolean tryMove(Position pos, Piece piece, Direction dir) {
        if (!hasPiece(pos, piece)) {
            if (pieceAt(pos) == Piece.Empty) {
                System.out.print("No piece in " + pos.toString() + " to move.");
            } else {
                System.out.print("Piece in " + pos.toString() + " is not a " + piece + " piece.");
            }
            return false;
        } else if (!canMove(pos, dir)) {
            System.out.print("Can't move piece " + pos.toString() + " in direction " + dir.toString());
            return false;
        } else {
            move(pos, dir);
            return true;
        }
    }
}
