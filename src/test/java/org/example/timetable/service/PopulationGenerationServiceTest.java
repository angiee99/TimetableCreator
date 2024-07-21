package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Generation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PopulationGenerationServiceTest {
    @Autowired
    PopulationGenerationService generationService;

    @Autowired
    InputReaderService readerService;

    @Test
    void test(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");

       Generation generatedPopulation = generationService.generate(list, 10);

        assertFalse(generatedPopulation.getPopulation().isEmpty());
        assertEquals(10, generatedPopulation.getPopulation().size());
    }
}
