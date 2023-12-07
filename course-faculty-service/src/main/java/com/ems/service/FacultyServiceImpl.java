package com.ems.service;

import com.ems.dao.FacultyRepository;
import com.ems.entity.Faculty;
import com.ems.event.FacultyRegisteredEvent;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.restclients.AuthFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private KafkaTemplate<String, FacultyRegisteredEvent> kafkaTemplate;

    @Override
    public List<Faculty> getAllFaculty() {
        final List<Faculty> faculty = this.facultyRepo.findAll();
        return faculty;
    }

    @Override
    public Faculty getFacultyById(int id, String token) throws InvalidTokenException, FacultyNotFoundException {
        Faculty faculty = null;
        if ((this.authFeign.getValidityForAdmin(token).getBody()).isValid()) {
            faculty = this.facultyRepo.findById(id).orElseThrow(() -> new FacultyNotFoundException("faculty Not Found"));
            return faculty;
        }
        throw new InvalidTokenException("Invalid Credentials");
    }

    @Override
    public Faculty addFaculty(Faculty faculty, String token) throws InvalidTokenException {
        if ((this.authFeign.getValidityForAdmin(token).getBody()).isValid()) {
            faculty = this.facultyRepo.save(faculty);

            kafkaTemplate.send("facultyNotificationTopic", FacultyRegisteredEvent.builder()
                    .facultyId(faculty.getFacultyId())
                    .facultyName(faculty.getEmpName())
                    .build());

            return faculty;
        }
        throw new InvalidTokenException("Invalid Credentials");
    }

}
