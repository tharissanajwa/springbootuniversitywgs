package com.bootcamp.springbootuniversitywgs.dto.responses;

import com.bootcamp.springbootuniversitywgs.models.Major;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MajorResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    public MajorResponse(Major major) {
        this.id = major.getId();
        this.name = major.getName();
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
