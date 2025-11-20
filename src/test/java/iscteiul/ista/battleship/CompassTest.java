package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários da enum Compass")
class CompassTest {

    @Test
    @DisplayName("getDirection devolve o caracter correto")
    void getDirectionFunciona() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    @DisplayName("toString devolve string com o mesmo caracter")
    void toStringFunciona() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @Test
    @DisplayName("charToCompass converte corretamente os caracteres válidos")
    void charToCompassValido() {
        assertEquals(Compass.NORTH, Compass.charToCompass('n'));
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
        assertEquals(Compass.EAST,  Compass.charToCompass('e'));
        assertEquals(Compass.WEST,  Compass.charToCompass('o'));
    }

    @Test
    @DisplayName("charToCompass devolve UNKNOWN para caracteres inválidos")
    void charToCompassInvalido() {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('N')); // maiúscula
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
    }
}
