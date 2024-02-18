package fr.uparis.informatique.cpoo5.game.model;

import java.util.List;

public final class AIPlayer extends Player {
    public AIPlayer(int x, int y, List<Player> players, List<Entity> entities, Terrain terrain, InputStrat inputStrat) {
        super(x, y, players, entities, terrain, inputStrat);
    }

    @Override
    public Player copy() {
        return new AIPlayer(getX(), getY(), getPlayers()
                , getEntities(), getTerrain(), getInputStrat())
                .makeCopy(this.getId());
    }
}
