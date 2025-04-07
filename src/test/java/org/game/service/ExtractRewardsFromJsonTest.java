package org.game.service;

import org.game.data.BonusSymbolData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtractRewardsFromJsonTest {
    private static final String TEST_CONFIG = "/test-config-for-extract-rewards.json";
    private static final String TEST_CONFIG_NOT_FOUND = "/test-config-not-found.json";

    @Test
    void testGetStandardSymbolRewards_Success() throws IOException {
       Map<String, Double> result = ExtractRewardsFromJson.getStandardSymbolRewards(TEST_CONFIG);

        assertEquals(1, result.size());
        assertEquals(1.5, result.get("A"), 0.001);
        assertFalse(result.containsKey("C"));
    }

    @Test
    void testGetStandardSymbolRewards_FileNotFound() {
        IOException exception = assertThrows(IOException.class, () ->
                ExtractRewardsFromJson.getStandardSymbolRewards(TEST_CONFIG_NOT_FOUND));
        assertEquals("File not found: " + TEST_CONFIG_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetBonusSymbolRewards_Success() throws IOException {
        Map<String, BonusSymbolData> result = ExtractRewardsFromJson.getBonusSymbolRewards(TEST_CONFIG);

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
}