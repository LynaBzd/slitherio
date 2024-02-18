package fr.uparis.informatique.cpoo5.game;

import fr.uparis.informatique.cpoo5.game.model.Game;
import fr.uparis.informatique.cpoo5.game.model.Player;
import fr.uparis.informatique.cpoo5.game.view.GameView;

public final class Main {
    public static void main(String[] args) throws Exception {
        Game game = Game.builder()
                        .setBoard(14, 17)
                        .build()
        ;

        new GameView(game);
        
        game.spawnPlayer(Player.Type.REAL);
        game.spawnPlayer(Player.Type.AI_RANDOM);
        game.spawnPlayer(Player.Type.AI_RANDOM);

        game.run();
    }
}

