package com.example.java.freegamestracker.entities;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {

        private static final Map<String, Image> cache = new HashMap<>();

        public static Image get(String url) {
            return cache.get(url);
        }

        public static void put(String url, Image image) {
            cache.put(url, image);
        }
    }
