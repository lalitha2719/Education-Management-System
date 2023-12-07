package com.ems.service;

import java.util.List;

import com.ems.entity.Course;
import com.ems.entity.CourseDto;
import com.ems.exception.CourseNotFoundException;
import com.ems.exception.InvalidTokenException;

public interface CourseService {
	
    List<Course> getAllCourses();
    Course getCourseById(final int id, final String token) throws InvalidTokenException, CourseNotFoundException;
    Course addCourse(CourseDto course, final String token) throws InvalidTokenException;

    Course isCourseAvailableForRegistration(int id, String token) throws InvalidTokenException, CourseNotFoundException;
}
