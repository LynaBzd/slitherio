package fr.uparis.informatique.cpoo5.game.model;

public abstract class Entity {
    int x, y;
    public final Terrain terrain;

    public Entity(int x, int y, Terrain terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }

    public final int getX() {
        return x;
    }
    public final int getY() {
        return y;
    }
    public final Terrain getTerrain() {
        return terrain;
    }

    public Direction adj(Entity ent) {
        if (x == ent.x && y == Math.floorMod(ent.y-1, terrain.height)) {
            return Direction.UP;
        } else if (x == ent.x && y == Math.floorMod(ent.y+1, terrain.height)) {
            return Direction.DOWN;
        } else if (x == Math.floorMod(ent.x-1, terrain.width) && y == ent.y) {
            return Direction.LEFT;
        } else if (x == Math.floorMod(ent.x+1, terrain.width) && y == ent.y) {
            return Direction.RIGHT;
        }
        return null;
    }

    public boolean overlap(Entity ent) {
        return this != ent && ent != null && ent.terrain == terrain
                && x == ent.x && y == ent.y;
    }
}
