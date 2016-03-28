package korver.breakaway.entities;

import java.awt.*;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Block extends Rectangle {

    private final int HEIGHT = 20;
    private final int WIDTH = 60;

    public Block(Point location) {
        super(location);
        super.setSize(new Dimension(WIDTH, HEIGHT));
    }
}
