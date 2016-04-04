package korver.breakaway.logic;

import korver.breakaway.engine.input.InputReader;
import korver.breakaway.physics.Vector;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class InputHandler {

    private static final int DEFAULT_MOVE_LEFT = -20;
    private static final int DEFAULT_MOVE_RIGHT = 20;
    private static final double MOUSE_SENSITIVITY_MULTIPLIER = 20;
    private static final int NO_MOVE = 0;
    private final boolean LOCK_MOUSE_TO_CENTER = true;
    private final InputReader inputReader;
    private boolean rightArrowPressed = false;
    private boolean leftArrowPressed = false;
    private int bumperStep = NO_MOVE;
    private boolean launchQueued = false;
    private BumperDirection lastKeyDirection;

    public InputHandler() {
        this.inputReader = new InputReader(this, LOCK_MOUSE_TO_CENTER);
    }

    public boolean isBumperMove() {
        updateBumperStep();
        readMouseMovement();

        return bumperStep != NO_MOVE;
    }

    private void readMouseMovement() {
        Vector mouseVector = inputReader.getMouseMovement();
        if (mouseVector.getXVelocity() != NO_MOVE) {
            bumperStep = (int) (mouseVector.getXVelocity() * MOUSE_SENSITIVITY_MULTIPLIER);
        }
    }

    public int consumeBumperMove() {
        int step = bumperStep;
        updateBumperStep();
        return step;
    }


    private void updateBumperStep() {
        // if both right and left are true, step in the most recently activated direction
        if ((leftArrowPressed && rightArrowPressed)) {
            switch (lastKeyDirection) {
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
        if (leftArrowPressed) {
            bumperStep = DEFAULT_MOVE_LEFT;
            return;
        }
        // if just right is true, move right
        if (rightArrowPressed) {
            bumperStep = DEFAULT_MOVE_RIGHT;
            return;
        }
        bumperStep = NO_MOVE;
    }


    public void queueLaunch() {
        launchQueued = true;
    }

    public boolean isLaunchQueued() {
        if (launchQueued) {
            launchQueued = false;
            return true;
        }
        return launchQueued;
    }

    public void keyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftArrowPressed = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightArrowPressed = false;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            queueLaunch();
        }
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftArrowPressed = true;
            lastKeyDirection = BumperDirection.LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightArrowPressed = true;
            lastKeyDirection = BumperDirection.RIGHT;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            queueLaunch();
        }
    }

    public InputReader getInputReader() {
        return inputReader;
    }

    private enum BumperDirection {
        LEFT, RIGHT
    }
}
