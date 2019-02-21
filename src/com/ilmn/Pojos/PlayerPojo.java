package com.ilmn.Pojos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;

import sun.rmi.runtime.Log;

public class PlayerPojo {
    private String playerName;
    private int gamesWon;
    private int gamesLost;
    List<String> gameIds;

    public PlayerPojo() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public List<String> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<String> gameIds) {
        this.gameIds = gameIds;
    }

    public static PlayerPojo deserialize(JsonObject jsonPlayer) {
        PlayerPojo player = new PlayerPojo();

        player.setPlayerName(jsonPlayer.getString("name"));
        player.setGamesWon(jsonPlayer.getInt("won"));
        player.setGamesLost(jsonPlayer.getInt("lost"));
        
        List<String> gameIds = new ArrayList<>();
        JsonArray gameIdArray = jsonPlayer.getJsonArray("games");
        for (int i = 0; i < gameIdArray.size(); i++) {
            gameIds.add(gameIdArray.getString(i));
        }
        player.setGameIds(gameIds);
        
        return player;
    }

    @Override
    public String toString() {
        return "PlayerPojo{" +
                "playerName='" + playerName + '\'' +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", games=" + gameIds +
                '}';
    }
}
