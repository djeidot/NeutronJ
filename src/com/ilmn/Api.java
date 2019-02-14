package com.ilmn;

import java.net.URI;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.ilmn.Pojos.GameList;

public class Api {

    String baseUrl =  "http://10.46.36.105:8080";

    public void run() {
        getGames();
    }
    
    public void getGames() {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        String response = target.path("games").
                request().
                accept(MediaType.TEXT_PLAIN).
                get(Response.class)
                .toString();

        JsonObject jsonAnswer = target.path("games").request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);

        GameList gameList = GameList.deserialize(jsonAnswer);
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(baseUrl).build();
    }
}

//    JsonObject myObject = Json.createObjectBuilder()
//            .add("name", "Agamemnon")
//            .add("age", 32)
//            .build();