package com.ilmn;

public class Position {
    int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos1) {
        this.x = pos1.getX();
        this.y = pos1.getY();
    }

    public void move(Direction dir) {
        x += dir.getDirX();
        y += dir.getDirY();
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "" + (char)('A'+y) + x;
    }

    public int getY() {
        return y;
    }

    boolean isOffScreen() {
        return x < 0 || x >= 5 || y < 0 || y >= 5;
    }
}
