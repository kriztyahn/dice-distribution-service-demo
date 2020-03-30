package com.avaloq.dicedemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class Dice {

    private int sideCount;

    public int roll() {
        return new Random().nextInt(sideCount)+1;
    }
}
