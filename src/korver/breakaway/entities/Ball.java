package korver.breakaway.entities;

import korver.breakaway.physics.Direction;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Ball extends Rectangle {

    private final int BALL_SIZE = 20;
    private Direction direction;
    private int speed;

    public Ball(Point location) {
        super(location);
        this.setSize(new Dimension(BALL_SIZE, BALL_SIZE));
        direction = new Direction(0, 0);
        speed = 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
