package pl.comp.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.compo.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    public GridPane grid1;
    public GridPane grid = new GridPane();
    @FXML
    public Button buttonShowAuth;
    private final Button buttonSave = new Button(bundle.getString("save"));
    private Button buttonSaveBase = new Button(bundle.getString("saveB"));
    private TextField tf = new TextField();

    public MenuButton menuButton1 = new MenuButton();
    @FXML
    private Button buttonLoad;
    @FXML
    private Button langPl;
    @FXML
    private Button langEn;
    SudokuBoard board = Repository.getBoard();
    private Difficulty level = Difficulty.EASY;
    private static Locale locale = new Locale("pl");
    private static ResourceBundle bundle = ResourceBundle.getBundle("MyBundle", locale);

    @FXML
    private RadioButton raButtonEasy;

    @FXML
    private RadioButton raButtonMedium;

    @FXML
    private RadioButton raButtonHard;

    @FXML
    private AnchorPane rootPane;


    public void getDifficulty() {
        if (raButtonEasy.isSelected()) {
            level = Difficulty.EASY;
        } else if (raButtonMedium.isSelected()) {
            level = Difficulty.MEDIUM;
        } else if (raButtonHard.isSelected()) {
            level = Difficulty.HARD;
        }
    }


    public void setLanguagePl() {
        locale = new Locale("pl");
        bundle = ResourceBundle.getBundle("MyBundle", locale);
        loadView();
    }


    public void setLanguageEn() {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("MyBundle", locale);
        loadView();
    }

    private void loadView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/start.fxml"));
        loader.setResources(bundle);
        try {
            Parent root = loader.load();
            Stage s = (Stage) langPl.getScene().getWindow();
            s.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startGame() {
        board = level.prepareBoard(board);
        insertBoard();
    }

    @FXML
    public void loadSecondScene() {
        startGame();
        loadGame();
    }

    private void loadGame() {
        grid.setLayoutX(48.0);
        grid.setLayoutY(32.0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPrefSize(505, 505);
        grid.setPadding(new Insets(5, 5, 5, 5));
        insertBoard();
        buttonSave.setLayoutX(150.0);
        buttonSave.setLayoutY(583.0);
        buttonSave.setFont(Font.font("System", 14));
        buttonSave.setOnAction(this::saveFile);
        buttonSaveBase.setLayoutX(300.0);
        buttonSaveBase.setLayoutY(583.0);
        buttonSaveBase.setFont(Font.font("System", 14));
        buttonSaveBase.setOnAction(this::saveToDatabase);
        tf.setLayoutX(300.0);
        tf.setLayoutY(550.0);
        tf.setFont(Font.font("System", 14));
        rootPane.getChildren().setAll(grid, buttonSave, buttonSaveBase, tf);

    }

    private void insertBoard() {
        JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField t = new TextField();
                TextFormatter<Object> textFormatter = new TextFormatter<>(change -> {
                    if (change.getControlNewText().matches("[1-9]?")) {
                        return change;
                    } else {
                        return null;
                    }
                });
                t.setTextFormatter(textFormatter);
                DummySudokuField dsf = new DummySudokuField(board, i, j);
                try {
                    Bindings.bindBidirectional(t.textProperty(),
                            builder.bean(dsf).name("value").build(),
                            new MyConverter());
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                if (board.get(i, j) != 0) {
                    t.setEditable(false);
                }
                t.setAlignment(Pos.CENTER);
                t.setFont(Font.font("System", 22));

                grid.add(t, i, j);
            }
        }
    }

    public void loadFile(ActionEvent actionEvent) throws DaoFileException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao(selectedFile.getPath())) {
            board = fileSudokuBoardDao.read("");
            loadGame();
        } catch (Exception | DaoException e) {
            throw new DaoFileException("readProblem", e);
        }
    }

    public void saveFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao(selectedFile.getPath())) {
            fileSudokuBoardDao.write(board, "");
        } catch (Exception | DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAuthors(ActionEvent actionEvent) throws SQLException {
        AuthorsBundle authorsBundle = new AuthorsBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("authors"));
        alert.setContentText(authorsBundle.getString("Author1") + "\n" + authorsBundle.getString("Author2"));
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setMenuButton(Event actionEvent) {
        try (JdbcSudokuBoardDao DaoDatabase = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDatabaseDao()) {
            ArrayList<String> listy = DaoDatabase.getAllNames();
            menuButton1.getItems().clear();
            for (String tmpstring : listy) {
                MenuItem menuItem = new MenuItem(tmpstring);
                menuItem.setOnAction(actionEvent1 -> loadFromDataBase(actionEvent1, menuItem.getText()));
                menuButton1.getItems().add(menuItem);
            }

        } catch (Exception | DaoDatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromDataBase(ActionEvent actionEvent, String name) {
        try (Dao<SudokuBoard> DaoDatabase = SudokuBoardDaoFactory.getDatabaseDao()) {
            board = DaoDatabase.read(name);
            loadGame();
        } catch (Exception | DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDatabase(ActionEvent actionEvent) {
        try (Dao<SudokuBoard> DaoDatabase = SudokuBoardDaoFactory.getDatabaseDao()) {
            DaoDatabase.write(board, tf.getText());
        } catch (Exception | DaoException e) {
            throw new RuntimeException(e);
        }
    }

}
