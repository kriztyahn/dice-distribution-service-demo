package com.avaloq.dicedemo.service;

import com.avaloq.dicedemo.model.Dice;
import com.avaloq.dicedemo.repository.RollRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
public class SimulationServiceTest {

    @Mock
    private RollRepository rollRepository;

    private SimulationService simulationService;

    @Before
    public void setup() {
        this.simulationService = new SimulationService(rollRepository);
    }

    @Test
    public void initializeMapTest() {
        Map<String, Integer> map = simulationService.initializeMap(18);
        Assert.assertEquals(map.size(), 16);
    }

    @Test
    public void testGetRollResults() {
        List<Integer> resultList = simulationService.getRollResults(new Dice(3), 3, 100);
        Assert.assertNotNull(resultList);
    }
}
