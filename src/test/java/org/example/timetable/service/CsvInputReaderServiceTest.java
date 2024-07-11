package org.example.timetable.service;

import org.example.timetable.service.implementation.CsvInputReaderService;
import org.junit.jupiter.api.Test;

public class CsvInputReaderServiceTest {
    @Test
    void test(){
        CsvInputReaderService readerService = new CsvInputReaderService();
        readerService.read("/Users/angelina/Documents/BachelorsWork/stage0/DemoInputSchedule.csv");
    }
}
