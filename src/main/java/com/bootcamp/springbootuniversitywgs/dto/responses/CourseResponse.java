package com.bootcamp.springbootuniversitywgs.dto.responses;

import com.bootcamp.springbootuniversitywgs.models.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
