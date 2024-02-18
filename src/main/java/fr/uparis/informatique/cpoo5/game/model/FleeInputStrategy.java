package fr.uparis.informatique.cpoo5.game.model;

import java.util.List;

public class FleeInputStrategy implements WiseInputStrat, Observer {
    private Player current;
    private List<Player> players;
    private Game game;
    private boolean triedUp = false;
    private boolean triedLeft = false;
    private boolean triedDown = false;
    private boolean triedRight = false;

    public FleeInputStrategy(Game game) {
        this.game = game;
        this.players = game.getPlayers();
    }
    
    @Override
    public void setPlayer(Player player) {
        this.current = player;
    }

    @Override
    public Direction input() {
        this.update();
        Direction choice = new RandomInputStrat().input();
        if (triedUp && triedLeft && triedDown && triedRight) {
            triedUp = triedLeft = triedDown = triedRight = false;
            return choice;
        }
        for (Player p : players) {
            if (p.getX() == current.getX() && p.getY() == current.getY()) {
                continue;
            }
            if (p.getX() == current.getX()) {
                if (p.getY() == current.getY()-1 && !triedUp) {
                    choice = Direction.DOWN;
                    triedDown = true;
                }
                else if (p.getY() == current.getY()+1 && !triedDown) {
                    choice = Direction.UP;
                    triedUp = true;
                }
            } else if (p.getY() == current.getY()) {   
                if (p.getX() == current.getX()-1 && !triedLeft) {
                    choice = Direction.RIGHT;
                    triedRight = true;
                }
                else if (p.getX() == current.getX()+1 && !triedRight) {
                    choice = Direction.LEFT;
                    triedLeft = true;
                }
            }
        }

        return choice;
    }

    @Override
    public void update() {
        this.players = game.getPlayers();
    }    
}
