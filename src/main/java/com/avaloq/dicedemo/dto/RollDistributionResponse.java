package com.avaloq.dicedemo.dto;

import com.avaloq.dicedemo.model.Roll;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class RollDistributionResponse {

    @JsonProperty("dice_count")
    private int diceCount;

    @JsonProperty("side_count")
    private int sideCount;

    @JsonProperty("roll_sum")
    private String rollSum;

    @JsonProperty("roll_sum_count")
    private int rollSumCount;

    @JsonProperty("total_roll_count")
    private int totalRollCount;

    private BigDecimal distribution;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o == null) return false;

        if(this.getClass() != o.getClass()) return false;

        RollDistributionResponse roll = (RollDistributionResponse) o;

        return diceCount == roll.diceCount
                && sideCount == roll.sideCount
                && rollSum.equals(roll.getRollSum());
    }

    @Override
    public int hashCode() {
        return diceCount * sideCount * Integer.parseInt(rollSum);
    }
}
