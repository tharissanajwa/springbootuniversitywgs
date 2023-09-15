package com.bootcamp.springbootuniversitywgs.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("major_id")
    private Long majorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }
}
