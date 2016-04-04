package korver.breakaway.entities;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Bumper extends Rectangle{

    private final int HEIGHT = 15;
    private final int WIDTH = 75;
    public boolean hasBall = false;

    public Bumper(Point location) {
        super(location);
        super.setSize(new Dimension(WIDTH, HEIGHT));
    }

}
