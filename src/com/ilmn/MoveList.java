package com.ilmn;

import java.util.ArrayList;
import java.util.List;

import com.ilmn.Enums.Direction;
import com.ilmn.Enums.MoveType;
import com.ilmn.Enums.Piece;
import com.ilmn.Enums.Position;
import com.ilmn.Players.Player;
import com.ilmn.Players.Remote;
import com.ilmn.Pojos.GamePojo;
import com.ilmn.Pojos.MovePojo;
import com.ilmn.Pojos.PlayerMovePojo;

import static com.ilmn.Format.center;

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

    public MoveList() {
    }
    
    public void setPlayers(Player playerO, Player playerX, Piece startingPlayer) {
        switch (startingPlayer) {
            case PlayerO:
                this.player1 = playerO;
                this.player2 = playerX;
                break;
            case PlayerX:
                this.player1 = playerX;
                this.player2 = playerO;
                break;
            default:
                throw new RuntimeException("Invalid starting player");
        }
    }

    public void setPreviousMoves(GamePojo gamePojo) {
        boolean player1Move = true;
        for (MovePojo movePojo : gamePojo.getHistory()) {
            Player player = (player1Move ? player1 : player2);
            
            MoveType moveType = (gamePojo.getWinner() == null ? MoveType.other
                    : gamePojo.getWinner().equals(player.getPlayerPiece().getMark()) ? MoveType.winning
                    : Piece.fromMark(gamePojo.getWinner()).isPlayer() ? MoveType.losing
                    : MoveType.other);

            if (movePojo.getPiece() == null) {
                addToList(new PlayerMove(player, movePojo.getNeutronDirn(), moveType));
            } else {
                addToList(new PlayerMove(player, movePojo.getNeutronDirn(), movePojo.getPiece(), movePojo.getPieceDirn(), moveType));
            }
        }
    }
    
    public void setApiGame(Api api, String gameId) {
        this.api = api;
        this.gameId = gameId;
    }

    public void addMove(Player player, Piece piece, Position pos, Direction dir, MoveType moveType) {
        if (piece == Piece.Neutron) {
            PlayerMove move = new PlayerMove(player, dir, moveType);
            addToList(move);
            if (moveType == MoveType.winning || moveType == MoveType.losing) {
                if (api != null && gameId != null && !(player instanceof Remote)) {
                    PlayerMovePojo playerMovePojo = new PlayerMovePojo(move.getNeutronMove(), null, null);
                    api.movePiece(gameId, playerMovePojo);
                }               
            }
        } else {
            PlayerMove move = getLastMove();
            move.setPieceMove(player, pos, dir, moveType);
            if (api != null && gameId != null && !(player instanceof Remote)) {
                PlayerMovePojo playerMovePojo = new PlayerMovePojo(move.getNeutronMove(), move.getPieceMove().getKey(), move.getPieceMove().getValue());
                api.movePiece(gameId, playerMovePojo);
            }
        }
    }
    
    public String getHeaders() {
        // ex:        Brian    |     Cpu4
        return spacer + " " + center(player1.getName(), 12, true) + "|" + center(player2.getName(), 12, false);
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
