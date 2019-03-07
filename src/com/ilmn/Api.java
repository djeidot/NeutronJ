package com.ilmn;

import java.net.URI;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Position;
import com.ilmn.Pojos.GameStartPojo;
import com.ilmn.Pojos.GamesPojo;
import com.ilmn.Pojos.GamePojo;
import com.ilmn.Pojos.PlayerMovePojo;
import com.ilmn.Pojos.PlayerPojo;

public class Api {

    String baseUrl =  "http://10.46.36.105:8080";
    private URI getBaseURI() {
        return UriBuilder.fromUri(baseUrl).build();
    }
    private WebTarget target;

    public Api() {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        target = client.target(getBaseURI());
    }

    public void run() {
//        PlayerPojo player = getPlayer("Neil");
//        System.out.println(player.toString());
//        
//        GamesPojo games = getGames();
//        System.out.println(games.toString());

//        GamePojo game = getGame("140359470144144");
//        System.out.println(game.toString());
//        
//        GameStartPojo gameStartPojo = new GameStartPojo("Joao1", "Joao2", "O");
//        startGame(gameStartPojo);

        PlayerMovePojo playerMovePojo = new PlayerMovePojo(Direction.N, new Position("E3"), Direction.N);
        movePiece("140359470144144", playerMovePojo);
    }
    
    public GamesPojo getGames() {
        JsonObject jsonAnswer = target.path("games/").request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);
        return GamesPojo.deserialize(jsonAnswer);
    }
    
    public GamePojo getGame(String gameId) {
        JsonObject jsonAnswer = target.path("games/").path(gameId + "/").request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);
        return GamePojo.deserialize(gameId, jsonAnswer);
    }
    
    public PlayerPojo getPlayer(String playerName) {
        JsonObject jsonAnswer = target.path("players/").path(playerName).request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);
        return PlayerPojo.deserialize(jsonAnswer);
    }

    public String startGame(GameStartPojo gameStartPojo) {
        JsonObject jsonRequest = gameStartPojo.serialize();
        Response response = target.path("games/").request().post(Entity.json(jsonRequest));
        return response.readEntity(String.class);
    }

    public boolean movePiece(String gameId, PlayerMovePojo playerMovePojo) {
        JsonObject jsonRequest = playerMovePojo.serialize();
        Response response = target.path("games/").path(gameId + "/").request().post(Entity.json(jsonRequest));
        return response.getStatus() == 204;
    }
}

//    JsonObject myObject = Json.createObjectBuilder()
//            .add("name", "Agamemnon")
//            .add("age", 32)
//            .build();