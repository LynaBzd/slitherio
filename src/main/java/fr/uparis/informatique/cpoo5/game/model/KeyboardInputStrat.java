package fr.uparis.informatique.cpoo5.game.model;

import java.util.Scanner;

import fr.uparis.informatique.cpoo5.game.controller.KeyboardControls;

public final class KeyboardInputStrat implements InputStrat {
    Scanner sc = new Scanner(System.in);
    KeyboardControls controls;

    public KeyboardInputStrat(KeyboardControls controls) {
        this.controls = controls;
    }

    @Override
    public Direction input() {
        char input = scan();

        if (input == controls.up) {
            return Direction.UP;
        } else if (input == controls.left) {
            return Direction.LEFT;
        } else if (input == controls.down) {
            return Direction.DOWN;
        } else if (input == controls.right) {
            return Direction.RIGHT;
        } else {
            return null;
        }
    }
    
    private char scan() {
        return sc.next().toLowerCase().charAt(0);
    }
}

