package fr.uparis.informatique.cpoo5.game.model;

import java.util.ArrayList;
import java.util.List;

import fr.uparis.informatique.cpoo5.game.controller.KeyboardControls;

public final class Game implements Observable {
    private final Terrain terrain;
    private final List<Player> players;
    private final KeyboardControls controls;
    private final List<Entity> entities;

    public enum State {
        SPLASHSCREEN, MENU, PLAY, PAUSE, END
    }
    private State state;

    private Turn turn;
    private final List<Turn> history;

    private int nbTurns;

    private final List<Observer> observers;

    // BUILD
    private Game(GameBuilder builder) {
        this.terrain = builder.board;
        this.players = new ArrayList<>();
        this.controls = builder.controls;
        this.observers = new ArrayList<>();
        this.state = State.SPLASHSCREEN;
        this.history = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static final class GameBuilder {
        // Builder Pattern
        private Terrain board;
        private KeyboardControls controls;
        private static final Terrain DEFAULT_TERRAIN = new Terrain(8, 8);
        private static final KeyboardControls DEFAULT_CONTROLS = new KeyboardControls
                ('z', 'q', 's', 'd');

        private GameBuilder() {
            this.board = DEFAULT_TERRAIN;
            this.controls = DEFAULT_CONTROLS;
        }


        public GameBuilder setBoard(int width, int height) {
            this.board = new Terrain(width, height);
            return this;
        }

        public GameBuilder setControls(char up, char left,
                char down, char right) {
            this.controls = new KeyboardControls(up, left, down, right);
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
    // ----

    // GETTTERS
    public Terrain getTerrain() {
        return terrain;
    }
    public List<Player> getPlayers() {
        // TODO : devrait retourner une copie mais cela renvoie une liste non updatée
        return players;
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public int nbPlayers() {
        return players.size();
    }
    public State getState() {
        return state;
    }
    public int getNbTurns() {
        return nbTurns;
    }
    public Turn.State getTurnState() {
        return turn.getState();
    }
    public Turn getTurn() {
        return turn;
    }
    // ----
    
    // ACTIONS
    private boolean addPlayer(int x, int y, Player.Type playerType) {
        if (x < 0 || x >= terrain.width || y < 0 || y >= terrain.height) {
            return false;
        }

        Player p = null;
        switch (playerType) {
            case REAL -> p = new RealPlayer(x, y, players, entities, terrain, controls);
            case AI_RANDOM -> p = new AIPlayer(x, y, players
                    , entities, terrain, new RandomInputStrat());
            case AI_FLEE -> p = new AIPlayer(x, y, players
                    , entities, terrain, new FleeInputStrategy(this));
        }
        if (p == null) {
            return false;
        }
        players.add(p);
        entities.addAll(p.getSnake().getSegments());

        return true;
    }
    public boolean spawnPlayer(Player.Type playerType) {
        int x = (int) (Math.random()*(terrain.width-4)) +4;
        int y = (int) (Math.random()*(terrain.height));
        return addPlayer(x, y, playerType);
    }
    private boolean removePlayer(Player p) {
        if (!players.contains(p)) {
            return false;
        }
        for (Entity e : entities) {
            if (e instanceof Segment && ((Segment) e).getOwner() == p) {
                entities.remove(e);
            }
        }
        players.remove(p);
        return true;
    }

    private boolean updateSegments() {
        for (Player p : players) {
            for (Segment s : p.getSnake().getSegments()) {
                if (!entities.contains(s)) {
                    entities.add(s);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean removeEntity(Entity e) {
        if (!entities.contains(e)) {
            return false;
        }
        entities.remove(e);
        return true;
    }

    private boolean addFood(int x, int y) {
        if (x < 0 || x >= terrain.width || y < 0 || y >= terrain.height) {
            return false;
        }
        entities.add(new Food(x, y, terrain));
        return true;
    }
    private boolean spawnFood() {
        int x = (int) (Math.random()*(terrain.width));
        int y = (int) (Math.random()*(terrain.height));
        return addFood(x, y);
    } 
    
    private boolean isThereFood() {
        for (Entity e : entities) {
            if (e instanceof Food) {
                return true;
            }
        }
        return false;
    }
    // ----

    // LOOP
    public void run() {
        state = State.SPLASHSCREEN;
        notifyObservers();
        state = State.PLAY;
        while (true) {
            if (players.isEmpty() || !isThereReal(players)) {
                return;
            }
            nbTurns++;
            this.turn = new Turn(this, players);
            if (!isThereFood()) {
                spawnFood();
            }
            turn.run();

            ArrayList<Player> pcpy = new ArrayList<>(this.players);
            for (Player p : pcpy) {
                if (p.isDead()) {
                    removePlayer(p);
                    notifyObservers();
                } 
                Food f = p.getEaten();
                if (f != null) {
                    removeEntity(f);
                }
            }
            updateSegments();

            history.add(turn);
        }
    }
    // ----

    // OBSERVABLE
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
    // ----

    // UTILS
    private boolean isThereReal(List<Player> players) {
        for (Player p : players) {
            if (p instanceof RealPlayer) {
                return true;
            }
        }
        return false;
    }
    // ----
}

