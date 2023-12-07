package com.ems.service;

import com.ems.dao.CourseRepository;
import com.ems.dao.FacultyRepository;
import com.ems.entity.Course;
import com.ems.entity.CourseDto;
import com.ems.entity.Faculty;
import com.ems.event.CourseAddedEvent;
import com.ems.exception.CourseNotFoundException;
import com.ems.exception.FacultyNotFoundException;
import com.ems.exception.InvalidTokenException;
import com.ems.restclients.AuthFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private KafkaTemplate<String, CourseAddedEvent> kafkaTemplate;

    @Override
    public List<Course> getAllCourses() {
        final List<Course> courses = this.courseRepo.findAll();
        return courses;
    }

    @Override
    public Course getCourseById(final int id, final String token) throws InvalidTokenException, CourseNotFoundException {
        Course course = null;
        if ((this.authFeign.getValidityForAdmin(token).getBody()).isValid()) {
            course = this.courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException("Course Not Found"));
            return course;
        }
        throw new InvalidTokenException("Invalid Credentials");
    }

    @Override
    public Course addCourse(CourseDto courseDto, String token) throws InvalidTokenException {
        if ((this.authFeign.getValidityForAdmin(token).getBody()).isValid()) {
            Faculty faculty = facultyRepo.findById(courseDto.getFacultyId()).orElseThrow(() -> new FacultyNotFoundException("Faculty with given ID not found"));
            Course course= Course.builder().courseName(courseDto.getCourseName())
                    .duration(courseDto.getDuration())
                    .faculty(faculty)
                    .schedule(courseDto.getSchedule()).build();
            course = this.courseRepo.save(course);

            kafkaTemplate.send("courseNotificationTopic", CourseAddedEvent.builder()
                    .courseName(course.getCourseName())
                    .facultyName(course.getFaculty().getEmpName())
                    .build());

            return course;
        }
        throw new InvalidTokenException("Invalid Credentials");
    }

    @Override
    public Course isCourseAvailableForRegistration(int id, String token) throws InvalidTokenException, CourseNotFoundException {
        Course course = null;
        if ((this.authFeign.getValidityForStudent(token).getBody()).isValid()) {
            course = this.courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException("Course Not Found"));
            return course;
        }
        throw new InvalidTokenException("Invalid Credentials");
    }
}
