package com.avaloq.dicedemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulationRequest {

    @JsonProperty("side_count")
    private int sideCount;

    @JsonProperty("dice_count")
    private int diceCount;

    @JsonProperty("roll_count")
    private int rollCount;
}
