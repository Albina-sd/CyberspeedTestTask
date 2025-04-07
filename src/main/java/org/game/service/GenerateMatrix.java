package org.game.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static org.game.service.ExtractSymbolsAndProbabilitiesFromJson.getBonusSymbols;
import static org.game.service.ExtractSymbolsAndProbabilitiesFromJson.getProbabilitiesByPosition;


public class GenerateMatrix {

    private static final int SIZE_OF_MATRIX = 3;
    private static final int PROBABILITY_OF_BONUS = 7;

    public String[][] generateRandomMatrix(String config) throws IOException {
        Random random = new Random();
        String[][] matrix = new String[SIZE_OF_MATRIX][SIZE_OF_MATRIX];
        System.out.println("matrix:");

        for (int i = 0; i < SIZE_OF_MATRIX; i++) {
            for (int j = 0; j < SIZE_OF_MATRIX; j++) {
                String selected = getRandomSymbol(random, i, j, config);

                if (selected.equals("X")) {
                    selected = getBonusSymbol(random, config);
                }

                System.out.format("%-5s ", selected);

                matrix[i][j] = selected;
            }
            System.out.println();
        }

        return matrix;
    }

    private String getRandomSymbol(Random random, int i, int j, String config) throws IOException {
        Map<String, Integer> charactersWithProbabilities = getProbabilitiesByPosition(i, j, config);
        assert charactersWithProbabilities != null;
        charactersWithProbabilities.put("X", PROBABILITY_OF_BONUS); // add bonus
        String[] characters = charactersWithProbabilities.keySet().toArray(new String[0]);
        int totalWeight = Arrays.stream(charactersWithProbabilities.values().toArray(new Integer[0])).reduce(0, Integer::sum);

        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;
        String selected = characters[characters.length - 1];

        for (int k = 0; k < charactersWithProbabilities.size(); k++) {
            cumulativeWeight += charactersWithProbabilities.get(characters[k]);
            if (randomValue < cumulativeWeight) {
                selected = characters[k];
                break;
            }
        }

        return selected;
    }

    private String getBonusSymbol(Random random, String config) throws IOException {
        String result = "";

        Map<String, Integer> bonusValuesWithProbabilities = getBonusSymbols(config);
        String [] bonuses = bonusValuesWithProbabilities.keySet().toArray(new String[0]);
        int totalBonusWeight = Arrays.stream(bonusValuesWithProbabilities.values().toArray(new Integer[0])).reduce(0, Integer::sum);


        int bonusRandom = random.nextInt(totalBonusWeight);
        int bonusCumulative = 0;
        for (int k = 0; k < bonusValuesWithProbabilities.size(); k++) {
            bonusCumulative += bonusValuesWithProbabilities.get(bonuses[k]);
            if (bonusRandom < bonusCumulative) {
                result = bonuses[k];
                break;
            }
        }

        return result;
    }
}
