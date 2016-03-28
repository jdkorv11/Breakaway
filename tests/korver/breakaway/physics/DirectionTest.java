package korver.breakaway.physics;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class DirectionTest {

    @Test
    public void testGetDegrees() throws Exception {
        int east = 0;
        int northEast = 45;
        int north = 90;
        int northWest = 135;
        int west = 180;
        int southWest = -135;
        int south = -90;
        int southEast = -45;

        Direction dEast = new Direction(1,0);
        Direction dNorthEast = new Direction(1,1);
        Direction dNorth = new Direction(0,1);
        Direction dNorthWest = new Direction(-1,1);
        Direction dWest = new Direction(-1,0);
        Direction dSouthWest = new Direction(-1,-1);
        Direction dSouth = new Direction(0,-1);
        Direction dSouthEast = new Direction(1,-1);
        Direction dZeroZero = new Direction (0,0);

        assertEquals(east, dEast.getDegrees());
        assertEquals(northEast, dNorthEast.getDegrees());
        assertEquals(north, dNorth.getDegrees());
        assertEquals(northWest, dNorthWest.getDegrees());
        assertEquals(west, dWest.getDegrees());
        assertEquals(southWest, dSouthWest.getDegrees());
        assertEquals(south, dSouth.getDegrees());
        assertEquals(southEast, dSouthEast.getDegrees());
        assertEquals(north, dZeroZero.getDegrees());

    }

    @Test
    public void testGetRelXVelocity() throws Exception {
        byte x1 = 5;
        byte x2 = -2;
        byte y = 3;
        Direction d1 = new Direction(x1, y);
        Direction d2 = new Direction(x2, y);
        assertEquals(x1, d1.getRelXVelocity());
        assertEquals(x2, d2.getRelXVelocity());
    }

    @Test
    public void testGetRelYVelocity() throws Exception {
        byte y1 = 5;
        byte y2 = -2;
        byte x = 3;
        Direction d1 = new Direction(x, y1);
        Direction d2 = new Direction(x, y2);
        assertEquals(y1, d1.getRelYVelocity());
        assertEquals(y2, d2.getRelYVelocity());
    }
}