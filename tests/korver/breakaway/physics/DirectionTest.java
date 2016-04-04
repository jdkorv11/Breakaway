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

        Vector dEast = new Vector(1, 0,0);
        Vector dNorthEast = new Vector(1, 1,0);
        Vector dNorth = new Vector(0, 1,0);
        Vector dNorthWest = new Vector(-1, 1,0);
        Vector dWest = new Vector(-1, 0,0);
        Vector dSouthWest = new Vector(-1, -1,0);
        Vector dSouth = new Vector(0, -1,0);
        Vector dSouthEast = new Vector(1, -1,0);
        Vector dZeroZero = new Vector(0, 0,0);

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
        Vector d1 = new Vector(x1, y,0);
        Vector d2 = new Vector(x2, y,0);
        assertEquals(x1, d1.getRelXVelocity());
        assertEquals(x2, d2.getRelXVelocity());
    }

    @Test
    public void testGetRelYVelocity() throws Exception {
        byte y1 = 5;
        byte y2 = -2;
        byte x = 3;
        Vector d1 = new Vector(x, y1,0);
        Vector d2 = new Vector(x, y2,0);
        assertEquals(y1, d1.getRelYVelocity());
        assertEquals(y2, d2.getRelYVelocity());
    }
}