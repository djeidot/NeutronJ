package com.ilmn.Enums;

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

    public Position(String posStr) {
        this.y = (int)(posStr.charAt(0) - 'A');
        try {
            this.x = Integer.parseInt(posStr.substring(1, 2)) - 1;
            if (this.isOffScreen()) {
                throw new IndexOutOfBoundsException(posStr + " is not a valid position.");
            }
        } catch (NumberFormatException ex) {
            throw new IndexOutOfBoundsException(posStr + " is not a valid position.");
        }
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
        return "" + (char)('A'+y) + (x+1);
    }

    public int getY() {
        return y;
    }

    public boolean isOffScreen() {
        return x < 0 || x >= 5 || y < 0 || y >= 5;
    }
}
