package fr.uparis.informatique.cpoo5.game.model;

public interface Observable {
    public void addObserver(Observer obs);
    public void removeObserver(Observer obs);
    public void notifyObservers();
}
