package org.example.timetable;

import org.example.timetable.geneticAlg.ScheduleGenerationRunner;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Timeslot;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.example.timetable.ioservice.IOServiceException;
import org.example.timetable.ioservice.InputFiltration;
import org.example.timetable.ioservice.InputReader;
import org.example.timetable.ioservice.OutputService;
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
    ScheduleGenerationRunner scheduleGenerationRunner;

    @Autowired
    InputReader readerService;

    @Autowired
    OutputService outputService;

    @Autowired
    InputFiltration inputFiltration;
    @Test
    void fullSuccessScenario() throws IOServiceException {
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoSixthSemester.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltration.filtrateByAvailability(list);

        List<Activity> schedule = scheduleGenerationRunner.createSchedule(filteredList);
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
    void fullNoSolutionScenario() throws IOServiceException {
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoInputNoSolution.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltration.filtrateByAvailability(list);

        assertThrows(NoSolutionFoundException.class, () -> scheduleGenerationRunner.createSchedule(filteredList));
    }
    @Test
    void biggerInputFileTest() throws IOServiceException {
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/BiggerInput.csv");
        assertFalse(list.isEmpty());
        // remove not available activities
        List<Activity> filteredList = inputFiltration.filtrateByAvailability(list);

        List<Activity> schedule = scheduleGenerationRunner.createSchedule(filteredList);
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
