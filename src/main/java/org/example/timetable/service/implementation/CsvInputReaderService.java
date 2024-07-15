package org.example.timetable.service.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.timetable.model.Activity;
import org.example.timetable.model.ActivityType;
import org.example.timetable.service.InputReaderService;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvInputReaderService implements InputReaderService {

    @Override
    public List<Activity> read(String path) {
        List<Activity> activities = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {

            String[] nextRecord;
            List<String> tempElement = new ArrayList<>();

            while ((nextRecord = csvReader.readNext()) != null) {

                // Parse each line and print it out or store it in a list
                for (String cell : nextRecord) {
                    tempElement.add(cell);
                    System.out.print(cell + "\t");
                }

                activities.add(stringToActivity(tempElement));
                tempElement.clear();
                System.out.println();
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return activities;
    }

    // TODO: add good error messages about what is wrong in input file
    private Activity stringToActivity(List<String> tempElement) {
        if(tempElement.size() == Activity.class.getDeclaredFields().length){
            Activity activity = new Activity();
            ActivityType type = new ActivityType(tempElement.get(0), Integer.parseInt(tempElement.get(1)));

            activity.setActivityType(type);
            activity.setDay(DayOfWeek.valueOf(tempElement.get(2).toUpperCase()));
            activity.setStart(LocalTime.parse(tempElement.get(3)));
            activity.setEnd(LocalTime.parse(tempElement.get(4)));
            activity.setRoom(tempElement.get(5));
            activity.setAvailable(Boolean.parseBoolean(tempElement.get(6)));

            return activity;
        }
        else return new Activity();
    }
}