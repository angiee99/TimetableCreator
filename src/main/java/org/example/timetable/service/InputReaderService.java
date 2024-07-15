package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface InputReaderService {
    List<Activity> read(String path);
}
