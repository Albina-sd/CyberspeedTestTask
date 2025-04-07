package org.game.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtractSymbolsAndProbabilitiesFromJsonTest {

    private static final String CONFIG_PATH = "/test-config-for-symbols-probabilities.json";

    @Test
    void testGetProbabilitiesByPosition_Success() throws IOException {
        // Подготовка тестового JSON
        String configPath = CONFIG_PATH;

        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 50);
        expected.put("B", 30);
        expected.put("C", 20);

        Map<String, Integer> result = ExtractSymbolsAndProbabilitiesFromJson.getProbabilitiesByPosition(0, 0, configPath);

        assertNotNull(result, "Result should not be null for valid position");
        assertEquals(expected, result, "Probabilities should match expected values");
    }

    @Test
    void testGetProbabilitiesByPosition_InvalidPosition() throws IOException {
        String configPath = CONFIG_PATH;

        Map<String, Integer> result = ExtractSymbolsAndProbabilitiesFromJson.getProbabilitiesByPosition(999, 999, configPath);

        assertNull(result, "Result should be null for invalid position");
    }

    @Test
    void testGetProbabilitiesByPosition_FileNotFound() {
        String invalidConfigPath = "/non_existent_file.json";

        IOException exception = assertThrows(IOException.class, () -> {
            ExtractSymbolsAndProbabilitiesFromJson.getProbabilitiesByPosition(0, 0, invalidConfigPath);
        });

        assertEquals("File not found: " + invalidConfigPath, exception.getMessage());
    }

    @Test
    void testGetBonusSymbols_Success() throws IOException {
        String configPath = CONFIG_PATH;

        Map<String, Integer> expected = new HashMap<>();
        expected.put("WILD", 10);
        expected.put("SCATTER", 5);

        Map<String, Integer> result = ExtractSymbolsAndProbabilitiesFromJson.getBonusSymbols(configPath);

        assertNotNull(result, "Result should not be null");
        assertEquals(expected, result, "Bonus symbols should match expected values");
    }

    @Test
    void testGetBonusSymbols_FileNotFound() {
        String invalidConfigPath = "/non_existent_file.json";

        IOException exception = assertThrows(IOException.class, () -> {
            ExtractSymbolsAndProbabilitiesFromJson.getBonusSymbols(invalidConfigPath);
        });

        assertEquals("File not found: " + invalidConfigPath, exception.getMessage());
    }
}