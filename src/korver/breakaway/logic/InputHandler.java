package korver.breakaway.logic;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class InputHandler implements MouseMotionListener {

    private static final int DEFAULT_MOVE_LEFT = -50;
    private static final int DEFAULT_MOVE_RIGHT = 50;
    private static final double MOUSE_SENSITIVITY_MULTIPLIER = 3.5;
    private static final int NO_MOVE = 0;
    private boolean rightArrowPressed = false;
    private boolean leftArrowPressed = false;
    private int bumperStep = NO_MOVE;
    private Point lastMouseLocation;
    private boolean launchQueued = false;

    public boolean isBumperMove() {
        return bumperStep != NO_MOVE;
    }

    public int getBumperMove() {
        return bumperStep;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftArrowPressed = true;
            updateBumperStep(BumperDirection.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightArrowPressed = true;
            updateBumperStep(BumperDirection.RIGHT);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            launchQueued = true;
        }
    }

    private void mouseLocationChanged(Point mouseLocation) {
        if (lastMouseLocation == null) {
            lastMouseLocation = mouseLocation;
            return;
        }
        bumperStep = (int) ((mouseLocation.x - lastMouseLocation.x) * MOUSE_SENSITIVITY_MULTIPLIER);
        lastMouseLocation = mouseLocation;
    }

    private void updateBumperStep(BumperDirection lastDirection) {
        // if both right and left are true, step in the most recently activated direction
        if ((leftArrowPressed && rightArrowPressed)) {
            switch (lastDirection) {
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

    public void keyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftArrowPressed = false;
            updateBumperStep(BumperDirection.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightArrowPressed = false;
            updateBumperStep(BumperDirection.RIGHT);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseLocationChanged(mouseEvent.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseLocationChanged(mouseEvent.getPoint());
    }

    public boolean isLaunchQueued() {
        if (launchQueued) {
            launchQueued = false;
            return true;
        }
        return launchQueued;
    }

    private enum BumperDirection {
        LEFT, RIGHT
    }
}
