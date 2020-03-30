package com.avaloq.dicedemo.service;

import com.avaloq.dicedemo.dto.RollDistributionResponse;
import com.avaloq.dicedemo.dto.SimulationSummaryResponse;
import com.avaloq.dicedemo.model.*;
import com.avaloq.dicedemo.repository.RollRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Service
public class SimulationService {

    private final RollRepository rollRepository;

    public SimulationService(RollRepository rollRepository) {
        this.rollRepository = rollRepository;
    }

    public List<RollDistributionResponse> getDistribution(int diceCount, int sideCount) {
        List<Roll> rollList = rollRepository.findAllByDiceCountAndSideCount(diceCount, sideCount);

        List<RollDistributionResponse> rollDistributionResponseList = new ArrayList<>();
        if(null != rollList) {
            rollList.forEach(roll -> {
                RollDistributionResponse rollDistributionResponse = RollDistributionResponse.builder()
                        .diceCount(roll.getDiceCount())
                        .sideCount(roll.getSideCount())
                        .rollSum(roll.getRollSum())
                        .rollSumCount(roll.getRollSumCount())
                        .totalRollCount(roll.getTotalRollCount())
                        .distribution(BigDecimal.ZERO)
                        .build();

                if(rollDistributionResponseList.contains(rollDistributionResponse)) {
                    rollDistributionResponse = rollDistributionResponseList.get(rollDistributionResponseList.indexOf(rollDistributionResponse));
                    rollDistributionResponse.setTotalRollCount(rollDistributionResponse.getTotalRollCount() + roll.getTotalRollCount());
                    rollDistributionResponse.setRollSumCount(rollDistributionResponse.getRollSumCount() + roll.getRollSumCount());
                } else {
                    rollDistributionResponseList.add(rollDistributionResponse);
                }
            });
        }

        rollDistributionResponseList.forEach(roll -> {
            BigDecimal distribution = BigDecimal.ZERO;
            try {
                distribution = BigDecimal.valueOf(roll.getRollSumCount())
                        .divide(BigDecimal.valueOf(roll.getTotalRollCount()), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            } catch(ArithmeticException e) {
                log.error(e.getMessage());
            } finally {
                roll.setDistribution(distribution);
            }
        });

        return rollDistributionResponseList;
    }

    public List<SimulationSummaryResponse> getSimulationSummary() {
        List<SummaryProjection> summaryProjectionList = rollRepository.findAllSimulation();

        List<SimulationSummaryResponse> simulationSummaryResponseList = new ArrayList<>();
        if(null != summaryProjectionList) {
            summaryProjectionList.forEach(simulation -> {
                SimulationSummaryResponse summary = SimulationSummaryResponse.builder()
                        .sideCount(simulation.getSideCount())
                        .diceCount(simulation.getDiceCount())
                        .totalRollCount(simulation.getTotalRollCount())
                        .simulationCount(1)
                        .build();

                if (simulationSummaryResponseList.contains(summary)) {
                    summary = simulationSummaryResponseList.get(simulationSummaryResponseList.indexOf(summary));
                    summary.setTotalRollCount(summary.getTotalRollCount() + simulation.getTotalRollCount());
                    summary.setSimulationCount(summary.getSimulationCount() + 1);
                } else {
                    simulationSummaryResponseList.add(summary);
                }
            });
        }

        return simulationSummaryResponseList;
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
            Roll roll = new Roll(diceCount, sideCount, key, value, rollCount);

            rollRepository.save(roll);
        });
    }

    protected List<Integer> getRollResults(Dice dice, int numberOfDice, int numbeOfRolls) {
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

    protected Map<String, Integer> initializeMap(int numberOfItems) {
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
