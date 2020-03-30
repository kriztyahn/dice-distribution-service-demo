package com.avaloq.dicedemo.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class RollDistribution extends Roll {

    private BigDecimal distribution;

    @Builder
    public RollDistribution(int diceCount, int sideCount, String rollSum, int rollSumCount, int totalRollCount, BigDecimal distribution) {
        this.diceCount = diceCount;
        this.sideCount = sideCount;
        this.rollSum = rollSum;
        this.rollSumCount = rollSumCount;
        this.totalRollCount = totalRollCount;
        this.distribution = distribution;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o == null) return false;

        if(this.getClass() != o.getClass()) return false;

        RollDistribution roll = (RollDistribution) o;

        return diceCount == roll.diceCount
                && sideCount == roll.sideCount
                && rollSum.equals(roll.getRollSum());
    }

    @Override
    public int hashCode() {
        return diceCount * sideCount * Integer.parseInt(rollSum);
    }
}
