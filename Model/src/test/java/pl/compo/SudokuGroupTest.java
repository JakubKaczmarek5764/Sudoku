package pl.compo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SudokuGroupTest {
    SudokuBoard board;

    @BeforeEach
    void init() {
        board = Repository.getBoard();

    }

    @Test
    void verifyValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        SudokuRow fields2 = board.getRow(0);
        SudokuBox fields3 = board.getBox(0, 0);
        assertTrue(fields1.verify() && fields2.verify() && fields3.verify());
        board.set(0, 0, board.get(1, 0));
        fields3 = board.getBox(0, 0);
        assertFalse(fields1.verify() && fields2.verify() && fields3.verify());
    }

    @Test
    void verifyNotValid() {
        board.solveGame();
        board.set(0, 0, board.get(1, 0));
        SudokuColumn fields1 = board.getColumn(0);
        SudokuRow fields2 = board.getRow(0);
        SudokuBox fields3 = board.getBox(0, 0);
        assertFalse(fields1.verify() && fields2.verify() && fields3.verify());
    }

    @Test
    void equalsValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        SudokuColumn fields2 = board.getColumn(0);
        assertEquals(fields1, fields2);
        assertEquals(fields1, fields1);
    }

    @Test
    void equalsNotValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        board.set(0, 0, board.get(1, 0));
        SudokuColumn fields2 = board.getColumn(0);
        assertNotEquals(fields1, fields2);
        assertNotEquals(fields1, new Object());
    }

    @Test
    void hashCodeValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        SudokuColumn fields2 = board.getColumn(0);
        assertEquals(fields1.hashCode(), fields2.hashCode());
    }

    @Test
    void hashCodeNotValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        board.set(0, 0, board.get(1, 0));
        SudokuColumn fields2 = board.getColumn(0);
        assertNotEquals(fields1.hashCode(), fields2.hashCode());
    }

    @Test
    void toStringValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        assertNotNull(fields1.toString());
    }

    @Test
    void toStringNotValid() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        board.set(0, 0, board.get(1, 0));
        SudokuColumn fields2 = board.getColumn(0);
        assertNotEquals(fields1.toString(), fields2.toString());
    }

    @Test
    void equalsHashCodeIntegrity() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        SudokuColumn fields2 = board.getColumn(1);
        assertNotEquals(fields1.hashCode(), fields2.hashCode());
        assertNotEquals(fields1, fields2);
        fields2 = board.getColumn(0);
        assertEquals(fields1.hashCode(), fields2.hashCode());
        assertEquals(fields1, fields2);
    }

    @Test
    void cloneTest() {
        board.solveGame();
        SudokuColumn fields1 = board.getColumn(0);
        SudokuColumn fields2 = (SudokuColumn) fields1.clone();
        assertEquals(fields1, fields2);
        assertNotSame(fields2, fields1);
    }
}