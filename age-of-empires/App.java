package indy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It's time for Indy! This is the main class to get things started.
 *
 * Class comments here...
 *
 */

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Age of Empires 2!");
        PaneOrganizer po1 = new PaneOrganizer(primaryStage);
        Scene scene = new Scene(po1.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args); // launch is a method inherited from Application
    }
}
