package korver.breakaway.entities;

import java.awt.Rectangle;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class Block extends Rectangle {

    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 60;

    public Block(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Block clone() {
        return new Block(x, y);
    }
}
