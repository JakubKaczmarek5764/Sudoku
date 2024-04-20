package pl.compo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    SudokuBoard board1, board2;

    @BeforeEach
    void init() {

        board1 = Repository.getBoard();
        board2 = Repository.getBoard();

    }

    @Test
    public void areSudokuSolversTheSame() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver solver2 = new BacktrackingSudokuSolver();
        SudokuBoard board3 = new SudokuBoard(solver2);
        board3.solveGame();
        assertEquals(solver1, solver1);
        assertNotEquals(solver1, solver2);
        assertNotEquals(null, solver1);
    }

    @Test
    public void isBoardLegal() {
        board1.solveGame();
        assertTrue(isBoardLegal(board1));
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

    private boolean isBoardLegal(SudokuBoard board1) {
        return checkRows(board1) && checkColumns(board1) && checkSquares(board1);
    }

    // Checks if numbers in one row/column/square are different
    private boolean checkArrNumbers(int[] arr) {
        for (int i = 0; i < 9; i++) {
            if (arr[i] != 1) {
                return true;
            }
        }
        return false;
    }

    // Checks if numbers in rows are legal
    private boolean checkRows(SudokuBoard b) {
        int index;
        for (int i = 0; i < 9; i++) {
            int[] arr = new int[9];
            for (int j = 0; j < 9; j++) {
                index = b.get(j, i);
                if (index < 1 || index > 9) {
                    return false;
                }
                arr[index - 1]++;
            }
            if (checkArrNumbers(arr)) {
                return false;
            }
        }
        return true;
    }

    // Checks if numbers in columns are legal
    private boolean checkColumns(SudokuBoard b) {
        int index;
        for (int i = 0; i < 9; i++) {
            int[] arr = new int[9];
            for (int j = 0; j < 9; j++) {
                index = b.get(i, j);
                if (index < 1 || index > 9) {
                    return false;
                }
                arr[index - 1]++;
            }
            if (checkArrNumbers(arr)) {
                return false;
            }
        }
        return true;
    }

    // Checks if numbers in 3x3 squares are legal
    private boolean checkSquares(SudokuBoard b) {
        int index;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int[] arr = new int[9];
                for (int i = x * 3; i < x * 3 + 3; i++) {
                    for (int j = y * 3; j < y * 3 + 3; j++) {
                        index = b.get(i, j);
                        if (index < 1 || index > 9) {
                            return false;
                        }
                        arr[index - 1]++;
                    }
                }
                if (checkArrNumbers(arr)) {
                    return false;
                }
            }
        }
        return true;
    }

}