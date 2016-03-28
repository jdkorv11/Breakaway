package korver.breakaway.engine.display;

import korver.breakaway.engine.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class GameDisplay extends JPanel {

    private Image dbImage;
    private Graphics graphics;

    private int dispWidth;
    private int dispHeight;
    private JFrame container;

    public GameDisplay(Game game) {
        super();
        dispWidth = game.getWidth();
        dispHeight = game.getHeight();
        // get hold the content of the frame and set up the
        // resolution of the game
        setPreferredSize(new Dimension(dispWidth, dispHeight));
        setLayout(null);
        setDoubleBuffered(true);
        setIgnoreRepaint(true);

        // create a frame to contain our game
        // setup our canvas size and put it into the content of the frame
        container = new JFrame("Breakaway");
        container.setLayout(new BorderLayout());
        container.add(this, BorderLayout.CENTER);
        container.pack();
        container.setResizable(true);
        container.setVisible(true);
    }

    public void renderGame(Game game) {
        if (dbImage == null) {
            dbImage = createImage(getWidth(), getHeight());
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = dbImage.getGraphics();
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
        DrawingUtils.drawGame(game, graphics);

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
