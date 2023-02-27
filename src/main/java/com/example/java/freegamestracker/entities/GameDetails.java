package com.example.java.freegamestracker;

import com.example.java.freegamestracker.entities.Game;

import java.util.List;
import java.util.Map;

public class GameDetails extends Game {

    String description;
    List<String> screenshots;
    Map<String, Object> minimumSystemRequirements;

    public GameDetails(int id, String title, String thumbnail, String platform, String developer, String shortDescription, String releaseDate, String genre, String description, List<String> screenshots, Map<String, Object> minimumSystemRequirements) {
        super(id, title, thumbnail, platform, developer, shortDescription, releaseDate, genre);
        this.description = description;
        this.screenshots = screenshots;
        this.minimumSystemRequirements = minimumSystemRequirements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public Map<String, Object> getMinimumSystemRequirements() {
        return minimumSystemRequirements;
    }

    public void setMinimumSystemRequirements(Map<String, Object> minimumSystemRequirements) {
        this.minimumSystemRequirements = minimumSystemRequirements;
    }
}
