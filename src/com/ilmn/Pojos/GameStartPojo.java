package com.ilmn.Pojos;

import javax.json.Json;
import javax.json.JsonObject;

import com.ilmn.Enums.Piece;
import com.ilmn.Players.Player;

public class GameStartPojo {
    String playerO;
    String playerX;
    String move;

    public GameStartPojo(Player pO, Player pX, Piece startingPlayer) {
        playerO = pO.getName();
        playerX = pX.getName();
        assert (startingPlayer.isPlayer());
        move = startingPlayer.getMark();
    }

    public GameStartPojo(String pO, String pX, String startingPlayer) {
        playerO = pO;
        playerX = pX;
        move = startingPlayer;
    }
    
    public String getPlayerO() {
        return playerO;
    }

    public String getPlayerX() {
        return playerX;
    }

    public String getMove() {
        return move;
    }
    
    public JsonObject serialize() {
        JsonObject gameStartObj = Json.createObjectBuilder()
            .add("playerO", playerO)
            .add("playerX", playerX)
            .add("move", move)
            .build();
        return gameStartObj;
    }
}
