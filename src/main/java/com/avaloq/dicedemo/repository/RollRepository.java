package com.avaloq.dicedemo.repository;

import com.avaloq.dicedemo.model.Roll;
import com.avaloq.dicedemo.model.SummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollRepository extends JpaRepository<Roll, RollIdentity> {

    List<Roll> findAllByDiceCountAndSideCount(int diceCount, int sideCount);

    @Query("SELECT new com.avaloq.dicedemo.model.SummaryProjection(diceCount, sideCount, totalRollCount) " +
            "FROM Roll GROUP BY diceCount, sideCount, totalRollCount")
    List<SummaryProjection> findAllSimulation();
}
