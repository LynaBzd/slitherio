package fr.uparis.informatique.cpoo5.game.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private int id;
    private static int nbids;
    private final Snake snake;
    private final List<Player> players;
    private final List<Entity> entities;
    private final Terrain terrain;
    private final InputStrat inputStrat;
    private boolean dead = false;
    private Food eaten = null;

    public Player(int x, int y, List<Player> players
            , List<Entity> entities, Terrain terrain, InputStrat inputStrat) {
        this.id = ++nbids;
        this.inputStrat = inputStrat;
        this.players = players;
        this.entities = entities;
        this.terrain = terrain;
        this.snake = new Snake(this, x, y);
    }

    // GETTERS
    public int getId() {
        return id;
    }
    public int getX() {
        return snake.getHead().getX();
    }
    public int getY() {
        return snake.getHead().getY();
    }
    public InputStrat getInputStrat() {
        // TODO Defensive Copy
        return inputStrat;
    }
    public Snake getSnake() {
        // TODO Defensive Copy
        return snake;
    }
    public Terrain getTerrain() {
        return terrain;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public boolean isDead() {
        return dead;
    }
    public Food getEaten() {
        return eaten;
    }

    protected Player makeCopy(int id) {
        // Prototype Pattern
        nbids--;
        this.id = id;
        return this;
    }

    public abstract Player copy(); // Defensive Copy Technique

    public static List<Player> copyList(List<Player> players) {
        List<Player> res = new ArrayList<>();
        for (Player p : players) {
            res.add(p.copy());
        }
        return res;
    }

    public boolean eq(Player p) {
        return p.getX() == this.getX() && p.getY() == this.getY()
                && p.getId() == this.getId() 
                && p.getTerrain() == this.getTerrain(); 
    }
    // ----


    // STATE CHANGE
    private boolean move(Direction dir) {
        return snake.move(dir);
    }

    public Entity touched() {
        for (Entity e : entities) {
            if (e instanceof Segment && ((Segment) e).getOwner() == this) {
                continue;
            }
            if (e.getX() == this.getX() && e.getY() == this.getY()) {
                return e;
            }
        }
        return null;
    }
    // ----

    
    // ACTIONS
    public boolean play() {
        if (inputStrat instanceof WiseInputStrat) {
            ((WiseInputStrat) inputStrat).setPlayer(this);
        }
        Direction input = inputStrat.input();
        if (input == null) {
            return false;
        }
        return move(input);
    }

    public void eat(Food e) {
        eaten = e;
        snake.grow();
    }

    public void die() {
        dead = true;
    }
    // ----


    public enum Type {
        REAL, AI_RANDOM, AI_FLEE
    }
}

