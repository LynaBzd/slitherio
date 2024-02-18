package fr.uparis.informatique.cpoo5.game.controller;

import fr.uparis.informatique.cpoo5.game.model.Direction;

public final class KeyboardControls {
    public final char up, left, down, right;

    public KeyboardControls(char up, char left, char down, char right) {
        this.up    = up;
        this.left  = left;
        this.down  = down;
        this.right = right;
    }

    public Direction getDirection(char input) {
        if (input == up) {
            return Direction.UP;
        } else if (input == left) {
            return Direction.LEFT;
        } else if (input == down) {
            return Direction.DOWN;
        } else if (input == right) {
            return Direction.RIGHT;
        } else {
            return null;
        }
    }
}

