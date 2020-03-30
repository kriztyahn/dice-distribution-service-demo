package com.avaloq.dicedemo.model;

import com.avaloq.dicedemo.repository.RollIdentity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(RollIdentity.class)
@Entity
public class Roll {

    @Id
    protected int diceCount;

    @Id
    protected int sideCount;

    @Id
    protected String rollSum;

    protected int rollSumCount;

    @Id
    protected int totalRollCount;
}
