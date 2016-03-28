package korver.breakaway.engine.display;

import korver.breakaway.engine.Game;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class DrawingUtils {

    /**
     * Draws a game onto a graphics object
     * @param game the game to be drawn
     * @param graphics the graphics to draw the game to
     */
    static void drawGame(Game game, Graphics graphics) {
        // get wall and game dimensions
        int gameHeight = game.getHeight();
        int gameWidth = game.getWidth();
        int wallThickness = game.getWallThickness();
        // draw the walls
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, wallThickness, gameHeight);//left
        graphics.fillRect(0, 0, gameWidth, wallThickness);//top
        graphics.fillRect(gameWidth - wallThickness, 0, wallThickness, gameHeight);//right

    }
}
