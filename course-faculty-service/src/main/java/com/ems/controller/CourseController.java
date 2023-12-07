package com.ems.controller;

import com.ems.entity.Course;
import com.ems.entity.CourseDto;
import com.ems.exception.CourseNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(produces = "application/json", value = "Manages Courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @CrossOrigin
    @ApiOperation(value = "Get all courses", response = List.class)
    @GetMapping({"/getAllCourses"})
    public List<Course> getAllCourses() {
        return this.courseService.getAllCourses();
    }

    @CrossOrigin
    @ApiOperation(value = "Search course by id", response = Course.class)
    @GetMapping({"/searchCourseById/{id}"})
    public Course getCourseById(@RequestHeader("Authorization") final String token, @PathVariable("id") final int id) throws InvalidTokenException, CourseNotFoundException {
        return this.courseService.getCourseById(id, token);
    }

    @CrossOrigin
    @ApiOperation(value = "Search course by id", response = Course.class)
    @GetMapping({"/searchCourseForStudent/{courseId}"})
    public Course isCourseAvailableForRegistration(@RequestHeader("Authorization") final String token, @PathVariable("courseId") final int courseId) throws InvalidTokenException, CourseNotFoundException {
        return this.courseService.isCourseAvailableForRegistration(courseId, token);
    }

    @PostMapping({"/addCourse"})
    public ResponseEntity<Course> addCourse(@RequestHeader("Authorization") final String token, @RequestBody CourseDto course) throws InvalidTokenException {
        Course savedCourse = courseService.addCourse(course, token);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }
}
