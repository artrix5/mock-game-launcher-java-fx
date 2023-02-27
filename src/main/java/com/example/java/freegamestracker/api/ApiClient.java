package com.example.java.freegamestracker;

import com.example.java.freegamestracker.entities.Game;
import com.google.gson.Gson;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class ApiClient {

    public static List<Game> getGames(String genre) throws IOException {
        // Create a URL object for the API endpoint
        URL url = new URL(genre);

        // Open an HTTP connection to the API endpoint
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the response from the API
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response and create a list of Games objects
        Gson gson = new Gson();
        Game[] gamesArray = gson.fromJson(response.toString(), Game[].class);
        List<Game> gamesList = new ArrayList<>();
        for (Game game : gamesArray) {
            gamesList.add(new Game(game.getId(), game.getTitle(), game.getThumbnail(), game.getPlatform(), game.getDeveloper(), game.getShortDescription(), game.getReleaseDate(), game.getGenre()));
            System.out.println(game.getTitle());
            System.out.println(game.getThumbnail());
        }

        // Return the list of Games objects
        return gamesList;
    }

    public static GameDetails getGameDetails(int id) throws IOException {
        // Create a URL object for the API endpoint
        URL url = new URL("https://www.freetogame.com/api/game?id=" + id);

        // Open an HTTP connection to the API endpoint
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the response from the API
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the response as JSON and create a GameDetails object
        JSONObject gameJson = new JSONObject(response.toString());
        String title = gameJson.getString("title");
        String thumbnail = gameJson.getString("thumbnail");
        String platform = gameJson.getString("platform");
        String developer = gameJson.getString("developer");
        String shortDescription = gameJson.getString("short_description");
        String releaseDate = gameJson.getString("release_date");
        String genre = gameJson.getString("genre");
        String description = gameJson.getString("description");
        JSONArray screenshotsArray = gameJson.getJSONArray("screenshots");
        List<String> screenshots = new ArrayList<>();
        for (int i = 0; i < screenshotsArray.length(); i++) {
            JSONObject screenshotJson = screenshotsArray.getJSONObject(i);
            String screenshotUrl = screenshotJson.getString("image");
            screenshots.add(screenshotUrl);
        }

        JSONObject requirementsJson = gameJson.optJSONObject("minimum_system_requirements");
        Map<String, Object> requirementsMap = new HashMap<>();

        if (requirementsJson != null) {
            for (String key : requirementsJson.keySet()) {
                Object value = requirementsJson.get(key);
                requirementsMap.put(key, value);
            }
        }


        return new GameDetails(id, title, thumbnail, platform, developer, shortDescription, releaseDate, genre, description, screenshots, requirementsMap);
    }

}