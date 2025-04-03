package org.example.timetable.controller;

import org.example.timetable.geneticAlg.ScheduleGenerationRunner;
import org.example.timetable.model.Activity;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.example.timetable.ioservice.InputFiltration;
import org.example.timetable.ioservice.IOServiceException;
import org.example.timetable.ioservice.InputReader;
import org.example.timetable.ioservice.OutputService;
import org.example.timetable.utils.ConfigurationLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleGenerationRunner scheduleGenerationRunner;
    private final InputReader readerService;
    private final InputFiltration inputFiltration;
    private final OutputService outputService;
    private final ConfigurationLoader configurationLoader;
    @Autowired
    public ScheduleController(ScheduleGenerationRunner scheduleGenerationRunner,
                              InputReader readerService,
                              InputFiltration inputFiltration,
                              OutputService outputService,
                              ConfigurationLoader configurationLoader) {
        this.scheduleGenerationRunner = scheduleGenerationRunner;
        this.readerService = readerService;
        this.inputFiltration = inputFiltration;
        this.outputService = outputService;
        this.configurationLoader = configurationLoader;
    }

    @PostMapping()
    public ResponseEntity<String> generateSchedule(@RequestParam("file") MultipartFile file) {
        String jsonOutput;
        try{
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("The uploaded file is empty");
            }
            // Convert MultipartFile to InputStream to read CSV contents
            ArrayList<Activity> list = (ArrayList<Activity>) readerService.read(file.getInputStream());

            // filter the activities
            List<Activity> filteredList = inputFiltration.filtrateByAvailability(list);
            // create the schedule
            List<Activity> schedule = scheduleGenerationRunner.createSchedule(filteredList);

            // create output in JSON file
            jsonOutput = (String) outputService.formatOutput(schedule);
        } catch(NoSolutionFoundException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Expose-Headers", "message");
            headers.add("message","No schedule without overlap can be created from the given input file");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .headers(headers)
                    .body("");
        } catch (IOException | IOServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(jsonOutput);
    }

    @GetMapping()
    public ResponseEntity<Map<String, String> > getConfigs(){
        Map<String, String> configs = configurationLoader.loadAll();
        return ResponseEntity.ok(configs);
    }
}
