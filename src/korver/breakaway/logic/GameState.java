package korver.breakaway.logic;

import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Block;
import korver.breakaway.entities.Bumper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdkorv11 on 5/10/2016.
 */
public class GameState {

    public Bumper bumper;
    public Ball ball;
    public List<Block> blockList = new ArrayList();

}
