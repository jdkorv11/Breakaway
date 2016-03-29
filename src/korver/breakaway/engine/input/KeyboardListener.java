package korver.breakaway.engine.input;


import korver.breakaway.logic.InputHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class KeyboardListener implements KeyListener {

    private InputHandler handler;

    public KeyboardListener(InputHandler handler) {
        this.handler = handler;
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        handler.keyPressed(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
