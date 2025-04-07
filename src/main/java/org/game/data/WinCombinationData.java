package org.game.data;

import java.util.List;

public class WinCombinationData {
    private String name;
    private double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    private List<List<String>> coveredAreas;
    private List<String> symbolsWithWinCombinations;

    public WinCombinationData(String name, double rewardMultiplier, String when, Integer count,
                          String group, List<List<String>> coveredAreas) {
        this.name = name;
        this.rewardMultiplier = rewardMultiplier;
        this.when = when;
        this.count = count;
        this.group = group;
        this.coveredAreas = coveredAreas;
    }

    public void setSymbolsWithWinCombinations(List<String> symbolsWithWinCombinations) {
        this.symbolsWithWinCombinations = symbolsWithWinCombinations;
    }

    public List<String> getSymbolsWithWinCombinations() {
        return symbolsWithWinCombinations;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getName() {
        return name;
    }

    public String getWhen() {
        return when;
    }

    public Integer getCount() {
        return count;
    }

    public String getGroup() {
        return group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }
}
