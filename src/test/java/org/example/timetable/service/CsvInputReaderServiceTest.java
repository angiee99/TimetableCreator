package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CsvInputReaderServiceTest {
    @Autowired
    PopulationGenerationService generationService;

    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;

    @Autowired
    InputReaderService readerService;
    @Test
    void test(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");

        assertEquals(13, list.size());
        assertFalse(list.get(0).getRoom().isEmpty());
    }
}
