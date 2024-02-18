package fr.uparis.informatique.cpoo5.game.view;

import fr.uparis.informatique.cpoo5.game.model.Entity;
import fr.uparis.informatique.cpoo5.game.model.Game;
import fr.uparis.informatique.cpoo5.game.model.Observer;
import fr.uparis.informatique.cpoo5.game.model.Player;

public final class GameView implements Observer {
    private static final String NAME = "SNAKAWAI.IO";
    private final Game game;
    private final TerrainView terrainView;
    private Entity[][] entities;

    
    public GameView(Game game) {
        this.game = game;
        game.addObserver(this);
        this.terrainView = new TerrainView(game.getTerrain().width, game.getTerrain().height);
    }

    @Override
    public void update() {
        switch (game.getState()) {
            case SPLASHSCREEN:
                System.out.println(splashscreen());
                break;
            
            case MENU:
                System.out.printf("%s\t/!\\ Menu Unimplemented /!\\%s\n", 
                        AnsiColor.RED_BRIGHT, AnsiColor.RESET);
                break;
            case PLAY:
                System.out.println(playscreen());
                break;
            case PAUSE:
                System.out.printf("%s\t/!\\ Pause Unimplemented /!\\%s\n",
                        AnsiColor.RED_BRIGHT, AnsiColor.RESET);
                break;
            default:
                break;
        }
    }

    public String splashscreen() {
        StringBuilder res = new StringBuilder();

        res.append(PrintUtils.prettyString("#", terrainView.displayLength));
        res.append(PrintUtils.prettyString("#", terrainView.displayLength
                , NAME, AnsiColor.GREEN_BOLD_BRIGHT));
        res.append(PrintUtils.prettyString("#", terrainView.displayLength));
        
        return res.toString();
    }

    public String playscreen() {
        switch (game.getTurnState()) {
            case START:
                return turnStartInfo();
            case RUNNING:
                return turnPlayerInfo() +"\n"+ currentTerrainGrid();
            case INPUT_ERR:
                return inputError();
            case END:
                return "";
            default:
                return currentTerrain();
        }
    }

    public String turnStartInfo() {
        return PrintUtils.prettyString("-", terrainView.displayLength
                , "TOUR " + game.getNbTurns(), AnsiColor.GREEN_BACKGROUND_BRIGHT);
    }

    public String turnPlayerInfo() {
        Player player = game.getTurn().getCurrent();
        if (player == null) {
            throw new IllegalStateException();
        }

        return String.format("Joueur %d (%d,%d) joue...\n",
                player.getId(), player.getX(), player.getY());
    }
    
    public String currentTerrainGrid() {
        StringBuilder res = new StringBuilder();
        
        this.entities = new Entity[terrainView.height][terrainView.width];
        for (Entity e : game.getEntities()) {
            entities[e.getY()][e.getX()] = e;
        }

        for (Entity[] line : entities) {
            res.append(String.format("%s+\n", TerrainView
                    .UNIT_EVEN.repeat(terrainView.width)));

            for (Entity e : line) {
                StringBuilder col = new StringBuilder(TerrainView.UNIT_ODD);
                if (e != null) {
                    int place = TerrainView.UNIT_LENGTH/2;

                    String pstr = EntityView.toString(e);
                    col.insert(place, pstr);
                    col.deleteCharAt(place + pstr.length());
                }
                res.append(col);
            }
            res.append("|\n");
        }

        res.append(String.format("%s+\n", TerrainView
                    .UNIT_EVEN.repeat(terrainView.width)));
        return res.toString();
    }
    public String currentTerrain() {
        StringBuilder res = new StringBuilder(terrainView.toString());

        int len = TerrainView.UNIT_LENGTH;

        // +2 pour '+' final et '\n'
        int lnlen = terrainView.displayLength + 2;
        for (Player p : game.getPlayers()) {
            int boardY = lnlen + lnlen*p.getY()*2;
            int boardX = p.getX()*len+2;
            int index = boardY + boardX;
            res.insert(index, PlayerView.toString(p.getId()));
            res.deleteCharAt(index+1);
        }

        return res.toString();
    }

    public String inputError() {
        Player player = game.getTurn().getCurrent();
        if (player == null) {
            throw new IllegalStateException();
        }

        return String.format(
                "%sJoueur %d (%d,%d) mauvais input. Rejoue...%s\n"
                , AnsiColor.RED, player.getId()
                , player.getX(), player.getY(), AnsiColor.RESET);
    }
}
