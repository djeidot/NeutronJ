package com.ilmn;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Players.Player;
import com.ilmn.Pojos.MovePojo;
import com.ilmn.Pojos.PlayerMovePojo;
import javafx.util.Pair;

public class MoveList {

    private Player player1;
    private Player player2;
    private Api api;
    private String gameId;

    class MoveLine {
        PlayerMove player1Move;
        PlayerMove player2Move;
    }
    
    private List<MoveLine> moves = new ArrayList<>();
    
    private String spacer = "     ";

    public void setPlayers(Player player0, Player playerX) {
        this.player1 = player0;
        this.player2 = playerX;
    }

    public void setApiGame(Api api, String gameId) {
        this.api = api;
        this.gameId = gameId;
    }

    public void addMove(Player player, Piece piece, Position pos, Direction dir) {
        if (piece == Piece.Neutron) {
            PlayerMove move = new PlayerMove(player, dir);
            addToList(move);
        } else {
            PlayerMove move = getLastMove();
            move.setPieceMove(player, pos, dir);
            if (api != null && gameId != null) {
                PlayerMovePojo playerMovePojo = new PlayerMovePojo(move.getNeutronMove(), move.getPieceMove().getKey(), move.getPieceMove().getValue());
                api.movePiece(gameId, playerMovePojo);
            }
        }
    }
    
    public String getHeaders() {
        // ex:        Brian    |     Cpu4
        return spacer + " " + center(player1.getName(), 12, true) + "|" + center(player2.getName(), 12, false);
    }

    private String center(String text, int length, boolean rightAligned) {
        int spaces = length - text.length();
        if (spaces <= 0) {
            return text.substring(0, length);
        } else {
            int spacesLeft = spaces / 2;
            int spacesRight = spaces - spacesLeft;

            if (rightAligned) {
                return spaces(spacesRight) + text + spaces(spacesLeft);
            } else {
                return spaces(spacesLeft) + text + spaces(spacesRight);
            }
        }
    }

    private String spaces(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    public String getSeparator() {
        return spacer + "-------------|-------------";
    }

    public String getMoveString(int lineNr) {
        // ex:    * NE, A1 SW  | * SE, E5 NW
        if (lineNr >= moves.size())
            return "";
        
        MoveLine line = moves.get(lineNr);
        StringBuilder builder = new StringBuilder();
        builder.append(spacer);
        builder.append(" * " + String.format("%2s", line.player1Move.getNeutronMove().toString()));
        if (line.player1Move.getPieceMove() != null) {
            builder.append(", " + line.player1Move.getPieceMove().getKey().toString() + " " 
                    + String.format("%2s", line.player1Move.getPieceMove().getValue().toString()) + " |");
        } else {
            builder.append("        |");
        }
        if (line.player2Move != null) {
            builder.append(" * " + String.format("%2s", line.player2Move.getNeutronMove().toString()));
            if (line.player2Move.getPieceMove() != null) {
                builder.append(", " + line.player2Move.getPieceMove().getKey().toString() + " "
                        + String.format("%2s", line.player2Move.getPieceMove().getValue().toString()));
            }
        }
        return builder.toString();
    }

    public int getMoveLineCount() {
        return moves.size();
    }

    private void addToList(PlayerMove move) {
        if (moves.isEmpty()) {
            addNewMoveLine(move);
        } else {
            MoveLine moveLine = moves.get(moves.size() - 1);
            if (moveLine.player2Move == null) {
                moveLine.player2Move = move;
            } else {
                addNewMoveLine(move);
            }
        }
    }

    private void addNewMoveLine(PlayerMove move) {
        MoveLine newMoveLine = new MoveLine();
        newMoveLine.player1Move = move;
        moves.add(newMoveLine);
    }

    private PlayerMove getLastMove() {
        MoveLine moveLine = moves.get(moves.size() - 1);
        return (moveLine.player2Move == null ? moveLine.player1Move : moveLine.player2Move);
    }
}
