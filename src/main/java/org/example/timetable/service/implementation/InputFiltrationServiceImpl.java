package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.service.InputFiltrationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InputFiltrationServiceImpl implements InputFiltrationService {

    @Override
    public List<Activity> filtrate(List<Activity> activities) {
        // remove all not available activities
        return activities.stream().filter(Activity::getAvailable).toList();
    }
}
