import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.geometry.Orientation;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import java.io.File;
import java.io.FilenameFilter;

import java.util.List;
import java.util.ArrayList;

/**
* The ChessGui
*
* @author klin96
* @version java_1.8.0.144
*/

public class ChessGui extends Application {

    @Override
    public void start(Stage stage) {
        String[] pgnFiles = pgnFinder();
        ChessDb chessGamesDb = new ChessDb();
        modifyDB(pgnFiles, chessGamesDb);
        List<ChessGame> allGames = chessGamesDb.getGames();
        TableView<ChessGame> chessGames = new TableView<ChessGame>();
        ObservableList<ChessGame> games =
            FXCollections.observableArrayList(allGames);
        TableColumn<ChessGame, String> eventCol =
            new TableColumn<ChessGame, String>("Event");
        eventCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("event"));
        TableColumn<ChessGame, String> siteCol =
            new TableColumn<ChessGame, String>("Site");
        siteCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("site"));
        TableColumn<ChessGame, String> dataCol =
            new TableColumn<ChessGame, String>("Date");
        dataCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("date"));
        TableColumn<ChessGame, String> whiteCol =
            new TableColumn<ChessGame, String>("White");
        whiteCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("white"));
        TableColumn<ChessGame, String> blackCol =
            new TableColumn<ChessGame, String>("Black");
        blackCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("black"));
        TableColumn<ChessGame, String> resultCol =
            new TableColumn<ChessGame, String>("Result");
        resultCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("result"));
        TableColumn<ChessGame, String> openingCol =
            new TableColumn<ChessGame, String>("Opening");
        openingCol.setCellValueFactory(new
            PropertyValueFactory<ChessGame, String>("opening"));
        chessGames.getColumns().
            addAll(eventCol, siteCol, dataCol, whiteCol,
                blackCol, resultCol, openingCol);
        Button view = new Button();
        view.setDisable(true);
        chessGames.getSelectionModel().clearSelection();
        view.disableProperty().bind(Bindings.isEmpty(chessGames.
            getSelectionModel().getSelectedItems()));
        Button dismiss = new Button();
        view.setText("View");
        view.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (chessGames.getSelectionModel().
                    selectedItemProperty() != null) {
                    ChessGame selectedGame =
                        chessGames.getSelectionModel().getSelectedItem();
                    Stage details = new Stage();
                    List<String> datas = new ArrayList<>();
                    datas.add(selectedGame.getEvent());
                    datas.add(selectedGame.getSite());
                    datas.add(selectedGame.getDate());
                    datas.add(selectedGame.getWhite());
                    datas.add(selectedGame.getBlack());
                    datas.add(selectedGame.getResult());
                    String moves = "";
                    int i = 1;
                    try {
                        while (i > 0) {
                            moves += i + ". " + selectedGame.getMove(i) + " ";
                            i++;
                        }
                    } catch (IndexOutOfBoundsException exp) {
                        i = -10;
                    }
                    datas.add(moves);
                    datas.add(selectedGame.getOpening());
                    ListView<String> theGame = new ListView<String>();
                    ObservableList<String> items  =
                        FXCollections.observableArrayList(datas);
                    theGame.setItems(items);
                    theGame.setOrientation(Orientation.HORIZONTAL);
                    Scene detail = new Scene(theGame);
                    details.setTitle("Detailed Game");
                    details.setHeight(150);
                    details.setScene(detail);
                    details.show();
                    chessGames.getSelectionModel().
                        clearSelection();
                }
            }
        });
        dismiss.setText("Dismiss");
        dismiss.setOnAction(e -> stage.close());
        TextField win = new TextField();
        win.setPromptText("Search by White or Black");
        FilteredList<ChessGame> winningSearch =
            new FilteredList<ChessGame>(games, p -> true);
        win.textProperty().addListener((obs, oldValue, newValue) -> {
                winningSearch.setPredicate(cGame -> {
                        if (newValue.toLowerCase().equals("white")) {
                            return cGame.getResult().equals("1-0");
                        } else if (newValue.toLowerCase().equals("black")) {
                            return cGame.getResult().equals("0-1");
                        } else {
                            return true;
                        }
                    });
            });
        chessGames.setItems(winningSearch);
        VBox wholeWindow = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(view, dismiss, win);
        wholeWindow.getChildren().addAll(chessGames, buttons);
        Scene scene = new Scene(wholeWindow);
        stage.setScene(scene);
        stage.setTitle("Chess DB Gui");
        stage.show();
    }

    /**
    * @return a file array containing the name of all pgnFiles
    */
    public static String[] pgnFinder() {
        String currentPath = System.getProperty("user.dir");
        File f = new File(currentPath);
        FilenameFilter pgnFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".pgn");
            }
        };
        File[] pgnFiles = f.listFiles(pgnFilter);
        String[] names = new String[pgnFiles.length];
        for (int i = 0; i < pgnFiles.length; i++) {
            names[i] = pgnFiles[i].getName();
        }
        return names;
    }

    /**
    * @param files a string array containing file names
    * @param toBeModified a ChessDb to be modified
    */
    public static void modifyDB(String[] files, ChessDb toBeModified) {
        for (int j = 0; j < files.length; j++) {
            String game = PgnReader.fileContent(files[j]);
            ChessGame newOne = new ChessGame(PgnReader.tagValue("Event", game),
                PgnReader.tagValue("Site", game),
                PgnReader.tagValue("Date", game),
                PgnReader.tagValue("White", game),
                PgnReader.tagValue("Black", game),
                PgnReader.tagValue("Result", game));
            String finalPosition = "";
            String currentStep = "";
            String steps = game.substring(game.lastIndexOf("]") + 1);
            steps = steps.substring(steps.indexOf("1"));
            String[] everyTurn = steps.split("[0-9]" + "\\.");
            int elementNum = 1;
            for (int i = 1; i < everyTurn.length; i++) {
                if (everyTurn[i].contains("[0-9]")) {
                    everyTurn[i] = everyTurn[i].substring(0,
                        everyTurn[i].indexOf("[0-9]"));
                }
                everyTurn[i] = everyTurn[i].
                substring(1, everyTurn[i].length() - 1);
                if (Math.log10(i) == elementNum) {
                    elementNum++;
                }
                if (Math.log10(i) < elementNum && i < everyTurn.length - 1) {
                    everyTurn[i] = everyTurn[i].substring(0,
                        everyTurn[i].length() - elementNum + 1);
                }
                newOne.addMove(everyTurn[i]);
            }
            toBeModified.getGames().add(newOne);
        }
    }
}
