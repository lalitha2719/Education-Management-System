package com.ems;

import com.ems.event.CourseAddedEvent;
import com.ems.event.FacultyRegisteredEvent;
import com.ems.event.StudentRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "studentNotificationTopic")
    public void handleStudentRegisteredNotification(StudentRegisteredEvent studentRegisteredEvent) {
        log.info("Received Notification for studentId - {} and studentName - {}",
                studentRegisteredEvent.getStudentId(), studentRegisteredEvent.getStudentName());
    }

    @KafkaListener(topics = "facultyNotificationTopic")
    public void handleFacultyNotificationTopic(FacultyRegisteredEvent facultyRegisteredEvent) {
        log.info("Received Notification for facultyId - {} and facultyName - {}",
                facultyRegisteredEvent.getFacultyId(), facultyRegisteredEvent.getFacultyName());
    }

    @KafkaListener(topics = "courseNotificationTopic")
    public void handleCourseNotificationTopic(CourseAddedEvent courseAddedEvent) {
        log.info("Course {} is assigned to faculty {}",
                courseAddedEvent.getCourseName(), courseAddedEvent.getFacultyName());
    }
}
