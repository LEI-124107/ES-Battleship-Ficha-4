package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

class GalleonTest {

    private IPosition pos(int r, int c) {
        return new Position(r, c);
    }

    @Test
    void testConstructorNorth() {
        Galleon g = new Galleon(Compass.NORTH, pos(5,5));
        List<IPosition> p = g.getPositions();
        assertEquals(5, p.size());
        assertTrue(p.contains(new Position(5,5)));
        assertTrue(p.contains(new Position(5,6)));
        assertTrue(p.contains(new Position(5,7)));
        assertTrue(p.contains(new Position(6,6)));
        assertTrue(p.contains(new Position(7,6)));
    }

    @Test
    void testConstructorSouth() {
        Galleon g = new Galleon(Compass.SOUTH, pos(3,3));
        List<IPosition> p = g.getPositions();
        assertEquals(5, p.size());
        assertTrue(p.contains(new Position(3,3)));
        assertTrue(p.contains(new Position(4,3)));
        assertTrue(p.contains(new Position(5,3)));
        assertTrue(p.contains(new Position(5,4)));
        assertFalse(p.contains(new Position(5,5)));
    }

    @Test
    void testConstructorEast() {
        Galleon g = new Galleon(Compass.EAST, pos(2,2));
        List<IPosition> p = g.getPositions();
        assertEquals(5, p.size());
        assertTrue(p.contains(new Position(2,2)));
        assertFalse(p.contains(new Position(3,-1)));
        assertTrue(p.contains(new Position(3,0)));
        assertTrue(p.contains(new Position(3,1)));
        assertTrue(p.contains(new Position(4,2)));
    }

    @Test
    void testConstructorWest() {
        Galleon g = new Galleon(Compass.WEST, pos(1,1));
        List<IPosition> p = g.getPositions();
        assertEquals(5, p.size());
        assertTrue(p.contains(new Position(1,1)));
        assertTrue(p.contains(new Position(2,2)));
        assertTrue(p.contains(new Position(2,3)));
        assertFalse(p.contains(new Position(2,4)));
        assertTrue(p.contains(new Position(3,1)));
    }

    @Test
    void testGetSize() {
        assertEquals(5, new Galleon(Compass.NORTH, pos(0,0)).getSize());
    }
}