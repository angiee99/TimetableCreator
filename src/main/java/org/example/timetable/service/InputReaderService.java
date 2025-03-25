package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.io.InputStream;
import java.util.List;

public interface InputReaderService {
    List<Activity> read(String path) throws IOServiceException;
    List<Activity> read(InputStream stream) throws IOServiceException;
}
