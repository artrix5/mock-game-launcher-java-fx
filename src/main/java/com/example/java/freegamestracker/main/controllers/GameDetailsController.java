package com.example.java.freegamestracker;

import com.example.java.freegamestracker.api.ApiClient;
import com.example.java.freegamestracker.entities.GameDetails;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameDetailsController {

    @FXML
    private Label title;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Text gameDescription;
    @FXML
    private TabPane tabPane;
    @FXML
    private Text gameMinimumRequirements;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox hbox;
    @FXML
    public void initialize(int id) throws IOException {

        GameDetails details = ApiClient.getGameDetails(id);

        title.setText(details.getTitle());
        gameDescription.setText(details.getDescription());
        Map<String, Object> requirements = details.getMinimumSystemRequirements();
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> entry : requirements.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            sb.append(key).append(": ").append(value).append("\n");
        }

        String giantString = sb.toString();
        gameMinimumRequirements.setText(giantString);
        gameMinimumRequirements.wrappingWidthProperty().bind(anchorPane.widthProperty());
        gameMinimumRequirements.fontProperty().bind(Bindings.createObjectBinding(() ->
                        Font.font("Arial", FontWeight.NORMAL, anchorPane.getWidth() / 70),
                anchorPane.widthProperty()));

        List<String> screenshots = details.getScreenshots();
        hbox.setSpacing(10); // adjust as needed
        hbox.setPadding(new Insets(20)); // add padding of 20

        for (String screenshot : screenshots) {
            ImageView imageView = new ImageView(new Image(screenshot));
            imageView.setFitWidth(300); // adjust as needed
            imageView.setFitHeight(200); // adjust as needed
            imageView.setPreserveRatio(true);
            hbox.getChildren().add(imageView);
            gameDescription.wrappingWidthProperty().bind(anchorPane.widthProperty());
            gameDescription.fontProperty().bind(Bindings.createObjectBinding(() ->
                            Font.font("Arial", FontWeight.NORMAL, anchorPane.getWidth() / 70),
                    anchorPane.widthProperty()));

            // add listener to resize image when screen size changes
            /*ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
                imageView.setFitWidth(hbox.getWidth() / screenshots.size() - 10); // adjust as needed
                imageView.setFitHeight(hbox.getHeight() - hbox.getPadding().getTop() - hbox.getPadding().getBottom()); // adjust as needed
            };
            hbox.widthProperty().addListener(stageSizeListener);
            hbox.heightProperty().addListener(stageSizeListener);
        }

             */

            // center the HBox in the ScrollPane
            hbox.setAlignment(Pos.CENTER);


        }
    }
}







