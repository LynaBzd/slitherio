package fr.uparis.informatique.cpoo5.game.model;

public final class Segment extends Entity implements Movable {
    private final Player owner;

    public Segment(Player owner, int x, int y) {
        super(x, y, owner.getTerrain());
        this.owner = owner;
    }

    public boolean isHead() {
        return owner.getSnake().getHead() == this;
    }

    public Player getOwner() {
        return owner;
    }

    private void up() {
        y = (y-1 < 0) ? terrain.height-1 : y-1;
    }
    private void down() {
        y = (y+1 > terrain.height-1) ? 0 : y+1;
    }
    private void left() {
        x = (x-1 < 0) ? terrain.width-1 : x-1;
    }
    private void right() {
        x = (x+1 > terrain.width-1) ? 0 : x+1;
    }

    @Override
    public void move(Direction dir) {
        switch (dir) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        }
    }

    public Direction adj(Segment seg) {
        if (x == seg.x && y == Math.floorMod(seg.y-1, terrain.height)) {
            return Direction.UP;
        } else if (x == seg.x && y == Math.floorMod(seg.y+1, terrain.height)) {
            return Direction.DOWN;
        } else if (x == Math.floorMod(seg.x-1, terrain.width) && y == seg.y) {
            return Direction.LEFT;
        } else if (x == Math.floorMod(seg.x+1, terrain.width) && y == seg.y) {
            return Direction.RIGHT;
        }
        return null;
    }
}

