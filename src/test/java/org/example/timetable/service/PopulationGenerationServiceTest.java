package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;
import org.example.timetable.service.implementation.CsvInputReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PopulationGenerationServiceTest {
    @Autowired
    PopulationGenerationService generationService;

    @Test
    void test(){
        CsvInputReaderService readerService = new CsvInputReaderService();
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");

        ArrayList<Individual> generatedPopulation = new ArrayList<>
                (generationService.generate(list, 10));

        assertFalse(generatedPopulation.isEmpty());
        assertEquals(10, generatedPopulation.size());
    }
}
