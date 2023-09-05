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

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses;

    public Student() {
        // Constructor default
    }

    public Student(String name, Major major) {
        this.name = name;
        this.major = major;
    }

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
