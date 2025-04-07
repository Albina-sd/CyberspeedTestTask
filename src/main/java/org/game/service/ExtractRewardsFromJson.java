package org.game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.data.BonusSymbolData;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class ExtractRewardsFromJson {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Double> getStandardSymbolRewards(String config) throws IOException {
        try (InputStream inputStream = ExtractRewardsFromJson.class.getResourceAsStream(config)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + config);
            }

            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode symbolsNode = rootNode.path("symbols");

            Map<String, Double> symbolRewards = new HashMap<>();

            symbolsNode.fields().forEachRemaining(entry -> {
                String symbol = entry.getKey();
                JsonNode symbolData = entry.getValue();

                if ("standard".equals(symbolData.path("type").asText())) {
                    double rewardMultiplier = symbolData.path("reward_multiplier").asDouble();
                    symbolRewards.put(symbol, rewardMultiplier);
                }
            });

            return symbolRewards;
        }
    }

    public static Map<String, BonusSymbolData> getBonusSymbolRewards(String config) throws IOException {
        try (InputStream inputStream = ExtractRewardsFromJson.class.getResourceAsStream(config)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + config);
            }

            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode symbolsNode = rootNode.path("symbols");

            Map<String, BonusSymbolData> bonusRewards = new HashMap<>();
            symbolsNode.fields().forEachRemaining(entry -> {
                String symbol = entry.getKey();
                JsonNode symbolData = entry.getValue();

                if ("bonus".equals(symbolData.path("type").asText())) {
                    Double rewardMultiplier = symbolData.has("reward_multiplier") ?
                            symbolData.path("reward_multiplier").asDouble() : null;
                    Integer extra = symbolData.has("extra") ?
                            symbolData.path("extra").asInt() : null;
                    String impact = symbolData.path("impact").asText();

                    bonusRewards.put(symbol, new BonusSymbolData(rewardMultiplier, extra, impact));
                }
            });
            return bonusRewards;
        }
    }

    protected abstract InputStream getResourceAsStream(String resource);
}
