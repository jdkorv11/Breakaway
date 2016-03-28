package korver.breakaway.engine;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Game {

    private final int BOARD_WIDTH = 1500;
    private final int BOARD_HEIGHT = 840;
    private final int WALL_THICKNESS = 20;
    private final int GAME_WIDTH = WALL_THICKNESS + BOARD_WIDTH + WALL_THICKNESS;
    private final int GAME_HEIGHT = WALL_THICKNESS + BOARD_HEIGHT;

    public void update() {

    }

    public int getHeight() {
        return GAME_HEIGHT;
    }

    public int getWidth() {
        return GAME_WIDTH;
    }

    public int getWallThickness() {
        return WALL_THICKNESS;
    }
}
