package com.bootcamp.springbootuniversitywgs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

// Ini adalah kelas Course yang merepresentasikan data mata kuliah ke database
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; // Id matkul dijadikan auto increment
    private String name; // Nama matkul
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted; // Status matkul

    @JsonIgnore
    // Hubungan satu-ke-banyak dengan kelas Student Course, dan di-mapped oleh properti 'course' dalam studentCourse
    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourses;

    public Course() {
        // Konstruktor default
    }

    // Konstruktor untuk membuat objek matkul
    public Course(String name) {
        this.name = name;
    }

    // Metode getter setter untuk field-field yg dibutuhkan
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
