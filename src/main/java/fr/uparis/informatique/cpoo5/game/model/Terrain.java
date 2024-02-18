package fr.uparis.informatique.cpoo5.game.model;

public final class Terrain {
    public final int width, height;

    public Terrain(int width, int height) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
    }
}

