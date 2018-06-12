package vaperio.controllers;

import ggi.core.AbstractGameState;
import ggi.core.SimplePlayerInterface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyController extends KeyAdapter implements SimplePlayerInterface {

    private final Set<Integer> pressed = new HashSet<>();

    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }


    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    private int calculateActionWithoutDirection() {
        if (!pressed.contains(KeyEvent.VK_H) && pressed.contains(KeyEvent.VK_SPACE)) {
            return 9;
        }
        if (pressed.contains(KeyEvent.VK_H) && !pressed.contains(KeyEvent.VK_SPACE)) {
            return 18;
        }
        if (pressed.contains(KeyEvent.VK_H) && pressed.contains(KeyEvent.VK_SPACE)) {
            return 27;
        }
        return 0;
    }

    // 0: no movement. 1: up. 2: up+right. Follows clockwise fashion
    private int addDirectionOffset() {
        if (pressed.contains(KeyEvent.VK_UP) &&  pressed.contains(KeyEvent.VK_RIGHT)) {
            return 2;
        }
        if (pressed.contains(KeyEvent.VK_UP) &&  pressed.contains(KeyEvent.VK_LEFT)) {
            return 8;
        }
        if (pressed.contains(KeyEvent.VK_DOWN) &&  pressed.contains(KeyEvent.VK_RIGHT)) {
            return 4;
        }
        if (pressed.contains(KeyEvent.VK_DOWN) &&  pressed.contains(KeyEvent.VK_LEFT)) {
            return 6;
        }
        if (pressed.contains(KeyEvent.VK_UP)) {
            return 1;
        }
        if (pressed.contains(KeyEvent.VK_RIGHT)) {
            return 3;
        }
        if (pressed.contains(KeyEvent.VK_DOWN)) {
            return 5;
        }
        if (pressed.contains(KeyEvent.VK_LEFT)) {
            return 7;
        }
        return 0;
    }

    @Override
    public int getAction(AbstractGameState gameState, int playerId) {
        int actionWithoutDirection = calculateActionWithoutDirection();
        return actionWithoutDirection + addDirectionOffset();
    }

    @Override
    public SimplePlayerInterface reset() {
        return this;
    }
}
