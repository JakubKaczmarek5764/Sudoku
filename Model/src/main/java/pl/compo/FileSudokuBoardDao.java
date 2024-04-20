package pl.compo;


import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read(String name) throws DaoFileException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoFileException(ExceptionMessages.fileReadProblem, e);
        }

    }

    @Override
    public void write(SudokuBoard obj, String name) throws DaoFileException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new DaoFileException(ExceptionMessages.fileWriteProblem, e);
        }
    }

    @Override
    public void close() {

    }
}
