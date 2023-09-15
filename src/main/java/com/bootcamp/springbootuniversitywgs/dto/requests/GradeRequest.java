package com.bootcamp.springbootuniversitywgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeRequest {
    @JsonProperty("student_course_id")
    private Long studentCourseId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("grade")
    private Integer grade;

    public Long getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(Long studentCourseId) {
        this.studentCourseId = studentCourseId;
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
