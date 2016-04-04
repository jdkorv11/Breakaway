package korver.breakaway.engine.input;

import korver.breakaway.logic.InputHandler;
import korver.breakaway.physics.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jdkorv11 on 3/29/2016.
 */
public class InputReader implements MouseListener, KeyListener{

    private final Dimension screenDims;
    private Robot robot;
    private Point lastMousePosition;
    private boolean CENTER_MOUSE_FLAG;
    private final InputHandler handler;

    public InputReader(InputHandler handler, boolean lockMouseOnCenter) {
        this.handler = handler;
        this.CENTER_MOUSE_FLAG = lockMouseOnCenter;
        screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void lockMouse(){
        CENTER_MOUSE_FLAG = true;
    }
    public void unlockMouse(){
        CENTER_MOUSE_FLAG = false;
    }
    /**
     * returns a vector that depicts the mouse movement from that last time the method was called. The first time this
     * is calls it will return as though there was no movement
     *
     * @return
     */
    public Vector getMouseMovement() {
        PointerInfo inf = MouseInfo.getPointerInfo();
        Point mousePosition = inf.getLocation();
        // return vector of no movement when first called so set lastMousePosition to current position
        if (lastMousePosition == null) {
            lastMousePosition = mousePosition;
        }
        int dx = mousePosition.x - lastMousePosition.x;
        int dy = mousePosition.y - lastMousePosition.y;
        int distance = (int) Math.sqrt((dx * dx) + (dy * dy));

        // if locking mouse to the center of the screen reposition the mouse
        if (CENTER_MOUSE_FLAG) {
            robot.mouseMove(screenDims.width / 2, screenDims.height / 2);
            mousePosition = MouseInfo.getPointerInfo().getLocation();
        }
        // save the mouse position as the last position
        lastMousePosition = mousePosition;

        // return the vector
        return new Vector(dx, dy, distance);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        handler.mouseClicked(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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
        handler.keyReleased(keyEvent.getKeyCode());
    }
}
