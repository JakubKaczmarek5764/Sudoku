package pl.compo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class SudokuBoard implements Serializable, Cloneable {
    private SudokuField[][] board;
    private boolean isSolved = false;
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solv) {
        if (solv == null) {
            sudokuSolver = new BacktrackingSudokuSolver();
        } else {
            sudokuSolver = solv;
        }
        board = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
        isSolved = checkBoard();
    }

    public boolean getSolved() {
        return isSolved;
    }

    public void set(int x, int y, int value) {
        board[y][x].setValue(value);
    }

    public int get(int x, int y) {
        return board[y][x].getValue();
    }

    private boolean checkBoard() {
        boolean isOK = true;
        for (int i = 0; i < 9; i++) { //row and col
            if (!getRow(i).verify() || !getColumn(i).verify()) {
                isOK = false;
                break;
            }
        }
        for (int i = 0; i <= 2; i++) { //boxes
            for (int j = 0; j <= 2; j++) {
                if (!(getBox(i, j).verify())) {
                    isOK = false;
                    break;
                }
            }
        }
        return isOK;

    }

    public SudokuRow getRow(int y) {
        List<SudokuField> tmpList = Arrays.asList(new SudokuField[9]);
        tmpList.replaceAll(e -> new SudokuField());
        for (int i = 0; i < 9; i++) {
            tmpList.get(i).setValue(get(i, y));
        }
        return new SudokuRow(tmpList);
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> tmpList = Arrays.asList(new SudokuField[9]);
        tmpList.replaceAll(e -> new SudokuField());
        for (int i = 0; i < 9; i++) {
            tmpList.get(i).setValue(get(x, i));
        }
        return new SudokuColumn(tmpList);

    }

    public SudokuBox getBox(int x, int y) { // 0 <= x,y <= 2
        List<SudokuField> tmpList = Arrays.asList(new SudokuField[9]);
        tmpList.replaceAll(e -> new SudokuField());
        ListIterator<SudokuField> it = tmpList.listIterator();
        for (int i = x * 3; i < x * 3 + 3; i++) {
            for (int j = y * 3; j < y * 3 + 3; j++) {
                it.next().setValue(get(i, j));
            }
        }
        return new SudokuBox(tmpList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .append("isSolved", isSolved)
                .append("sudokuSolver", sudokuSolver)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard that)) {
            return false;
        }

        return new EqualsBuilder()
                .append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).append(getSolved()).toHashCode();
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard clone = (SudokuBoard) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.board = this.board.clone();
            for (int i = 0; i < 9; i++) {
                clone.board[i] = this.board[i].clone();
                for (int j = 0; j < 9; j++) {
                    clone.board[j][i] = this.board[j][i].clone();
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
