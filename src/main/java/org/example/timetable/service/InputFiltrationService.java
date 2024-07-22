package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface InputFiltrationService {
    List<Activity> filtrate(List<Activity> activities);
}
