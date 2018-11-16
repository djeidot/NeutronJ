package com.ilmn;

public class Board {

    private Piece[][] board = new Piece[5][5];

    public Board() {
        initBoard();
    }

    private void initBoard() {
        for (int c = 0; c < 5; c++) {
            board[0][c] = Piece.PlayerX;
            board[4][c] = Piece.PlayerO;
        }
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                board[r][c] = Piece.Empty;
            }
        }
        board[2][2] = Piece.Neutron;
    }

    public void show() {
        System.out.print("    +-----+\n");
        for (int r = 0; r < 5; r++) {
            System.out.print("    |");
            for (int c = 0; c < 5; c++) {
                System.out.print(board[r][c].getMark());
            }
            System.out.print("|\n");
        }
        System.out.print("    +-----+");
    }
}
