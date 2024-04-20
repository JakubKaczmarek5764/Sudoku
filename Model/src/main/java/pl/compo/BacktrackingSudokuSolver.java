package pl.compo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    private final int boardSize = 9;
    private boolean isSolved;

    @Override
    public void solve(SudokuBoard board) {
        isSolved = false;
        int[] startingIndexes = nextSquare(board);
        solve(board, startingIndexes[0], startingIndexes[1]);
    }

    private void solve(SudokuBoard board, int column, int row) {

        // If true then board is solved

        if (column == 9) {
            isSolved = true;
            return;
        }
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);
        for (int i = 0; i < boardSize; i++) {
            // Checks if number can be put
            if (canPutNumber(board, column, row, numbers.get(i))) {
                board.set(column, row, numbers.get(i));
                // Checks if the put number leads to solution
                int[] nextIndexes = nextSquare(board);
                solve(board, nextIndexes[0], nextIndexes[1]);
                if (isSolved) {
                    return;
                } else {
                    // If no replace it with 0
                    board.set(column, row, 0);
                }
            }
        }

    }

    private int[] nextSquare(SudokuBoard board) {
        int[] tmpIndexes = {9, 9};
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (board.get(column, row) == 0) {
                    tmpIndexes[0] = column;
                    tmpIndexes[1] = row;
                    return tmpIndexes;
                }
            }
        }
        return tmpIndexes;
    }

    private boolean canPutNumber(SudokuBoard board, int column, int row, int num) {
        // Check if the number already exists in the row or column
        for (int i = 0; i < boardSize; i++) {
            if (board.get(i, row) == num) {
                return false;
            }
            if (board.get(column, i) == num) {
                return false;
            }
        }
        int startRow = row - row % 3;
        int startColumn = column - column % 3;
        // Check if the number already exists in 3x3 box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(startColumn + j, startRow + i) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("boardSize", boardSize)
                .append("isSolved", isSolved)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BacktrackingSudokuSolver that)) {
            return false;
        }

        return new EqualsBuilder().append(boardSize, that.boardSize).append(isSolved, that.isSolved).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(boardSize).append(isSolved).toHashCode();
    }
}
