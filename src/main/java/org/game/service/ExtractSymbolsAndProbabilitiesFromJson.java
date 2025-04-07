package org.game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExtractSymbolsAndProbabilitiesFromJson {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Integer> getProbabilitiesByPosition(int column, int row, String config)
            throws IOException {
        try (InputStream inputStream = ExtractSymbolsAndProbabilitiesFromJson.class.getResourceAsStream(config)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + config);
            }

            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode standardSymbols = rootNode.path("probabilities").path("standard_symbols");

            for (JsonNode node : standardSymbols) {
                int nodeColumn = node.path("column").asInt();
                int nodeRow = node.path("row").asInt();

                if (nodeColumn == column && nodeRow == row) {
                    Map<String, Integer> probabilities = new HashMap<>();
                    JsonNode symbolsNode = node.path("symbols");

                    symbolsNode.fields().forEachRemaining(entry ->
                            probabilities.put(entry.getKey(), entry.getValue().asInt())
                    );

                    return probabilities;
                }
            }

            return null;
        }
    }

    public static Map<String, Integer> getBonusSymbols(String config) throws IOException {
        try (InputStream inputStream = ExtractSymbolsAndProbabilitiesFromJson.class.getResourceAsStream(config)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + config);
            }

            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode bonusSymbolsNode = rootNode.path("probabilities").path("bonus_symbols").path("symbols");

            Map<String, Integer> bonusSymbols = new HashMap<>();
            bonusSymbolsNode.fields().forEachRemaining(entry ->
                    bonusSymbols.put(entry.getKey(), entry.getValue().asInt())
            );

            return bonusSymbols;
        }
    }
}
