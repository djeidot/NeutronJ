package com.ilmn.Enums;

import org.omg.CosNaming.NamingContextPackage.NotFound;

public enum Piece {
    PlayerX ("X"),
    PlayerO ("0"),
    Neutron ("*"),
    Empty (" ");

    private final String mark;
    Piece(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    public Piece opponent() {
        if (this.equals(PlayerO)) {
            return PlayerX;
        } else if (this.equals(PlayerX)) {
            return PlayerO;
        } else {
            return this;
        }
    }

    public static Piece fromMark(String mark) {
        for (Piece piece : Piece.values()) {
            if (piece.getMark().equals(mark)) {
                return piece;
            }
        }
        return Empty;
    }
}
