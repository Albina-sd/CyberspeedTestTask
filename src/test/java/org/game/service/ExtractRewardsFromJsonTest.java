package org.game.service;

import org.game.data.BonusSymbolData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtractRewardsFromJsonTest {
    private static final String TEST_CONFIG = "/test-config-for-extract-rewards.json";
    private static final String TEST_CONFIG_NOT_FOUND = "/test-config.json";

    @Test
    void testGetStandardSymbolRewards_Success() throws IOException {
        String json = """
                {
                   "symbols": {
                     "A": {
                       "type": "standard",
                       "reward_multiplier": 1.5
                     },
                     "B": {
                       "type": "bonus",
                       "reward_multiplier": 2.0,
                       "extra": 5,
                       "impact": "multiply"
                     },
                     "C": {
                       "type": "bonus",
                       "impact": "wild"
                     }
                   }
                 }
            """;

        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        ExtractRewardsFromJsonTestWrapper wrapper = new ExtractRewardsFromJsonTestWrapper(inputStream);

        Map<String, Double> result = wrapper.getStandardSymbolRewards(TEST_CONFIG);

        assertEquals(1, result.size());
        assertEquals(1.5, result.get("A"), 0.001);
        assertFalse(result.containsKey("C"));
    }

    @Test
    void testGetStandardSymbolRewards_FileNotFound() throws IOException {
        ExtractRewardsFromJsonTestWrapper wrapper = new ExtractRewardsFromJsonTestWrapper(null);

        IOException exception = assertThrows(IOException.class, () ->
                wrapper.getStandardSymbolRewards(TEST_CONFIG_NOT_FOUND));
        assertEquals("File not found: " + TEST_CONFIG_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetBonusSymbolRewards_Success() throws IOException {
        String json = """
            {
                "symbols": {
                    "A": {
                        "type": "standard",
                        "reward_multiplier": 1.5
                    },
                    "B": {
                        "type": "bonus",
                        "reward_multiplier": 2.0,
                        "extra": 5,
                        "impact": "multiply"
                    },
                    "C": {
                        "type": "bonus",
                        "impact": "wild"
                    }
                }
            }
            """;

        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        ExtractRewardsFromJsonTestWrapper wrapper = new ExtractRewardsFromJsonTestWrapper(inputStream);

        Map<String, BonusSymbolData> result = wrapper.getBonusSymbolRewards(TEST_CONFIG);

        assertEquals(2, result.size());

        BonusSymbolData bData = result.get("B");
        assertEquals(2.0, bData.getRewardMultiplier(), 0.001);
        assertEquals(5, bData.getExtra().intValue());
        assertEquals("multiply", bData.getImpact());

        BonusSymbolData cData = result.get("C");
        assertNull(cData.getRewardMultiplier());
        assertNull(cData.getExtra());
        assertEquals("wild", cData.getImpact());

        assertFalse(result.containsKey("A"));
    }

    static class ExtractRewardsFromJsonTestWrapper extends ExtractRewardsFromJson {
        private final InputStream testInputStream;

        ExtractRewardsFromJsonTestWrapper(InputStream testInputStream) {
            this.testInputStream = testInputStream;
        }

        @Override
        protected InputStream getResourceAsStream(String resource) {
            return testInputStream;
        }
    }
}