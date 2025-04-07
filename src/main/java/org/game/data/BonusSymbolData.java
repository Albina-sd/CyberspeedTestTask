package org.game.data;

public class BonusSymbolData {
    private Double rewardMultiplier;
    private Integer extra;
    private String impact;

    public BonusSymbolData(Double rewardMultiplier, Integer extra, String impact) {
        this.rewardMultiplier = rewardMultiplier;
        this.extra = extra;
        this.impact = impact;
    }

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public Integer getExtra() {
        return extra;
    }

    public String getImpact() {
        return impact;
    }
}
