package org.example.timetable.ioservice.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.timetable.model.Activity;
import org.example.timetable.ioservice.IOServiceException;
import org.example.timetable.ioservice.OutputService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JSONOutputService implements OutputService {

    @Override
    public Object formatOutput(List<Activity> schedule) throws IOServiceException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.writeValueAsString(schedule);
        } catch (JsonProcessingException e) {
            throw new IOServiceException("Error formatting the output to JSON " + e.getMessage(), e);
        }
    }
}
