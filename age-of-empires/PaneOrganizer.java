package indy;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class PaneOrganizer {

    private BorderPane _root;

    public PaneOrganizer(Stage s) {
        _root = new BorderPane();
        createAgeOfEmpiresPane(createButtonsPane());
    }

    public void createAgeOfEmpiresPane(Pane buttonPane) {
        Pane ageOfEmpiresPane = new Pane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setContent(ageOfEmpiresPane);
        scrollPane.setPrefSize(Constants.BOARD_LENGTH * Constants.BIOME_SQUARE_SIZE,Constants.BOARD_LENGTH * Constants.BIOME_SQUARE_SIZE);
        ageOfEmpiresPane.setPrefSize(Constants.BOARD_LENGTH * Constants.BIOME_SQUARE_SIZE, Constants.BOARD_LENGTH * Constants.BIOME_SQUARE_SIZE );
        ageOfEmpiresPane.setStyle("-fx-background-color: gray;");
        _root.setCenter(scrollPane);
        new AgeOfEmpires(ageOfEmpiresPane, buttonPane,Constants.BOARD_LENGTH, Constants.BOARD_LENGTH);
    }

    public Pane createButtonsPane() {
        Pane buttonsPane = new Pane();
        buttonsPane.setPrefSize(300,20 * Constants.BIOME_SQUARE_SIZE);
        buttonsPane.setStyle("-fx-background-color: white;");
        _root.setRight(buttonsPane);
        return buttonsPane;
    }

    public BorderPane getRoot() {
        return _root;
    }
}
