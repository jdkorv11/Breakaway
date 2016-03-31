package korver.breakaway.logic;

import korver.breakaway.engine.Utilities;
import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Bumper;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Game {

    private final int BOARD_WIDTH = 1500;
    private final int BOARD_HEIGHT = 840;
    private final int WALL_THICKNESS = 20;
    private final int BUMPER_SPEED_LIMIT = 40;
    private final int DEFAULT_BALL_SPEED = 15;
    private final Bumper bumper;
    private final Ball ball;
    private final InputHandler inputHandler;

    public Game() {
        Point initializationPoint = new Point(0, 0);
        bumper = new Bumper(initializationPoint);
        bumper.setLocation(new Point((int) ((BOARD_WIDTH / 2) - (bumper.getWidth() / 2)),
                                     (int) (BOARD_HEIGHT - bumper.getHeight())));
        ball = new Ball(initializationPoint);
        ball.setLocation(
                new Point(bumper.x + (bumper.width / 2) - ball.width / 2, bumper.y - (int) (ball.height * 0.9)));
        inputHandler = new InputHandler();
    }

    public void update() {
        if (inputHandler.isBumperMove()) {
            submitBumperMove(inputHandler.getBumperMove());
        }
        if (inputHandler.isLaunchQueued()) {
            attemptBallLaunch();
        }
        moveBall();
    }

    private void attemptBallLaunch() {
        // get a random direction for the ball
        int xVel = Utilities.randomWithRange(-4, 4);
        int yVel = Utilities.randomWithRange(1, 5);
        // set the direction to the ball
        ball.getDirection().setRelXVelocity(xVel);
        ball.getDirection().setRelYVelocity(yVel);
        //set the ball's speed
        ball.setSpeed(DEFAULT_BALL_SPEED);
    }

    private void moveBall() {
        int dx = (int) (ball.getSpeed() * Math.cos(Math.toRadians(ball.getDirection().getDegrees())));
        int dy = (int) (ball.getSpeed() * Math.sin(Math.toRadians(ball.getDirection().getDegrees())));

        ball.x += dx;
        ball.y -= dy; // subtract dy because positive direction needs to move toward negative coordinates
    }

    public int getHeight() {
        return BOARD_HEIGHT;// WALL_THICKNESS + BOARD_HEIGHT;
    }

    public int getWidth() {
        return BOARD_WIDTH;// WALL_THICKNESS + BOARD_WIDTH + WALL_THICKNESS;
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

        Point futLocation = new Point(bumper.x + move, bumper.y);

        while (futLocation.x != bumper.x) {
            if (!willCollideWithWall(bumper, futLocation)) {
                bumper.setLocation(futLocation);
            } else {
                move = shrinkStep(move);
                futLocation.x = bumper.x + move;
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

    private boolean willCollideWithWall(Rectangle rect, Point futureLocation) {

        boolean hitsLeftWall = futureLocation.x < 0;
        boolean hitsTopWall = futureLocation.y < 0;
        boolean hitsRightWall = futureLocation.x + rect.width > BOARD_WIDTH;

        return (hitsLeftWall || hitsTopWall || hitsRightWall);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
