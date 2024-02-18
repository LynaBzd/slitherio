package fr.uparis.informatique.cpoo5.game.model;

public enum Direction {
    UP, LEFT, DOWN, RIGHT;

    public Direction opposite() {
        switch(this) {
            case UP    : return DOWN;
            case LEFT  : return RIGHT;
            case DOWN  : return UP;
            case RIGHT : return LEFT;
        }
        return null;
    }
}
