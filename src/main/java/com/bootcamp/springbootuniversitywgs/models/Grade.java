package com.bootcamp.springbootuniversitywgs.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Ini adalah kelas Grade yang merepresentasikan data nilai ke database
@Entity
@Table(name = "grades")
public class Grade extends BaseModel {
    private String name;
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "student_course_id")
    private StudentCourse studentCourse;

    public Grade() {
        // Constructor default
    }

    // Konstruktor untuk membuat objek tugas(nilai)
    public Grade(String name, Integer grade, StudentCourse studentCourse) {
        this.name = name;
        this.grade = grade;
        this.studentCourse = studentCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }
}
