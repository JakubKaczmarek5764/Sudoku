package pl.compo;

public class Repository {
    private static SudokuBoard sudokuBoard = new SudokuBoard(null);

    public static SudokuBoard getBoard() {
        return sudokuBoard.clone();
    }
}
