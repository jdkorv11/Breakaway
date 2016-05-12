package korver.breakaway.logic;

import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Block;
import korver.breakaway.entities.Bumper;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdkorv11 on 5/10/2016.
 */
public class GameState {

    public Bumper bumper = new Bumper(new Point(0, 0));
    public Ball ball = new Ball(new Point(0,0));
    public List<Block> blockList = new ArrayList();
    public int livesLeft = 0;

    public GameState mimic(GameState gameState) {
        mimic(gameState.bumper, bumper);
        mimic(gameState.ball, ball);
        for(Block block: blockList){
            gameState.blockList.add(block.clone());
        }
        return gameState;
    }


    private void mimic(Rectangle copy, Rectangle original){
        copy.x = original.x;
        copy.y = original.y;
        copy.width = original.width;
        copy.height = original.height;
    }

}
