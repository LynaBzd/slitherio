package fr.uparis.informatique.cpoo5.game.model;

public class RandomInputStrat implements InputStrat {
    @Override
    public Direction input() {
        return Direction.values()[(int) (Math.random() * 4)];
    }
}
