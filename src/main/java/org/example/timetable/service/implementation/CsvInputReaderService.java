package org.example.timetable.service.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.timetable.model.ScheduleElement;
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
    public List<ScheduleElement> read(String path) {
        List<ScheduleElement> elements = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {

            String[] nextRecord;
            List<String> tempElement = new ArrayList<>();

            while ((nextRecord = csvReader.readNext()) != null) {

                // Parse each line and print it out or store it in a list
                for (String cell : nextRecord) {
                    tempElement.add(cell);
                    System.out.print(cell + "\t");
                }

                elements.add(stringToScheduleElement(tempElement));
                tempElement.clear();
                System.out.println();
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return elements;
    }

    // TODO: add good error messages about what is wrong in input file
    private ScheduleElement stringToScheduleElement(List<String> tempElement) {
        if(tempElement.size() == ScheduleElement.class.getDeclaredFields().length){
            ScheduleElement element = new ScheduleElement();

            element.setSubject(tempElement.get(0));
            element.setType(Integer.parseInt(tempElement.get(1)));
            element.setDay(DayOfWeek.valueOf(tempElement.get(2).toUpperCase()));
            element.setStart(LocalTime.parse(tempElement.get(3)));
            element.setEnd(LocalTime.parse(tempElement.get(4)));
            element.setRoom(tempElement.get(5));
            element.setAvailable(Boolean.parseBoolean(tempElement.get(6)));

            return element;
        }
        else return new ScheduleElement();
    }
}