package korver.breakaway.engine;

import korver.breakaway.engine.display.GameDisplay;
import korver.breakaway.engine.input.KeyboardListener;
import korver.breakaway.logic.Game;
import korver.breakaway.logic.InputHandler;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.MouseEvent;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Application extends JFrame {

    private static final int DEFAULT_FPS = 60;
    public static final long NANOSECONDS_PER_SECOND = 1000000000;
    private GameDisplay gameView;

    public Application(Game game) {
        super("Breakaway");

        gameView = new GameDisplay(game);
        this.setLayout(new BorderLayout());
        this.add(gameView, BorderLayout.CENTER);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);

        this.addKeyListener(new KeyboardListener(new InputHandler(game)));

//        addWindowListener(this);
//        pack();
//        setResizable(true);
//        setVisible(true);
    }

    public static void main(String args[]) {
        int fps = DEFAULT_FPS;
        if (args.length != 0) {
            fps = Integer.parseInt(args[0]);
        }
        long period = NANOSECONDS_PER_SECOND / fps;

        Game game = new Game();
        Application app = new Application(game);
        Loop gameLoop = new Loop(period, app, game);
        gameLoop.startGame();

        System.out.println("fps: " + fps + "; period: " + period + " ms");
    }

    public void renderGame(Game game) {
        gameView.renderGame(game);
    }

    public void paintScreen() {
        gameView.paintScreen();
    }

}
