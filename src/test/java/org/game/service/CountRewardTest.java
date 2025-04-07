package org.game.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountRewardTest {

    private static final String TEST_CONFIG = "/test-config.json";

    @Test
    void countReward_withValidInput_returnsExpectedReward() throws Exception {
        int betAmount = 100;
        String[][] matrix = {
                {"A", "B", "A"},
                {"B", "A", "B"},
                {"A", "A", "C"}
        };

        CountReward countRewardService = new CountReward();

        String result = countRewardService.countReward(betAmount, matrix, TEST_CONFIG);

        assertTrue(result.contains("reward: 5300.0"));
    }

    @Test
    void countReward_withVerticalCombination_returnsExpectedWinCombination() throws Exception {
        int betAmount = 100;
        String[][] matrix = {
                {"A", "B", "B"},
                {"A", "+500", "F"},
                {"A", "F", "MISS"}
        };

        CountReward countRewardService = new CountReward();

        String result = countRewardService.countReward(betAmount, matrix, TEST_CONFIG);

        assertTrue(result.contains("applied_winning_combinations: \nA: same_symbols_vertically"));
    }

    @Test
    void countReward_withLefToRightCombination_returnsExpectedWinCombination() throws Exception {
        int betAmount = 100;
        String[][] matrix = {
                {"A", "B", "B"},
                {"F", "A", "F"},
                {"D", "D", "A"}
        };

        CountReward countRewardService = new CountReward();

        String result = countRewardService.countReward(betAmount, matrix, TEST_CONFIG);

        assertTrue(result.contains("applied_winning_combinations: \nA: same_symbols_diagonally_left_to_right"));
    }

    @Test
    void countReward_withNoWinningCombination_returnsZeroReward() throws Exception {
        int betAmount = 50;
        String[][] matrix = {
                {"X", "Y", "Z"},
                {"Y", "X", "Y"},
                {"Z", "Y", "X"}
        };

        CountReward countRewardService = new CountReward();

        String result = countRewardService.countReward(betAmount, matrix, TEST_CONFIG);

        assertTrue(result.contains("reward: 0.0"));
    }

    @Test
    void countReward_withAppliedBonuses_returnsModifiedReward() throws Exception {
        int betAmount = 150;
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "B", "B"},
                {"+500", "+500", "+500"}
        };

        CountReward countRewardService = new CountReward();

        String result = countRewardService.countReward(betAmount, matrix, TEST_CONFIG);

        assertTrue(result.contains("applied_bonuses: [+500, +500, +500]"));
    }
}