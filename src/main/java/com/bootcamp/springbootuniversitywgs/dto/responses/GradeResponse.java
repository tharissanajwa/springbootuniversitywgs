package com.bootcamp.springbootuniversitywgs.dto.responses;

import com.bootcamp.springbootuniversitywgs.models.Grade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GradeResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("course_name")
    private String courseName;
    @JsonProperty("assignment_name")
    private String name;
    @JsonProperty("grade")
    private Integer grade;

    public GradeResponse(Grade grade) {
        this.id = grade.getId();
        this.studentName = grade.getStudentCourse().getStudent().getName();
        this.courseName = grade.getStudentCourse().getCourse().getName();
        this.name = grade.getName();
        this.grade = grade.getGrade();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
