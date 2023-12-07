package com.ems.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDto {

    @ApiModelProperty(name = "courseName", example = "Data Science")
    private String courseName;
    @ApiModelProperty(name = "duration", example = "3 months")
    private int duration;
    @ApiModelProperty(name = "schedule", example = "mon-fri 10AM-12-PM")
    private String schedule;
    @ApiModelProperty(name = "facultyId", example = "11")
    private Integer facultyId;
}
