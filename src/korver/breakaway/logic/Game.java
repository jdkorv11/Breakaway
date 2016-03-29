package korver.breakaway.logic;

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
    private final int BUMPER_SPEED_LIMIT = 10;
    private final Bumper bumper;

    public Game() {
        bumper = new Bumper(new Point(0, 0));
        bumper.setLocation(new Point((int) ((BOARD_WIDTH / 2) - (bumper.getWidth() / 2)),
                                     (int) (BOARD_HEIGHT - bumper.getHeight())));
    }

    public void update() {

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

    public void submitBumperMove(int move) {
        if (move < -1 * BUMPER_SPEED_LIMIT) {
            move = -1 * BUMPER_SPEED_LIMIT;
        } else if (move > BUMPER_SPEED_LIMIT) {
            move = BUMPER_SPEED_LIMIT;
        }
        Point futLocation = new Point(bumper.x + move, bumper.y);
        if (!willCollideWithWall(bumper, futLocation)) {
            bumper.setLocation(futLocation);
        }

    }

    private boolean willCollideWithWall(Rectangle rect, Point futureLocation) {

        boolean hitsLeftWall = futureLocation.x < 0;
        boolean hitsTopWall = futureLocation.y < 0;
        boolean hitsRightWall = futureLocation.x + rect.width > BOARD_WIDTH;

        return (hitsLeftWall || hitsTopWall || hitsRightWall);
    }
}
