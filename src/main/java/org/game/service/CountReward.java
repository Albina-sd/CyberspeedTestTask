package org.game.service;

import org.game.data.WinCombinationData;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.game.service.ExtractRewardsFromJson.getStandardSymbolRewards;
import static org.game.service.ExtractSymbolsAndProbabilitiesFromJson.getBonusSymbols;
import static org.game.service.ExtractSymbolsAndProbabilitiesFromJson.getProbabilitiesByPosition;
import static org.game.service.ExtractWinCombinationsFromJson.getWinCombinations;

public class CountReward {

    public String countReward(int betAmount, String [][] matrix, String config) throws IOException {
        StringBuilder result = new StringBuilder();
        Map<String, WinCombinationData> winCombinationDataMap = getWinCombinations(config);
        String[] basicCharacters = Objects.requireNonNull(getProbabilitiesByPosition(0, 0, config)).keySet().toArray(new String[0]);
        Map<String, Integer> countMap = countSameSymbols(matrix, basicCharacters);
        List<String> appliedBonuses = getAppliedBonus(matrix, config);

        FindWinCombinations.findAndAddWinCombinations(winCombinationDataMap, countMap, matrix, basicCharacters);

        double reward = calculateRewardForOne(winCombinationDataMap, betAmount, appliedBonuses, countMap.keySet(), config);
        result.append("\nreward: ").append(reward);

        StringBuilder charactersWithWinCombinations = resulOfWinCombinations(winCombinationDataMap, countMap);
        if (!charactersWithWinCombinations.isEmpty()){
            result.append("\n\napplied_winning_combinations: \n").append(charactersWithWinCombinations);
        }

        if (reward > 0 && !appliedBonuses.isEmpty()){
            result.append("\napplied_bonuses: ").append(appliedBonuses).append("\n");
        }

        return result.toString();
    }

    private StringBuilder resulOfWinCombinations(Map<String, WinCombinationData> winCombinationDataMap, Map<String, Integer> countMap) {
        StringBuilder result = new StringBuilder();
        Set<String> characters = countMap.keySet();
        final Map<String, WinCombinationData> tempWinCombinationDataMap = winCombinationDataMap;
        characters.forEach(character -> {
            StringBuilder winCombinationForCharacter = new StringBuilder(character + ": ");
            tempWinCombinationDataMap.forEach((name, data) ->{
                if (data.getSymbolsWithWinCombinations() != null && !data.getSymbolsWithWinCombinations().isEmpty() && data.getSymbolsWithWinCombinations().contains(character)){
                    winCombinationForCharacter.append(name).append(", ");
                }
            });
            if (winCombinationForCharacter.length() > 3){
                result.append(winCombinationForCharacter.delete(winCombinationForCharacter.length() - 2, winCombinationForCharacter.length())).append("\n");
            }
        });

        return result;
    }

    private double calculateRewardForOne(Map<String, WinCombinationData> winCombinationDataMap,
                                         int betAmount,
                                         List<String> appliedBonuses,
                                         Set<String> characters,
                                         String config) throws IOException {
        double totalReward = 0.0;
        Map<String, Double> standardSymbolRewards = getStandardSymbolRewards(config);

        for (String character : characters) {
            double combinationReward = 0.0;
            boolean hasWinningCombination = false;

            for (WinCombinationData data : winCombinationDataMap.values()) {
                if (data.getSymbolsWithWinCombinations().contains(character)) {
                    if (!hasWinningCombination) {
                        combinationReward = betAmount * standardSymbolRewards.getOrDefault(character, 0.0);
                        hasWinningCombination = true;
                    }
                    combinationReward *= data.getRewardMultiplier();
                }
            }

            if (hasWinningCombination) {
                totalReward += combinationReward;
            }
        }

        return totalReward > 0 ? applyBonuses(totalReward, appliedBonuses) : 0.0;
    }

    private double applyBonuses(double reward, List<String> appliedBonuses) {
        if (appliedBonuses.isEmpty()) {
            return reward;
        }
        AtomicReference<Double> newReward = new AtomicReference<>(reward);
        appliedBonuses.forEach(appliedBonus -> {
            if (appliedBonus.contains("x")) {
                newReward.updateAndGet(v -> v * Double.parseDouble(appliedBonus.substring(0, appliedBonus.length() - 1)));
            }
            if (appliedBonus.contains("+")) {
                newReward.updateAndGet(v -> v + Double.parseDouble(appliedBonus.substring(1)));
            }
        });

        return newReward.get();
    }

    private Map<String, Integer> countSameSymbols(String [][] matrix, String[] basicCharacters) {
        Map<String, Integer> countMap = new HashMap<>();

        for (String ch : basicCharacters) {
            countMap.put(ch, 0);
        }

        for (String[] strings : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                String current = strings[j];
                if (countMap.containsKey(current)) {
                    countMap.put(current, countMap.get(current) + 1);
                }
            }
        }
        return countMap;
    }

    private List<String> getAppliedBonus(String [][] matrix, String config) throws IOException {
        Set<String> basicCharacters = getBonusSymbols(config).keySet();
        List<String> appliedBonuses = new ArrayList<>();

        for (String[] strings : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                String current = strings[j];
                if (basicCharacters.contains(current)) {
                    appliedBonuses.add(current);
                }
            }
        }
        return appliedBonuses;
    }
}
