package pl.compo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    SudokuBoard board1, board2, board3, board4;

    @BeforeEach
    void init() {

        board1 = Repository.getBoard();
        board2 = Repository.getBoard();
        board3 = Repository.getBoard();
        board4 = Repository.getBoard();
    }

    @Test
    void set_and_get() {
        board1.set(1, 1, 5);
        assertEquals(5, board1.get(1, 1));
        assertFalse(board1.getSolved());

    }

    @Test
    public void areBoardsTheSame() {
        board1.solveGame();
        assertTrue(board1.getSolved());
        board2.solveGame();
        assertTrue(board2.getSolved());
        assertFalse(areBoardsTheSame(board1, board2));

    }

    @Test
    public void notLegalBoards() {
        //unsolvable
        board3.set(0, 0, 1);
        board3.set(1, 0, 2);
        board3.set(2, 0, 3);
        board3.set(4, 1, 4);
        board3.set(8, 2, 4);
        board3.solveGame();
        assertFalse(board1.getSolved());
        board4.solveGame();
        board4.set(0, 1, board4.get(0, 0));
        board4.solveGame();
        assertFalse(board4.getSolved());

    }

    @Test
    void equalsValid() {
        board1.solveGame();
        board2.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i, j, board1.get(i, j));
            }
        }
        assertEquals(board1, board1);
        assertEquals(board1, board2);
    }

    @Test
    void equalsNotValid() {
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1, board2);
        assertNotEquals(board1, new Object());
    }

    @Test
    void hashCodeValid() {
        board1.solveGame();
        board2.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i, j, board1.get(i, j));
            }
        }
        assertEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    void hashCodeNotValid() {
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    void toStringValid() {
        board1.solveGame();
        assertNotNull(board1.toString());
    }

    @Test
    void toStringNotValid() {
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1.toString(), board2.toString());
    }

    @Test
    void equalsHashCodeIntegrity() {
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1.hashCode(), board2.hashCode());
        assertNotEquals(board1, board2);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i, j, board1.get(i, j));
            }
        }
        assertEquals(board1, board2);
        assertEquals(board1.hashCode(), board2.hashCode());
    }

    private boolean areBoardsTheSame(SudokuBoard b1, SudokuBoard b2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b1.get(j, i) != b2.get(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    void cloneTest() {
        board1.solveGame();
        board2 = board1.clone();
        assertEquals(board1, board2);
        assertNotSame(board1, board2);
    }
}