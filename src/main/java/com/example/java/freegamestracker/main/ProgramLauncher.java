package com.example.java.freegamestracker.main;

import com.example.java.freegamestracker.api.ApiClient;
import com.example.java.freegamestracker.api.ApiURL;
import com.example.java.freegamestracker.entities.Game;
import com.example.java.freegamestracker.main.controllers.GameLibraryController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class FreeGamesTracker extends Application {

    private Parent loadingScreen;
    private Parent gameLibraryScreen;
    private List<Game> gamesList = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML files for the loading screen and game library screen
        FXMLLoader loadingScreenLoader = new FXMLLoader(getClass().getResource("/com/example/java/freegamestracker/main/controllers/loading_screen.fxml"));
        loadingScreen = loadingScreenLoader.load();
        FXMLLoader gameLibraryLoader = new FXMLLoader(getClass().getResource("/com/example/java/freegamestracker/main/controllers/game_library.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Set up the stage
        primaryStage.setTitle("My Game Library");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(loadingScreen, 800, 600));
        primaryStage.show();

        Task<List<Game>> loadDataTask = new Task<>() {
            @Override
            protected List<Game> call() throws Exception {
                return ApiClient.getGames(ApiURL.allGames);
            }
        };

        // Set up the event handlers for the task
        loadDataTask.setOnRunning(event -> {
            System.out.println("Loading data from API...");
        });

        loadDataTask.setOnSucceeded(event -> {
            gamesList = loadDataTask.getValue();
            // Close the loading screen and show the main application window
            primaryStage.close();
            Stage gameLibraryStage = new Stage();
            // Hide the toolbar

            try {
                gameLibraryScreen = gameLibraryLoader.load();
                GameLibraryController gameLibraryController = gameLibraryLoader.getController();
                gameLibraryController.initialize(ApiURL.allGames);
                gameLibraryStage.setMinWidth(1280);
                gameLibraryStage.setMinHeight(720);
                gameLibraryStage.setScene(new Scene(gameLibraryScreen, 1280, 720));

                //StageResizer.enableResize(gameLibraryStage);
               // StageMover.enableMove(gameLibraryStage);
                
            } catch (Exception e) {
                System.out.println("Failed to load game library screen: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
            gameLibraryStage.show();

            // Process the games list
            System.out.println("Loaded " + gamesList.size() + " games from API.");
        });

        loadDataTask.setOnFailed(event -> {
            Throwable exception = loadDataTask.getException();
            System.out.println("Failed to load data from API: " + exception.getMessage());
            exception.printStackTrace();
        });

        // Start the task
        Thread thread = new Thread(loadDataTask);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}