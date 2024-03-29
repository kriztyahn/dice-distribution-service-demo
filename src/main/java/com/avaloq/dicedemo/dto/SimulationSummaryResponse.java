package com.avaloq.dicedemo.dto;

import com.avaloq.dicedemo.model.SummaryProjection;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class SimulationSummaryResponse  {

    @JsonProperty("dice_count")
    private int diceCount;

    @JsonProperty("side_count")
    private int sideCount;

    @JsonProperty("total_roll_count")
    private int totalRollCount;

    @JsonProperty("simulation_count")
    private long simulationCount;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o == null) return false;

        if(this.getClass() != o.getClass()) return false;

        SimulationSummaryResponse simulationSummaryResponse = (SimulationSummaryResponse) o;

        return diceCount == simulationSummaryResponse.diceCount
                && sideCount == simulationSummaryResponse.sideCount;
    }

    @Override
    public int hashCode() {
        return diceCount * sideCount;
    }
}
