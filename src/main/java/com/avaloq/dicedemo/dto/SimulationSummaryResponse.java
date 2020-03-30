package com.avaloq.dicedemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter
@Setter
public class SimulationSummaryResponse {

    @JsonProperty("dice_count")
    private int diceCount;

    @JsonProperty("side_count")
    private int sideCount;

    @JsonProperty("total_simulation_count")
    private int totalSimulationCount;

    @JsonProperty("total_rolls_count")
    private int totalRollsCount;
}
