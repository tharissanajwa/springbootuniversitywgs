package com.bootcamp.springbootuniversitywgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
