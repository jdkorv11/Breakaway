package korver.breakaway.engine.display;

import korver.breakaway.logic.Game;
import sun.java2d.pipe.RenderingEngine;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class GameDisplay extends JPanel {

    private Image dbImage;
    private Graphics2D graphics;

    private int dispWidth;
    private int dispHeight;

    private DrawingUtils drawingHelper;

    public GameDisplay(Game game) {
        super();
        dispWidth = game.getWidth();
        dispHeight = game.getHeight();

        drawingHelper = new DrawingUtils();
        // get hold the content of the frame and set up the
        // resolution of the game
        setPreferredSize(new Dimension(dispWidth, dispHeight));
        setLayout(null);
        setDoubleBuffered(true);
        setIgnoreRepaint(true);
    }

    public void renderGame(Game game) {
        if (dbImage == null) {
            dbImage = createImage(getWidth(), getHeight());
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = (Graphics2D) dbImage.getGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            }
        }
        // scale the image to the panel size
        if (getWidth() != dispWidth || getHeight() != dispHeight) {
            dispHeight = getHeight();
            dispWidth = getWidth();
            dbImage = dbImage.getScaledInstance(dispWidth, dispHeight, Image.SCALE_DEFAULT);
        }

        // clear the background
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, dispWidth, dispHeight);

        // draw game elements: the obstacles and the worm
        drawingHelper.drawGame(game, graphics);

        graphics.setColor(Color.WHITE);
//        graphics.setFont(font);

        // report frame count & average FPS and UPS at top left
        // graphics.drawString("Frame Count " + frameCount, 10, 25);
//        graphics.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " + df.format(averageUPS),
//                            20, 25); // was (10,55)

        graphics.setColor(Color.black);


//        if (gameOver) {
//            gameOverMessage(graphics);
//        }
    }

    public void paintScreen()
    // use active rendering to put the buffered image on-screen
    {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
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
