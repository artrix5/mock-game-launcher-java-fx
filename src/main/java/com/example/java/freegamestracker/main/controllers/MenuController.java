package com.example.java.freegamestracker;

import com.example.java.freegamestracker.entities.Contants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private GameLibraryController gameLibraryController;
    @FXML
    private Button allGames;

    @FXML
    public void showGames(String category) throws IOException {
        if (gameLibraryController == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_library.fxml"));
            Parent gameLibraryParent = loader.load();
            gameLibraryController = loader.getController();

            // Save the current scene dimensions
            Scene currentScene = allGames.getScene();
            double currentWidth = currentScene.getWidth();
            double currentHeight = currentScene.getHeight();

            Scene scene = new Scene(gameLibraryParent, currentWidth, currentHeight); // Apply the saved dimensions
            Stage stage = (Stage) allGames.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        gameLibraryController.initialize(category);
    }
    @FXML
    public void showAllGames() throws IOException {
        showGames(Contants.allGames);
    }

    @FXML
    public void showShooters() throws IOException {
        showGames(Contants.shooters);
    }

    @FXML
    public void showMMOARPG() throws IOException {
        showGames(Contants.MMOARPG);
    }

    @FXML
    public void showStategy() throws IOException {
        showGames(Contants.Strategy);
    }

    @FXML
    public void showFighting() throws IOException {
        showGames(Contants.Fighting);
    }

    @FXML
    public void showActionRPG() throws IOException {
        showGames(Contants.ActionRPG);
    }

    @FXML
    public void showBattleRoyale() throws IOException {
        showGames(Contants.battleRoyale);
    }

    @FXML
    public void showSports() throws IOException {
        showGames(Contants.Sports);
    }

    @FXML
    public void showOpenWorld() throws IOException {
        showGames(Contants.OpenWorld);
    }

    @FXML
    public void showPvP() throws IOException {
        showGames(Contants.PvP);
    }

    @FXML
    public void showPvE() throws IOException {
        showGames(Contants.PvE);
    }

    @FXML
    public void showSurvival() throws IOException {
        showGames(Contants.Survival);
    }

    @FXML
    public void showRacing() throws IOException {
        showGames(Contants.Racing);
    }

    @FXML
    public void showMOBA() throws IOException {
        showGames(Contants.Moba);
    }
}
