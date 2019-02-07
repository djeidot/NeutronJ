package com.ilmn;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Api api = new Api();
        try {
            api.getGames();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        
//        Board board = new Board();
//        board.show();
//        Game game = new Game(board);
    }
}
