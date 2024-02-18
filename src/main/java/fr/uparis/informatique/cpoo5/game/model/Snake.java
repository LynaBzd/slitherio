package fr.uparis.informatique.cpoo5.game.model;

import java.util.LinkedList;

public final class Snake {
    private LinkedList<Segment> segments;
    private final Player owner;
    private Direction lstdir;
    
    public Snake(Player owner, int x, int y) {
        this.owner = owner;
        this.segments = new LinkedList<>();

        segments.add(new Segment(this.owner, x, y));

        for(int i = 0; i < 4-1; i++) {
            addSegment();
        }
    }

    public LinkedList<Segment> getSegments() {
        return segments;
    }

    public Segment getSegment(int i) {
        return segments.get(i);
    }

    public int getLength() {
        return segments.size();
    }
    
    private void addSegment() {
        Segment last = segments.getLast();
        if (lstdir == null) {
            segments.addLast(new Segment(owner, last.getX()-1, last.getY()));
            return;
        }

        int x = last.getX();
        int y = last.getY();

        switch (lstdir) {
            case LEFT -> x = Math.floorMod(x+1, owner.getTerrain().height);
            case RIGHT -> x = Math.floorMod(x-1, owner.getTerrain().height);
            case UP -> y = Math.floorMod(y+1, owner.getTerrain().height);
            case DOWN -> y = Math.floorMod(y-1, owner.getTerrain().height);
        }

        segments.addLast(new Segment(owner, x, y));
    }

    public Segment getHead() {
        return segments.getFirst();
    }

    public Player getOwner() {
        return owner;
    }

    public boolean move(Direction dir) {
        if (!canMove(dir)) {
            return false;
        }
        Direction d = dir;

        for (int i = 0; i < segments.size()-1; i++) {
            Segment cur = segments.get(i);
            Segment nxt = segments.get(i+1);

            Direction nxtdir = cur.adj(nxt);
            cur.move(d);
            d = nxtdir;
        }

        Segment lst = segments.getLast();
        lst.move(d);
        lstdir = d;

        return true;
    }

    private boolean canMove(Direction dir) {
        if (segments.size() < 2) {
            return true;
        }
        Segment hd = getHead();
        Segment nxt = segments.get(1);

        return nxt.adj(hd) != dir;
    }

    public void grow() {
        addSegment();
    }
}

