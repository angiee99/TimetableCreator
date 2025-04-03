package org.example.timetable.ioservice.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.ioservice.InputFiltration;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InputFiltrationImpl implements InputFiltration {

    @Override
    public List<Activity> filtrateByAvailability(List<Activity> activities) {
        // remove all not available activities
        return activities.stream().filter(Activity::getAvailable).toList();
    }
}
