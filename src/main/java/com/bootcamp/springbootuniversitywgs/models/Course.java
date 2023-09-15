package com.bootcamp.springbootuniversitywgs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

// Ini adalah kelas Course yang merepresentasikan data mata kuliah ke database
@Entity
@Table(name = "courses")
public class Course extends BaseModel {
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourses;

    public Course() {
        // Konstruktor default
    }

    // Metode getter setter untuk field-field yg dibutuhkan
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
