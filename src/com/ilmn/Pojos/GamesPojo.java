package com.ilmn.Pojos;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class GamesPojo {
    private Map<String, GamePojo> games;

    public GamesPojo() {
        games = new HashMap<>();
    }
    
    public Map<String, GamePojo> getGames() {
        return games;
    }

    public void setGames(Map<String, GamePojo> games) {
        this.games = games;
    }

    public void addGame(String id, GamePojo gamePojo) {
        this.games.put(id, gamePojo);
    }

    public static GamesPojo deserialize(JsonObject jsonGameList) {
        GamesPojo games = new GamesPojo();

        for (String jsonIdStr : jsonGameList.keySet()) {
            GamePojo gamePojo = GamePojo.deserialize(jsonIdStr, jsonGameList.getJsonObject(jsonIdStr));
            games.addGame(jsonIdStr, gamePojo);
        }
        return games;
    }

    @Override
    public String toString() {
        return "\nGamesPojo{" +
                "\ngames=" + games +
                "\n}";
    }
}
