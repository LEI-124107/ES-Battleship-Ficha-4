package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    @DisplayName("getCategory returns the correct category")
    void getCategory() {
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(2,3));
        assertEquals("Barca", ship.getCategory(), "Category should be 'barca'");
    }

    @Test
    @DisplayName("getPosition returns the correct position")
    void getPosition() {
        Position position = new Position(2, 3);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, position);
        assertEquals(position, ship.getPosition(), "Position provided should match");
    }

    @Nested
    @DisplayName("Barge (size 1)")
    class BargeTest {
        Ship barge;

        @BeforeEach
        void setUp() {
            barge = Ship.buildShip("barca", Compass.NORTH, new Position(0,0));
        }

        @Test
        @DisplayName("Size should be 1")
        void testSize() {
            assertEquals(1, barge.getSize());
        }

        @Test
        @DisplayName("Ship is floating before being hit")
        void testStillFloating() {
            assertTrue(barge.stillFloating());
        }

        @Test
        @DisplayName("Ship sinks after being hit")
        void testShoot() {
            IPosition pos = barge.getPositions().get(0);
            barge.shoot(pos);
            assertFalse(barge.stillFloating());
        }
    }

    @Nested
    @DisplayName("Caravel (size 2)")
    class CaravelTest {
        Ship caravel;

        @BeforeEach
        void setUp() {
            caravel = Ship.buildShip("caravela", Compass.EAST, new Position(0,0));
        }

        @Test
        @DisplayName("Size should be 2")
        void testSize() {
            assertEquals(2, caravel.getSize());
        }

        @Test
        @DisplayName("Occupies initial and adjacent position")
        void testOccupies() {
            assertTrue(caravel.occupies(new Position(0,0)));
            assertTrue(caravel.occupies(new Position(0,1)));
        }
    }

    @Nested
    @DisplayName("Carrack (size 3)")
    class CarrackTest {
        Ship carrack;

        @BeforeEach
        void setUp() {
            carrack = Ship.buildShip("nau", Compass.SOUTH, new Position(5,5));
        }

        @Test
        @DisplayName("Size should be 3")
        void testSize() {
            assertEquals(3, carrack.getSize());
        }

        @Test
        @DisplayName("Top and bottom limits are correct")
        void testLimits() {
            assertEquals(5, carrack.getTopMostPos());
            assertEquals(7, carrack.getBottomMostPos());
        }
    }
}
