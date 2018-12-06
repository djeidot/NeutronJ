package com.ilmn.Enums;

public enum Direction {
    N  (0, -1),
    NE (1, -1),
    E  (1, 0),
    SE (1, 1),
    S  (0, 1),
    SW (-1, 1),
    W  (-1, 0),
    NW (-1, -1);

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    private final int dirX;
    private final int dirY;

    Direction(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }
}
