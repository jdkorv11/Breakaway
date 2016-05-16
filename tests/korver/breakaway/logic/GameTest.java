package korver.breakaway.logic;

import korver.breakaway.entities.Block;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jdkorv11 on 5/12/2016.
 */
public class GameTest {

    @Test
    public void testIsEndOfLevel() {
        GameState testState = new GameState();
        testState.livesLeft = 3;
        testState.blockList.add(new Block(0, 0));
        Game testGame = new Game(testState);

        assertFalse(testGame.isEndOfLevel());
        testState.livesLeft = -1;
        assertTrue(testGame.isEndOfLevel());
        testState.livesLeft = 0;
        assertFalse(testGame.isEndOfLevel());
        testState.livesLeft = 2;
        testState.blockList.clear();
        assertTrue(testGame.isEndOfLevel());
    }


}
