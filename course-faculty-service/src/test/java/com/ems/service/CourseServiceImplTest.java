package com.ems.service;

import com.ems.dao.CourseRepository;
import com.ems.dao.FacultyRepository;
import com.ems.entity.Course;
import com.ems.entity.CourseDto;
import com.ems.entity.Faculty;
import com.ems.entity.TokenValid;
import com.ems.event.CourseAddedEvent;
import com.ems.exception.CourseNotFoundException;
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
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private AuthFeign authFeign;
    @Mock
    private KafkaTemplate<String, CourseAddedEvent> kafkaTemplate;
    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getAllCourses() {
        when(courseRepository.findAll()).thenReturn(someListOfCourses());
        List<Course> courseList = courseService.getAllCourses();
        verify(courseRepository, times(1)).findAll();
        assertNotNull(courseList);
        assertFalse(courseList.isEmpty());
    }

    @Test
    void getCourseById_ValidToken() throws InvalidTokenException, CourseNotFoundException {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someValidTokenResponse(), HttpStatus.OK));;
        when(courseRepository.findById(any())).thenReturn(Optional.of(someCourse()));
        Course course = courseService.getCourseById(1, "validToken");
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(courseRepository, times(1)).findById(any());
        assertNotNull(course);
    }

    @Test
    void getCourseById_InvalidToken() {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someInvalidTokenResponse(), HttpStatus.OK));
        assertThrows(InvalidTokenException.class, () -> courseService.getCourseById(1, "invalidToken"));
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(courseRepository, never()).findById(any());
    }

    @Test
    void addCourse_ValidToken() throws InvalidTokenException {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someValidTokenResponse(), HttpStatus.OK));;
        when(courseRepository.save(any())).thenReturn(someCourse());
        when(facultyRepository.findById(any())).thenReturn(Optional.of(someFaculty()));
        CourseDto course = new CourseDto( "Mathematics", 6, "9AM - 10AM",1);
        Course addedCourse = courseService.addCourse(course, "validToken");
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(courseRepository, times(1)).save(any());
        assertNotNull(addedCourse);
    }

    @Test
    void addCourse_InvalidToken() {
        when(authFeign.getValidityForAdmin(any())).thenReturn(new ResponseEntity<>(someInvalidTokenResponse(), HttpStatus.OK));
        CourseDto course = new CourseDto( "Mathematics", 6, "9AM - 10AM",1);
        assertThrows(InvalidTokenException.class, () -> courseService.addCourse(course, "invalidToken"));
        verify(authFeign, times(1)).getValidityForAdmin(any());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void isCourseAvailableForRegistration_ValidToken() throws InvalidTokenException, CourseNotFoundException {
        when(authFeign.getValidityForStudent(any())).thenReturn(new ResponseEntity<>(someValidTokenResponse(), HttpStatus.OK));
        when(courseRepository.findById(any())).thenReturn(Optional.of(someCourse()));
        Course availableCourse = courseService.isCourseAvailableForRegistration(1, "validToken");
        verify(authFeign, times(1)).getValidityForStudent(any());
        verify(courseRepository, times(1)).findById(any());
        assertNotNull(availableCourse);
    }

    @Test
    void isCourseAvailableForRegistration_InvalidToken() {
        when(authFeign.getValidityForStudent(any())).thenReturn(new ResponseEntity<>(someInvalidTokenResponse(), HttpStatus.OK));
        assertThrows(InvalidTokenException.class, () -> courseService.isCourseAvailableForRegistration(1, "invalidToken"));
        verify(authFeign, times(1)).getValidityForStudent(any());
        verify(courseRepository, never()).findById(any());
    }

    private List<Course> someListOfCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "Mathematics", 6, "9AM - 10AM"));
        courseList.add(new Course(2, "English", 5, "10AM - 11AM"));
        return courseList;
    }

    private TokenValid someValidTokenResponse() {
        return new TokenValid("uid", "name", true);
    }

    private TokenValid someInvalidTokenResponse() {
        return new TokenValid("uid", "name", false);
    }

    private Course someCourse() {
        return new Course(1, "Mathematics", 6, "9AM - 10AM",new Faculty());
    }

    private Faculty someFaculty() {
        return new Faculty(1, "Vikram", "9999999999", new ArrayList<>());
    }
}