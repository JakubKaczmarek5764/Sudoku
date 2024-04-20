package pl.compo;

import java.sql.*;
import java.util.ArrayList;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private Connection conn = null;
    public static final String dbName = "SudokuBoard";
    private final String protocol = "jdbc:derby:";

    private Connection connect() throws DaoDatabaseException {

        try {
            conn = DriverManager.getConnection(protocol + dbName);
            conn.setAutoCommit(false);
            conn.commit();
        } catch (SQLException e) {
            throw new DaoDatabaseException(ExceptionMessages.databaseConnectionProblem, e);

        }
        return conn;
    }

    public ArrayList<String> getAllNames() throws DaoDatabaseException {
        ArrayList<String> listOfNames = new ArrayList<>();
        if (conn == null) {
            conn = connect();
        }
        try {
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("select boardName from Boards");
            while (resultSet.next()) {
                String boardName = resultSet.getString("boardName");
                listOfNames.add(boardName);
            }
            stat.close();
            resultSet.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DaoDatabaseException(ExceptionMessages.databaseReadProblem, e);
        }
        return listOfNames;
    }

    @Override
    public SudokuBoard read(String name) throws DaoDatabaseException {
        SudokuBoard newBoard = Repository.getBoard();

        if (conn == null) {
            conn = connect();
        }
        try {
            PreparedStatement stat = conn.prepareStatement("select boardID from Boards where boardName = ?");
            stat.setString(1, name);

            ResultSet resultSet = stat.executeQuery();
            int boardID = 0;
            if (resultSet.next()) {
                boardID = resultSet.getInt("boardID");
            } else {
                throw new DaoDatabaseException(ExceptionMessages.databaseReadProblem, new Throwable());
            }


            stat = conn.prepareStatement("select * from Fields where boardID = ?");
            stat.setInt(1, boardID);
            resultSet = stat.executeQuery();
            while (resultSet.next()) {
                int posX = resultSet.getInt("posX");
                int posY = resultSet.getInt("posY");
                int value = resultSet.getInt("value");
                newBoard.set(posX, posY, value);
            }
            stat.close();
            resultSet.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DaoDatabaseException(ExceptionMessages.databaseReadProblem, e);
        }
        return newBoard;

    }

    @Override
    public void write(SudokuBoard obj, String name) throws DaoDatabaseException {
        if (conn == null) {
            conn = connect();
        }
        try {
            //zapis tablicy
            PreparedStatement stat = conn.prepareStatement("insert into Boards(boardName) VALUES(?)");
            stat.setString(1, name);
            stat.executeUpdate();

            //zapis fieldow
            PreparedStatement prStat =
                    conn.prepareStatement("insert into Fields(boardID, value, posX, posY, isEditable)"
                            + " VALUES ((SELECT boardID from Boards where boardName = ?), ?, ?, ?, ?)");
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    int fieldValue = obj.get(x, y);
                    boolean isEditable = true;
                    if (fieldValue != 0) {
                        isEditable = false;
                    }
                    prStat.setString(1, name);
                    prStat.setInt(2, fieldValue);
                    prStat.setInt(3, x);
                    prStat.setInt(4, y);
                    prStat.setBoolean(5, isEditable);
                    prStat.executeUpdate();
                }
            }
            stat.close();
            prStat.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DaoDatabaseException(ExceptionMessages.databaseWriteProblem, e);
        }
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
