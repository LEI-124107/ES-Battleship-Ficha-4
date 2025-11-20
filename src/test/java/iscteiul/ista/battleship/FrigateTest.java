package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("Testes da classe Frigate")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FrigateTest {

    private IPosition basePos;

    @BeforeAll
    @DisplayName("Configuração inicial para os testes da Fragata")
    void setupAll() {
        basePos = new Position(5, 5);
    }

    @Nested
    @DisplayName("Testes de construção com diferentes orientações")
    class ConstructionTests {

        @Test
        @DisplayName("Construção orientada a Norte deve gerar 4 posições verticais")
        void testNorthConstruction() {
            Frigate f = new Frigate(Compass.NORTH, basePos);
            List<IPosition> pos = f.getPositions();

            assertAll(
                    () -> assertEquals(4, pos.size()),
                    () -> assertEquals(new Position(5, 5), pos.get(0)),
                    () -> assertEquals(new Position(6, 5), pos.get(1)),
                    () -> assertEquals(new Position(7, 5), pos.get(2)),
                    () -> assertEquals(new Position(8, 5), pos.get(3))
            );
        }

        @Test
        @DisplayName("Construção orientada a Sul deve gerar 4 posições verticais")
        void testSouthConstruction() {
            Frigate f = new Frigate(Compass.SOUTH, basePos);
            List<IPosition> pos = f.getPositions();

            assertAll(
                    () -> assertEquals(4, pos.size()),
                    () -> assertEquals(new Position(5, 5), pos.get(0)),
                    () -> assertEquals(new Position(6, 5), pos.get(1)),
                    () -> assertEquals(new Position(7, 5), pos.get(2)),
                    () -> assertEquals(new Position(8, 5), pos.get(3))
            );
        }

        @Test
        @DisplayName("Construção orientada a Este deve gerar 4 posições horizontais")
        void testEastConstruction() {
            Frigate f = new Frigate(Compass.EAST, basePos);
            List<IPosition> pos = f.getPositions();

            assertAll(
                    () -> assertEquals(4, pos.size()),
                    () -> assertEquals(new Position(5, 5), pos.get(0)),
                    () -> assertEquals(new Position(5, 6), pos.get(1)),
                    () -> assertEquals(new Position(5, 7), pos.get(2)),
                    () -> assertEquals(new Position(5, 8), pos.get(3))
            );
        }

        @Test
        @DisplayName("Construção orientada a Oeste deve gerar 4 posições horizontais")
        void testWestConstruction() {
            Frigate f = new Frigate(Compass.WEST, basePos);
            List<IPosition> pos = f.getPositions();

            assertAll(
                    () -> assertEquals(4, pos.size()),
                    () -> assertEquals(new Position(5, 5), pos.get(0)),
                    () -> assertEquals(new Position(5, 6), pos.get(1)),
                    () -> assertEquals(new Position(5, 7), pos.get(2)),
                    () -> assertEquals(new Position(5, 8), pos.get(3))
            );
        }
    }

    @Test
    @DisplayName("getSize() deve devolver o tamanho correto da fragata")
    void testGetSize() {
        Frigate f = new Frigate(Compass.NORTH, basePos);
        assertEquals(4, f.getSize());
    }





}
