package org.example.timetable.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.timetable.model.Activity;
import org.example.timetable.service.OutputService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
@Service
public class JSONOutputService implements OutputService {

    @Override
    public Object formatOutput(List<Activity> schedule) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule

        // sort the activities in schedule
        List<Activity> sortedSchedule = schedule.stream()
               .sorted(Comparator.comparing(Activity::getDay).thenComparing(Activity::getStart)).toList();
        try {
            return objectMapper.writeValueAsString(sortedSchedule);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
