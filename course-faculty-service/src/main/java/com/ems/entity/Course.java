package com.ems.entity;

import lombok.Builder;

import javax.persistence.*;


@Entity
@Builder
@SequenceGenerator(name = "id_gen", sequenceName = "id_gen",  initialValue = 3)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen")
    private int courseId;
    private String courseName;
    private int duration;
    private String schedule;
    @ManyToOne
    @JoinColumn(name = "facultyId")
    private Faculty faculty;


    public Course() {

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course(int courseId, String courseName, int duration, String schedule, Faculty faculty) {
        super();
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.schedule = schedule;
        this.faculty = faculty;
    }

    public Course(int courseId, String courseName, int duration, String schedule) {
        super();
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.schedule = schedule;
    }
}
