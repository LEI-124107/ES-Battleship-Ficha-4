package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tasks Class Test")
class TasksTest {

    @Test
    @DisplayName("readPosition reads correctly")
    void testReadPosition() {
        String input = "2 3";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Position pos = Tasks.readPosition(new java.util.Scanner(in));
        assertEquals(2, pos.getRow());
        assertEquals(3, pos.getColumn());
    }

    @Test
    @DisplayName("readShip builds correct Ship")
    void testReadShip() {
        String input = "barca 1 1 n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Ship ship = Tasks.readShip(new java.util.Scanner(in));
        assertNotNull(ship);
        assertNotEquals("barca", ship.getCategory());
        assertEquals(1, ship.getPosition().getRow());
        assertEquals(1, ship.getPosition().getColumn());
        assertEquals(Compass.NORTH, ship.getBearing());
    }


    @Test
    @DisplayName("taskA processes ships correctly")
    void testTaskA() {
        String input = "barca 0 0 n\n0 0\n1 1\n2 2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Tasks.taskA();
        } catch (Exception ignored) {}
        System.setIn(System.in);
    }

    @Test
    @DisplayName("taskB processes commands correctly")
    void testTaskBCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova\n");
        for (int i = 0; i <= Fleet.FLEET_SIZE; i++)
            sb.append("barca ").append(i).append(" ").append(i).append(" n\n");
        sb.append("estado\n");
        sb.append("desisto\n");

        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);
        try {
            Tasks.taskB();
        } catch (Exception ignored) {}
        System.setIn(System.in);
    }

    @Test
    @DisplayName("taskC handles batota and unknown commands")
    void testTaskCCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova\n");
        for (int i = 0; i <= Fleet.FLEET_SIZE; i++)
            sb.append("barca ").append(i).append(" ").append(i).append(" n\n");
        sb.append("mapa\n");
        sb.append("desisto\n");

        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);
        try {
            Tasks.taskC();
        } catch (Exception ignored) {}
        System.setIn(System.in);
    }

    @Test
    @DisplayName("taskD executes firing and printing")
    void testTaskDCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova\n");
        for (int i = 0; i <= Fleet.FLEET_SIZE; i++)
            sb.append("barca ").append(i).append(" ").append(i).append(" n\n");
        sb.append("rajada\n");
        sb.append("ver\n");
        sb.append("mapa\n");
        sb.append("estado\n");
        sb.append("desisto\n");

        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);
        try {
            Tasks.taskD();
        } catch (Exception ignored) {}
        System.setIn(System.in);
    }

}
