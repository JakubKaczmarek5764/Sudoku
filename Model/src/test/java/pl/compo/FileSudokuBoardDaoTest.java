package pl.compo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    private SudokuBoard board1, board2;

    @BeforeEach
    void init() {

        board1 = Repository.getBoard();
        board2 = Repository.getBoard();
    }

    @Test
    void writeReadTest() {
        board1.solveGame();
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("testDao.txt")) {
            fileSudokuBoardDao.write(board1, "");
            board2 = fileSudokuBoardDao.read("");

        } catch (Exception | DaoException e) {
            throw new RuntimeException(e);
        }

        assertEquals(board1, board2);

    }

    @Test
    void closeTest() {
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao(null)) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}