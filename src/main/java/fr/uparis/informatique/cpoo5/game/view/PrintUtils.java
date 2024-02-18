package fr.uparis.informatique.cpoo5.game.view;

public final class PrintUtils {
    public static String prettyString(String deco, int len, String s, String color) {
        int slen = s.length();
        int nbdeco = (len-slen-2)/2;
        if (nbdeco <= 0) {
            return s + '\n';
        }
        String format = "#".repeat(nbdeco);
        String res = format + " " + color + s + AnsiColor.RESET + " " + format;
        if (slen %2 != 0) {
            return res+'#'+'\n';
        }
        return res+'\n';
    }
    
    public static String prettyString(String deco, int len, String s) {
        return prettyString(deco, len, s, AnsiColor.RESET);
    }

    public static String prettyString(String deco, int len) {
        return "#".repeat(len)+'\n';
    }
}
