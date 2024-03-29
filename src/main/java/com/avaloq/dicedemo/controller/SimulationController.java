package com.avaloq.dicedemo.controller;

import com.avaloq.dicedemo.exception.InvalidRequestException;
import com.avaloq.dicedemo.model.Dice;
import com.avaloq.dicedemo.dto.SimulationRequest;
import com.avaloq.dicedemo.dto.SimulationResponse;
import com.avaloq.dicedemo.dto.RollDistributionResponse;
import com.avaloq.dicedemo.dto.SimulationSummaryResponse;
import com.avaloq.dicedemo.service.SimulationService;
import com.avaloq.dicedemo.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(Constant.BASE_URL)
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/roll")
    public ResponseEntity<SimulationResponse> rollDice(@RequestBody(required = false) SimulationRequest simulationRequest) throws Exception {
        if(simulationRequest == null) {
            throw new InvalidRequestException("Request body is empty");
        } else if(!Constant.DICE_SIDE_LIST.contains(simulationRequest.getSideCount())) {
            throw new InvalidRequestException("Number of dice sides should be 4, 6, 8, 10, 12, 20, 100");
        } else if(simulationRequest.getDiceCount() < 1 || simulationRequest.getDiceCount() > 100) {
            throw new InvalidRequestException("Dice count should be 1-100");
        } else if(simulationRequest.getRollCount() < 1) {
            throw new InvalidRequestException("Minimum number of rolls should be 1");
        }

        Map<String, Integer> resultMap = simulationService.roll(new Dice(simulationRequest.getSideCount()), simulationRequest.getDiceCount(),
                simulationRequest.getRollCount());

        return new ResponseEntity<>(new SimulationResponse(resultMap), HttpStatus.OK);
    }

    @GetMapping("/simulation/history")
    public ResponseEntity<List<SimulationSummaryResponse>> getSimulationSummary() {
        List<SimulationSummaryResponse> summary = simulationService.getSimulationSummary();

        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/simulation/distribution")
    public ResponseEntity<List<RollDistributionResponse>> getSimulationDistribution(@RequestParam("dice_count") int diceCount, @RequestParam("side_count") int sideCount) {
        List<RollDistributionResponse> rollDistributionResponseList = simulationService.getDistribution(diceCount, sideCount);

        return new ResponseEntity<>(rollDistributionResponseList, HttpStatus.OK);
    }
}

