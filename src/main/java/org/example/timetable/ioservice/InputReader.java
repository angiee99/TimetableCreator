package org.example.timetable.ioservice;

import org.example.timetable.model.Activity;

import java.io.InputStream;
import java.util.List;

public interface InputReader {
    List<Activity> read(String path) throws IOServiceException;
    List<Activity> read(InputStream stream) throws IOServiceException;
}
