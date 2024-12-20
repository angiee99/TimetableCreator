package org.example.timetable.service.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.timetable.model.Activity;
import org.example.timetable.model.ActivityType;
import org.example.timetable.model.Timeslot;
import org.example.timetable.service.InputReaderService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
                tempElement.addAll(Arrays.asList(nextRecord));

                activities.add(stringToActivity(tempElement));
                tempElement.clear();
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return activities;
    }

    public List<Activity> read(InputStream inputStream) {
        List<Activity> activities = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream)))) {

            String[] nextRecord;
            List<String> tempElement = new ArrayList<>();

            // Read each record from the CSV
            while ((nextRecord = csvReader.readNext()) != null) {

                // Parse each line and store values in a temporary list
                tempElement.addAll(Arrays.asList(nextRecord));

                // Convert the string list to an Activity object
                activities.add(stringToActivity(tempElement));

                // Clear the temporary list for the next record
                tempElement.clear();
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return activities;
    }

    // TODO: add good error messages about what is wrong in input file
    private Activity stringToActivity(List<String> tempElement) {
        if(tempElement.size() == 7){ // change the check
            Activity activity = new Activity();

            ActivityType type = new ActivityType(tempElement.get(0), Integer.parseInt(tempElement.get(1)));
            Timeslot timeslot = new Timeslot(
                    DayOfWeek.valueOf(tempElement.get(2).toUpperCase()),
                    LocalTime.parse(tempElement.get(3)),
                    LocalTime.parse(tempElement.get(4)));

            activity.setActivityType(type);
            activity.setTimeslot(timeslot);
            activity.setRoom(tempElement.get(5));
            activity.setAvailable(Boolean.parseBoolean(tempElement.get(6)));

            return activity;
        }
        else return new Activity();
    }
}