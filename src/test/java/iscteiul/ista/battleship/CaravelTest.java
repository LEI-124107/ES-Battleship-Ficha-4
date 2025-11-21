package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    Ship caravel;

    @BeforeEach
    void setUp() {
        caravel = Ship.buildShip("caravela", Compass.EAST, new Position(0,0));
    }

    @Test
    @DisplayName("Category should be 'caravela'")
    void testCategory() {
        assertEquals("Caravela", caravel.getCategory());
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
