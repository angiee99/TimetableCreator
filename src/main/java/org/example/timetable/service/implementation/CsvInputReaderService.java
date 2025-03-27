package org.example.timetable.service.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.timetable.model.Activity;
import org.example.timetable.model.ActivityType;
import org.example.timetable.model.Timeslot;
import org.example.timetable.service.IOServiceException;
import org.example.timetable.service.InputReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CsvInputReaderService implements InputReaderService {
    @Value("${ga.input.csv.fields-count}")
    private final int countOfFields = 7;
    @Override
    public List<Activity> read(String path) throws IOServiceException {
        List<Activity> activities;
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            activities = read(csvReader);
        }
        catch (IOException | CsvValidationException e) {
            throw new IOServiceException("Could not reading the input file: " + e.getMessage(), e);
        }
        return activities;
    }
    @Override
    public List<Activity> read(InputStream inputStream) throws IOServiceException {
        List<Activity> activities;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream)))) {
            activities = read(csvReader);
        } catch (IOException | CsvValidationException e) {
           throw new IOServiceException("Could not reading the input stream. " + e.getMessage(), e);
        }
        return activities;
    }

    private List<Activity> read(CSVReader csvReader) throws IOException, CsvValidationException, IOServiceException {
        String[] nextRecord;
        List<Activity> activities = new ArrayList<>();
        List<String> tempElement = new ArrayList<>();

        while ((nextRecord = csvReader.readNext()) != null) {
            // Parse each line and store values in a temporary list
            tempElement.addAll(Arrays.asList(nextRecord));

            activities.add(stringToActivity(tempElement));
            tempElement.clear();
        }
        return activities;
    }

    private Activity stringToActivity(List<String> tempElement) throws IOServiceException {
        try{
            if(tempElement.size() == countOfFields){ // change the check
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
            } else throw new IOServiceException("The input file format is not as expected. The count of fields must be " + countOfFields);
        } catch (IOServiceException | NoSuchElementException |
                IndexOutOfBoundsException | DateTimeParseException | IllegalArgumentException e) {
            throw new IOServiceException("The input file format is not as expected. " + e.getMessage(), e);
        }
    }
}