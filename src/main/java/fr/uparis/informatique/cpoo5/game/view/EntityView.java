package fr.uparis.informatique.cpoo5.game.view;

import fr.uparis.informatique.cpoo5.game.model.Entity;
import fr.uparis.informatique.cpoo5.game.model.Food;
import fr.uparis.informatique.cpoo5.game.model.Segment;

public abstract class EntityView {
    public static String toString(Entity ent) {
        if (ent instanceof Food) {
            return AnsiColor.PURPLE_BRIGHT + '@' + AnsiColor.RESET;
        } else if (ent instanceof Segment) {
            Segment seg = (Segment) ent;
            char plid = (char)('0'+seg.getOwner().getId());
            
            if (seg.isHead()) {
                return AnsiColor.GREEN_BRIGHT + plid + AnsiColor.RESET;
            } else {
                return AnsiColor.YELLOW_BRIGHT + plid + AnsiColor.RESET;
            }
        }

        return "?";
    }
}
