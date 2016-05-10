package korver.breakaway.physics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jdkorv11 on 3/24/2016.
 */
public class VectorTest {

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

        Vector vEast = new Vector(1, 0, 0);
        Vector vNorthEast = new Vector(1, 1, 0);
        Vector vNorth = new Vector(0, 1, 0);
        Vector vNorthWest = new Vector(-1, 1, 0);
        Vector vWest = new Vector(-1, 0, 0);
        Vector vSouthWest = new Vector(-1, -1, 0);
        Vector vSouth = new Vector(0, -1, 0);
        Vector vSouthEast = new Vector(1, -1, 0);
        Vector vZeroZero = new Vector(0, 0, 0);

        assertEquals(east, vEast.getDegrees());
        assertEquals(northEast, vNorthEast.getDegrees());
        assertEquals(north, vNorth.getDegrees());
        assertEquals(northWest, vNorthWest.getDegrees());
        assertEquals(west, vWest.getDegrees());
        assertEquals(southWest, vSouthWest.getDegrees());
        assertEquals(south, vSouth.getDegrees());
        assertEquals(southEast, vSouthEast.getDegrees());
        assertEquals(north, vZeroZero.getDegrees());

    }

    @Test
    public void testGetRelXVelocity() throws Exception {
        int x1 = 5;
        int x2 = -2;
        int y = 3;
        Vector v1 = new Vector(x1, y, 0);
        Vector v2 = new Vector(x2, y, 0);
        assertEquals(x1, v1.getRelXVelocity());
        assertEquals(x2, v2.getRelXVelocity());
    }

    @Test
    public void testSetRelXVelocity() throws Exception {
        int x1 = 5;
        int x2 = -2;
        int y = 3;
        Vector v = new Vector(x1, y, 0);
        assertEquals(x1, v.getRelXVelocity());
        v.setRelXVelocity(x2);
        assertEquals(x2, v.getRelXVelocity());
    }

    @Test
    public void testGetRelYVelocity() throws Exception {
        int y1 = 5;
        int y2 = -2;
        int x = 3;
        Vector v1 = new Vector(x, y1, 0);
        Vector v2 = new Vector(x, y2, 0);
        assertEquals(y1, v1.getRelYVelocity());
        assertEquals(y2, v2.getRelYVelocity());
    }

    @Test
    public void testSetRelYVelocity() throws Exception {
        int y1 = 5;
        int y2 = -2;
        int x = 3;
        Vector v = new Vector(x, y1, 0);
        assertEquals(y1, v.getRelYVelocity());
        v.setRelYVelocity(y2);
        assertEquals(y2, v.getRelYVelocity());
    }

    @Test
    public void testGetSpeed() throws Exception {
        int y = 4;
        int x = 3;
        int sp1 = 5;
        int sp2 = 6;
        Vector d1 = new Vector(x, y, sp1);
        Vector d2 = new Vector(x, y, sp2);
        assertEquals(sp1, d1.getSpeed());
        assertEquals(sp2, d2.getSpeed());
    }

    @Test
    public void testSetSpeed() throws Exception {
        int y = 4;
        int x = 3;
        int sp1 = 5;
        int sp2 = 6;
        Vector v1 = new Vector(x, y, sp1);
        assertEquals(sp1, v1.getSpeed());
        v1.setSpeed(sp2);
        assertEquals(sp2, v1.getSpeed());
    }

    @Test
    public void testGetXVelocity() {
        int x = 3;
        int y = 4;
        int speed = 10;
        int expectedXVel = 6;
        Vector v = new Vector(x, y, speed);
        assertEquals(expectedXVel, v.getXVelocity());
        x = -3;
        expectedXVel = -6;
        v = new Vector(x, y, speed);
        assertEquals(expectedXVel, v.getXVelocity());
    }

    @Test
    public void testGetYVelocity() {
        int x = 4;
        int y = 3;
        int speed = 10;
        int expectedYVel = 6;
        Vector v = new Vector(x, y, speed);
        assertEquals(expectedYVel, v.getYVelocity());
        y = -3;
        expectedYVel = -6;
        v = new Vector(x, y, speed);
        assertEquals(expectedYVel, v.getYVelocity());
    }
}