package org.example.timetable.ioservice;

import org.example.timetable.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CsvInputReaderTest {

    @Autowired
    InputReader readerService;
    @Test
    void test() throws IOServiceException {
        ArrayList<Activity> list = (ArrayList<Activity>) readerService
                .read("src/test/resources/DemoInputSchedule.csv");

        assertEquals(13, list.size());
        assertFalse(list.getFirst().getRoom().isEmpty());
    }
}
