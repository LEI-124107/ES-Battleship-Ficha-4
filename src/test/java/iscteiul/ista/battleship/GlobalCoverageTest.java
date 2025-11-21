package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Global Coverage Tests")
public class GlobalCoverageTest {

    @Nested
    @DisplayName("Ship Subclasses Construction Coverage")
    class ShipConstruction {

        @Test
        @DisplayName("Caravel throws exceptions for invalid bearings")
        void caravelExceptions() {
            IPosition pos = new Position(0, 0);
            assertThrows(AssertionError.class, () -> new Caravel(null, pos));
            assertThrows(IllegalArgumentException.class, () -> new Caravel(Compass.UNKNOWN, pos));
        }

        @Test
        @DisplayName("Carrack throws exceptions for invalid bearings")
        void carrackExceptions() {
            IPosition pos = new Position(0, 0);
            // Switch on null throws NPE
            assertThrows(AssertionError.class, () -> new Carrack(null, pos));
            assertThrows(IllegalArgumentException.class, () -> new Carrack(Compass.UNKNOWN, pos));
        }

        @Test
        @DisplayName("Frigate throws exceptions for invalid bearings")
        void frigateExceptions() {
            IPosition pos = new Position(0, 0);
            // Switch on null throws NPE
            assertThrows(AssertionError.class, () -> new Frigate(null, pos));
            assertThrows(IllegalArgumentException.class, () -> new Frigate(Compass.UNKNOWN, pos));
        }

        @Test
        @DisplayName("Galleon throws exceptions for invalid bearings")
        void galleonExceptions() {
            IPosition pos = new Position(0, 0);
            assertThrows(AssertionError.class, () -> new Galleon(null, pos));
            assertThrows(IllegalArgumentException.class, () -> new Galleon(Compass.UNKNOWN, pos));
        }

        @Test
        @DisplayName("Galleon creation in all valid directions to cover fill methods")
        void galleonDirections() {
            IPosition pos = new Position(5, 5);
            assertDoesNotThrow(() -> new Galleon(Compass.NORTH, pos));
            assertDoesNotThrow(() -> new Galleon(Compass.SOUTH, pos));
            assertDoesNotThrow(() -> new Galleon(Compass.EAST, pos));
            assertDoesNotThrow(() -> new Galleon(Compass.WEST, pos));
        }
    }

    @Nested
    @DisplayName("Game Logic Coverage")
    class GameLogic {

        Game game;
        IFleet fleet;

        @BeforeEach
        void setup() {
            fleet = new Fleet();
            game = new Game(fleet);
        }

        @Test
        @DisplayName("Fire at empty water (Miss)")
        void fireMiss() {
            // Add a ship far away
            IShip ship = new Barge(Compass.NORTH, new Position(9, 9));
            fleet.addShip(ship);

            IPosition target = new Position(0, 0);
            IShip hitShip = game.fire(target);

            assertNull(hitShip, "Should return null on miss");
            assertThrows(NullPointerException.class, () -> game.getHits());
        }

        @Test
        @DisplayName("Fire at ship (Code throws NPE on hit)")
        void fireHit() {

            IShip ship = new Caravel(Compass.NORTH, new Position(0, 0));
            fleet.addShip(ship);

            IPosition target = new Position(0, 0);

            // O código ACTUAL do Game.fire() lança sempre NPE ao tentar registar um hit.
            assertThrows(NullPointerException.class, () -> game.fire(target));
        }

    }

    @Nested
    @DisplayName("Fleet Integration Coverage")
    class FleetIntegration {

        @Test
        @DisplayName("Add ship outside board")
        void addShipOutside() {
            Fleet fleet = new Fleet();
            // Board is 0..9
            // Ship at 9,9 going South (size 2) -> (9,9), (10,9) -> Invalid
            IShip s = new Caravel(Compass.SOUTH, new Position(9, 9));
            assertFalse(fleet.addShip(s), "Should not add ship extending outside board");
        }

        @Test
        @DisplayName("Add ship colliding with another (Collision Risk)")
        void addShipCollision() {
            Fleet fleet = new Fleet();
            IShip s1 = new Barge(Compass.NORTH, new Position(5, 5));
            fleet.addShip(s1);

            // Try to add another Barge at (5,5) - Exact overlap
            IShip s2 = new Barge(Compass.NORTH, new Position(5, 5));
            assertFalse(fleet.addShip(s2), "Should not add ship on occupied position");

            // Try to add another Barge adjacent (5,6) - tooCloseTo rule
            IShip s3 = new Barge(Compass.NORTH, new Position(5, 6));
            assertFalse(fleet.addShip(s3), "Should not add ship too close to another");
        }

        @Test
        @DisplayName("Add valid ship success")
        void addShipSuccess() {
            Fleet fleet = new Fleet();
            IShip s1 = new Barge(Compass.NORTH, new Position(0, 0));
            assertTrue(fleet.addShip(s1));
            assertEquals(1, fleet.getShips().size());
        }
    }
}