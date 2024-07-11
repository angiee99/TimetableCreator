package org.example.timetable.service;

import org.example.timetable.model.ScheduleElement;
import org.example.timetable.service.implementation.CsvInputReaderService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvInputReaderServiceTest {
    @Test
    void test(){
        CsvInputReaderService readerService = new CsvInputReaderService();
        List<ScheduleElement> list = readerService
                .read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");

        assertEquals(10, list.size());
    }
}
