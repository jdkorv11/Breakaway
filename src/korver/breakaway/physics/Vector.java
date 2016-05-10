package korver.breakaway.physics;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Vector {

    private static final int HORIZONTAL_RIGHT = 0;
    private static final int VERTICAL_DOWN = 90;
    private static final int HORIZONTAL_LEFT = 180;
    private static final int VERTICAL_UP = -90;

    private int xVelocity;
    private int yVelocity;
    private int speed;

    public Vector(int relXVelocity, int relYVelocity, int speed) {
        this.xVelocity = relXVelocity;
        this.yVelocity = relYVelocity;
        this.speed = speed;
    }

    /**
     * Returns the direction represented as degrees based on 0 degrees satisfying the following condition (relXVelocity
     * > 0 && relYVelocity == 0) where positive degrees have a positive relYVelocity and negative degrees have a
     * negative relYVelocity. Values Will range from -179 to 180
     *
     * @return the direction in degrees
     */
    public int getDegrees() {

        if (xVelocity == 0) {
            return yVelocity < 0 ? VERTICAL_UP : VERTICAL_DOWN;
        }
        if (yVelocity == 0) {
            return xVelocity < 0 ? HORIZONTAL_LEFT : HORIZONTAL_RIGHT;
        }
        double slope = (double) Math.abs(yVelocity) / (double) Math.abs(xVelocity);
        int degrees = (int) Math.toDegrees(Math.atan(slope));
        /* adjust the degree to be accurate around the circle */
        if (xVelocity < 0) {
            degrees = HORIZONTAL_LEFT - degrees;
        }
        if (yVelocity < 0) {
            degrees = HORIZONTAL_RIGHT - degrees;
        }
        // return the adjusted degree
        return degrees;
    }

    public int getRelXVelocity() {

        return xVelocity;
    }

    public void setRelXVelocity(int velocity) {
        xVelocity = velocity;
    }

    public int getRelYVelocity() {

        return yVelocity;
    }

    public void setRelYVelocity(int velocity) {
        yVelocity = velocity;
    }

    public int getXVelocity() {
        return (int) Math.round(speed * Math.cos(Math.toRadians(getDegrees())));
    }

    public int getYVelocity() {
        return (int) Math.round(speed * Math.sin(Math.toRadians(getDegrees())));
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
