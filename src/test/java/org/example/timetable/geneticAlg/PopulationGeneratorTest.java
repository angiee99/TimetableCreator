package org.example.timetable.geneticAlg;

import org.example.timetable.geneticAlg.generators.PopulationGenerator;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Generation;
import org.example.timetable.service.InputReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PopulationGeneratorTest {
    @Autowired
    PopulationGenerator generationService;

    @Autowired
    InputReaderService readerService;

    @Test
    void test(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoInputSchedule.csv");

       Generation generatedPopulation = generationService.generate(list, 10);

        assertFalse(generatedPopulation.getPopulation().isEmpty());
        assertEquals(10, generatedPopulation.getPopulation().size());
    }
}
