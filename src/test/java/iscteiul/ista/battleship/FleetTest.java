package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Fleet Test Without Mockito")
class FleetTest {

    private Fleet fleet;

    static class TestShip implements IShip {
        private final String category;
        private final int left, right, top, bottom;
        private final boolean floating;

        public TestShip(String category, int left, int right, int top, int bottom, boolean floating) {
            this.category = category;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            this.floating = floating;
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public Integer getSize() {
            return 0;
        }

        @Override
        public List<IPosition> getPositions() {
            return List.of();
        }

        @Override
        public IPosition getPosition() {
            return null;
        }

        @Override
        public Compass getBearing() {
            return null;
        }

        @Override
        public boolean stillFloating() {
            return floating;
        }

        @Override
        public boolean occupies(IPosition pos) {
            int row = pos.getRow();
            int col = pos.getColumn();
            return row >= top && row <= bottom && col >= left && col <= right;
        }

        @Override
        public int getLeftMostPos() { return left; }

        @Override
        public int getRightMostPos() { return right; }

        @Override
        public int getTopMostPos() { return top; }

        @Override
        public int getBottomMostPos() { return bottom; }

        @Override
        public boolean tooCloseTo(IShip other) {
            return !(other.getRightMostPos() < left - 1 || other.getLeftMostPos() > right + 1
                    || other.getBottomMostPos() < top - 1 || other.getTopMostPos() > bottom + 1);
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            return false;
        }

        @Override
        public void shoot(IPosition pos) {

        }

        @Override
        public String toString() {
            return category + " (" + left + "," + top + " -> " + right + "," + bottom + ")";
        }
    }

    static class TestPosition implements IPosition {
        private final int row, column;
        private boolean occupied = false;
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
        public void occupy() { occupied = true; }

        @Override
        public void shoot() { hit = true; }

        @Override
        public boolean isOccupied() { return occupied; }

        @Override
        public boolean isHit() { return hit; }
    }

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
    }

    @Test
    @DisplayName("Test Fleet initialization")
    void testFleetInitialization() {
        assertNotNull(fleet.getShips());
        assertEquals(0, fleet.getShips().size());
    }

    @Test
    @DisplayName("Test adding a valid ship")
    void testAddShipSuccess() {
        TestShip ship = new TestShip("Galeao", 0, 2, 0, 0, true);
        assertTrue(fleet.addShip(ship));
        assertEquals(1, fleet.getShips().size());
        assertEquals(ship, fleet.getShips().get(0));
    }

    @Test
    @DisplayName("Test adding a ship outside board fails")
    void testAddShipOutsideBoard() {
        TestShip ship = new TestShip("Galeao", -1, 2, 0, 0, true);
        assertFalse(fleet.addShip(ship));
        assertEquals(0, fleet.getShips().size());
    }

    @Test
    @DisplayName("Test adding a ship too close fails")
    void testAddShipCollisionRisk() {
        TestShip ship1 = new TestShip("Galeao", 0, 2, 0, 0, true);
        TestShip ship2 = new TestShip("Galeao", 1, 3, 0, 0, true);
        fleet.addShip(ship1);
        assertFalse(fleet.addShip(ship2));
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Test getShipsLike returns correct ships")
    void testGetShipsLike() {
        TestShip ship1 = new TestShip("Galeao", 0, 2, 0, 0, true);
        TestShip ship2 = new TestShip("Fragata", 3, 4, 0, 0, true);
        fleet.addShip(ship1);
        fleet.addShip(ship2);

        List<IShip> galeaos = fleet.getShipsLike("Galeao");
        assertEquals(1, galeaos.size());
        assertEquals(ship1, galeaos.get(0));

        List<IShip> fragatas = fleet.getShipsLike("Fragata");
        assertNotEquals(1, fragatas.size());
    }

    @Test
    @DisplayName("Test getFloatingShips")
    void testGetFloatingShips() {
        TestShip floating = new TestShip("Galeao", 0, 2, 0, 0, true);
        TestShip sunk = new TestShip("Fragata", 3, 4, 0, 0, false);
        fleet.addShip(floating);
        fleet.addShip(sunk);

        List<IShip> floatingShips = fleet.getFloatingShips();
        assertEquals(1, floatingShips.size());
        assertEquals(floating, floatingShips.get(0));
    }

    @Test
    @DisplayName("Test shipAt returns correct ship or null")
    void testShipAt() {
        TestShip ship = new TestShip("Galeao", 0, 2, 0, 0, true);
        fleet.addShip(ship);

        TestPosition inside = new TestPosition(0, 1);
        TestPosition outside = new TestPosition(1, 3);

        assertEquals(ship, fleet.shipAt(inside));
        assertNull(fleet.shipAt(outside));
    }

    @Test
    @DisplayName("Test print methods do not throw exceptions")
    void testPrintMethods() {
        TestShip ship = new TestShip("Galeao", 0, 2, 0, 0, true);
        fleet.addShip(ship);

        fleet.printAllShips();
        fleet.printFloatingShips();
        fleet.printShipsByCategory("Galeao");
        fleet.printStatus();
    }
}
