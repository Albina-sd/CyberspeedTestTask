package org.game.service;

import org.game.data.WinCombinationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindWinCombinations {
    public static void findAndAddWinCombinations(
            Map<String, WinCombinationData> winCombinationDataMap,
            Map<String, Integer> countMap,
            String [][] matrix,
            String[] basicCharacters) {

        hasSameSymbolsSeveralTimes(countMap, winCombinationDataMap);
        checkTriples(matrix, winCombinationDataMap, basicCharacters);
        checkDiagonalTriples(matrix, winCombinationDataMap, basicCharacters);
    }

    private static void hasSameSymbolsSeveralTimes(Map<String, Integer> countMap, Map<String, WinCombinationData> winCombinationDataMap) {
        for (int i = 3; i <= 9; i++) {
            WinCombinationData winCombinationData = winCombinationDataMap.get("same_symbol_" + i + "_times");
            List<String> tripleChars = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                if (entry.getValue() == i) {
                    tripleChars.add(entry.getKey());
                }
            }
            winCombinationData.setSymbolsWithWinCombinations(tripleChars);
            winCombinationDataMap.put("same_symbol_" + i + "_times", winCombinationData);
        }
    }

    private static void checkTriples(String[][] matrix,
                                     Map<String, WinCombinationData> winCombinationDataMap,
                                     String[] basicCharacters) {
        List<String> verticalTripleChars = new ArrayList<>();
        for (int j = 0; j < matrix[0].length; j++) {
            String first = matrix[0][j];
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
            boolean isTriple = true;
            for (int i = 1; i < matrix.length; i++) {
                if (!matrix[i][j].equals(first)) {
                    isTriple = false;
                    break;
                }
            }
            if (isTriple) {
                verticalTripleChars.add(first);
            }
        }
        WinCombinationData verticalTripleCharsData = winCombinationDataMap.get("same_symbols_vertically");
        verticalTripleCharsData.setSymbolsWithWinCombinations(verticalTripleChars);
        winCombinationDataMap.put("same_symbols_vertically", verticalTripleCharsData);

        List<String> horizontalTripleChars = new ArrayList<>();
        for (String[] strings : matrix) {
            String first = strings[0];
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
            boolean isTriple = true;
            for (int j = 1; j < strings.length; j++) {
                if (!strings[j].equals(first)) {
                    isTriple = false;
                    break;
                }
            }
            if (isTriple) {
                horizontalTripleChars.add(first);
            }
        }
        WinCombinationData horizontalTripleCharsData = winCombinationDataMap.get("same_symbols_horizontally");
        horizontalTripleCharsData.setSymbolsWithWinCombinations(horizontalTripleChars);
        winCombinationDataMap.put("same_symbols_horizontally", horizontalTripleCharsData);
    }

    private static void checkDiagonalTriples(String[][] matrix,
                                                                        Map<String, WinCombinationData> winCombinationDataMap,
                                                                        String[] basicCharacters) {
        List<String> leftToRightChars = new ArrayList<>();
        String firstLTR = matrix[0][0];
        boolean isBasicLTR = false;
        for (String basic : basicCharacters) {
            if (firstLTR.equals(basic)) {
                isBasicLTR = true;
                break;
            }
        }
        if (isBasicLTR) {
            boolean isTripleLTR = true;
            for (int i = 1; i < matrix.length; i++) {
                if (!matrix[i][i].equals(firstLTR)) {
                    isTripleLTR = false;
                    break;
                }
            }
            if (isTripleLTR) {
                leftToRightChars.add(firstLTR);
            }
        }
        WinCombinationData leftToRightWinCombination = winCombinationDataMap.get("same_symbols_diagonally_left_to_right");
        leftToRightWinCombination.setSymbolsWithWinCombinations(leftToRightChars);
        winCombinationDataMap.put("same_symbols_diagonally_left_to_right", leftToRightWinCombination);

        List<String> rightToLeftChars = new ArrayList<>();
        String firstRTL = matrix[0][matrix[0].length - 1];
        boolean isBasicRTL = false;
        for (String basic : basicCharacters) {
            if (firstRTL.equals(basic)) {
                isBasicRTL = true;
                break;
            }
        }
        if (isBasicRTL) {
            boolean isTripleRTL = matrix[1][1].equals(firstRTL) && matrix[2][0].equals(firstRTL);
            if (isTripleRTL) {
                rightToLeftChars.add(firstRTL);
            }
        }

        WinCombinationData rightToLeftWinCombination = winCombinationDataMap.get("same_symbols_diagonally_right_to_left");
        rightToLeftWinCombination.setSymbolsWithWinCombinations(rightToLeftChars);
        winCombinationDataMap.put("same_symbols_diagonally_right_to_left", rightToLeftWinCombination);
    }
}
