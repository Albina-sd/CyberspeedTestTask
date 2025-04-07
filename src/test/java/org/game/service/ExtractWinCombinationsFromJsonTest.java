package org.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.data.WinCombinationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtractWinCombinationsFromJsonTest {

    private InputStream createInputStream(String json) {
        return new ByteArrayInputStream(json.getBytes());
    }

    @Test
    void testGetWinCombinations_SuccessfulParsing() throws IOException {
        String jsonConfig = """
                {
                    "win_combinations": {
                        "same_symbol_3_times": {
                            "reward_multiplier": 2.0,
                            "when": "same_symbols",
                            "count": 3,
                            "group": "horizontal"
                        },
                        "same_symbol_diagonally": {
                            "reward_multiplier": 5.0,
                            "when": "same_symbols",
                            "group": "diagonal",
                            "covered_areas": [
                                ["0_0", "1_1", "2_2"]
                            ]
                        }
                    }
                }
                """;

        try (InputStream inputStream = createInputStream(jsonConfig)) {
            Map<String, WinCombinationData> result = ExtractWinCombinationsFromJson.getWinCombinations("/test-config-for-win-combinations.json");

            assertNotNull(result);
            assertEquals(2, result.size());

            WinCombinationData combination1 = result.get("same_symbol_3_times");
            assertEquals("same_symbol_3_times", combination1.getName());
            assertEquals(2.0, combination1.getRewardMultiplier(), 0.001);
            assertEquals("same_symbols", combination1.getWhen());
            assertEquals(3, combination1.getCount());
            assertEquals("horizontal", combination1.getGroup());
            assertNull(combination1.getCoveredAreas());

            WinCombinationData combination2 = result.get("same_symbol_diagonally");
            assertEquals("same_symbol_diagonally", combination2.getName());
            assertEquals(5.0, combination2.getRewardMultiplier(), 0.001);
            assertEquals("same_symbols", combination2.getWhen());
            assertNull(combination2.getCount());
            assertEquals("diagonal", combination2.getGroup());
            assertNotNull(combination2.getCoveredAreas());
            assertEquals(1, combination2.getCoveredAreas().size());
            assertEquals(List.of("0_0", "1_1", "2_2"), combination2.getCoveredAreas().get(0));
        }
    }

    @Test
    void testGetWinCombinations_FileNotFound() {
        IOException exception = assertThrows(IOException.class, () -> {
            ExtractWinCombinationsFromJson.getWinCombinations("/invalid_config.json");
        });
        assertEquals("File not found: /invalid_config.json", exception.getMessage());
    }

    @Test
    void testGetWinCombinations_EmptyWinCombinations() throws IOException {
        String jsonConfig = """
                {
                    "other_field": "value"
                }
                """;

        try (InputStream inputStream = createInputStream(jsonConfig)) {
            Map<String, WinCombinationData> result = ExtractWinCombinationsFromJson.getWinCombinations("/test-config-for-empty-win-combinations.json");

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }
}