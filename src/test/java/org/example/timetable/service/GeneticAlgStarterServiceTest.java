package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.service.implementation.CsvInputReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class GeneticAlgStarterServiceTest {

    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;
    @Test
    void test(){
        CsvInputReaderService readerService = new CsvInputReaderService();
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");

        List<Activity> result = geneticAlgStarterService.createSchedule(list);
        assertFalse(result.isEmpty());

    }
}
