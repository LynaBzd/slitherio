package fr.uparis.informatique.cpoo5.game.view;

public final class TerrainView {
    public final int width, height;

    public static final String UNIT_EVEN = "+---";
    public static final String UNIT_ODD = "|   ";

    public static final int UNIT_LENGTH = UNIT_EVEN.length();

    public final int displayLength;

    public TerrainView(int width, int height) {
        this.width = width;
        this.height = height;
        this.displayLength = UNIT_LENGTH * width;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        
        for (int i = 0; i < height; i++) {
            res.append(String.format("%s+\n", UNIT_EVEN.repeat(width)));
            res.append(String.format("%s|\n", UNIT_ODD.repeat(width)));
        }
        res.append(String.format("%s+\n", UNIT_EVEN.repeat(width)));

        return res.toString();
    }
}
