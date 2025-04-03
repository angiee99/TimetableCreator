package org.example.timetable.ioservice;

import org.example.timetable.model.Activity;

import java.util.List;

public interface InputFiltration {
    List<Activity> filtrateByAvailability(List<Activity> activities);
}
