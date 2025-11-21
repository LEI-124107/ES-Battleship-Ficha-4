package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    Ship carrack;

    @BeforeEach
    void setUp() {
        carrack = Ship.buildShip("nau", Compass.SOUTH, new Position(5,5));
    }

    @Test
    @DisplayName("Category should be 'nau'")
    void testCategory() {
        assertEquals("Nau", carrack.getCategory());
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
