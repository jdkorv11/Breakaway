package korver.breakaway.logic;

import korver.breakaway.entities.Bumper;

import java.awt.event.KeyEvent;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class InputHandler {

    private static final int DEFAULT_MOVE_LEFT = -100;
    private static final int DEFAULT_MOVE_RIGHT = 100;
    private static final int NO_MOVE = 0;
    private boolean bumperMoveRight = false;
    private boolean bumperMoveLeft = false;
    private int bumperStep = NO_MOVE;
    private int lastBumperStep = NO_MOVE;

    public boolean isBumperMove() {
        return bumperMoveRight || bumperMoveLeft;
    }

    public int getBumperStep() {
        return bumperStep;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            bumperMoveLeft = true;
            updateBumperStep(BumperDirection.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            bumperMoveRight = true;
            updateBumperStep(BumperDirection.RIGHT);
        }
    }

    private void updateBumperStep(BumperDirection lastDirection) {
        // if both right and left are true, step in the most recently activated direction
        if ((bumperMoveLeft && bumperMoveRight)){
            switch (lastDirection){
                case LEFT:
                    bumperStep = DEFAULT_MOVE_LEFT;
                    break;
                case RIGHT:
                    bumperStep = DEFAULT_MOVE_RIGHT;
                    break;
            }
            return;
        }
        // if just left is true, move left
        if (bumperMoveLeft){
            bumperStep = DEFAULT_MOVE_LEFT;
            return;
        }
        // if just right is true, move right
        if (bumperMoveRight){
            bumperStep = DEFAULT_MOVE_RIGHT;
            return;
        }
        bumperStep = NO_MOVE;
    }

    public void keyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            bumperMoveLeft = false;
            updateBumperStep(BumperDirection.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            bumperMoveRight = false;
            updateBumperStep(BumperDirection.RIGHT);
        }
    }

    private enum BumperDirection {
        LEFT, RIGHT
    }
}
