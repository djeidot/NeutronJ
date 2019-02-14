package com.ilmn.Pojos;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class GameList {
    private Map<Long, Game> games;

    public GameList() {
        games = new HashMap<>();
    }
    
    public Map<Long, Game> getGames() {
        return games;
    }

    public void setGames(Map<Long, Game> games) {
        this.games = games;
    }

    public void addGame(Long id, Game game) {
        this.games.put(id, game);
    }

    public static GameList deserialize(JsonObject jsonGameList) {
        GameList games = new GameList();

        for (String jsonIdStr : jsonGameList.keySet()) {
            Long id = Long.parseLong(jsonIdStr);
            Game game = Game.deserialize(id, jsonGameList.getJsonObject(jsonIdStr));
            games.addGame(id, game);
        }
        return games;
    }
}
