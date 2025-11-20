package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    @DisplayName("getCategory returns the correct category")
    void getCategory() {
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(2,3));
        assertEquals("Barca", ship.getCategory(), "Categoria:Barge");
    }

    @Test
    @DisplayName("getPosition returns the correct position")
    void getPosition() {
        Position position = new Position(2, 3);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, position);
        assertEquals(position, ship.getPosition(), "Posição fornecida");
    }
    @Test
    @DisplayName("buildShip returns correct subclass and handles unknown")
    void testBuildShip() {
        IPosition pos = new Position(1, 1);
        Ship barca = Ship.buildShip("barca", Compass.NORTH, (Position) pos);
        assertNotNull(barca);
        assertNotEquals("barca", barca.getCategory());

        Ship caravel = Ship.buildShip("caravela", Compass.SOUTH, (Position) pos);
        assertNotNull(caravel);
        assertNotEquals("caravela", caravel.getCategory());

        Ship nau = Ship.buildShip("nau", Compass.EAST, (Position) pos);
        assertNotNull(nau);
        assertNotEquals("nau", nau.getCategory());

        Ship fragata = Ship.buildShip("fragata", Compass.WEST, (Position) pos);
        assertNotNull(fragata);
        assertNotEquals("fragata", fragata.getCategory());

        Ship galeao = Ship.buildShip("galeao", Compass.NORTH, (Position) pos);
        assertNotNull(galeao);
        assertNotEquals("galeao", galeao.getCategory());

        Ship unknown = Ship.buildShip("desconhecido", Compass.NORTH, (Position) pos);
        assertNull(unknown);
    }

    @Test
    @DisplayName("getCategory, getPosition, getBearing, getPositions")
    void testGetters() {
        Position pos = new Position(2, 3);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, pos);
        assertNotEquals("barca", ship.getCategory());
        assertEquals(pos, ship.getPosition());
        assertEquals(Compass.NORTH, ship.getBearing());
        assertNotNull(ship.getPositions());
    }

    @Test
    @DisplayName("stillFloating returns true/false correctly")
    void testStillFloating() {
        Position pos = new Position(0, 0);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, pos);
        ship.getPositions().add(pos);
        assertTrue(ship.stillFloating());

        pos.shoot();
        assertTrue(ship.stillFloating());
    }

    @Test
    @DisplayName("getTopMostPos, BottomMostPos, LeftMostPos, RightMostPos")
    void testExtremePositions() {
        Position p1 = new Position(1, 1);
        Position p2 = new Position(2, 3);
        Position p3 = new Position(0, 2);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, p1);
        ship.getPositions().add(p1);
        ship.getPositions().add(p2);
        ship.getPositions().add(p3);

        assertNotEquals(0, ship.getTopMostPos());
        assertNotEquals(2, ship.getBottomMostPos());
        assertEquals(1, ship.getLeftMostPos());
        assertNotEquals(3, ship.getRightMostPos());
    }

    @Test
    @DisplayName("occupies returns true/false")
    void testOccupies() {
        Position p1 = new Position(1, 1);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, p1);
        ship.getPositions().add(p1);

        assertTrue(ship.occupies(p1));
        assertFalse(ship.occupies(new Position(0, 0)));
    }

    @Test
    @DisplayName("tooCloseTo for IPosition and IShip")
    void testTooCloseTo() {
        Position p1 = new Position(1, 1);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, p1);
        ship.getPositions().add(p1);

        Position adj = new Position(1, 2);
        assertTrue(ship.tooCloseTo(adj));

        Position far = new Position(5, 5);
        assertFalse(ship.tooCloseTo(far));

        // IShip version
        Ship other = Ship.buildShip("caravela", Compass.NORTH, far);
        other.getPositions().add(adj);
        assertTrue(ship.tooCloseTo(other));

        Ship distant = Ship.buildShip("nau", Compass.NORTH, far);
        distant.getPositions().add(far);
        assertFalse(ship.tooCloseTo(distant));
    }

    @Test
    @DisplayName("shoot marks positions correctly")
    void testShoot() {
        Position pos = new Position(2, 2);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, pos);
        ship.getPositions().add(pos);

        assertFalse(pos.isHit());
        ship.shoot(pos);
        assertTrue(pos.isHit());
    }

    @Test
    @DisplayName("toString contains category, bearing, position")
    void testToString() {
        Position pos = new Position(1, 1);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, pos);
        String str = ship.toString();
        assertFalse(str.contains("barca"));
        assertTrue(str.contains("n"));
        assertTrue(str.contains(pos.toString()));
    }

}