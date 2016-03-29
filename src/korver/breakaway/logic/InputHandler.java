package korver.breakaway.logic;

import java.awt.event.KeyEvent;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class InputHandler {

    private static final int MOVE_LEFT = -100;
    private static final int MOVE_RIGHT = 100;
    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            game.submitBumperMove(MOVE_LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            game.submitBumperMove(MOVE_RIGHT);
        }
    }
}
