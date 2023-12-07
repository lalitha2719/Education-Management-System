package com.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int facultyId;

    public Faculty(int facultyId, String empName, String phno, List<Course> courses) {
        super();
        this.facultyId = facultyId;
        this.empName = empName;
        this.phno = phno;
        this.courses = courses;
    }


    @ApiModelProperty(name = "empName", example = "Shubham")
    private String empName;

    @ApiModelProperty(name = "phno", example = "9999999999")
    private String phno;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private List<Course> courses;

    public Faculty() {

    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
