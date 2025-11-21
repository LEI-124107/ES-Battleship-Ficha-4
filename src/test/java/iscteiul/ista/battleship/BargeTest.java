package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BargeTest {

    Ship barge;

    @BeforeEach
    void setUp() {
        barge = Ship.buildShip("barca", Compass.NORTH, new Position(0,0));
    }

    @Test
    @DisplayName("Category should be 'barca'")
    void testCategory() {
        assertEquals("Barca", barge.getCategory());
    }

    @Test
    @DisplayName("Size should be 1")
    void testSize() {
        assertEquals(1, barge.getSize());
    }

    @Test
    @DisplayName("Ship sinks after being hit")
    void testShoot() {
        IPosition pos = barge.getPositions().get(0);
        barge.shoot(pos);
        assertFalse(barge.stillFloating());
    }
}
