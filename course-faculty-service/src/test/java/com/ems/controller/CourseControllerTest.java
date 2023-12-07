package com.ems.controller;

import com.ems.entity.Course;
import com.ems.entity.CourseDto;
import com.ems.exception.CourseNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Mock
    private CourseService courseService;
    @InjectMocks
    private CourseController courseController;

    @Test
    void getAllCourses() {
        when(courseService.getAllCourses()).thenReturn(someListOfCourses());
        List<Course> courseList = courseController.getAllCourses();
        verify(courseService, times(1)).getAllCourses();
        assertNotNull(courseList);
        assertFalse(courseList.isEmpty());
    }

    @Test
    void getCourseById() throws InvalidTokenException, CourseNotFoundException {
        when(courseService.getCourseById(anyInt(), any())).thenReturn(someCourse());
        Course course = courseController.getCourseById("validToken", 1);
        verify(courseService, times(1)).getCourseById(anyInt(), any());
        assertNotNull(course);
    }

    @Test
    void isCourseAvailableForRegistration() throws InvalidTokenException, CourseNotFoundException {
        when(courseService.isCourseAvailableForRegistration(anyInt(), any())).thenReturn(someCourse());
        Course availableCourse = courseController.isCourseAvailableForRegistration("validToken", 1);
        verify(courseService, times(1)).isCourseAvailableForRegistration(anyInt(), any());
        assertNotNull(availableCourse);
    }

    @Test
    void addCourse() throws InvalidTokenException {
        when(courseService.addCourse(any(), any())).thenReturn(someCourse());
        CourseDto course = new CourseDto("Mathematics", 2, "10AM - 12PM",1);
        ResponseEntity<Course> response = courseController.addCourse("validToken", course);
        verify(courseService, times(1)).addCourse(any(), any());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private List<Course> someListOfCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "Social", 2, "10AM - 12PM"));
        courseList.add(new Course(2, "Science", 1, "09AM - 12PM"));
        return courseList;
    }

    private Course someCourse() {
        return new Course(1, "Mathematics", 8, "10AM - 12PM");
    }
}