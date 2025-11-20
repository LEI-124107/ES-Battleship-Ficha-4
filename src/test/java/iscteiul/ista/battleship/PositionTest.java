package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários da classe Position")
class PositionTest {

    private Position base;

    @BeforeEach
    void setUp() {
        base = new Position(3, 5);
    }

    @Test
    @DisplayName("Construtor guarda row e column corretamente")
    void constructorGuardaValores() {
        assertEquals(3, base.getRow());
        assertEquals(5, base.getColumn());
        assertFalse(base.isOccupied(), "Nova posição não deve estar ocupada");
        assertFalse(base.isHit(), "Nova posição não deve estar atingida");
    }

    @Test
    @DisplayName("Duas posições com mesma linha e coluna são iguais")
    void posicoesIguais() {
        Position p2 = new Position(3, 5);

        assertEquals(base, p2);
        assertEquals(base.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("Posições diferentes NÃO são iguais")
    void posicoesDiferentes() {
        Position p2 = new Position(2, 1);
        assertNotEquals(base, p2);
    }

    @Test
    @DisplayName("Position não é igual a null nem a objetos de outras classes")
    void equalsComNullEOutroTipo() {
        assertNotEquals(base, null);
        assertNotEquals(base, "texto");
    }

    @Test
    @DisplayName("isAdjacentTo: posições na mesma casa são adjacentes")
    void isAdjacentToMesmaPosicao() {
        Position p2 = new Position(3, 5);
        assertTrue(base.isAdjacentTo(p2));
    }

    @Test
    @DisplayName("isAdjacentTo: posições vizinhas são adjacentes")
    void isAdjacentToVizinhos() {
        assertTrue(base.isAdjacentTo(new Position(2, 5))); // cima
        assertTrue(base.isAdjacentTo(new Position(4, 5))); // baixo
        assertTrue(base.isAdjacentTo(new Position(3, 4))); // esquerda
        assertTrue(base.isAdjacentTo(new Position(3, 6))); // direita
        assertTrue(base.isAdjacentTo(new Position(2, 4))); // diagonal
    }

    @Test
    @DisplayName("isAdjacentTo: posições afastadas NÃO são adjacentes")
    void isAdjacentToLonge() {
        assertFalse(base.isAdjacentTo(new Position(5, 5)));
        assertFalse(base.isAdjacentTo(new Position(1, 5)));
        assertFalse(base.isAdjacentTo(new Position(3, 7)));
    }

    @Test
    @DisplayName("occupy marca posição como ocupada")
    void occupyFunciona() {
        assertFalse(base.isOccupied());
        base.occupy();
        assertTrue(base.isOccupied());
    }

    @Test
    @DisplayName("shoot marca posição como atingida")
    void shootFunciona() {
        assertFalse(base.isHit());
        base.shoot();
        assertTrue(base.isHit());
    }

    @Test
    @DisplayName("toString contém linha e coluna")
    void toStringContemLinhaEColuna() {
        String s = base.toString();
        assertTrue(s.contains("3"));
        assertTrue(s.contains("5"));
    }
}
