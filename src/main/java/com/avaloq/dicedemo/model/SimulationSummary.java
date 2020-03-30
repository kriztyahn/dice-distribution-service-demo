package com.avaloq.dicedemo.model;

import lombok.*;

@Getter
@Setter
public class SimulationSummary extends SummaryProjection{

    private long simulationCount;

    @Builder
    public SimulationSummary(int diceCount, int sideCount, int totalRollCount, long simulationCount) {
        this.diceCount = diceCount;
        this.sideCount = sideCount;
        this.totalRollCount = totalRollCount;
        this.simulationCount = simulationCount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o == null) return false;

        if(this.getClass() != o.getClass()) return false;

        SimulationSummary simulationSummary = (SimulationSummary) o;

        return diceCount == simulationSummary.diceCount
                && sideCount == simulationSummary.sideCount;
    }

    @Override
    public int hashCode() {
        return diceCount * sideCount;
    }
}
