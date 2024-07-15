package org.example.timetable.model;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityTest {
    @Test
    void testGetDuration(){
        Activity element = new Activity(new ActivityType("dkfj",1),
                DayOfWeek.MONDAY,
                LocalTime.now(), LocalTime.now().plusHours(2L).plusMinutes(15),
                "jdf", true);

        assertEquals(element.getDuration(), 135L);
    }
}
