package com.bootcamp.springbootuniversitywgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentCourseRequest {
    @JsonProperty("student_id")
    private Long studentId;
    @JsonProperty("course_id")
    private Long courseId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
