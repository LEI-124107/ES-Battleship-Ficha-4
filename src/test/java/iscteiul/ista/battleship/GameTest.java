package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Game Class Test")
class GameTest {

    private Game game;
    private Fleet fleet;

    static class TestPosition implements IPosition {
        private final int row, column;
        private boolean hit = false;

        public TestPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int getRow() { return row; }

        @Override
        public int getColumn() { return column; }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof IPosition)) return false;
            IPosition o = (IPosition) other;
            return this.row == o.getRow() && this.column == o.getColumn();
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            int dr = Math.abs(this.row - other.getRow());
            int dc = Math.abs(this.column - other.getColumn());
            return (dr <= 1 && dc <= 1) && !(dr == 0 && dc == 0);
        }

        @Override
        public void occupy() { }

        @Override
        public void shoot() { hit = true; }

        @Override
        public boolean isOccupied() { return false; }

        @Override
        public boolean isHit() { return hit; }
    }

    static class TestShip implements IShip {
        private final List<IPosition> positions = new ArrayList<>();
        private boolean floating = true;

        public TestShip(List<IPosition> positions) {
            this.positions.addAll(positions);
        }

        @Override
        public String getCategory() { return "TestShip"; }

        @Override
        public Integer getSize() { return positions.size(); }

        @Override
        public boolean stillFloating() { return floating; }

        @Override
        public boolean occupies(IPosition pos) {
            return positions.stream().anyMatch(p -> p.equals(pos));
        }

        @Override
        public int getLeftMostPos() { return positions.stream().mapToInt(IPosition::getColumn).min().orElse(0); }

        @Override
        public int getRightMostPos() { return positions.stream().mapToInt(IPosition::getColumn).max().orElse(0); }

        @Override
        public int getTopMostPos() { return positions.stream().mapToInt(IPosition::getRow).min().orElse(0); }

        @Override
        public int getBottomMostPos() { return positions.stream().mapToInt(IPosition::getRow).max().orElse(0); }

        @Override
        public boolean tooCloseTo(IShip other) { return false; }

        @Override
        public boolean tooCloseTo(IPosition pos) { return false; }

        @Override
        public void shoot(IPosition pos) {
            for (IPosition p : positions) {
                if (p.equals(pos)) {
                    p.shoot();
                    floating = false;
                }
            }
        }

        @Override
        public List<IPosition> getPositions() { return positions; }

        @Override
        public IPosition getPosition() { return positions.isEmpty() ? null : positions.get(0); }

        @Override
        public Compass getBearing() { return Compass.UNKNOWN; }
    }

    @BeforeEach
    void setUp() throws Exception {
        fleet = new Fleet();
        game = new Game(fleet);

        Field countHits = Game.class.getDeclaredField("countHits");
        Field countSinks = Game.class.getDeclaredField("countSinks");
        Field countInvalidShots = Game.class.getDeclaredField("countInvalidShots");
        Field countRepeatedShots = Game.class.getDeclaredField("countRepeatedShots");

        countHits.setAccessible(true);
        countSinks.setAccessible(true);
        countInvalidShots.setAccessible(true);
        countRepeatedShots.setAccessible(true);

        countHits.set(game, 0);
        countSinks.set(game, 0);
        countInvalidShots.set(game, 0);
        countRepeatedShots.set(game, 0);
    }


    @Test
    @DisplayName("Firing invalid shot increments invalidShots")
    void testInvalidShot() {
        TestPosition invalid = new TestPosition(-1, 0);
        assertNull(game.fire(invalid));
        assertEquals(1, game.getInvalidShots());
    }

    @Test
    @DisplayName("Firing repeated shot increments repeatedShots")
    void testRepeatedShot() {
        TestPosition pos = new TestPosition(0, 0);
        game.fire(pos);
        game.fire(pos);
        assertEquals(1, game.getRepeatedShots());
        assertEquals(1, game.getShots().size());
    }

    @Test
    @DisplayName("Firing at ship increments hits and sinks correctly")
    void testFireShip() {
        TestPosition pos = new TestPosition(0, 0);
        List<IPosition> positions = new ArrayList<>();
        positions.add(pos);
        TestShip ship = new TestShip(positions);
        fleet.addShip(ship);

        IShip sunk = game.fire(pos);
        assertEquals(ship, sunk);
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
        assertEquals(0, game.getRemainingShips());
    }

    @Test
    @DisplayName("getShots returns all fired positions")
    void testGetShots() {
        TestPosition pos1 = new TestPosition(0, 0);
        TestPosition pos2 = new TestPosition(1, 1);
        game.fire(pos1);
        game.fire(pos2);
        List<IPosition> shots = game.getShots();
        assertEquals(2, shots.size());
        assertTrue(shots.contains(pos1));
        assertTrue(shots.contains(pos2));
    }

    @Test
    @DisplayName("printBoard, printValidShots, and printFleet do not throw exceptions")
    void testPrintMethods() {
        TestPosition pos1 = new TestPosition(0, 0);
        TestPosition pos2 = new TestPosition(1, 1);
        game.fire(pos1);
        game.fire(pos2);

        TestShip ship = new TestShip(List.of(new TestPosition(2, 2), new TestPosition(3, 3)));
        fleet.addShip(ship);

        // apenas verificamos que não lança exceção
        game.printValidShots();
        game.printFleet();
    }

    @Test
    @DisplayName("Firing multiple positions increments counters correctly")
    void testMultipleShots() {
        TestPosition pos1 = new TestPosition(0, 0);
        TestPosition pos2 = new TestPosition(1, 1);
        TestPosition pos3 = new TestPosition(2, 2);

        List<IPosition> positions = new ArrayList<>();
        positions.add(pos3);
        TestShip ship = new TestShip(positions);
        fleet.addShip(ship);

        game.fire(pos1);
        game.fire(pos2);
        IShip sunk = game.fire(pos3);

        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
        assertEquals(3, game.getShots().size());
        assertEquals(ship, sunk);
    }
}
