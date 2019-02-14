package com.ilmn.Pojos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.ilmn.Enums.Piece;

public class Game {
    private Long id;
    private String[] board;
    private String playerO;
    private String playerX;
    private Piece move;
    private String winner;
    private List<Move> history;
    
    public Game() {
        history = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }

    public String getPlayerX() {
        return playerX;
    }

    public void setPlayerX(String playerX) {
        this.playerX = playerX;
    }

    public Piece getMove() {
        return move;
    }

    public void setMove(Piece move) {
        this.move = move;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<Move> getHistory() {
        return history;
    }

    public void setHistory(List<Move> history) {
        this.history = history;
    }

    public void addMove(Move move) {
        history.add(move);
    }

    public static Game deserialize(Long id, JsonObject jsonGame) {
        Game game = new Game();
        game.setId(id);
        
        String[] board = new String[5];
        for (int i = 0; i < 5; i++) {
            board[i] = jsonGame.getJsonArray("board").getString(i);
        }
        game.setBoard(board);

        game.setPlayerO(jsonGame.getString("playerO"));
        game.setPlayerX(jsonGame.getString("playerX"));
        game.setMove(Piece.fromMark(jsonGame.getString("move")));
        game.setWinner(jsonGame.isNull("winner") ? null : jsonGame.getString("winner"));

        JsonArray history = jsonGame.getJsonArray("history");
        for (int i = 0; i < history.size(); i++) {
            Move move = Move.deserialize(history.getJsonObject(i));
            game.addMove(move);
        }

        return game;
    }
}
