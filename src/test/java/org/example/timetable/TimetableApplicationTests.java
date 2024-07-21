package org.example.timetable;

import org.example.timetable.model.Activity;
import org.example.timetable.service.GeneticAlgStarterService;
import org.example.timetable.service.InputReaderService;
import org.example.timetable.service.OutputService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TimetableApplicationTests {
    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;

    @Autowired
    InputReaderService readerService;

    @Autowired
    OutputService outputService;
    @Test
    void fullSuccessScenario(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");
        assertFalse(list.isEmpty());

        List<Activity> schedule = geneticAlgStarterService.createSchedule(list);
        assertFalse(schedule.isEmpty());

        // create output in JSON file
        String jsonOutput = (String) outputService.formatOutput(schedule);

        // Write the JSON output to a file
        try (FileWriter fileWriter = new FileWriter("schedule.json")) {
            fileWriter.write(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
