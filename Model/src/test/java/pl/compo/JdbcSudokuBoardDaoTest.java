package pl.compo;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    @Test
    void writeReadTest() {
        try (JdbcSudokuBoardDao DaoDatabase = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao();) {
            SudokuBoard newBoard1 = Repository.getBoard();
            newBoard1.solveGame();
            UUID uuid = UUID.randomUUID();
            String nazwa = uuid.toString().substring(0, 25);
            DaoDatabase.write(newBoard1, nazwa);
            SudokuBoard newBoard2 = DaoDatabase.read(nazwa);
            assertTrue(newBoard1.equals(newBoard2));
            assertThrows(DaoDatabaseException.class, () -> DaoDatabase.write(newBoard1, nazwa));
        } catch (Exception | DaoException e) {
            throw new RuntimeException(e);
        }
    }
}