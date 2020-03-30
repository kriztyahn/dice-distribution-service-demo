package com.avaloq.dicedemo.service;

import com.avaloq.dicedemo.model.*;
import com.avaloq.dicedemo.repository.RollRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SimulationService {

    private final RollRepository rollRepository;

    public SimulationService(RollRepository rollRepository) {
        this.rollRepository = rollRepository;
    }

    public List<RollDistribution> getDistribution(int diceCount, int sideCount) {
        List<Roll> rollList = rollRepository.findAllByDiceCountAndSideCount(diceCount, sideCount);

        List<RollDistribution> rollDistributionList = new ArrayList<>();
        if(null != rollList) {
            rollList.forEach(roll -> {
                RollDistribution rollDistribution = RollDistribution.builder()
                        .diceCount(roll.getDiceCount())
                        .sideCount(roll.getSideCount())
                        .rollSum(roll.getRollSum())
                        .distribution(BigDecimal.ZERO)
                        .build();

                if(rollDistributionList.contains(rollDistribution)) {
                    rollDistribution = rollDistributionList.get(rollDistributionList.indexOf(rollDistribution));
                    rollDistribution.setTotalRollCount(rollDistribution.getTotalRollCount() + roll.getTotalRollCount());
                    rollDistribution.setRollSumCount(rollDistribution.getRollSumCount() + roll.getRollSumCount());
                } else {
                    rollDistributionList.add(rollDistribution);
                }
            });
        }

        rollDistributionList.forEach(roll -> {
            BigDecimal distribution = BigDecimal.valueOf(roll.getRollSumCount())
                    .divide(BigDecimal.valueOf(roll.getTotalRollCount()), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            roll.setDistribution(distribution);
        });

        return rollDistributionList;
    }

    public List<SimulationSummary> getSimulationSummary() {
        List<SummaryProjection> summaryProjectionList = rollRepository.findAllSimulation();

        List<SimulationSummary> simulationSummaryList = new ArrayList<>();
        if(null != summaryProjectionList) {
            summaryProjectionList.forEach(simulation -> {
                SimulationSummary summary = SimulationSummary.builder()
                        .sideCount(simulation.getSideCount())
                        .diceCount(simulation.getDiceCount())
                        .totalRollCount(simulation.getTotalRollCount())
                        .simulationCount(1)
                        .build();

                if (simulationSummaryList.contains(summary)) {
                    summary = simulationSummaryList.get(simulationSummaryList.indexOf(summary));
                    summary.setTotalRollCount(summary.getTotalRollCount() + simulation.getTotalRollCount());
                    summary.setSimulationCount(summary.getSimulationCount() + 1);
                } else {
                    simulationSummaryList.add(summary);
                }
            });
        }

        return simulationSummaryList;
    }

    public Map<String, Integer> roll(Dice dice, int diceCount, int rollCount) {
        Map<String, Integer> rollSumMap = initializeMap(18);

        List<Integer> rollSumList = getRollResults(dice, diceCount, rollCount);

        for(Integer result : rollSumList) {
            Integer currentCount = rollSumMap.get(result.toString());
            rollSumMap.put(result.toString(), (currentCount == null) ? 1 : currentCount+1);
        }

        saveSimulation(rollSumMap, diceCount, dice.getSideCount(), rollCount);

        return rollSumMap;
    }

    private void saveSimulation(Map<String, Integer> rollSumMap, int diceCount, int sideCount, int rollCount) {
        rollSumMap.forEach((key, value) -> {
//            Roll roll = Roll.builder()
//                    .rollSum(key)
//                    .rollSumCount(value)
//                    .diceCount(diceCount)
//                    .sideCount(sideCount)
//                    .totalRollCount(rollCount)
//                    .build();
            Roll roll = new Roll(diceCount, sideCount, key, value, rollCount);

            rollRepository.save(roll);
        });
    }

    private List<Integer> getRollResults(Dice dice, int numberOfDice, int numbeOfRolls) {
        List<Integer> resultList = new ArrayList<>();

        for(int i=0; i<numbeOfRolls; i++) {
            int result = 0;
            for(int j=0; j<numberOfDice; j++) {
                result += dice.roll();
            }
            resultList.add(result);
        }

        return resultList;
    }

    private Map<String, Integer> initializeMap(int numberOfItems) {
        Map<String, Integer> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.parseInt(s1) - Integer.parseInt(s2);
            }
        });

        for(int i=3; i<=numberOfItems; i++) {
            map.put(String.valueOf(i), 0);
        }

        return map;
    }
}
