package com.bootcamp.springbootuniversitywgs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

// Ini adalah kelas Major yang merepresentasikan data jurusan universitas ke database
@Entity
@Table(name = "majors")
public class Major extends BaseModel {
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "major")
    private List<Student> students;

    public Major() {
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
