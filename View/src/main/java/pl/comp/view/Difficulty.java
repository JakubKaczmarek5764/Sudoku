package pl.comp.view;

import pl.compo.SudokuBoard;

import java.util.Random;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getFieldCount() {
        return switch (this) {
            case EASY -> 10;
            case MEDIUM -> 20;
            case HARD -> 40;
        };
    }

    public SudokuBoard prepareBoard(SudokuBoard board) {
        board.solveGame();
        int fields = getFieldCount();
        Random rand = new Random();
        for (int i = 0; i < fields; i++) {
            int randInt1 = rand.nextInt(9);
            int rantInt2 = rand.nextInt(9);
            if (board.get(randInt1, rantInt2) != 0) {
                board.set(randInt1, rantInt2, 0);
            } else {
                i--;
            }
        }
        return board;
    }
}
