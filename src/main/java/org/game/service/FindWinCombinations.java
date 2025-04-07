package org.game.service;

import org.game.data.WinCombinationData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindWinCombinations {
    //todo refactor
    public static Map<String, WinCombinationData> findAndAddWinCombinations(
            Map<String, WinCombinationData> winCombinationDataMap,
            Map<String, Integer> countMap,
            String [][] matrix,
            String[] basicCharacters) throws IOException {
        WinCombinationData tripleChars = hasSameSymbolsThreeTimes(countMap, winCombinationDataMap.get("same_symbol_3_times"));
        winCombinationDataMap.put("same_symbol_3_times", tripleChars);

        WinCombinationData fourChars = hasSameSymbolsFourTimes(countMap, winCombinationDataMap.get("same_symbol_4_times"));
        winCombinationDataMap.put("same_symbol_4_times", fourChars);

        WinCombinationData fiveChars = hasSameSymbolsFiveTimes(countMap, winCombinationDataMap.get("same_symbol_5_times"));
        winCombinationDataMap.put("same_symbol_5_times", fiveChars);

        WinCombinationData sixChars = hasSameSymbolsSixTimes(countMap, winCombinationDataMap.get("same_symbol_6_times"));
        winCombinationDataMap.put("same_symbol_6_times", sixChars);

        WinCombinationData sevenChars = hasSameSymbolsSevenTimes(countMap, winCombinationDataMap.get("same_symbol_7_times"));
        winCombinationDataMap.put("same_symbol_7_times", sevenChars);

        WinCombinationData eightChars = hasSameSymbolsEightTimes(countMap, winCombinationDataMap.get("same_symbol_8_times"));
        winCombinationDataMap.put("same_symbol_8_times", eightChars);

        WinCombinationData nineChars = hasSameSymbolsNineTimes(countMap, winCombinationDataMap.get("same_symbol_9_times"));
        winCombinationDataMap.put("same_symbol_9_times", nineChars);

        WinCombinationData verticalTripleChars = hasVerticalTriples(matrix, winCombinationDataMap.get("same_symbols_vertically"), basicCharacters);
        winCombinationDataMap.put("same_symbols_vertically", verticalTripleChars);

        WinCombinationData horizontalTripleChars = hasHorizontalTriples(matrix, winCombinationDataMap.get("same_symbols_horizontally"), basicCharacters);
        winCombinationDataMap.put("same_symbols_horizontally", horizontalTripleChars);

        WinCombinationData sameSymbolsDiagonalLeftToRight = hasDiagonalRightToLeftTriples(matrix ,basicCharacters, winCombinationDataMap.get("same_symbols_diagonally_left_to_right"));
        winCombinationDataMap.put("same_symbols_diagonally_left_to_right", sameSymbolsDiagonalLeftToRight);

        WinCombinationData sameSymbolsDiagonalRightToLeft = hasDiagonalLeftToRightTriples(matrix ,basicCharacters, winCombinationDataMap.get("same_symbols_diagonally_right_to_left"));
        winCombinationDataMap.put("same_symbols_diagonally_right_to_left", sameSymbolsDiagonalRightToLeft);

        return winCombinationDataMap;
    }

    private static WinCombinationData hasSameSymbolsThreeTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData) {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 3) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsFourTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData) {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 4) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsFiveTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData) {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 5) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsSixTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData)  {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 6) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsSevenTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData)  {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 7) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsEightTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData)  {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 8) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasSameSymbolsNineTimes(Map<String, Integer> countMap, WinCombinationData winCombinationData) {
        List<String> tripleChars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 9) {
                tripleChars.add(entry.getKey());
            }
        }
        winCombinationData.setSymbolsWithWinCombinations(tripleChars);
        return winCombinationData;
    }

    private static WinCombinationData hasVerticalTriples(String[][] matrix, WinCombinationData winCombinationData, String[] basicCharacters) throws IOException {
        List<String> verticalTripleSymbols = new ArrayList<>();

        for (int j = 0; j < matrix.length; j++) {
            String first = matrix[0][j];
            boolean isTriple = true;
            boolean isBasic = false;

            for (String basic : basicCharacters) {
                if (first.equals(basic)) {
                    isBasic = true;
                    break;
                }
            }

            if (!isBasic) {
                continue;
            }

            for (int i = 1; i < matrix.length; i++) {
                if (!matrix[i][j].equals(first)) {
                    isTriple = false;
                    break;
                }
            }

            if (isTriple) {
                verticalTripleSymbols.add(first);
            }
        }

        winCombinationData.setSymbolsWithWinCombinations(verticalTripleSymbols);
        return winCombinationData;
    }

    private static WinCombinationData hasHorizontalTriples(String[][] matrix, WinCombinationData winCombinationData, String[] basicCharacters) throws IOException {
        List<String> horizontalTripleSymbols = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            String first = matrix[i][0];
            boolean isTriple = true;
            boolean isBasic = false;

            for (String basic : basicCharacters) {
                if (first.equals(basic)) {
                    isBasic = true;
                    break;
                }
            }

            if (!isBasic) {
                continue;
            }

            for (int j = 1; j < matrix.length; j++) {
                if (!matrix[i][j].equals(first)) {
                    isTriple = false;
                    break;
                }
            }

            if (isTriple) {
                horizontalTripleSymbols.add(first);
            }
        }

        winCombinationData.setSymbolsWithWinCombinations(horizontalTripleSymbols);
        return winCombinationData;
    }

    private static WinCombinationData hasDiagonalLeftToRightTriples(String[][] matrix, String[] basicCharacters, WinCombinationData winCombinationData) {
        List<String> diagonalTripleSymbols = new ArrayList<>();

        String first = matrix[0][0];
        boolean isTriple = true;
        boolean isBasic = false;

        for (String basic : basicCharacters) {
            if (first.equals(basic)) {
                isBasic = true;
                break;
            }
        }

        if (isBasic) {
            for (int i = 1; i < matrix.length; i++) {
                if (!matrix[i][i].equals(first)) {
                    isTriple = false;
                    break;
                }
            }
            if (isTriple) {
                diagonalTripleSymbols.add(first);
            }
        }

        winCombinationData.setSymbolsWithWinCombinations(diagonalTripleSymbols);

        return winCombinationData;
    }

    private static WinCombinationData hasDiagonalRightToLeftTriples(String[][] matrix, String[] basicCharacters, WinCombinationData winCombinationData) {
        List<String> diagonalTripleSymbols = new ArrayList<>();

        String first = matrix[0][2];
        boolean isTriple = true;
        boolean isBasic = false;

        for (String basic : basicCharacters) {
            if (first.equals(basic)) {
                isBasic = true;
                break;
            }
        }

        if (isBasic) {
            if (!matrix[1][1].equals(first) || !matrix[2][0].equals(first)) {
                isTriple = false;
            }
            if (isTriple) {
                diagonalTripleSymbols.add(first);
            }
        }

        winCombinationData.setSymbolsWithWinCombinations(diagonalTripleSymbols);

        return winCombinationData;
    }
}
