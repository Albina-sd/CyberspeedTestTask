package org.game.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import org.game.data.WinCombinationData;

public class FindWinCombinationsTest {

    private WinCombinationData createWinCombinationData() {
        return new WinCombinationData("dummy", 0.0, "", 0, "", new ArrayList<>());
    }

    @Test
    public void testFindAndAddWinCombinations() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"}
        };

        String[] basicCharacters = {"A"};

        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("A", 3);

        Map<String, WinCombinationData> winCombinationDataMap = new HashMap<>();
        for (int i = 3; i <= 9; i++) {
            winCombinationDataMap.put("same_symbol_" + i + "_times", createWinCombinationData());
        }
        winCombinationDataMap.put("same_symbols_vertically", createWinCombinationData());
        winCombinationDataMap.put("same_symbols_horizontally", createWinCombinationData());
        winCombinationDataMap.put("same_symbols_diagonally_left_to_right", createWinCombinationData());
        winCombinationDataMap.put("same_symbols_diagonally_right_to_left", createWinCombinationData());

        FindWinCombinations.findAndAddWinCombinations(winCombinationDataMap, countMap, matrix, basicCharacters);

        List<String> expectedCount3 = List.of("A");
        assertEquals(expectedCount3,
                winCombinationDataMap.get("same_symbol_3_times").getSymbolsWithWinCombinations(),
                "Для ключа same_symbol_3_times ожидается список с 'A'");

        for (int i = 4; i <= 9; i++) {
            assertTrue(winCombinationDataMap.get("same_symbol_" + i + "_times").getSymbolsWithWinCombinations().isEmpty(),
                    "Для ключа same_symbol_" + i + "_times ожидается пустой список");
        }

        List<String> expectedVertical = List.of("A", "A", "A");
        assertEquals(expectedVertical,
                winCombinationDataMap.get("same_symbols_vertically").getSymbolsWithWinCombinations(),
                "Вертикальные триплеты должны содержать три 'A'");

        assertEquals(expectedVertical,
                winCombinationDataMap.get("same_symbols_horizontally").getSymbolsWithWinCombinations(),
                "Горизонтальные триплеты должны содержать три 'A'");

        List<String> expectedDiagLTR = List.of("A");
        assertEquals(expectedDiagLTR,
                winCombinationDataMap.get("same_symbols_diagonally_left_to_right").getSymbolsWithWinCombinations(),
                "Диагональ слева-направо должна содержать 'A'");

        List<String> expectedDiagRTL = List.of("A");
        assertEquals(expectedDiagRTL,
                winCombinationDataMap.get("same_symbols_diagonally_right_to_left").getSymbolsWithWinCombinations(),
                "Диагональ справа-налево должна содержать 'A'");
    }
}