package com.ilmn.Pojos;

import javax.json.Json;
import javax.json.JsonObject;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Position;

public class PlayerMovePojo {
    String neutronDir;
    String playerPiece;
    String pieceDir;

    public PlayerMovePojo(Direction neutronDir, Position playerPiece, Direction pieceDir) {
        this.neutronDir = neutronDir.toString();
        this.playerPiece = playerPiece == null ? null : playerPiece.toString();
        this.pieceDir = pieceDir == null ? null : pieceDir.toString();
    }
    
    public JsonObject serialize() {
        JsonObject playerMoveObj;
        if (playerPiece == null || pieceDir == null) {
            playerMoveObj = Json.createObjectBuilder()
                    .add("neutrondirn", neutronDir)
                    .build();
        } else {
            playerMoveObj = Json.createObjectBuilder()
                    .add("neutrondirn", neutronDir)
                    .add("piece", playerPiece)
                    .add("piecedirn", pieceDir)
                    .build();
        }
        return playerMoveObj;
    }

    @Override
    public String toString() {
        return "'" + neutronDir + ", " + playerPiece + " " + pieceDir + "'";
    }
}
