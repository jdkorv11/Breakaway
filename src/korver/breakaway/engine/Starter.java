package korver.breakaway.engine;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Starter {

    private static final int DEFAULT_FPS = 60;
    private Loop gameLoop;

    public Starter(long period) {
        makeGUI(period);
        gameLoop = new Loop(period);
        gameLoop.startGame();
//        addWindowListener(this);
//        pack();
//        setResizable(true);
//        setVisible(true);
    }

    private void makeGUI(long period) {

        JPanel ctrls = new JPanel(); // a row of textfields
        ctrls.setLayout(new BoxLayout(ctrls, BoxLayout.X_AXIS));

        JTextField jtfTime = new JTextField("Time Spent: 0 secs");
        jtfTime.setEditable(false);
        ctrls.add(jtfTime);
    }

    public static void main(String args[]) {
        int fps = DEFAULT_FPS;
        if (args.length != 0)
            fps = Integer.parseInt(args[0]);

        long period = (long) 1000.0 / fps;
        System.out.println("fps: " + fps + "; period: " + period + " ms");

        new Starter(period * 1000000L); // ms --> nanosecs
    }
}
