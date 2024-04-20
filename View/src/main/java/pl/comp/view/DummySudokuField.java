package pl.comp.view;

import pl.compo.SudokuBoard;

public class DummySudokuField {
    private final SudokuBoard board;
    private final int coordX;
    private final int coordY;

    public DummySudokuField(SudokuBoard board, int x, int y) {
        this.board = board;
        this.coordX = x;
        this.coordY = y;
    }

    public int getValue() {
        return this.board.get(coordX, coordY);
    }

    public void setValue(int val) {
        this.board.set(coordX, coordY, val);
    }

}
