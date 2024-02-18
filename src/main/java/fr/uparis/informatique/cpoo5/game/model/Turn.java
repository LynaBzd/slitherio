package fr.uparis.informatique.cpoo5.game.model;

import java.util.List;

public final class Turn {
    private Game game;
    private List<Player> players;
    private Player current;

    public enum State {
        START, RUNNING, INPUT_ERR, END;
    }
    private State state;

    public Turn(Game game, List<Player> players) {
        this.game = game;
        this.players = players;
    }

    public State getState() {
        return state;
    }

    public Player getCurrent() {
        return current;
    }


    public void run() {
        state = State.START;
        game.notifyObservers();

        for (Player player : players) {
            this.current = player;
            state = State.RUNNING;
            game.notifyObservers();

            boolean played = player.play();
            while (!played) {
                state = State.INPUT_ERR;
                game.notifyObservers();
                played = player.play();
                state = State.RUNNING;
            };
            
            Entity touched = player.touched();
            if (touched == null) {
            } else if (touched instanceof Segment) {
                player.die();
            } else if (touched instanceof Food) {
                player.eat((Food)touched);
            }

            state = State.END;
            game.notifyObservers();
        }
    }
}
