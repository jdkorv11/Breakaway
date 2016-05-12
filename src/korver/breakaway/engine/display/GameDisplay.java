package korver.breakaway.engine.display;

import korver.breakaway.logic.Game;
import korver.breakaway.logic.GameState;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class GameDisplay extends JPanel {

    private Image bufferImage;
    private Graphics2D graphics;
    private Image displayImage;

    private int dispWidth;
    private int dispHeight;
    private int gameWidth;
    private int gameHeight;

    private DrawingUtils drawingHelper;

    public GameDisplay(Game game) {
        super();
        gameWidth = game.getWidth();
        gameHeight = game.getHeight();
        dispWidth = gameWidth;
        dispHeight = gameHeight;

        drawingHelper = new DrawingUtils();
        // get hold the content of the frame and set up the
        // resolution of the game
        setPreferredSize(new Dimension(dispWidth, dispHeight));
        setLayout(null);
        setDoubleBuffered(true);
        setIgnoreRepaint(true);
    }

    public void renderGame(GameState game) {
        if (bufferImage == null) {
            bufferImage = createImage(gameWidth, gameHeight);
            if (bufferImage == null) {
                System.out.println("bufferImage is null");
                return;
            } else {
                graphics = (Graphics2D) bufferImage.getGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            }
        }

        // clear the background
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, gameWidth, gameHeight);

        // draw game elements: the obstacles and the worm
        drawingHelper.drawGame(game, graphics);

        // scale the image to the panel size
        if (getWidth() != dispWidth || getHeight() != dispHeight) {
            dispHeight = getHeight();
            dispWidth = getWidth();
        }
        displayImage = bufferImage.getScaledInstance(dispWidth, dispHeight, Image.SCALE_DEFAULT);
    }

    public void paintScreen()
    // use active rendering to put the buffered image on-screen
    {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (displayImage != null)) {
                g.drawImage(displayImage, 0, 0, null);
            }
            // Sync the display on some systems.
            // (on Linux, this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) // quite commonly seen at applet destruction
        {
            System.out.println("Graphics error: " + e);
        }
    }
}
