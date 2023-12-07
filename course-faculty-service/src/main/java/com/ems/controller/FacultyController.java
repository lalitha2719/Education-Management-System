package com.ems.controller;

import com.ems.entity.Faculty;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.service.FacultyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(produces = "application/json", value = "Manages Courses")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @CrossOrigin
    @ApiOperation(value = "Get all faculty", response = List.class)
    @GetMapping({"/getAllFaculty"})
    public List<Faculty> getAllFaculty() {
        return this.facultyService.getAllFaculty();
    }

    @CrossOrigin
    @ApiOperation(value = "Search faculty by id", response = Faculty.class)
    @GetMapping({"/searchFacultyById/{id}"})
    public Faculty getFacultyById(@RequestHeader("Authorization") final String token, @PathVariable("id") final int id) throws InvalidTokenException, FacultyNotFoundException {
        return this.facultyService.getFacultyById(id, token);
    }

    @PostMapping({"/addFaculty"})
    public ResponseEntity<Faculty> addFaculty(@RequestHeader("Authorization") final String token, @RequestBody Faculty faculty) throws InvalidTokenException {
        Faculty savedFaculty = facultyService.addFaculty(faculty, token);
        return new ResponseEntity<>(savedFaculty, HttpStatus.CREATED);
    }
}
