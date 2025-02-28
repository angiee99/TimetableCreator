package org.example.timetable;

import org.example.timetable.geneticAlg.GeneticAlgStarterService;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Timeslot;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.example.timetable.service.InputFiltrationService;
import org.example.timetable.service.InputReaderService;
import org.example.timetable.service.OutputService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TimetableApplicationTests {
    @Autowired
    GeneticAlgStarterService geneticAlgStarterService;

    @Autowired
    InputReaderService readerService;

    @Autowired
    OutputService outputService;

    @Autowired
    InputFiltrationService inputFiltrationService;
    @Test
    void fullSuccessScenario(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoSixthSemester.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltrationService.filtrateByAvailability(list);

        List<Activity> schedule = geneticAlgStarterService.createSchedule(filteredList);
        assertFalse(schedule.isEmpty());

        // check if there are no overlaps
        assertFalse(hasOverlaps(schedule));

        // create output in JSON file
        String jsonOutput = (String) outputService.formatOutput(schedule);

        // Write the JSON output to a file
        try (FileWriter fileWriter = new FileWriter("schedule.json")) {
            fileWriter.write(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void fullNoSolutionScenario(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoInputNoSolution.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltrationService.filtrateByAvailability(list);

        assertThrows(NoSolutionFoundException.class, () -> geneticAlgStarterService.createSchedule(filteredList));
    }
    @Test
    void biggerInputFileTest(){
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/BiggerInput.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltrationService.filtrateByAvailability(list);

        List<Activity> schedule = geneticAlgStarterService.createSchedule(filteredList);
        assertFalse(schedule.isEmpty());

        // check if there are no overlaps
        assertFalse(hasOverlaps(schedule));
    }

    private List<Activity> getActivitiesByDay(List<Activity> individual, int i) {
        return individual.stream().filter(
                        gene -> gene.getTimeslot().getDay()
                                    .equals(DayOfWeek.of(i))).toList();
    }

    private boolean hasOverlaps(List<Activity> activities){
        for (int i = 1; i <= 7; i++) {
            List<Activity> activitiesByDay = getActivitiesByDay(activities, i);
            // sort by time
            List<Activity> activitiesByDaySorted = activitiesByDay.stream().sorted
                    (Comparator.comparing(o -> o.getTimeslot().getStart())).toList();

            for (int j = 0; j < activitiesByDaySorted.size() - 1; j++) {
                Timeslot current = activitiesByDaySorted.get(j).getTimeslot();
                Timeslot next = activitiesByDaySorted.get(j + 1).getTimeslot();

                if (current.getEnd().isAfter(next.getStart())) {
                    return true; // there is an overlap
                }
            }

        }
        return false;
    }
}
