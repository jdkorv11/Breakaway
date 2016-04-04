package korver.breakaway.entities;

import korver.breakaway.physics.Vector;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Ball extends Rectangle {

    private final int BALL_SIZE = 20;
    private Vector vector;

    public Ball(Point location) {
        super(location);
        this.setSize(new Dimension(BALL_SIZE, BALL_SIZE));
        vector = new Vector(0, 0, 0);
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public int getSpeed() {
        return vector.getSpeed();
    }

    public void setSpeed(int speed) {
        vector.setSpeed(speed);
    }
}
