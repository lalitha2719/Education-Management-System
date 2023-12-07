package com.ems.service;

import com.ems.dao.FacultyRepository;
import com.ems.entity.Course;
import com.ems.entity.Faculty;
import com.ems.entity.TokenValid;
import com.ems.event.FacultyRegisteredEvent;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.restclients.AuthFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private AuthFeign authFeign;
    @Mock
    private KafkaTemplate<String, FacultyRegisteredEvent> kafkaTemplate;
    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void getAllFaculty() {
        when(facultyRepository.findAll()).thenReturn(someListOfFaculty());
        List<Faculty> facultyList = facultyService.getAllFaculty();
        verify(facultyRepository, times(1)).findAll();
        assertNotNull(facultyList);
        assertFalse(facultyList.isEmpty());
    }

    @Test
    void getFacultyById_ValidToken() throws InvalidTokenException, FacultyNotFoundException {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someValidTokenResponse(), HttpStatus.OK));
        when(facultyRepository.findById(any())).thenReturn(Optional.of(someFaculty()));
        Faculty faculty = facultyService.getFacultyById(1, "validToken");
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(facultyRepository, times(1)).findById(any());
        assertNotNull(faculty);
    }

    @Test
    void getFacultyById_InvalidToken() {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someInvalidTokenResponse(), HttpStatus.OK));
        assertThrows(InvalidTokenException.class, () -> facultyService.getFacultyById(1, "invalidToken"));
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(facultyRepository, never()).findById(any());
    }

    @Test
    void addFaculty_ValidToken() throws InvalidTokenException, FacultyNotFoundException {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someValidTokenResponse(), HttpStatus.OK));
        when(facultyRepository.save(any())).thenReturn(someFaculty());
        Faculty faculty = facultyService.addFaculty(someFaculty(), "token");
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(facultyRepository, times(1)).save(any());
        assertNotNull(faculty);
    }

    @Test
    void addFaculty_InvalidToken() {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someInvalidTokenResponse(), HttpStatus.OK));
        assertThrows(InvalidTokenException.class, () -> facultyService.addFaculty(someFaculty(), "invalidToken"));
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(facultyRepository, never()).save(any());
    }

    private List<Faculty> someListOfFaculty() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty(1, "John Doe", "9999999", someListOfCourses()));
        facultyList.add(new Faculty(2, "Jane Smith", "978785764", someListOfCourses()));
        return facultyList;
    }

    private TokenValid someValidTokenResponse() {
        return new TokenValid("uid", "name", true);
    }

    private TokenValid someInvalidTokenResponse() {
        return new TokenValid("uid", "name", false);
    }

    private Faculty someFaculty() {
        return new Faculty(1, "John Doe", "9999999", someListOfCourses());
    }

    private List<Course> someListOfCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "Social", 2, "10AM - 12PM"));
        courseList.add(new Course(2, "Science", 1, "09AM - 12PM"));
        return courseList;
    }
}