package com.ems.controller;

import com.ems.entity.Course;
import com.ems.entity.Faculty;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.service.FacultyService;
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
class FacultyControllerTest {

    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void getAllFaculty() {
        when(facultyService.getAllFaculty()).thenReturn(someListOfFaculty());
        List<Faculty> facultyList = facultyController.getAllFaculty();
        verify(facultyService, times(1)).getAllFaculty();
        assertNotNull(facultyList);
        assertFalse(facultyList.isEmpty());
    }

    @Test
    void getFacultyById() throws InvalidTokenException, FacultyNotFoundException {
        when(facultyService.getFacultyById(anyInt(), any())).thenReturn(someFaculty());
        Faculty faculty = facultyController.getFacultyById("validToken", 1);
        verify(facultyService, times(1)).getFacultyById(anyInt(), any());
        assertNotNull(faculty);
    }

    @Test
    void addFaculty() throws InvalidTokenException {
        when(facultyService.addFaculty(any(), any())).thenReturn(someFaculty());
        Faculty faculty = new Faculty(1, "John Doe", "9999999", someListOfCourses());
        ResponseEntity<Faculty> response = facultyController.addFaculty("validToken", faculty);
        verify(facultyService, times(1)).addFaculty(any(), any());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private List<Faculty> someListOfFaculty() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty(1, "John Doe", "8888888", someListOfCourses()));
        facultyList.add(new Faculty(2, "Jane Smith", "777777777", someListOfCourses()));
        return facultyList;
    }

    private Faculty someFaculty() {
        return new Faculty(1, "John Doe", "47386386", someListOfCourses());
    }

    private List<Course> someListOfCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "Social", 2, "10AM - 12PM"));
        courseList.add(new Course(2, "Science", 1, "09AM - 12PM"));
        return courseList;
    }
}