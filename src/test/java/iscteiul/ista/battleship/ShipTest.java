package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
}