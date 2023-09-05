package com.bootcamp.springbootuniversitywgs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

// Ini adalah kelas Student yang merepresentasikan data mahasiswa ke database
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id mahasiswa dijadikan auto increment
    private String name; // Nama mahasiswa
    @JsonIgnore
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted; // Status mahasiswa

    // Id jurusan sebagai relasi dengan model jurusan, dan memiliki hubungan banyak ke satu
    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;

    @JsonIgnore // Json ignore fungsinya untuk menghentikan infinity loop
    // Hubungan satu-ke-banyak dengan kelas Student Course, dan di-mapped oleh properti 'student' dalam studentCourse
    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses;

    public Student() {
        // Constructor default
    }

    // Konstruktor untuk membuat objek mahasiswa
    public Student(String name, Major major) {
        this.name = name;
        this.major = major;
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

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
