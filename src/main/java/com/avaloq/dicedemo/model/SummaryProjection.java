package com.avaloq.dicedemo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryProjection {
    protected int diceCount;

    protected int sideCount;

    protected int totalRollCount;
}
