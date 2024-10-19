package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface InputFiltrationService {
    List<Activity> filtrateByAvailability(List<Activity> activities);
}
