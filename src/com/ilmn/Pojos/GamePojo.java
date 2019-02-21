package com.ilmn.Pojos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.ilmn.Enums.Piece;

public class GamePojo {
    private String id;
    private String[] board;
    private String playerO;
    private String playerX;
    private Piece move;
    private String winner;
    private List<MovePojo> history;
    
    public GamePojo() {
        history = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<MovePojo> getHistory() {
        return history;
    }

    public void setHistory(List<MovePojo> history) {
        this.history = history;
    }

    public void addMove(MovePojo movePojo) {
        history.add(movePojo);
    }

    public static GamePojo deserialize(String id, JsonObject jsonGame) {
        GamePojo gamePojo = new GamePojo();
        gamePojo.setId(id);
        
        String[] board = new String[5];
        for (int i = 0; i < 5; i++) {
            board[i] = jsonGame.getJsonArray("board").getString(i);
        }
        gamePojo.setBoard(board);

        gamePojo.setPlayerO(jsonGame.getString("playerO"));
        gamePojo.setPlayerX(jsonGame.getString("playerX"));
        gamePojo.setMove(Piece.fromMark(jsonGame.getString("move")));
        gamePojo.setWinner(jsonGame.isNull("winner") ? null : jsonGame.getString("winner"));

        JsonArray history = jsonGame.getJsonArray("history");
        for (int i = 0; i < history.size(); i++) {
            MovePojo movePojo = MovePojo.deserialize(history.getJsonObject(i));
            gamePojo.addMove(movePojo);
        }

        return gamePojo;
    }

    @Override
    public String toString() {
        return "\nGamePojo{" +
                "\nid=" + id +
                "\n, board=[" + board[0] + "]" +
                "\n        [" + board[1] + "]" +
                "\n        [" + board[2] + "]" +
                "\n        [" + board[3] + "]" +
                "\n        [" + board[4] + "]" +
                "\n, playerO='" + playerO + '\'' +
                "\n, playerX='" + playerX + '\'' +
                "\n, move=" + move +
                "\n, winner='" + winner + '\'' +
                "\n, history=" + history +
                "\n}";
    }
}
