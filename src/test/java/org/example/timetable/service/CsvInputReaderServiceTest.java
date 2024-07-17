package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.service.implementation.CsvInputReaderService;
import org.example.timetable.service.implementation.PopulationGenerationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class CsvInputReaderServiceTest {
    @Autowired
    PopulationGenerationService generationService;

    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;
    @Test
    void test(){
        CsvInputReaderService readerService = new CsvInputReaderService();
        List<Activity> list = readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");


//        generationService.generate(list, 10);
        geneticAlgStarterService.createSchedule(list);

        assertEquals(12, list.size());
    }
}
