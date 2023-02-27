package com.example.java.freegamestracker;

import com.example.java.freegamestracker.api.ApiClient;
import com.example.java.freegamestracker.entities.Game;
import com.example.java.freegamestracker.cache.ImageCache;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GameLibraryController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TilePane tilePane;

    public void initialize(String genre) throws IOException {

        // populate the tile pane with all games
        List<Game> allGames;
        allGames = ApiClient.getGames(genre);

        tilePane.setPadding(new Insets(10, 10, 10, 40));
        tilePane.setHgap(30);
        tilePane.setVgap(30);
        tilePane.setOrientation(Orientation.HORIZONTAL);

        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = (double) newVal - 10;
            int numColumns = (int) Math.floor(width / 300);
            tilePane.setPrefColumns(numColumns == 0 ? 1 : numColumns);
        });


        // Populate the tile pane with all game images
        for (Game game : allGames) {
            addImageToTilePane(game);
        }

    }

    private void addImageToTilePane(Game game) {
        String imageUrl = game.getThumbnail();
        Image image = ImageCache.get(imageUrl);
        if (image == null) {
            image = new Image(imageUrl, true);
            ImageCache.put(imageUrl, image);
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);
        imageView.setOnMouseClicked(e -> {
            System.out.println("Clicked " + game.getTitle());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("game_details.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) imageView.getScene().getWindow();
                GameDetailsController controller = loader.getController();
                controller.initialize(game.getId());
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        imageView.setUserData(game);
        tilePane.getChildren().add(imageView);
    }
}