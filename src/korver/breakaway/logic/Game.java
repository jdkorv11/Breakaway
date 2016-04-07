package korver.breakaway.logic;

import korver.breakaway.engine.Utilities;
import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Bumper;
import korver.breakaway.physics.Vector;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Game {

    private final int BOARD_WIDTH = 1500;
    private final int BOARD_HEIGHT = 840;
    private final int WALL_THICKNESS = 20;
    private final int BUMPER_SPEED_LIMIT = 20;
    private final int DEFAULT_BALL_SPEED = 10;
    private final int OFF_SCREEN_MARGIN = 50;
    private final Bumper bumper;
    private final Ball ball;
    private final InputHandler inputHandler;
    private Point testPoint = new Point(0, 0);

    public Game() {
        inputHandler = new InputHandler();

        // create a bumper and center it on the bottom of the board
        bumper = new Bumper(testPoint);
        bumper.setLocation(new Point((int) ((BOARD_WIDTH / 2) - (bumper.getWidth() / 2)),
                                     (int) (BOARD_HEIGHT - bumper.getHeight())));
        //create a ball and place it on the bumper
        ball = new Ball(testPoint);
        initializeBall();

    }

    private void initializeBall() {
        ball.setLocation(
                new Point(bumper.x + (bumper.width / 2) - ball.width / 2, bumper.y - (int) (ball.height * 0.9)));
        ball.setSpeed(0);
        bumper.hasBall = true;
    }

    public void update() {
        if (isAlive()) {
            if (inputHandler.isBumperMove()) {
                submitBumperMove(inputHandler.consumeBumperMove());
            }
            if (inputHandler.isLaunchQueued()) {
                attemptBallLaunch();
            }
            processBallMove();
        } else {
            initializeBall();
        }
    }

    private boolean isAlive() {
        return ball.y < BOARD_HEIGHT + OFF_SCREEN_MARGIN;
    }

    private void attemptBallLaunch() {
        // verify the ball is on the bumper and launch if it is
        if (bumper.hasBall) {
            launchBall();
        }
    }

    private void launchBall() {
        // get a random direction for the ball
        int xVel;
        int xVel1 = Utilities.randomWithRange(-5, -2);
        int xVel2 = Utilities.randomWithRange(2, 5);
        int xVelSelector = Utilities.randomWithRange(1, 2);
        if (xVelSelector == 1) {
            xVel = xVel1;
        } else {
            xVel = xVel2;
        }
        int yVel = Utilities.randomWithRange(-5, -2);
        // set the ball's vector
        ball.setVector(new Vector(xVel, yVel, DEFAULT_BALL_SPEED));

        // remove ball from bumper
        bumper.hasBall = false;
    }

    private void processBallMove() {
        int dx = ball.getVector().getXVelocity();
        int dy = ball.getVector().getYVelocity();

        if (willCollideWithWall(ball, dx, dy)) {
            reflectBallOffWall(dx, dy);
        } else if (willCollide(ball, dx, dy, bumper)) {
            reflectBallOffObject(dx, dy, bumper);
        }
        moveBall();
    }

    private void moveBall() {
        ball.x += ball.getVector().getXVelocity();
        ball.y += ball.getVector().getYVelocity();
    }

    private void reflectBallOffBumper() {
        // reflect the ball
        int dx = ball.getVector().getXVelocity();
        int dy = ball.getVector().getYVelocity();
        reflectBallOffObject(dx, dy, bumper);

        // prevent deadlock by moving the ball up so the next step will not collide with the bumper
        dy = ball.getVector().getYVelocity();
        dx = ball.getVector().getXVelocity();
        while (!bumper.hasBall && willCollide(ball, dx, dy, bumper)) {
            ball.x += dx;
            ball.y += dy;
        }
    }

    private void reflectBallOffObject(int dx, int dy, Rectangle rectangle) {
        Vector ballDir = ball.getVector();
        boolean collidesNow = willCollide(ball, 0, 0, rectangle);
        if (willCollide(ball, dx, 0, rectangle) && !collidesNow) {
            dx = dx * -1;
        }
        if (willCollide(ball, 0, dy, rectangle) && !collidesNow) {
            dy = dy * -1;
        }
        ballDir.setRelXVelocity(dx);
        ballDir.setRelYVelocity(dy);

    }

    private void reflectBallOffWall(int dx, int dy) {
        Vector ballDir = ball.getVector();
        if (willHitLeftWall(ball, dx) || willHitRightWall(ball, dx)) {
            dx = -1 * dx;
        }
        if (willHitTopWall(ball, dy)) {
            dy = -1 * dy;
        }
        ballDir.setRelXVelocity(dx);
        ballDir.setRelYVelocity(dy);
    }

    /**
     * Checks to see if the projectile will collide with the object when it is moved by specified amounts
     *
     * @param projectile
     *         the rectangle to see if will collide when moved
     * @param dx
     *         the amount to move in the x direction
     * @param dy
     *         the amount to move in the y direction
     * @param object
     *         the object to see if the projectile collides with
     * @return whether the projectile will collide with the object when moved by dx and dy
     */
    private boolean willCollide(Rectangle projectile, int dx, int dy, Rectangle object) {
        projectile.x += dx;
        projectile.y += dy;
        boolean collides = ball.intersects(object);
        projectile.x -= dx;
        projectile.y -= dy;
        return collides;
    }

    public int getHeight() {
        return BOARD_HEIGHT;
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public int getWallThickness() {
        return WALL_THICKNESS;
    }

    public Bumper getBumper() {
        return bumper;
    }

    public Ball getBall() {
        return ball;
    }

    public void submitBumperMove(int move) {
        if (move < -1 * BUMPER_SPEED_LIMIT) {
            move = -1 * BUMPER_SPEED_LIMIT;
        } else if (move > BUMPER_SPEED_LIMIT) {
            move = BUMPER_SPEED_LIMIT;
        }

        testPoint.x = bumper.x + move;
        testPoint.y = bumper.y;

        while (testPoint.x != bumper.x) {
            if (!willCollideWithWall(bumper, move, 0)) {
                bumper.x = bumper.x + move;
                if (bumper.hasBall) {
                    ball.x = ball.x + move;
                }
            } else {
                move = shrinkStep(move);
                testPoint.x = bumper.x + move;
            }
        }

    }

    private int shrinkStep(int step) {
        if (step < 0) {
            return step + 1;
        } else if (step > 0) {
            return step - 1;
        }
        return step;

    }

    private boolean willCollideWithWall(Rectangle rect, int dx, int dy) {

        return willHitLeftWall(rect, dx) || willHitTopWall(rect, dy) || willHitRightWall(rect, dx);
    }

    private boolean willHitRightWall(Rectangle rect, int dx) {
        return (rect.x + dx + rect.width) > BOARD_WIDTH;
    }

    private boolean willHitTopWall(Rectangle rect, int dy) {
        return (rect.y + dy) < 0;
    }

    private boolean willHitLeftWall(Rectangle rect, int dx) {
        return (rect.x + dx) < 0;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
