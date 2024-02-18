package fr.uparis.informatique.cpoo5.game.view;

public final class PlayerView {
    public static String toString(int id) {
        return AnsiColor.YELLOW_BRIGHT +(char) ('0'+id) + AnsiColor.RESET;
    }
    public static String toStringHead(int id) {
        return AnsiColor.GREEN_BRIGHT +(char) ('0'+id) + AnsiColor.RESET;
    }
}
