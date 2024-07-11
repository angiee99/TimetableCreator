package org.example.timetable.service.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.timetable.service.InputReaderService;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
@Service
public class CsvInputReaderService implements InputReaderService {

    @Override
    public void read(String path) {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                // Parse each line and print it out or store it in a list
                for (String cell : nextRecord) {
                    // TODO: save the read data to model Activity
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}