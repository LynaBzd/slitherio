package fr.uparis.informatique.cpoo5.game.model;

import java.util.List;

import fr.uparis.informatique.cpoo5.game.controller.KeyboardControls;

public final class RealPlayer extends Player {
    private KeyboardControls controls;

    public RealPlayer(int x, int y, List<Player> players
            , List<Entity> entities, Terrain terrain, KeyboardControls controls) {
        super(x, y, players, entities, terrain, new KeyboardInputStrat(controls));
        this.controls = controls;
    }

    @Override
    public Player copy() {
        return new RealPlayer(getX(), getY(), getPlayers(), getEntities(), getTerrain(),
                controls).makeCopy(this.getId());
    }
}

