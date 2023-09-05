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

// Ini adalah kelas Major yang merepresentasikan data jurusan universitas ke database
@Entity
@Table(name = "majors")
public class Major {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; // Id jurusan dijadikan auto increment
    private String name; // Nama jurusan
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted; // Status jurusan

    @JsonIgnore // Json ignore fungsinya untuk menghentikan infinity loop
    // Hubungan satu-ke-banyak dengan kelas Student, dan di-mapped oleh properti 'major' dalam student
    @OneToMany(mappedBy = "major")
    private List<Student> students;

    public Major() {
        // Konstruktor default
    }

    // Konstruktor untuk membuat objek jurusan
    public Major(String name) {
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
