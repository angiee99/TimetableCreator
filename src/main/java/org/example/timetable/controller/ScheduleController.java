package org.example.timetable.controller;

import org.example.timetable.geneticAlg.GeneticAlgStarterService;
import org.example.timetable.model.Activity;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.example.timetable.service.InputFiltrationService;
import org.example.timetable.service.InputReaderService;
import org.example.timetable.service.OutputService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/schedule")
public class ScheduleController {
    GeneticAlgStarterService geneticAlgStarterService;
    InputReaderService readerService;
    InputFiltrationService inputFiltrationService;
    OutputService outputService;
    @Autowired
    public ScheduleController(GeneticAlgStarterService geneticAlgStarterService,
                              InputReaderService readerService, InputFiltrationService inputFiltrationService,
                              OutputService outputService) {
        this.geneticAlgStarterService = geneticAlgStarterService;
        this.readerService = readerService;
        this.inputFiltrationService = inputFiltrationService;
        this.outputService = outputService;
    }

    @PostMapping()
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        String jsonOutput = "";
        try{
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("The uploaded file is empty");
            }

            // Convert MultipartFile to InputStream to read CSV contents
            InputStream inputStream = file.getInputStream();
            ArrayList<Activity> list = (ArrayList<Activity>) readerService.read(inputStream);

            // filter the activities
            List<Activity> filteredList = inputFiltrationService.filtrateByAvailability(list);
            // create the schedule
            List<Activity> schedule = geneticAlgStarterService.createSchedule(filteredList);

            // create output in JSON file
            jsonOutput = (String) outputService.formatOutput(schedule);

        } catch(NoSolutionFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No schedule can be created from the given input file");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading the uploaded file");
        }

        return ResponseEntity.ok(jsonOutput);
    }
    @PostMapping("/test")
    public ResponseEntity<String> uploadCSVTest(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", "0");
        return ResponseEntity.ok(jsonObj.toJSONString());
    }
}
