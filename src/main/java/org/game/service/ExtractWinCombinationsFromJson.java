package org.game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.data.WinCombinationData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractWinCombinationsFromJson {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, WinCombinationData> getWinCombinations(String config) throws IOException {
        try (InputStream inputStream = ExtractWinCombinationsFromJson.class.getResourceAsStream(config)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + config);
            }
            return getWinCombinationsFromStream(inputStream);
        }
    }

    public static Map<String, WinCombinationData> getWinCombinationsFromStream(InputStream inputStream) throws IOException {
        JsonNode rootNode = objectMapper.readTree(inputStream);
        JsonNode winCombinationsNode = rootNode.path("win_combinations");

        Map<String, WinCombinationData> winCombinations = new HashMap<>();

        winCombinationsNode.fields().forEachRemaining(entry -> {
            String name = entry.getKey();
            JsonNode data = entry.getValue();

            double rewardMultiplier = data.path("reward_multiplier").asDouble();
            String when = data.path("when").asText();
            String group = data.path("group").asText();

            Integer count = data.has("count") ? data.path("count").asInt() : null;

            List<List<String>> coveredAreas = null;
            if (data.has("covered_areas")) {
                coveredAreas = new ArrayList<>();
                JsonNode areasNode = data.path("covered_areas");
                for (JsonNode area : areasNode) {
                    List<String> positions = new ArrayList<>();
                    for (JsonNode position : area) {
                        positions.add(position.asText());
                    }
                    coveredAreas.add(positions);
                }
            }

            winCombinations.put(name, new WinCombinationData(name, rewardMultiplier, when, count, group, coveredAreas));
        });

        return winCombinations;
    }
}
