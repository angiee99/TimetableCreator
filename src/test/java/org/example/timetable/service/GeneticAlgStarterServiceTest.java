package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class GeneticAlgStarterServiceTest {

    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;
    @Autowired
    InputReaderService readerService;
    @Autowired
    InputFiltrationService inputFiltrationService;
    @Test
    void test(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoInputSchedule.csv");

        List<Activity> filtered = inputFiltrationService.filtrate(list);

        List<Activity> result = geneticAlgStarterService.createSchedule(filtered);
        assertFalse(result.isEmpty());
    }
}
