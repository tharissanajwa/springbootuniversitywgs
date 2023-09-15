package com.bootcamp.springbootuniversitywgs.dto.responses;

import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentCourseResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("course_name")
    private String courseName;

    public StudentCourseResponse(StudentCourse studentCourse) {
        this.id = studentCourse.getId();
        this.studentName = studentCourse.getStudent().getName();
        this.courseName = studentCourse.getCourse().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
