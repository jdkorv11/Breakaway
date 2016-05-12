package korver.breakaway.engine;

import korver.breakaway.engine.display.GameDisplay;
import korver.breakaway.logic.Game;
import korver.breakaway.logic.GameState;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Application extends JFrame implements WindowListener {

    public static final long NANOSECONDS_PER_SECOND = 1000000000;
    private static final int DEFAULT_FPS = 60;
    private static Loop gameLoop;
    private GameDisplay gameView;
    private GameState renderState = new GameState();

    public Application(Game game) {
        super("Breakaway");

        gameView = new GameDisplay(game);
        this.setLayout(new BorderLayout());
        this.add(gameView, BorderLayout.CENTER);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);

        this.addWindowListener(this);
        this.addMouseListener(game.getInputHandler().getInputReader());
        this.addKeyListener(game.getInputHandler().getInputReader());
    }

    public static void main(String args[]) {
        int fps = DEFAULT_FPS;
        if (args.length != 0) {
            fps = Integer.parseInt(args[0]);
        }
        long period = NANOSECONDS_PER_SECOND / fps;

        Game game = new Game();
        Application app = new Application(game);
        gameLoop = new Loop(period, app, game);
        gameLoop.startGame();

        System.out.println("fps: " + fps + "; period: " + period + " ms");
    }

    public void renderGame(Game game) {
        renderState = game.getState().mimic(renderState);
        gameView.renderGame(game.getState());
    }

    public void paintScreen() {
        gameView.paintScreen();
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.stopGame();
        }
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.stopGame();
        }
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.pauseGame();
        }
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.resumeGame();
        }    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.resumeGame();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
        if (gameLoop != null) {
            gameLoop.pauseGame();
        }
    }
}
