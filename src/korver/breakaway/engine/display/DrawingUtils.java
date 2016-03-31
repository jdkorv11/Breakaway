package korver.breakaway.engine.display;

import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Bumper;
import korver.breakaway.logic.Game;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class DrawingUtils {

    private final int CORNER_ROUNDING_RADIUS = 7;

    /**
     * Draws a game onto a graphics object
     * @param game the game to be drawn
     * @param graphics the graphics to draw the game to
     */
    void drawGame(Game game, Graphics graphics) {
        // get wall and game dimensions
        int gameHeight = game.getHeight();
        int gameWidth = game.getWidth();
        int wallThickness = game.getWallThickness();
        // draw the walls
        //drawWalls(graphics, gameHeight, gameWidth, wallThickness);
        // draw the bumper
        drawBumper(graphics, game.getBumper());
        drawBall(graphics, game.getBall());
    }

    private void drawBall(Graphics graphics, Ball ball) {
        graphics.setColor(Color.GRAY);
        graphics.fillOval(ball.x, ball.y, ball.width, ball.height);
    }

    private void drawBumper(Graphics graphics, Bumper bumper) {
        graphics.setColor(Color.GRAY);
        graphics.fillRoundRect(bumper.x, bumper.y, bumper.width, bumper.height, CORNER_ROUNDING_RADIUS, CORNER_ROUNDING_RADIUS);
    }

    private void drawWalls(Graphics graphics, int gameHeight, int gameWidth, int wallThickness) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, wallThickness, gameHeight);//left
        graphics.fillRect(0, 0, gameWidth, wallThickness);//top
        graphics.fillRect(gameWidth - wallThickness, 0, wallThickness, gameHeight);//right
    }

}
