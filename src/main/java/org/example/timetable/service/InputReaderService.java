package org.example.timetable.service;

import org.example.timetable.model.ScheduleElement;

import java.util.List;

public interface InputReaderService {
    List<ScheduleElement> read(String path);
}
