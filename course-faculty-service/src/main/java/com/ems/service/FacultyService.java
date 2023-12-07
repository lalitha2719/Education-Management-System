package com.ems.service;

import java.util.List;

import com.ems.entity.Faculty;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;

public interface FacultyService {
	
	List<Faculty> getAllFaculty();
	Faculty getFacultyById(final int id, final String token) throws InvalidTokenException, FacultyNotFoundException;
	Faculty addFaculty(Faculty course, final String token) throws InvalidTokenException;


}
