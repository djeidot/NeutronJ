package com.ilmn.Enums;

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
}
